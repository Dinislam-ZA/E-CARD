package example.com.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import example.com.data.db.model.User
import example.com.data.dto.AcceptFriendRequest
import example.com.data.dto.AddFriendRequest
import example.com.data.dto.LoginRequest
import example.com.data.dto.RegistrationRequest
import example.com.services.UserService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt
import java.io.File
import java.util.*

fun Application.configureRouting() {

    val userService by inject<UserService>()

    val config = environment.config
    val jwtAudience = config.property("jwt.audience").getString()
    val jwtDomain = config.property("jwt.domain").getString()
    val jwtSecret = config.property("jwt.secret").getString()
    routing {
        post("/login") {
            val loginRequest = call.receive<LoginRequest>()

            val user = userService.findUserByName(loginRequest.username)
            if (user != null && BCrypt.checkpw(loginRequest.password, user.password)) {
                val token = JWT.create()
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .withClaim("username", user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 600000 * 12))
                    .sign(Algorithm.HMAC256(jwtSecret))

                call.respond(mapOf("token" to token, "id" to user.id))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
            }
        }

        post("/register") {
            val registerRequest = call.receive<RegistrationRequest>()

            val newUser = User(
                username = registerRequest.username,
                password = registerRequest.password,
                money = 1000uL,
            )
            userService.addUser(newUser)

            call.respond(HttpStatusCode.Created, "User registered successfully")
        }
        authenticate {
            get("/users") {
                call.respond(HttpStatusCode.OK, userService.getAllUsers())
            }

            post("/friends/add") {
                val friendAddRequest = call.receive<AddFriendRequest>()
                userService.addFriendRequest(friendAddRequest.user1, friendAddRequest.user2)
                call.respond(HttpStatusCode.OK, "Friendship request was sent")
            }

            post("/friends/accept") {
                val friendAcceptRequest = call.receive<AcceptFriendRequest>()
                val res = userService.acceptFriendRequest(friendAcceptRequest.user1, friendAcceptRequest.user2)
                if (res) call.respond(
                    HttpStatusCode.OK,
                    "Friendship accepted"
                ) else call.respond(HttpStatusCode.BadRequest, "Can't accept friendship")
            }

            get("/friends/{userId}") {
                val userId = call.parameters["userId"]?.toIntOrNull()

                if (userId == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                    return@get
                }

                val friends = userService.getFriendsList(userId)

                call.respond(HttpStatusCode.OK, friends)
            }

            // TODO
            get("/friends/requests/{userId}") {
                val userId = call.parameters["userId"]?.toIntOrNull()

                if (userId == null){
                    call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                    return@get
                }


            }

            post("/upload-avatar") {
                val multipart = call.receiveMultipart()
                var fileName: String? = null
                var userId: Int? = null

                multipart.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            if (part.name == "userId") {
                                userId = part.value.toIntOrNull()
                            }
                        }

                        is PartData.FileItem -> {
                            val ext = part.originalFileName?.let { it1 -> File(it1).extension }
                            fileName = "avatars/${System.currentTimeMillis()}.$ext"
                            val file = File("uploads/$fileName")

                            part.streamProvider().use { inputStream ->
                                file.outputStream().buffered().use { outputStream ->
                                    inputStream.copyTo(outputStream)
                                }
                            }
                        }

                        else -> {}
                    }
                    part.dispose()
                }

                if (fileName != null && userId != null && userService.updateUser(
                        id = userId!!,
                        avatarUri = fileName
                    )
                ) {
                    call.respondText("File uploaded: $fileName", status = HttpStatusCode.OK)
                } else {
                    call.respondText("File upload failed", status = HttpStatusCode.InternalServerError)
                }
            }

            get("/uploads/avatars/{fileName}") {
                val fileName = call.parameters["fileName"]
                val file = File("uploads/avatars/$fileName")
                if (file.exists()) {
                    call.respondFile(file)
                } else {
                    call.respond(HttpStatusCode.NotFound, "File not found")
                }
            }
        }
    }
}

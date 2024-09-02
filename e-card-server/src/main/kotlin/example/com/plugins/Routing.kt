package example.com.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import example.com.model.User
import example.com.model.UserRepository
import example.com.model.UserRepositoryImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun Application.configureRouting(userRepository: UserRepository) {
    val config = environment.config
    val jwtAudience = config.property("jwt.audience").getString()
    val jwtDomain = config.property("jwt.domain").getString()
    val jwtSecret = config.property("jwt.secret").getString()
    routing {
        post ("/login") {
            val loginRequest = call.receive<User>()

            val user = userRepository.findUserByName(loginRequest.username)
            if (user != null && BCrypt.checkpw(loginRequest.password, user.password)) {
                val token = JWT.create()
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .withClaim("username", user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 600000*12))
                    .sign(Algorithm.HMAC256(jwtSecret))

                call.respond(mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
            }
        }

        post ("/register") {
            val registerRequest = call.receive<User>()

            val newUser = User(username = registerRequest.username, password = registerRequest.password)
            userRepository.addUser(newUser)

            call.respond(HttpStatusCode.Created, "User registered successfully")
        }
        authenticate {
            get("/users") {
                call.respond(HttpStatusCode.OK, userRepository.allUsers())
            }
        }
    }
}

package example.com.plugins

import example.com.model.UserRepositoryImpl
import example.com.model.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    connectToPostgres(embedded = false)

    transaction {
        SchemaUtils.create(Users)
    }
    
//    routing {
//        get("/users") {
//            val repository = UserRepositoryImpl()
//            call.respond(HttpStatusCode.OK, repository.allUsers())
//        }
//    }
}

fun Application.connectToPostgres(embedded: Boolean) {
    if (embedded) {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "root", password = "")
    } else {
        val url = environment.config.property("db.url").getString()
        val user = environment.config.property("db.user").getString()
        val password = environment.config.property("db.password").getString()

        Database.connect(url, driver = "org.postgresql.Driver", user = user, password = password)
    }
}

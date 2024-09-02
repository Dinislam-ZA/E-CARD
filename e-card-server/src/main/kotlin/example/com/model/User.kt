package example.com.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class User(
    val id: Int? = null,
    val username: String,
    val password: String
)

object Users : IntIdTable() {
    val username = varchar("username", 50).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
}

class UserDao(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, UserDao>(Users)

    var username by Users.username
    var passwordHash by Users.passwordHash
}
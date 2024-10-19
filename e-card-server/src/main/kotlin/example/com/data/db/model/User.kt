package example.com.data.db.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int? = null,
    val username: String,
    val password: String,
    val money: ULong,
    val avatarUri: String? = null,
)

object Users : IntIdTable() {
    val username = varchar("username", 50).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)
    val money = ulong("money")
    val avatarUri = varchar("avatarUri", 100).nullable()
}

object Friends : Table() {
    val user1 = reference("user1", Users)
    val user2 = reference("user2", Users)
    val status = varchar("status", 10)

    override val primaryKey = PrimaryKey(user1, user2)
}

enum class FriendshipStatus(val status:String){
    Pending("pending"),
    Accepted("accepted")
}

class UserDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDao>(Users)

    var username by Users.username
    var passwordHash by Users.passwordHash
    var money by Users.money
    var avatarUri by Users.avatarUri
}
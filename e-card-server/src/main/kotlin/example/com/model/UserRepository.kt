package example.com.model

import example.com.suspendTransaction
import org.mindrot.jbcrypt.BCrypt

interface UserRepository {
    suspend fun allUsers(): List<User>
    suspend fun findUserById(id: Int): User?
    suspend fun findUserByName(name: String): User?
    suspend fun addUser(user: User)
    suspend fun removeUser(id: Int): Boolean
}

class UserRepositoryImpl : UserRepository {
    override suspend fun allUsers(): List<User> = suspendTransaction {
        UserDao.all().map(::userDaoToModel)
    }

    override suspend fun findUserById(id: Int) = suspendTransaction {
        UserDao
            .find { (Users.id eq id) }
            .limit(1)
            .map(::userDaoToModel)
            .firstOrNull()
    }

    override suspend fun findUserByName(name: String) = suspendTransaction {
        UserDao
            .find { (Users.username eq name) }
            .limit(1)
            .map(::userDaoToModel)
            .firstOrNull()
    }

    override suspend fun addUser(user: User): Unit = suspendTransaction {
        UserDao.new {
            username = user.username
            passwordHash = BCrypt.hashpw(user.password, BCrypt.gensalt())
        }
    }

    override suspend fun removeUser(id: Int): Boolean = suspendTransaction {
        val user = UserDao.findById(id)
        if (user != null) {
            user.delete()
            true
        } else {
            false
        }
    }

}
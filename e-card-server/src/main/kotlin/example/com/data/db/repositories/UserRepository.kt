package example.com.data.db.repositories

import example.com.data.*
import example.com.data.db.model.*
import example.com.suspendTransaction
import org.jetbrains.exposed.sql.*
import org.mindrot.jbcrypt.BCrypt

interface UserRepository {
    suspend fun allUsers(): List<User>
    suspend fun findUserById(id: Int): User?
    suspend fun findUserByName(name: String): User?
    suspend fun addUser(user: User)
    suspend fun removeUser(id: Int): Boolean
    suspend fun updateUser(id: Int, username: String? = null, money: ULong? = null, avatarUri: String? = null): Boolean
    suspend fun addFriend(user1Id: Int, user2Id: Int)
    suspend fun acceptFriend(user1Id: Int, user2Id: Int): Boolean
    suspend fun friendsList(userId: Int): List<User>
}

class UserRepositoryImpl : UserRepository {
    override suspend fun allUsers(): List<User> = suspendTransaction {
        UserDao.all().map(::userDaoToModel)
    }


    override suspend fun findUserById(id: Int) = suspendTransaction {
        UserDao.findById(id)?.let { userDaoToModel(it) }
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
            money = user.money
            user.avatarUri?.let {
                avatarUri = it
            }
        }
    }

    override suspend fun updateUser(id: Int, username: String?, money: ULong?, avatarUri: String?): Boolean =
        suspendTransaction {
            val user = UserDao.findById(id)
            if (user != null) {
                username?.let { user.username = it }
                avatarUri?.let { user.avatarUri = it }
                money?.let { user.money = it }
                true
            } else {
                false
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

    override suspend fun addFriend(user1Id: Int, user2Id: Int): Unit = suspendTransaction {
        Friends.insert {
            it[user1] = user1Id
            it[user2] = user2Id
            it[status] = FriendshipStatus.Pending.status
        }
    }

    override suspend fun acceptFriend(user1Id: Int, user2Id: Int) = suspendTransaction {
        val updatedRows =
            Friends.update({ (Friends.user1 eq user1Id) and (Friends.user2 eq user2Id) and (Friends.status eq FriendshipStatus.Pending.status) }) {
                it[status] = FriendshipStatus.Accepted.status
            }

        updatedRows > 0
    }

    override suspend fun friendsList(userId: Int) = suspendTransaction {
        val friends = mutableListOf<Int>()
        Friends.select {
            (Friends.user1 eq userId) or (Friends.user2 eq userId)
        }.mapNotNull {
            val friendId = if (it[Friends.user1].value == userId) it[Friends.user2].value else it[Friends.user1].value
            friends.add(friendId)
        }
        friends.toList()
        UserDao.find { Users.id inList friends }
            .map(::userDaoToModel)
    }

}
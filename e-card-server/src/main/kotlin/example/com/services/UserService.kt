package example.com.services

import example.com.data.db.model.User
import example.com.data.db.repositories.UserRepository

interface UserService {
    suspend fun getAllUsers(): List<User>
    suspend fun findUserByName(name: String): User?
    suspend fun findUserById(id: Int): User?
    suspend fun addUser(user: User): Boolean
    suspend fun addFriendRequest(user1Id: Int, user2Id: Int)
    suspend fun acceptFriendRequest(user1Id: Int, user2Id: Int): Boolean
    suspend fun getFriendsList(userId: Int): List<User>
    suspend fun updateUser(id: Int, username: String? = null, money: ULong? = null, avatarUri: String? = null): Boolean
}

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun getAllUsers(): List<User> {
        return userRepository.allUsers()
    }

    override suspend fun findUserByName(name: String): User? {
        return userRepository.findUserByName(name)
    }

    override suspend fun findUserById(id: Int): User? {
        return userRepository.findUserById(id)
    }

    override suspend fun addUser(user: User): Boolean {
        if (user.username.isBlank()) {
            throw IllegalArgumentException("Username cannot be blank")
        }
        if (userRepository.findUserByName(user.username) != null) {
            throw IllegalArgumentException("Username already exists")
        }

        userRepository.addUser(user)
        return true
    }

    override suspend fun addFriendRequest(user1Id: Int, user2Id: Int) {
        if (userRepository.findUserById(user2Id) == null) {
            throw IllegalArgumentException("User not found")
        }
        userRepository.addFriend(user1Id, user2Id)
    }

    override suspend fun acceptFriendRequest(user1Id: Int, user2Id: Int): Boolean {
        return userRepository.acceptFriend(user1Id, user2Id)
    }

    override suspend fun getFriendsList(userId: Int): List<User> {
        return userRepository.friendsList(userId)
    }

    override suspend fun updateUser(id: Int, username: String?, money: ULong?, avatarUri: String?): Boolean {
        return userRepository.updateUser(id, username, money, avatarUri)
    }

}

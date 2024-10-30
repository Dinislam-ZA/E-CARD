package example.com.services

import example.com.data.db.model.FriendInviteNotification
import example.com.data.db.model.User
import example.com.data.db.repositories.UserRepository
import example.com.data.redis.FriendshipRedisManager
import kotlinx.serialization.json.Json

interface UserService {
    suspend fun getAllUsers(): List<User>
    suspend fun findUserByName(name: String): User?
    suspend fun findUserById(id: Int): User?
    suspend fun addUser(user: User): Boolean
    suspend fun addFriendRequest(user1Id: Int, user2Id: Int)
    suspend fun acceptFriendRequest(user1Id: Int, user2Id: Int)
    suspend fun removeFriend(user1Id: Int, user2Id: Int)
    suspend fun subscribeToFriendshipRequests(user2Id: Int, onMessage: (String) -> Unit)
    suspend fun getFriendsList(userId: Int): List<User>
    suspend fun getFriendRequests(userId: Int): List<String>
    suspend fun getUsersRequestedToBeFriend(userId: Int): List<Int>
    suspend fun updateUser(id: Int, username: String? = null, money: ULong? = null, avatarUri: String? = null): Boolean
}

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val friendshipService: FriendshipRedisManager
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

    override suspend fun addFriendRequest(sender: Int, recipient: Int) {
        if (userRepository.findUserById(recipient) == null) {
            throw IllegalArgumentException("User not found")
        }
        val friendshipRequest = FriendInviteNotification(
            sender = sender
        )
        friendshipService.addFriendRequest(friendshipRequest, recipient)
    }

    override suspend fun getFriendRequests(userId: Int): List<String> = friendshipService.getFriendRequestsForUser(userId)

    override suspend fun getUsersRequestedToBeFriend(userId: Int): List<Int> = friendshipService.getFriendRequestsForUser(userId)
        .map { Json.decodeFromString<FriendInviteNotification>(it).sender }

    override suspend fun acceptFriendRequest(sender: Int, recipient: Int) {
        friendshipService.removeFriendRequest(sender, recipient)
        userRepository.addFriend(sender, recipient)
    }

    override suspend fun removeFriend(user1Id: Int, user2Id: Int) {
        userRepository.removeFriend(user1Id, user2Id)
    }

    override suspend fun subscribeToFriendshipRequests(user2Id: Int, onMessage: (String) -> Unit) {
        friendshipService.subscribeToNotifications(user2Id, onMessage)
    }

    override suspend fun getFriendsList(userId: Int): List<User> {
        return userRepository.friendsList(userId)
    }

    override suspend fun updateUser(id: Int, username: String?, money: ULong?, avatarUri: String?): Boolean {
        return userRepository.updateUser(id, username, money, avatarUri)
    }

}

package example.com.plugins

import example.com.data.db.model.FriendInviteNotification
import example.com.data.db.model.GameInviteNotification
import example.com.data.db.model.NewsNotification
import example.com.data.db.model.dto.FriendInviteNotificationDto
import example.com.data.db.model.dto.NotificationDto
import example.com.getCurrentDateTime
import example.com.services.GameService
import example.com.services.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.ktor.ext.inject
import java.time.Duration


fun Application.configureSockets() {
    val userService: UserService by inject<UserService>()
    val gameService: GameService by inject<GameService>()
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {


        authenticate {
            // Сокет для уведомлений
            webSocket("/notification-channel/{userId}") {
                val parameter = call.parameters["userId"]
                if (parameter == null)
                    close(CloseReason(CloseReason.Codes.NOT_CONSISTENT, "User id must be in"))
                val userId = parameter?.toInt()!!
                val notifications = mutableListOf<NotificationDto>()
                val friendRequests = userService
                    .getFriendRequests(userId)
                    .map {
                        val request = Json.decodeFromString<FriendInviteNotification>(it)
                        val sender = userService.findUserById(request.sender)?.toUserVO()
                        val friendRequestDto = FriendInviteNotificationDto(
                            notificationType = request.notificationType,
                            time = getCurrentDateTime(),
                            sender = sender!!,
                            title = "Player ${sender.username} wants to be your friend"
                        )
                        friendRequestDto
                    }
                // TODO: Аналогично получаем список игр и новостей и добавляем в notifications
                notifications.addAll(friendRequests)
                outgoing.send(Frame.Text(Json.encodeToString(notifications)))

                userService.subscribeToFriendshipRequests(userId) { message ->
                    launch {
                        var messageForPlayer: String? = null
                        try {
                            val jsonElement = Json.parseToJsonElement(message)
                            when (val notificationType = jsonElement.jsonObject["notificationType"]?.jsonPrimitive?.content) {
                                "FriendRequest" -> {
                                    val notification = Json.decodeFromString<FriendInviteNotification>(message)
                                    val sender =
                                        userService.findUserById(notification.sender) ?: throw NotFoundException()
                                    val senderVO = sender.toUserVO()
                                    val friendRequestDto = FriendInviteNotificationDto(
                                        notificationType = notification.notificationType,
                                        sender = senderVO,
                                        title = "Player ${senderVO.username} wants to be your friend",
                                        time = getCurrentDateTime()
                                    )
                                    messageForPlayer = Json.encodeToString(friendRequestDto)
                                }

                                "GameInvite" -> {
                                    val notification = Json.decodeFromString<GameInviteNotification>(message)
                                }

                                // TODO: На потом..Нужно сделать какой-нибудь api чисто
                                //  для себя через который можно публиковать новости
                                "News" -> {
                                    val notification = Json.decodeFromString<NewsNotification>(message)
                                }

                                else -> {
                                    this@configureSockets.log.info("Неизвестный тип уведомления: $notificationType")
                                }
                            }
                        } catch (e: Exception) {
                            this@configureSockets.log.info("Ошибка при обработке сообщения: ${e.message}")
                        }
                        messageForPlayer?.let {
                            outgoing.send(Frame.Text(messageForPlayer))
                        }
                    }
                }

                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        // TODO: Можно accept и reject прям здесь делать
                        val text = frame.readText()
                        outgoing.send(Frame.Text("YOU SAID: $text"))
                        if (text.equals("bye", ignoreCase = true)) {
                            close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                        }
                    }
                }
            }

            // Сокет для игр
            webSocket("/game-channel/{gameId}") {
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        outgoing.send(Frame.Text("YOU SAID: $text"))
                        if (text.equals("bye", ignoreCase = true)) {
                            close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                        }
                    }
                }
            }
        }
    }
}

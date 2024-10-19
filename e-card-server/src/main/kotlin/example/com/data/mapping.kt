package example.com.data

import example.com.data.db.model.User
import example.com.data.db.model.UserDao

fun userDaoToModel(dao: UserDao): User = User(dao.id.value, dao.username, dao.passwordHash, dao.money, dao.avatarUri)
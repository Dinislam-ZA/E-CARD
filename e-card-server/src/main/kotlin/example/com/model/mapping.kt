package example.com.model

fun userDaoToModel(dao: UserDao): User = User(dao.id.value, dao.username, dao.passwordHash)
# Используем официальный образ JDK для запуска Kotlin приложений
FROM openjdk:17-jdk

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем файл сборки и исходный код в контейнер
COPY build.gradle.kts gradle.properties settings.gradle.kts /app/
COPY src /app/src

# Устанавливаем Gradle Wrapper
COPY gradlew /app/
COPY gradle /app/gradle

# Устанавливаем разрешения на выполнение Gradle Wrapper
RUN chmod +x gradlew

# Обновляем зависимости и строим приложение
RUN ./gradlew build --no-daemon

# Запускаем приложение
CMD ["./gradlew", "run"]

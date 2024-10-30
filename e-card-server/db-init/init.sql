-- Создание таблицы Users
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(64) NOT NULL,
    money BIGINT NOT NULL,
    avatarUri VARCHAR(100)
);

-- Вставка начальных пользователей
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('john_doe', '5f4dcc3b5aa765d61d8327deb882cf99', 5000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('jane_smith', '6dcd4ce23d88e2ee9568ba546c007c63', 7000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('mike_jordan', '7c6a180b36896a0a8c02787eeafb0e4c', 15000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('sara_connor', '7d793037a0760186574b0282f2f435e7', 12000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('luke_skywalker', '098f6bcd4621d373cade4e832627b4f6', 20000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('leia_organa', 'c4ca4238a0b923820dcc509a6f75849b', 16000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('bruce_wayne', 'd41d8cd98f00b204e9800998ecf8427e', 9000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('clark_kent', '9e107d9d372bb6826bd81d3542a419d6', 11000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('peter_parker', 'e4da3b7fbbce2345d7772b0674a318d5', 8000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('tony_stark', '1679091c5a880faf6fb5e6087eb1b2dc', 30000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('natasha_romanoff', '8f14e45fceea167a5a36dedd4bea2543', 13000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('steve_rogers', 'c9f0f895fb98ab9159f51fd0297e236d', 14000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('bruce_banner', '45c48cce2e2d7fbdea1afc51c7c6ad26', 9000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('diana_prince', '6512bd43d9caa6e02c990b0a82652dca', 17000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('arthur_curry', 'c20ad4d76fe97759aa27a0c99bff6710', 15000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('barry_allen', 'aab3238922bcc25a6f606eb525ffdc56', 8000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('victor_stone', '9bf31c7ff062936a96d3c8bd1f8f2ff3', 10000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('wanda_maximoff', 'c74d97b01eae257e44aa9d5bade97baf', 11000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('stephen_strange', '70efdf2ec9b086079795c442636b55fb', 12000, NULL);
INSERT INTO users (username, password_hash, money, avatarUri) VALUES ('tchalla', '6f4922f45568161a8cdf4ad2299f6d23', 20000, NULL);


-- Создание таблицы Friends
CREATE TABLE IF NOT EXISTS friends (
    user1 INT REFERENCES users(id),
    user2 INT REFERENCES users(id),
    PRIMARY KEY (user1, user2)
);

-- Вставка начальных данных в таблицу Friends
INSERT INTO friends (user1, user2) VALUES (1, 2);
INSERT INTO friends (user1, user2) VALUES (3, 4);
INSERT INTO friends (user1, user2) VALUES (5, 6);
INSERT INTO friends (user1, user2) VALUES (7, 8);
INSERT INTO friends (user1, user2) VALUES (9, 10);
INSERT INTO friends (user1, user2) VALUES (11, 12);
INSERT INTO friends (user1, user2) VALUES (13, 14);
INSERT INTO friends (user1, user2) VALUES (15, 16);
INSERT INTO friends (user1, user2) VALUES (17, 18);
INSERT INTO friends (user1, user2) VALUES (19, 20);
INSERT INTO friends (user1, user2) VALUES (2, 3);
INSERT INTO friends (user1, user2) VALUES (4, 5);
INSERT INTO friends (user1, user2) VALUES (6, 7);
INSERT INTO friends (user1, user2) VALUES (8, 9);
INSERT INTO friends (user1, user2) VALUES (10, 11);

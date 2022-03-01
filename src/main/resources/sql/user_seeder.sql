USE springblog_db;

DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

INSERT INTO users(email, password, username) VALUES ('bob@email.com','pa$$word', 'bob_user');

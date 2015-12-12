drop database if exists examsdb;

CREATE DATABASE examsdb;
USE examsdb;

CREATE TABLE users (
  id BINARY(16) NOT NULL,
  name VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE user_roles (
    userid BINARY(16) NOT NULL,
    role ENUM ('registered', 'admin') DEFAULT 'registered',
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (userid, role)
);

CREATE TABLE auth_tokens (
    userid BINARY(16) NOT NULL,
    token BINARY(16) NOT NULL,
    FOREIGN KEY (userid) REFERENCES users(id) on delete cascade,
    PRIMARY KEY (token)
);

CREATE TABLE career (
  id BINARY(16) NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE course (
  id BINARY(16) NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE subject (
  id BINARY(16) NOT NULL,
  name VARCHAR(100) NOT NULL,
  career_id BINARY(16) NOT NULL,
  course_id BINARY(16) NOT NULL,
  FOREIGN KEY (career_id) REFERENCES career (id)
    ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES course (id)
    ON DELETE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE exam (
  id BINARY(16) NOT NULL,
  subject VARCHAR(100) NOT NULL,
  text VARCHAR(1000) NOT NULL,
  statement_url char(36) NOT NULL,
  user_id BINARY(16) NOT NULL,
  created_at TIMESTAMP NOT NULL default current_timestamp,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  PRIMARY KEY (id)

);

CREATE TABLE correction (
  id BINARY(16),
  correction_url char (36) NOT NULL,
  text VARCHAR(1000) NOT NULL,
  user_id BINARY(16) NOT NULL,
  exam_id BINARY(16) NOT NULL,
  rating INT(10) NOT NULL DEFAULT 0,
  created_at TIMESTAMP NOT NULL default current_timestamp,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (exam_id) REFERENCES exam (id) ON DELETE CASCADE,
  PRIMARY KEY (id)
);

CREATE TABLE exam_subject (
  subject_id BINARY(16) NOT NULL,
  exam_id BINARY(16) NOT NULL,
  FOREIGN KEY (subject_id) REFERENCES subject (id)
    ON DELETE CASCADE,
  FOREIGN KEY (exam_id) REFERENCES exam (id)
    ON DELETE CASCADE
);

CREATE TABLE comment_exam (
  id BINARY(16) NOT NULL,
  text VARCHAR(1000) NOT NULL,
  exam_id BINARY(16) NOT NULL,
  user_id BINARY(16) NOT NULL,
  created_at TIMESTAMP NOT NULL default current_timestamp,
  FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE,
  FOREIGN KEY (exam_id) REFERENCES exam (id)
    ON DELETE CASCADE
);

CREATE TABLE comment_correction (
  id BINARY(16) NOT NULL,
  text VARCHAR(1000) NOT NULL,
  exam_id BINARY(16) NOT NULL,
  user_id BINARY(16) NOT NULL,
  correction_id BINARY(16) NOT NULL,
  created_at TIMESTAMP NOT NULL default current_timestamp,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
  FOREIGN KEY (exam_id) REFERENCES exam (id) ON DELETE CASCADE,
  FOREIGN KEY (correction_id) REFERENCES correction (id) ON DELETE CASCADE
);

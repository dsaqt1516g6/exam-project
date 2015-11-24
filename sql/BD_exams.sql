CREATE DATABASE exams;
USE exams;

CREATE TABLE user (
  id BINARY(16) NOT NULL,
  type ENUM('user', 'admin') DEFAULT 'user',
  name VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE exam (
  id BINARY(16) NOT NULL,
  text VARCHAR(1000) NOT NULL,
  user_id BINARY(16) NOT NULL,
  subject VARCHAR(100) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user (id)
    ON DELETE CASCADE,
  FOREIGN KEY (subject) REFERENCES subject (name)
    ON DELETE CASCADE,
);

CREATE TABLE correction (
  id BINARY(16),
  text VARCHAR(1000) NOT NULL,
  user_id BINARY(16) NOT NULL,
  exam_id BINARY(16) NOT NULL,
  rating INT(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user (id)
    ON DELETE CASCADE,
  FOREIGN KEY (exam_id) REFERENCES exam (id)
    ON DELETE CASCADE
);

CREATE TABLE subject (
  id BINARY(16) NOT NULL,
  name VARCHAR(100) NOT NULL,
  career_id BINARY(16) NOT NULL,
  course_id BINARY(16) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (career_id) REFERENCES career (id)
    ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES course (id)
    ON DELETE CASCADE
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

CREATE TABLE exam_subject (
  subject_id BINARY(16) NOT NULL,
  exam_id BINARY(16) NOT NULL,
  FOREIGN KEY (subject_id) REFERENCES subject (id)
    ON DELETE CASCADE,
  FOREIGN KEY (exam_id) REFERENCES exam (id)
    ON DELETE CASCADE
);

CREATE TABLE comment (
  id BINARY(16) NOT NULL,
  text VARCHAR(1000) NOT NULL,
  exam_id BINARY(16) NOT NULL,
  user_id BINARY(16) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user (id)
    ON DELETE CASCADE,
  FOREIGN KEY (exam_id) REFERENCES exam (id)
    ON DELETE CASCADE
);

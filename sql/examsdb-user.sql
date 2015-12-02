drop user 'exams'@'localhost';
create user 'exams'@'localhost' identified by 'exams';
grant all privileges on examsdb.* to 'exams'@'localhost';
flush privileges;

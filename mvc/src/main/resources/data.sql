drop table if exists `student`^;

CREATE TABLE `student` (
    `id` bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
)^;

DROP PROCEDURE IF EXISTS InsertStudents^;
CREATE PROCEDURE InsertStudents()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 3 DO
        INSERT INTO student (id, name) VALUES (i, CONCAT('Student_', i));
        SET i = i + 1;
END WHILE;
END^;

CALL InsertStudents()^;
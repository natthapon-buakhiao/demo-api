DROP TABLE IF EXISTS TBL_EMPLOYEES;

CREATE TABLE TBL_EMPLOYEES (
                               id INT AUTO_INCREMENT  PRIMARY KEY,
                               first_name VARCHAR(250) NOT NULL,
                               last_name VARCHAR(250) NOT NULL,
                               email VARCHAR(250) DEFAULT NULL,
                               is_delete VARCHAR(1) DEFAULT 'N'
);

CREATE TABLE USER (
                               id INT AUTO_INCREMENT  PRIMARY KEY,
                               user_name VARCHAR(250) NOT NULL,
                               email VARCHAR(250) NOT NULL,
                               password VARCHAR(250) NOT NULL,
                               count_invalid int DEFAULT 0,
                               lock_user VARCHAR(1) DEFAULT 'N'
);
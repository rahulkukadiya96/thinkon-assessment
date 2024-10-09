CREATE TABLE users
(
    id           INT NOT NULL,
    user_name   VARCHAR(255) NOT NULL UNIQUE,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50)  NOT null,
    delete_flag TINYINT(1) NOT NULL DEFAULT 0, -- 0 means active, 1 means deleted
    PRIMARY KEY (ID)
);

CREATE INDEX idx_id_delete_flag ON users(id, delete_flag);
CREATE INDEX idx_email_delete_flag ON users(user_name, delete_flag);
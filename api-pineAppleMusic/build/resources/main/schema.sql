CREATE TABLE users
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    uuid            BINARY(16)  NOT NULL,
    email           VARCHAR(50) NOT NULL,
    password        VARCHAR(20) NOT NULL,
    membership_type INT         NOT NULL,
    cart_id         BINARY(16)  NOT NULL,
    created_at      DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    login_at        DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unq_email UNIQUE (email)
);

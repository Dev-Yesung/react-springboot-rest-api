USE devdb;

CREATE TABLE membership
(
    id     INT PRIMARY KEY NOT NULL,
    status VARCHAR(50)     NOT NULL
);

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
    FOREIGN KEY (membership_type) REFERENCES membership (id),
    CONSTRAINT unq_email UNIQUE (email)
);

CREATE TABLE credits
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount  INT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE subscriptions
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT NOT NULL,
    expired_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE genres
(
    id   INT PRIMARY KEY NOT NULL,
    name VARCHAR(50)     NOT NULL
);

CREATE TABLE songs
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    image     VARCHAR(200)  NOT NULL,
    title     VARCHAR(100)  NOT NULL,
    genre_id  INT           NOT NULL,
    play_time FLOAT(4, 2)   NOT NULL,
    lyrics    VARCHAR(4000),
    url       VARCHAR(1000) NOT NULL,
    price     INT           NOT NULL,
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE artists
(
    id          INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100)    NOT NULL,
    description VARCHAR(500),
    image       BLOB
);

CREATE TABLE playlists
(
    id      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT             NOT NULL,
    title   VARCHAR(100)    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE songs_artists
(
    id        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    song_id   INT             NOT NULL,
    artist_id INT             NOT NULL,
    FOREIGN KEY (song_id) REFERENCES songs (id),
    FOREIGN KEY (artist_id) REFERENCES artists (id)
);

CREATE TABLE songs_playlists
(
    id          INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    song_id     INT             NOT NULL,
    playlist_id INT             NOT NULL,
    FOREIGN KEY (song_id) REFERENCES songs (id),
    FOREIGN KEY (playlist_id) REFERENCES playlists (id)
);

CREATE TABLE orders
(
    id      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id INT             NOT NULL,
    CONSTRAINT unq_user_id UNIQUE (user_id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE orders_songs
(
    order_id INT NOT NULL,
    song_id  INT NOT NULL,
    PRIMARY KEY (order_id, song_id),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (song_id) REFERENCES songs (id)
);

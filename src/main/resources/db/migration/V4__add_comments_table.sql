CREATE TABLE comments (
    id serial,
    user_id INT NOT NULL,
    bike_id INT NOT NULL,
    comment_text text,
    commented_at timestamp,

    PRIMARY KEY (id),
    CONSTRAINT FK_USER_ID_02 FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE SET NULL ON UPDATE NO ACTION,
    CONSTRAINT FK_BIKE_ID_02 FOREIGN KEY (bike_id)
            REFERENCES bikes (id)
            ON DELETE CASCADE ON UPDATE NO ACTION
);
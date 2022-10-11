CREATE TABLE users (
    id SERIAL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    enabled boolean NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),

    PRIMARY KEY (id)
);

CREATE TABLE roles (
    id SERIAL,
    name VARCHAR(50) DEFAULT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE users_roles (
    user_id INT NOT NULL,
    role_id INT NOT NULL,

    PRIMARY KEY (user_id, role_id),

    CONSTRAINT FK_USER_ID_01 FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ROLE_ID_01 FOREIGN KEY (role_id)
    REFERENCES roles (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO roles(name)
VALUES ('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');

INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$.SEJWx8XDAdK5D/GU61zw.fLIuQZIrHzplod8CNZ0Z3oY2kYvsUbC', TRUE);

INSERT INTO users_roles
VALUES (1, 1), (1, 2), (1,3);

CREATE TABLE bikes (
    id SERIAL,
    number int NOT NULL UNIQUE,
    qr_number int NOT NULL UNIQUE,
    VIN VARCHAR(20) NOT NULL UNIQUE,
    status boolean NOT NULL,
    description text,
    qr_link text,
    photo text,

    PRIMARY KEY (id)
);
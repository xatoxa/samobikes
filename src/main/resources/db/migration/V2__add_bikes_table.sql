CREATE TABLE bikes (
    id SERIAL,
    number int NOT NULL,
    qr_number int NOT NULL,
    VIN VARCHAR(20) NOT NULL,
    status boolean NOT NULL,
    description text,
    qr_link text,
    photo text,

    PRIMARY KEY (id),
    UNIQUE (number, qr_number, VIN)
);
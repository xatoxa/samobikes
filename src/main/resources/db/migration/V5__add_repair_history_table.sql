CREATE TABLE repair_history(
    id serial,
    user_id int NOT NULL,
    bike_id int NOT NULL,
    type varchar(100) NOT NULL,
    date_point timestamp NOT NULL
);
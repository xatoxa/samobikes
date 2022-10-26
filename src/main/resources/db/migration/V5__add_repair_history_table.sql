CREATE TABLE repair_history(
    id serial,
    user_id int NOT NULL,
    bike_id int NOT NULL,
    breakdown_date date,
    repair_date date
);
CREATE TABLE parts(
    bike_id int NOT NULL,
    f_wheel boolean DEFAULT TRUE,
    r_wheel boolean  DEFAULT TRUE,
    f_brake boolean  DEFAULT TRUE,
    r_brake boolean  DEFAULT TRUE,
    f_tyre boolean  DEFAULT TRUE,
    r_tyre boolean  DEFAULT TRUE,
    chain boolean  DEFAULT TRUE,
    saddle boolean  DEFAULT TRUE,
    crank boolean  DEFAULT TRUE,
    gears boolean  DEFAULT TRUE,
    l_pedal boolean  DEFAULT TRUE,
    r_pedal boolean  DEFAULT TRUE,
    cassete boolean  DEFAULT TRUE,
    chain_wheel boolean  DEFAULT TRUE,
    bot_bracket boolean  DEFAULT TRUE,
    steering_wheel boolean  DEFAULT TRUE,

    CONSTRAINT FK_PART_ID_01
    FOREIGN KEY (bike_id)
    REFERENCES bikes (id)
);
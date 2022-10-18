CREATE TABLE parts(
    id serial,
    bike_id int NOT NULL,
    name varchar(255),
    importance int DEFAULT 1,
    description text,
    status boolean DEFAULT TRUE,

    CONSTRAINT FK_PART_ID_01
    FOREIGN KEY (bike_id)
    REFERENCES bikes (id)
);

CREATE TABLE part_names(
    id serial,
    name varchar(255) UNIQUE,
    importance int,
    description text
);

INSERT INTO part_names (name, importance)
VALUES
('Переднее колесо', 1),
('Заднее колесо', 1),
('Камера', 1),
('Покрышка', 1),
('Тормоз', 1),
('Настройка тормоза', 2),
('Тормозная ручка', 1),
('Переключатель', 1),
('Настройка переключателя', 2),
('Педали', 1),
('Шатуны', 1),
('Каретка', 1),
('Кассета/трещотка', 1),
('Цепь', 1),
('Седло/штырь', 1),
('Руль', 1),
('Подножка', 3),
('Рама', 2),
('Вилка', 3),
('Грипсы', 3),
('Рубашка троса тормоза', 2),
('Рубашка троса переключателя', 2),
('Трос тормоза', 2),
('Трос переключателя', 2),
('Эксцентрик колеса', 1);

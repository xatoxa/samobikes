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
    name varchar(255) UNIQUE
);

INSERT INTO part_names (name)
VALUES
('Переднее колесо'),
('Заднее колесо'),
('Камера'),
('Покрышка'),
('Тормоз'),
('Настройка тормоза'),
('Тормозная ручка'),
('Переключатель'),
('Настройка переключателя'),
('Педали'),
('Шатуны'),
('Каретка'),
('Кассета/трещотка'),
('Цепь'),
('Седло/штырь'),
('Руль'),
('Подножка'),
('Рама'),
('Вилка'),
('Грипсы'),
('Рубашка троса тормоза'),
('Рубашка троса переключателя'),
('Трос тормоза'),
('Трос переключателя'),
('Эксцентрик колеса');

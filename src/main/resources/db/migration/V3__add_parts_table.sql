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

INSERT INTO part_names (name, importance, description)
VALUES
('Переднее колесо', 1, 'koleso_pered.jpg'),
('Заднее колесо', 1, 'koleso_zad.jpg'),
('Камера', 1, 'kamera.png'),
('Покрышка', 1, 'pokrishka.jpg'),
('Тормоз', 1, 'tormoz.png'),
('Настройка тормоза', 2, 'torm_nastr.png'),
('Тормозная ручка', 1, 'rychka_torm.png'),
('Переключатель', 1, 'perek_zad.jpg'),
('Манетка/Шифтер', 1, 'shifter.png'),
('Настройка переключателя', 2, 'perek_nastr.png'),
('Педали', 1, 'pedali.png'),
('Шатуны', 1, 'shatyni.png'),
('Каретка', 1, 'karetka.png'),
('Кассета/трещотка', 1, 'kasseta.png'),
('Цепь', 1, 'tsep.png'),
('Седло', 1, 'sedlo.png'),
('Подседельный штырь', 1, 'shtyr.png'),
('Руль', 1, 'ryl.jpg'),
('Подножка', 3, 'podnozhka.png'),
('Рама', 2, 'rama.png'),
('Вилка', 3, 'vilka.png'),
('Грипсы', 3, 'gripsi.png'),
('Рубашка троса тормоза', 2, 'torm_ryb.png'),
('Рубашка троса переключателя', 2, 'perek_ryb.png'),
('Трос тормоза', 2, 'torm_tros.png'),
('Трос переключателя', 2, 'perek_tros.png'),
('Эксцентрик колеса', 1, 'ekscentrik.png');

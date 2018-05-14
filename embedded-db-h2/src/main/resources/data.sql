--POPULATE CLUB TABLE
INSERT INTO club (name, city, country) VALUES ('FC Barcelona', 'Barcelona', 'Spain');
INSERT INTO club (name, city, country) VALUES ('FC Real Madrid', 'Madrid', 'Spain');

--POPULATE PLAYER TABLE
INSERT INTO player (name, position, club_id) VALUES ('messi', 'FW', 1);
INSERT INTO player (name, position, club_id) VALUES ('ronaldo', 'FW', 2);
INSERT INTO player (name, position, club_id) VALUES ('neymar', 'FW', 1);

--POPULATE AUTHOR TABLE
INSERT INTO author (name) VALUES ('Dan Brown');
INSERT INTO author (name) VALUES ('Paulo Coelho');
INSERT INTO author (name) VALUES ('Jim Rohn');

--POPULATE BOOK TABLE
INSERT INTO book (title, subtitle, author_id) VALUES ('Digital Fortress', '', 1);
INSERT INTO book (title, subtitle, author_id) VALUES ('Angels and Daemons', '', 1);
INSERT INTO book (title, subtitle, author_id) VALUES ('Code da Vinci', '', 1);
INSERT INTO book (title, subtitle, author_id) VALUES ('Four Seasons', '', 3);
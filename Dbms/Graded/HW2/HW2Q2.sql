CREATE TABLE Acted (
	pid INT,
	mname VARCHAR(32)
    PRIMARY KEY (pid)
);
CREATE TABLE Director (
	did INT,
	dname VARCHAR(32),
    earnings REAL
    PRIMARY KEY (did)
);
CREATE TABLE Movie (
	mname VARCHAR(32),
	genre VARCHAR(32),
	minutes INT,
	release_year INT,
	did INT
    PRIMARY KEY (mname)
);
CREATE TABLE Performer (
	pid INT,
	pname VARCHAR(32),
	years_of_experience INT,
	age INT
    PRIMARY KEY (pid)
);
INSERT INTO Director
VALUES (1, 'Parker', 580000),
(2, 'Black', 2500000),
(3, 'Black', 30000),
(4, 'Stone', 820000);

INSERT INTO Performer
VALUES(1,'Morgan', 48, 67),
(2, 'Cruz', 14, 28),
(3, 'Adams', 1, 16),
(4, 'Perry', 18, 32),
(5, 'Hanks', 36, 55),
(6, 'Hanks', 15, 24),
(7, 'Lewis', 13, 32);

INSERT INTO Movie
VALUES ('Jurassic Park', 'Action', 125, 1984, 2),
('Shawshank Redemption', 'Drama', 105, 2001, 2),
('Fight Club', 'Drama', 144, 2015, 2),
('The Departed', 'Drama', 130,1969,3),
('Back to the Future', 'Comedy', 89, 2008, 3),
('The Lion King', 'Animation', 97, 1990, 1),
('Alien', 'Sci-Fi', 115, 2006, 3),
('Toy Story', 'Animation', 104, 1978, 1),
('Scarface', 'Drama', 124, 2003, 1),
('Up', 'Animation', 111, 1999, 4);

INSERT INTO Acted
VALUES (4, 'Fight Club'),
(5, 'Fight Club'),
(6, 'Shawshank Redemption'),
(4, 'Up'),
(5, 'Shawshank Redemption'),
(1, 'The Departed'),
(2, 'Fight Club'),
(3, 'Fight Club'),
(4, 'Alien');

SELECT * FROM Director;
SELECT * FROM Acted;
SELECT * FROM Movie;
SELECT * FROM Performer;

SELECT mname FROM Movie WHERE genre = 'Action';

SELECT genre, AVG(minutes)
FROM Movie
GROUP BY genre;

SELECT distinct pname FROM Performer
INNER JOIN Acted ON Acted.pid = Performer.pid
INNER JOIN Movie ON Movie.mname = Acted.mname
INNER JOIN Director ON Director.did = Movie.did
WHERE years_of_experience >= 20 AND dname = 'Black';

SELECT MAX(age)
FROM Performer
INNER JOIN Acted ON Acted.pid = Performer.pid
INNER JOIN Movie ON Movie.mname = Acted.mname
WHERE pname = 'Hanks' OR Movie.mname = 'The Departed';

SELECT Movie.mname
FROM Movie
INNER JOIN Acted ON Acted.mname = Movie.mname
GROUP BY Movie.mname, Movie.genre
HAVING COUNT(*) > 1 OR Movie.genre = 'Comedy';

SELECT Performer.pid, Performer.pname
FROM Performer
INNER JOIN Acted ON Acted.pid = Performer.pid
INNER JOIN Movie ON Movie.mname = Acted.mname
GROUP BY genre, Performer.pname, Performer.pid
HAVING COUNT(genre) > 1;

UPDATE Director
SET Director.earnings = Director.earnings - .1*Director.earnings
FROM Director
INNER JOIN Movie ON Movie.did = Director.did
WHERE Movie.mname = 'Up';

DELETE FROM Movie
WHERE release_year >= 1970 AND release_year <= 1989;
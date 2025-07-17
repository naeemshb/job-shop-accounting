ALTER TABLE Acted
ADD FOREIGN KEY (pid) REFERENCES Performer(pid);
ADD FOREIGN KEY (mname) REFERENCES Movie(mname);

-- problem 1.1
--a
INSERT INTO Movie
VALUES ('Fight Club', 'Thriller', 130, 1969, 3);
--b
INSERT INTO Movie
VALUES (NULL, 'Thriller', 130, 1969, 3);
--c (insert, delete, update)
INSERT INTO Acted
VALUES (1337, 'Fight Club');

DELETE FROM Movie
WHERE mname = 'Fight Club';

UPDATE Acted
SET pid = 1337
WHERE pid = 1;

--d
SELECT * FROM Movie
WHERE mname = NULL;

CREATE INDEX IX_Movie
ON Movie(release_year);

-- I chose this search key for a secondary index because we make a delete query that uses the order of the table
-- with respect to the release_year feature.  No other features make use of this index sequential ordering like 
-- the delete query does.  I chose to rereun the queries below because they are the insertion queries that will
-- be relevant when the secondary index has to be updated and maintained, and also the deletion query that I identified
-- as the reason for creating the index.


-- rerun queries
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

DELETE FROM Movie
WHERE release_year >= 1970 AND release_year <= 1989;
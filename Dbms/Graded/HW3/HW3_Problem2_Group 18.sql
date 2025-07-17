INSERT INTO Performer VALUES (?, ?, ?, ?); -- QUERY_TEMPLATE_1

SELECT * FROM Performer; -- QUERY_TEMPLATE_2


SELECT CASE -- QUERY_TEMPLATE_3
    WHEN AVG(years_of_experience) is NULL AND ? - 18 > 0 THEN ? - 18 --age, age
    WHEN AVG(years_of_experience) is NULL AND ? - 18 <= 0 THEN 0 --age
    WHEN AVG(years_of_experience) > ? THEN ? --age, age
    ELSE
        AVG(years_of_experience)
    END AS years_of_experience
FROM Performer 
WHERE age < ? + 10 and age > ? - 10; --age, age


SELECT CASE -- QUERY_TEMPLATE_4
    WHEN AVG(years_of_experience) is NULL AND ? - 18 > 0 THEN ? - 18 --age, age
    WHEN AVG(years_of_experience) is NULL AND ? - 18 <= 0 THEN 0 --age
    WHEN AVG(years_of_experience) > ? THEN ? --age, age
    ELSE
        AVG(years_of_experience)
    END AS years_of_expeience
FROM Performer 
INNER JOIN Acted on Acted.pid = Performer.pid
INNER JOIN Movie on Movie.mname = Acted.mname
INNER JOIN Director on Movie.did = Director.did
WHERE Director.did = ?; -- did
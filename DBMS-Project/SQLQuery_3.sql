CREATE TABLE update1(
    transactionnumber INT,
    FOREIGN KEY (transactionnumber) REFERENCES transaction1(transactionnumber)
    );
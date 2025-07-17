CREATE TABLE Customer(
    name VARCHAR(32),
    address VARCHAR(32),
    category INT
    PRIMARY key(name)
    CONSTRAINT chk_category CHECK(category IN ('1','2','3','4','5','6','7','8','9',10) )

);
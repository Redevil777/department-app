CREATE TABLE IF NOT EXISTS department  (
    id BIGINT IDENTITY,
    dep_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee (
    id BIGINT IDENTITY,
    fname VARCHAR(50) NOT NULL,
    lname VARCHAR(50) NOT NULL,
    mname VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL,
    salary int NOT NULL,
    dep_id int NOT NULL
);
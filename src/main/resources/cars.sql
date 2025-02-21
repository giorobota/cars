CREATE SEQUENCE cars_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE cars (
                      id BIGSERIAL PRIMARY KEY,
                      brand VARCHAR(255) NOT NULL,
                      model VARCHAR(255) NOT NULL,
                      color VARCHAR(50) NOT NULL,
                      year INT NOT NULL,
                      price INT NOT NULL
);

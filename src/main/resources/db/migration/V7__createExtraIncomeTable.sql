CREATE TABLE "extra_income" (
    id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    "date" DATE NOT NULL,
    "timestamp" TIMESTAMP NOT NULL,
    CONSTRAINT EXTRA_INCOME_FK FOREIGN KEY (user_id) REFERENCES "user" (ID)
);
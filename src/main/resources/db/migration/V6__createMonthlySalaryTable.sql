CREATE TABLE "monthly_salary" (
    id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    day_of_month INTEGER NOT NULL,
    CONSTRAINT MONTHLY_SALARY_FK FOREIGN KEY (user_id) REFERENCES "user" (ID)
);
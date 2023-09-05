CREATE TABLE "cost" (
    id INTEGER PRIMARY KEY,
    cost_category_id INTEGER NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    "date" DATE NOT NULL,
    "timestamp" TIMESTAMP NOT NULL,
    CONSTRAINT COST_FK FOREIGN KEY (cost_category_id) REFERENCES "cost_category" (ID)
);
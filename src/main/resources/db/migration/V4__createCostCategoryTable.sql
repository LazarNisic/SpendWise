CREATE TABLE "cost_category" (
    id INTEGER PRIMARY KEY,
    user_id INTEGER NOT NULL,
    name VARCHAR(50) NOT NULL,
    search_key VARCHAR(128),
    CONSTRAINT COST_CATEGORY_FK FOREIGN KEY (user_id) REFERENCES "user" (ID)
);
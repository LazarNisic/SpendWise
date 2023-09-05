CREATE TABLE "user" (
    id INTEGER PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(512) NOT NULL,
    full_name VARCHAR(50) NOT NULL,
    search_key VARCHAR(128),
    enabled BOOLEAN NOT NULL
);
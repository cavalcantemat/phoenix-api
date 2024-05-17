CREATE TABLE products (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    price INTEGER NOT NULL,
    team TEXT NOT NULL,
    directory TEXT NOT NULL,
    category TEXT,
    league TEXT,
    color jsonb,
    description TEXT,
    storage INTEGER NOT NULL
);
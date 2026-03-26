CREATE TABLE IF NOT EXISTS auth_svc.users (
    id            UUID         NOT NULL PRIMARY KEY,
    username      VARCHAR(50)  NOT NULL UNIQUE,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    active        BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE INDEX IF NOT EXISTS idx_users_email    ON auth_svc.users (email);
CREATE INDEX IF NOT EXISTS idx_users_username ON auth_svc.users (username);

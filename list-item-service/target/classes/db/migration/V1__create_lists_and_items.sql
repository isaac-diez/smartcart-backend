CREATE TABLE IF NOT EXISTS listitem_svc.shopping_lists (
    id          UUID         NOT NULL PRIMARY KEY,
    group_id    UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    created_by  UUID         NOT NULL,
    created_at  TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    archived    BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE INDEX IF NOT EXISTS idx_lists_group_id ON listitem_svc.shopping_lists (group_id);

CREATE TABLE IF NOT EXISTS listitem_svc.items (
    id            UUID         NOT NULL PRIMARY KEY,
    list_id       UUID         NOT NULL REFERENCES listitem_svc.shopping_lists(id) ON DELETE CASCADE,
    name          VARCHAR(200) NOT NULL,
    category      VARCHAR(100),
    quantity      INTEGER      NOT NULL DEFAULT 1,
    unit          VARCHAR(50),
    status        VARCHAR(20)  NOT NULL DEFAULT 'DESIRED',
    added_by      UUID         NOT NULL,
    status_owner   UUID         NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    purchased_at  TIMESTAMPTZ,
    stored_at     TIMESTAMPTZ,
    wanted_at     TIMESTAMPTZ
);

CREATE INDEX IF NOT EXISTS idx_items_list_id     ON listitem_svc.items (list_id);
CREATE INDEX IF NOT EXISTS idx_items_list_status ON listitem_svc.items (list_id, status);

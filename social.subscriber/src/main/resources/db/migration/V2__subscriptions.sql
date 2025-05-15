CREATE TABLE subscriptions
(
    id           BIGSERIAL PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP
);
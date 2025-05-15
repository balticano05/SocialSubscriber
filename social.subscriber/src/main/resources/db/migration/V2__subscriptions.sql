CREATE TABLE subscriptions (
   id BIGSERIAL PRIMARY KEY,
   service_name VARCHAR(255) NOT NULL,
   user_id BIGINT NOT NULL,
   created_at TIMESTAMP,
   FOREIGN KEY (user_id) REFERENCES users(id)
);
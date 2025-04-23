CREATE TABLE user_health_unity (
    user_id UUID NOT NULL,
    health_unity_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, health_unity_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_health_unity FOREIGN KEY (health_unity_id) REFERENCES health_unity (id) ON DELETE CASCADE
);

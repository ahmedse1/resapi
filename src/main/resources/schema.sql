CREATE TABLE IF NOT EXISTS device (
    device_id INT AUTO_INCREMENT PRIMARY KEY,
    device_name VARCHAR(50),
    device_brand VARCHAR(50),
    created_at DATE NOT NULL,
    updated_at DATE DEFAULT NULL
);
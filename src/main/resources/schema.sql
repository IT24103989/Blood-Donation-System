CREATE DATABASE IF NOT EXISTS LifeLineDB;
USE LifeLineDB;

CREATE TABLE IF NOT EXISTS donors (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    nic_number VARCHAR(20) NOT NULL UNIQUE,
    blood_group VARCHAR(5) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    email VARCHAR(100) UNIQUE,
    address VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE
    );

CREATE TABLE IF NOT EXISTS donation_history (
                                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                donor_id BIGINT,
                                                donation_date DATE NOT NULL,
                                                donation_location VARCHAR(100) NOT NULL,
    hemoglobin_level DECIMAL(5,2),
    medical_officer VARCHAR(100),
    notes TEXT,
    FOREIGN KEY (donor_id) REFERENCES donors(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS appointments (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            donor_id BIGINT,
                                            appointment_time DATETIME,
                                            location VARCHAR(255),
    status VARCHAR(20),
    FOREIGN KEY (donor_id) REFERENCES donors(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS blood_inventory (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               blood_group VARCHAR(15),
    quantity INT,
    status VARCHAR(20)
    );
-- Create the oop-g1t7-db database
CREATE DATABASE IF NOT EXISTS oop_g1t7_db;
USE oop_g1t7_db;

-- Create the USER table
CREATE TABLE IF NOT EXISTS user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Create the PORTFOLIO table
CREATE TABLE IF NOT EXISTS portfolio (
    portfolio_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    capital FLOAT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Create the portfolio_stock table
CREATE TABLE IF NOT EXISTS portfolio_stock (
    portfolio_id INT NOT NULL,
    ticker VARCHAR(20) NOT NULL,
    proportion FLOAT NOT NULL,
    PRIMARY KEY (portfolio_id, ticker), 
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(portfolio_id)
);

-- Create the audit_log table 
CREATE TABLE IF NOT EXISTS audit_log (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    log_ts timestamp NOT NULL,
    detail VARCHAR(200) NOT NULL,
    outcome enum('Success', 'Failure'),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Create the audit_log table 
CREATE TABLE IF NOT EXISTS audit_log (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    log_ts timestamp NOT NULL,
    detail VARCHAR(200) NOT NULL,
    outcome enum('Success', 'Failure'),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- create password_reset_token table
CREATE TABLE IF NOT EXISTS password_reset_token (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    token VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- create password_reset_token_seq table
CREATE TABLE IF NOT EXISTS password_reset_token_seq (
    next_val BIGINT PRIMARY KEY
);

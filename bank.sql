CREATE DATABASE IF NOT EXISTS bankdb;
USE bankdb;

CREATE TABLE account (
    acc_no  INT PRIMARY KEY,
    name    VARCHAR(50)   NOT NULL,
    balance DECIMAL(12,2) NOT NULL CHECK (balance >= 0)
);

-- Sample data (5 accounts)
INSERT INTO account (acc_no, name, balance) VALUES
(1001, 'Ravi',   5000.00),
(1002, 'Sita',  12000.00),
(1003, 'Arjun',  2500.50),
(1004, 'Meena', 80000.00),
(1005, 'Kiran',   300.00);

-- 1. Account creation
INSERT INTO account (acc_no, name, balance) VALUES (1006, 'Latha', 1000.00);

-- 2. Deposit (amount 2000 into 1001)
UPDATE account SET balance = balance + 2000 WHERE acc_no = 1001;

-- 3. Withdrawal (amount 1500 from 1002; succeeds only if funds suffice)
UPDATE account SET balance = balance - 1500 WHERE acc_no = 1002 AND balance >= 1500;
-- "1 row affected" = success, "0 rows affected" = insufficient funds / no such account

-- 4. Balance enquiry
SELECT acc_no, name, balance FROM account WHERE acc_no = 1001;

-- View all accounts
SELECT * FROM account;

CREATE DATABASE IF NOT EXISTS application;


CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username CHAR(20),
  password CHAR(20),
  name CHAR(50),
  email CHAR(50)
);


CREATE TABLE IF NOT EXISTS appointments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  password CHAR(20),
  name CHAR(50),
  email CHAR(50)
);


CREATE TABLE IF NOT EXISTS alarms (
  id INT AUTO_INCREMENT PRIMARY KEY,
  appointment_id INT,
  user_id INT,
  time DATETIME,
  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON DELETE CASCADE,
  FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS meeting_room (
  room_number INT AUTO_INCREMENT PRIMARY KEY,
  capacity INT
);


CREATE DATABASE IF NOT EXISTS application;


CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username CHAR(20),
  password CHAR(20),
  name CHAR(50),
  email CHAR(50)
);


CREATE TABLE IF NOT EXISTS meeting_room (
  id INT AUTO_INCREMENT PRIMARY KEY,
  capacity INT
);


CREATE TABLE IF NOT EXISTS appointments (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name CHAR(100),
  description CHAR(200),
  start_date DATETIME,
  end_date DATETIME,
  place CHAR(50),
  last_updated DATETIME
);


CREATE TABLE IF NOT EXISTS alarms (
  appointment_id INT,
  user_id INT,
  time DATETIME,
  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON DELETE CASCADE,
  FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE,
  PRIMARY KEY (appointment_id, user_id)
);


CREATE TABLE IF NOT EXISTS meeting_room_reservation (
  meeting_room_id INT,
  appointment_id INT,
  PRIMARY KEY (meeting_room_id, appointment_id)
);



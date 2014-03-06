CREATE DATABASE IF NOT EXISTS calendar_system;

USE calendar_system;

CREATE TABLE IF NOT EXISTS users (
  id         INT      NOT NULL AUTO_INCREMENT,
  username   CHAR(20) NOT NULL,
  password   CHAR(64) NOT NULL,  # sha256
  first_name CHAR(50) NOT NULL,
  last_name  CHAR(50) NOT NULL,
  email      CHAR(50) NOT NULL,

  UNIQUE (username),
  UNIQUE (email),
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS meeting_rooms (
  id       INT NOT NULL AUTO_INCREMENT,
  capacity INT NOT NULL,

  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS appointments (
  id           INT       NOT NULL AUTO_INCREMENT,
  name         CHAR(100) NOT NULL,
  description  CHAR(200) NOT NULL,
  start_date   DATETIME  NOT NULL,
  end_date     DATETIME  NOT NULL,
  place        CHAR(50)  NULL,
  last_updated DATETIME  NOT NULL,
  owner_id     INT       NOT NULL,

  FOREIGN KEY (owner_id)
    REFERENCES users(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS alarms (
  appointment_id INT      NOT NULL,
  user_id        INT      NOT NULL,
  time           DATETIME NOT NULL,

  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (appointment_id, user_id)
);


CREATE TABLE IF NOT EXISTS meeting_room_reservations (
  meeting_room_id INT NOT NULL,
  appointment_id INT NOT NULL,

  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  FOREIGN KEY (meeting_room_id)
    REFERENCES meeting_rooms(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (meeting_room_id, appointment_id)
);


CREATE TABLE IF NOT EXISTS user_appointments (
  user_id INT NOT NULL,
  appointment_id INT NOT NULL,
  status INT NOT NULL,
  last_checked DATETIME NOT NULL,
  is_visible BOOL NOT NULL,

  FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (user_id, appointment_id)
);

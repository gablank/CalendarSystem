CREATE DATABASE IF NOT EXISTS calendar_system;

USE calendar_system;

CREATE TABLE IF NOT EXISTS users (
  email CHAR(50) NOT NULL,

  PRIMARY KEY(email)
);

CREATE TABLE IF NOT EXISTS internal_users (
  email      CHAR(50) NOT NULL,
  password   CHAR(64) NOT NULL,  # sha256 = 64 chars
  first_name CHAR(50) NOT NULL,
  last_name  CHAR(50) NOT NULL,

  FOREIGN KEY (email)
  REFERENCES users(email)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS external_users (
  email CHAR(50) NOT NULL,

  FOREIGN KEY (email)
    REFERENCES users(email)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY(email)
);


CREATE TABLE IF NOT EXISTS meeting_rooms (
  id       INT NOT NULL AUTO_INCREMENT,
  capacity INT NOT NULL,

  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS appointments (
  id           INT       NOT NULL AUTO_INCREMENT,
  title        CHAR(100) NOT NULL,
  description  CHAR(200) NOT NULL,
  start_date   DATETIME  NOT NULL,
  end_date     DATETIME  NOT NULL,
  place        CHAR(50)  NULL,
  last_updated DATETIME  NOT NULL,
  owner_email  CHAR(50)  NOT NULL,

  FOREIGN KEY (owner_email)
    REFERENCES users(email)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS alarms (
  appointment_id INT      NOT NULL,
  user_email     CHAR(50) NOT NULL,
  time           DATETIME NOT NULL,

  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  FOREIGN KEY (user_email)
    REFERENCES users(email)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (appointment_id, user_email)
);


CREATE TABLE IF NOT EXISTS meeting_room_reservations (
  meeting_room_id INT NOT NULL,
  appointment_id  INT NOT NULL,

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


CREATE TABLE IF NOT EXISTS attendants (
  user_email     CHAR(50) NOT NULL,
  appointment_id INT      NOT NULL,
  status         INT      NOT NULL,

  FOREIGN KEY (user_email)
    REFERENCES users(email)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (user_email, appointment_id)
);


CREATE TABLE IF NOT EXISTS internal_attendants (
  user_email     CHAR(50) NOT NULL,
  appointment_id INT      NOT NULL,
  last_checked   DATETIME NOT NULL,
  is_visible     BOOL     NOT NULL,

  FOREIGN KEY (user_email)
    REFERENCES users(email)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (user_email, appointment_id)
);


CREATE TABLE IF NOT EXISTS external_attendants (
  user_email     CHAR(50) NOT NULL,
  appointment_id INT      NOT NULL,

  FOREIGN KEY (user_email)
    REFERENCES users(email)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  FOREIGN KEY (appointment_id)
    REFERENCES appointments(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY (user_email, appointment_id)
);

CREATE TABLE IF NOT EXISTS groups (
  id   INT      NOT NULL AUTO_INCREMENT,
  name CHAR(50) NOT NULL,

  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS group_members (
  user_email CHAR(50) NOT NULL,
  group_id   INT      NOT NULL,

  FOREIGN KEY (user_email)
    REFERENCES users(email)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  FOREIGN KEY (group_id)
    REFERENCES groups(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE,

  PRIMARY KEY(user_email, group_id)
);
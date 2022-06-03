DROP TABLE IF EXISTS catalogs;
DROP TABLE IF EXISTS users;
-- CREATE TABLE IF NOT EXISTS catalog (
--   id int PRIMARY KEY AUTO_INCREMENT,
--   name VARCHAR(30) NOT NULL
-- );

CREATE TABLE IF NOT EXISTS catalogs (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL,
  password VARCHAR(150) NOT NULL
);
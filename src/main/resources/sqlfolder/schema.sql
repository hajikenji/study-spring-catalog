DROP TABLE IF EXISTS catalog;
-- CREATE TABLE IF NOT EXISTS catalog (
--   id int PRIMARY KEY AUTO_INCREMENT,
--   name VARCHAR(30) NOT NULL
-- );

CREATE TABLE IF NOT EXISTS catalog (
  id bigint PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(30) NOT NULL
);
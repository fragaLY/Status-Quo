CREATE TABLE IF NOT EXISTS CLIENTS
(
  ID         INT          PRIMARY KEY AUTO_INCREMENT,
  EMAIL      VARCHAR(100) NOT NULL,
  PASSWORD   VARCHAR(60),
  FIRSTNAME  VARCHAR(100),
  SECONDNAME VARCHAR(100),
  ROLE       VARCHAR(20)  DEFAULT 'ANONYMOUS'

);
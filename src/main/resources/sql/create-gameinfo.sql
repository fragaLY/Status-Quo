CREATE TABLE IF NOT EXISTS GAMEINFO
(
  ID         INT         PRIMARY KEY AUTO_INCREMENT,
  POKERROOM  VARCHAR(20) DEFAULT 'POKERSTARS',
  SPEED      VARCHAR(20) DEFAULT 'ALL',
  SIZE       VARCHAR(20) DEFAULT 'ALL',
  TYPE       VARCHAR(20) DEFAULT 'ALL'
);
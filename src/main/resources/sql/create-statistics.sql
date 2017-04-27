CREATE TABLE IF NOT EXISTS STATISTICS
(
  ID            INT           PRIMARY KEY AUTO_INCREMENT,
  PLAYER_NAME   VARCHAR(100)  NOT NULL,
  PROFIT        DECIMAL(10,2) DEFAULT     0.00,
  DATE_FROM     DATE          NOT NULL,
  DATE_TO       DATE          NOT NULL
);
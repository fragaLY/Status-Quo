CREATE TABLE IF NOT EXISTS Clients
(
  Id         INT PRIMARY KEY AUTO_INCREMENT,
  Email      VARCHAR(100) NOT NULL,
  Password   VARCHAR(60)  ,
  FirstName  VARCHAR(100) ,
  SecondName VARCHAR(100) ,
  Role       VARCHAR(20)  DEFAULT "USER"
);
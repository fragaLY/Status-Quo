CREATE TABLE STATISTIC_GAMEINFO
(
  STATISTIC_ID   INT NOT NULL,
  GAMEINFO_ID    INT NOT NULL,

  CONSTRAINT STATISTIC_STATISTIC_ID_FK   FOREIGN KEY (STATISTIC_ID)  REFERENCES STATISTICS (ID),
  CONSTRAINT GAMEINFO_GAMEINFO_ID_FK     FOREIGN KEY (GAMEINFO_ID)   REFERENCES GAMEINFO (ID)

);
CREATE TABLE MM_USER (
  ID   VARCHAR(36),
  NAME VARCHAR(50),
  SEX  VARCHAR(1),
  AGE  INT
);
COMMENT ON COLUMN MM_USER.SEX IS '1-男；0-女';

INSERT INTO MM_USER (ID, NAME, SEX, AGE) VALUES ('1', '邱胜明', '男', '24');

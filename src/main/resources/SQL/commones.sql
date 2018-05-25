-- 获取UUID
SELECT REPLACE(UUID(), '-', '') AS id;

-- 如果想在一个已经建好的表中添加一列，可以用诸如：
ALTER TABLE TABLE_NAME
  ADD COLUMN NEW_COLUMN_NAME VARCHAR(20) NOT NULL;

-- 这条语句会向已有的表中加入新的一列，这一列在表的最后一列位置。如果我们希望添加在指定的一列，可以用：
ALTER TABLE TABLE_NAME
  ADD COLUMN NEW_COLUMN_NAME VARCHAR(20) NOT NULL
  AFTER COLUMN_NAME;

-- 注意，上面这个命令的意思是说添加新列到某一列后面。如果想添加到第一列的话，可以用：
ALTER TABLE TABLE_NAME
  ADD COLUMN NEW_COLUMN_NAME VARCHAR(20) NOT NULL
  FIRST;
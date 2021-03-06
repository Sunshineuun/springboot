CREATE TABLE GRID_VIEW_CONFIGURE (
  ID                 VARCHAR(255) PRIMARY KEY NOT NULL,
  MODULE_NAME        VARCHAR(255) UNIQUE      NOT NULL,
  URL                VARCHAR(125) UNIQUE      NOT NULL,
  PAGE_SIZE          SMALLINT     DEFAULT 25,
  SORTERS            VARCHAR(255) DEFAULT 'id#ASC',
  FORCE_FIT          TINYINT (1) DEFAULT 1,
  AUTO_SCROLL        TINYINT (1) DEFAULT 1,
  ROW_LINES          TINYINT (1) DEFAULT 1,
  COLUMN_LINES       TINYINT (1) DEFAULT 1,
  ENABLE_COLUMN_HIDE TINYINT (1) DEFAULT 1,
  START_LOAD         TINYINT (1) DEFAULT 1
);
ALTER TABLE GRID_VIEW_CONFIGURE
  ADD (ENABLE_LOCKING TINYINT (1) DEFAULT 0);
-- IN_VIEWPORT_SHOW
ALTER TABLE GRID_VIEW_CONFIGURE
  ADD (IN_VIEWPORT_SHOW TINYINT (1) DEFAULT 1);
-- DICTIONARY_PARAMS
ALTER TABLE GRID_VIEW_CONFIGURE
  ADD (DICTIONARY_PARAMS VARCHAR(255) DEFAULT '');
UPDATE grid_view_configure
SET DICTIONARY_PARAMS = 'TYPE#'
WHERE ID = '1';
-- START_LOAD
ALTER TABLE GRID_VIEW_CONFIGURE
  ADD (START_LOAD TINYINT (1) DEFAULT 1);

INSERT INTO grid_view_configure (ID, MODULE_NAME, URL, DICTIONARY_PARAMS)
VALUES ('1', 'Book', '/book', 'TYPE#');
-- BookHistory
INSERT INTO grid_view_configure (ID, MODULE_NAME, URL, DICTIONARY_PARAMS)
VALUES ('2', 'BookHistory', '/bookHistory', 'TYPE#');

--------------------------------------------------------------------------------
CREATE TABLE EXT_COULUMN (
  ID         VARCHAR(36) PRIMARY KEY,
  MODULE_ID  VARCHAR(255)              NOT NULL,
  HEADER     VARCHAR(255) DEFAULT '默认' NOT NULL,
  DATA_INDEX VARCHAR(255)              NOT NULL,
  SEALED     TINYINT (1) NOT NULL DEFAULT 0,
  HIDDEN     TINYINT (1) NOT NULL DEFAULT 0,
  HIDEABLE   TINYINT (1) NOT NULL DEFAULT 1,
  SORTABLE   TINYINT (1) NOT NULL DEFAULT 1,
  GROUPABLE  TINYINT (1) NOT NULL DEFAULT 1,
  WIDTH      SMALLINT                  NOT NULL DEFAULT 125
);
ALTER TABLE EXT_COULUMN
  ADD (EDITOR VARCHAR(500) DEFAULT '{"xtype": "textfield"}');
ALTER TABLE EXT_COULUMN
  ADD (ORDER_INDEX TINYINT (5) DEFAULT 0);
ALTER TABLE EXT_COULUMN
  ADD (RENDERER_FUN VARCHAR(500) DEFAULT 'return value;');
ALTER TABLE EXT_COULUMN
  ADD (LOCKED TINYINT (1) DEFAULT 0);
ALTER TABLE EXT_COULUMN
  ADD (XTYPE VARCHAR(36) DEFAULT 'gridcolumn');
ALTER TABLE EXT_COULUMN
  ADD (FORMAT VARCHAR(36));
-- FILTER_TYPE
ALTER TABLE EXT_COULUMN
  ADD (FILTER_TYPE VARCHAR(36) DEFAULT 'string');
-- FILTER_VALUE
ALTER TABLE EXT_COULUMN
  ADD (FILTER_VALUE VARCHAR(36));
-- FILTER_ITEM_DEFAULTS
ALTER TABLE EXT_COULUMN
  ADD (FILTER_ITEM_DEFAULTS VARCHAR(36));
ALTER TABLE EXT_COULUMN
  ADD (DICTIONARY VARCHAR(36));

INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, HIDDEN, HIDEABLE)
VALUES (REPLACE(UUID(), '-', ''), '1', 0, 'ID', 'id', 1, 0);
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, GROUPABLE)
VALUES (REPLACE(UUID(), '-', ''), '1', 2, '书名', 'name', 0);
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, GROUPABLE)
VALUES (REPLACE(UUID(), '-', ''), '1', 3, '作者', 'author', 0);
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, GROUPABLE)
VALUES (REPLACE(UUID(), '-', ''), '1', 4, '归属', 'affiliation', 0);
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, EDITOR, RENDERER_FUN)
VALUES
  (REPLACE(UUID(), '-', ''), '1', 5, '占用', 'occupy',
   '{"xtype": "uxcombobox", "url": "/getDictionaryByTypeCode/TYPE"}',
   'return formatter("TYPE", value);');
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, HIDDEN, HIDEABLE)
VALUES (REPLACE(UUID(), '-', ''), '2', 0, 'ID', 'id', 1, 0);
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, HIDDEN, HIDEABLE)
VALUES (REPLACE(UUID(), '-', ''), '2', 1, '书名', 'bookId', 0, 0);
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, HIDDEN, HIDEABLE)
VALUES (REPLACE(UUID(), '-', ''), '2', 2, '借书人', 'borrower', 0, 0);
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, HIDDEN, HIDEABLE, RENDERER_FUN)
VALUES (REPLACE(UUID(), '-', ''), '2', 3, '是否还书', 'isEnable', 0, 0,
        'return formatter("TYPE", value);');
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, HIDDEN, HIDEABLE, XTYPE, FORMAT, EDITOR)
VALUES
  (REPLACE(UUID(), '-', ''), '2', 4, '开始日期', 'startDate', 0, 0, 'datecolumn',
   'Y-m-d H:i:s', '{"xtype": "datefield"}');
INSERT INTO EXT_COULUMN (ID, MODULE_ID, ORDER_INDEX, HEADER, DATA_INDEX, HIDDEN, HIDEABLE, XTYPE, FORMAT, EDITOR)
VALUES (REPLACE(UUID(), '-', ''), '2', 5, '结束日期', 'endDate', 0, 0, 'datecolumn',
        'Y-m-d H:i:s', '{"xtype": "datefield"}');

UPDATE ext_coulumn SET DICTIONARY = 'TYPE', FILTER_TYPE = 'list' WHERE MODULE_ID = '1' AND HEADER = '占用';


-- 插件配置信息
CREATE TABLE EXT_PLUGIN (
  ID        VARCHAR(36) PRIMARY KEY,
  MODULE_ID VARCHAR(36) NOT NULL,
  PTYPE     VARCHAR(36) NOT NULL,
  CONFIG    VARCHAR(2000)
);
INSERT INTO EXT_PLUGIN (ID, MODULE_ID, PTYPE, CONFIG)
VALUES (REPLACE(UUID(), '-', ''), '1', 'rowediting',
        '{"id":"rowediting","clicksToEdit": 5,"saveBtnText": "保存","cancelBtnText": "取消", "errorsText": "错误", "dirtyText": "你要确认或取消更改"}');
INSERT INTO EXT_PLUGIN (ID, MODULE_ID, PTYPE, CONFIG)
VALUES (REPLACE(UUID(), '-', ''), '1', 'gridfilters','');

-- 字典
CREATE TABLE DICTIONARY (
  ID         VARCHAR(36) PRIMARY KEY,
  VALUE      VARCHAR(36) NOT NULL,
  NAME       VARCHAR(36) NOT NULL,
  REMARK     VARCHAR(255),
  TYPE_CODE  VARCHAR(36),
  VIEW_ORDER TINYINT (5) DEFAULT 0
);
INSERT INTO dictionary (ID, VALUE, NAME, REMARK, TYPE_CODE, VIEW_ORDER)
VALUES ('1ed2343eb6cb10369c96c62771c06ee8', '1', '仓库', NULL, 'TYPE', 0);
INSERT INTO dictionary (ID, VALUE, NAME, REMARK, TYPE_CODE, VIEW_ORDER)
VALUES ('3ae87123b6cb10369c96c62771c06ee8', '2', '出借', NULL, 'TYPE', 1);
INSERT INTO dictionary (ID, VALUE, NAME, REMARK, TYPE_CODE, VIEW_ORDER)
VALUES (REPLACE(UUID(), '-', ''), '0', '未还', NULL, 'IS_RETURN', 0); -- 归还
INSERT INTO dictionary (ID, VALUE, NAME, REMARK, TYPE_CODE, VIEW_ORDER)
VALUES (REPLACE(UUID(), '-', ''), '1', '已还', NULL, 'IS_RETURN', 1);

CREATE TABLE SP_DATA_LOG (
  ID           VARCHAR(36) PRIMARY KEY,
  TARGET_ID    VARCHAR(36)   NOT NULL,
  ENTITY       VARCHAR(4000) NOT NULL,
  TARGET_CLASS VARCHAR(255)  NOT NULL,
  TYPE         VARCHAR(36)   NOT NULL,
  IP           VARCHAR(255),
  METHOD       VARCHAR(255)  NOT NULL,
  CREATE_BY    VARCHAR(36)   NOT NULL,
  CREATE_DATE  TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE TABLE EXT_FEATURE (
  ID        VARCHAR(36) PRIMARY KEY,
  MODULE_ID VARCHAR(36) NOT NULL,
  FTYPE     VARCHAR(36) NOT NULL,
  CONFIG    VARCHAR(2000)
);
INSERT INTO EXT_FEATURE (ID, MODULE_ID, FTYPE, CONFIG)
VALUES (REPLACE(UUID(), '-', ''), '2', 'groupingsummary',
        '');


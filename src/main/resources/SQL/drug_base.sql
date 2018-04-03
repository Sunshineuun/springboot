-- 药物成分表
DROP TABLE QSM_DRUG_INGREDIENT;
CREATE TABLE QSM_DRUG_INGREDIENT
(
  ID VARCHAR2(255),
  NAME VARCHAR2(255),
  PINYIN_CODE VARCHAR2(50),
  TYPE VARCHAR2(1)
);

INSERT INTO QSM_DRUG_INGREDIENT (ID, NAME, PINYIN_CODE, TYPE)
  SELECT
    SYS_GUID()       ID,
    BIG_GENERIC_NAME NAME,
    PINYIN_CODE,
    DRUG_TYPE TYPE
  FROM KBMS_DRUG_BIG_GENERIC;
COMMENT ON TABLE QSM_DRUG_INGREDIENT IS '药物成分表';
COMMENT ON COLUMN QSM_DRUG_INGREDIENT.NAME IS '药物成分名称';
COMMENT ON COLUMN QSM_DRUG_INGREDIENT.TYPE IS '药物成分类型，1-中成药 or 2-西药';
COMMENT ON COLUMN QSM_DRUG_INGREDIENT.PINYIN_CODE IS '药物成分名称拼音首字母';

/**
    唯一件约束
    规约：设置键的时候，数值代表那一列，u代表唯一键，其它以此类推。
 */
CREATE UNIQUE INDEX QSM_DRUG_INGREDIENT_0_u ON QSM_DRUG_INGREDIENT(ID);
CREATE UNIQUE INDEX QSM_DRUG_INGREDIENT_1_u ON QSM_DRUG_INGREDIENT(NAME);
-- 主键设置
ALTER TABLE QSM_DRUG_INGREDIENT ADD CONSTRAINT QSM_DRUG_INGREDIENT_ID_pk PRIMARY KEY (ID);

--==============================================================================
CREATE TABLE QSM_DRUG_GENERIC_NAME (
  ID            VARCHAR2(255) PRIMARY KEY,
  NAME          VARCHAR2(255) UNIQUE,
  INGREDIENT_ID VARCHAR2(255)
);

INSERT INTO QSM_DRUG_GENERIC_NAME (ID, NAME, INGREDIENT_ID)
  SELECT
    SYS_GUID() ID,
    SMALL_GENERIC_NAME,
    T3.ID
  FROM KBMS_DRUG_SMALL_GENERIC T1 LEFT JOIN KBMS_DRUG_BIG_GENERIC T2
      ON T1.BIG_GENERIC_ID = T2.ID
    LEFT JOIN QSM_DRUG_INGREDIENT T3 ON T3.NAME = T2.BIG_GENERIC_NAME;
COMMENT ON TABLE QSM_DRUG_GENERIC_NAME IS '药物通用名称表';
COMMENT ON COLUMN QSM_DRUG_GENERIC_NAME.NAME IS '药物通用名称';
COMMENT ON COLUMN QSM_DRUG_GENERIC_NAME.INGREDIENT_ID IS '药物成分ID';
-- 外键约束
ALTER TABLE QSM_DRUG_GENERIC_NAME
  ADD CONSTRAINT QSM_GENERIC_NAME_INGREDIENT_fk
FOREIGN KEY (INGREDIENT_ID) REFERENCES LUUN.QSM_DRUG_INGREDIENT;
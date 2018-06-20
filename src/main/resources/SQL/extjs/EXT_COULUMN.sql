CREATE TABLE sunshine.ext_coulumn
(
    ID varchar(255) DEFAULT '' PRIMARY KEY NOT NULL,
    MODULE_ID varchar(255) DEFAULT '' NOT NULL,
    HEADER varchar(255) DEFAULT '默认' NOT NULL,
    DATA_INDEX varchar(255) DEFAULT '' NOT NULL
);
INSERT INTO sunshine.ext_coulumn (ID, MODULE_ID, HEADER, DATA_INDEX) VALUES ('d543e6acb0801036a8d79bc610fab9af', '1', '书名', 'name');
INSERT INTO sunshine.ext_coulumn (ID, MODULE_ID, HEADER, DATA_INDEX) VALUES ('e2f0c9d6b0801036a8d79bc610fab9af', '1', '作者', 'author');
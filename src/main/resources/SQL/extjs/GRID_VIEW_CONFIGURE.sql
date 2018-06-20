CREATE TABLE sunshine.grid_view_configure
(
    ID varchar(255) DEFAULT '' PRIMARY KEY NOT NULL,
    MODULE_NAME varchar(255) DEFAULT '' NOT NULL
);
CREATE UNIQUE INDEX MODULE_NAME ON sunshine.grid_view_configure (MODULE_NAME);
INSERT INTO sunshine.grid_view_configure (ID, MODULE_NAME) VALUES ('1', 'Book');
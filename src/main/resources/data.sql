-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

DELETE
FROM users;

DELETE
FROM roles;

DELETE
FROM plants;

DELETE
FROM userroles;

INSERT INTO users(id,username, email, phone, password, created_by, created_date, last_modified_by, last_modified_date)
            VALUES (1, 'admin', 'nick.nick.com', '123456789', '$2b$10$4UQjJDicsYzyUkIg1yHgOepJuYGnfRLEQTl2q8j/PkMUUoxPUDXT2', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);

INSERT INTO roles(roleid, name, created_by, created_date, last_modified_by, last_modified_date)
            VALUES(1, 'ADMIN', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP),
                  (2, 'DATA', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP),
                  (3, 'USER', 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);

INSERT INTO userroles(id, roleid, created_by, created_date, last_modified_by, last_modified_date)
            VALUES(1, 1, 'SYSTEM', CURRENT_TIMESTAMP, 'SYSTEM', CURRENT_TIMESTAMP);

alter sequence hibernate_sequence restart with 10;
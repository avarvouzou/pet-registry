INSERT INTO municipalities(name) VALUES ("Agia Keftedoupoli");
INSERT INTO municipalities(name) VALUES ("Gaidouragkathou");

INSERT INTO privileges(name) VALUES ("CAN_CREATE_PET");
INSERT INTO privileges(name) VALUES ("CAN_REVIEW_PET");
INSERT INTO privileges(name) VALUES ("CAN_VIEW_ALL_PETS");
INSERT INTO privileges(name) VALUES ("CAN_VIEW_PENDING_PETS");
INSERT INTO privileges(name) VALUES ("CAN_VIEW_OWN_PETS");
INSERT INTO privileges(name) VALUES ("CAN_VIEW_APPROVED_MUNICIPALITY_PETS");
INSERT INTO privileges(name) VALUES ("CAN_DELETE_OWN_PETS");
INSERT INTO privileges(name) VALUES ("CAN_DELETE_ANY_PET");

INSERT INTO roles(name) VALUES ("ROLE_ADMIN");
INSERT INTO roles(name) VALUES ("ROLE_MUNICIPAL_EMPLOYEE");
INSERT INTO roles(name) VALUES ("ROLE_VET");
INSERT INTO roles(name) VALUES ("ROLE_CITIZEN");

INSERT INTO role_privileges(role_id, privilege_id) VALUES (1, 3);
INSERT INTO role_privileges(role_id, privilege_id) VALUES (1, 8);
INSERT INTO role_privileges(role_id, privilege_id) VALUES (2, 6);
INSERT INTO role_privileges(role_id, privilege_id) VALUES (3, 2);
INSERT INTO role_privileges(role_id, privilege_id) VALUES (3, 4);
INSERT INTO role_privileges(role_id, privilege_id) VALUES (4, 1);
INSERT INTO role_privileges(role_id, privilege_id) VALUES (4, 5);
INSERT INTO role_privileges(role_id, privilege_id) VALUES (4, 7);

INSERT INTO users(username, password, role_id, municipality_id) VALUES("admin", "$2a$12$X86hV8OPInflaBHX7rOWE.VsKV.2OZ.zam2hrsDw/dEcGkjW4ahWa", 1, 2);
INSERT INTO users(username, password, role_id, municipality_id) VALUES("municipal_employee", "$2a$12$91fgxiE7QsNRO/rWjBziA.wYICPurU9FXXaKbrwCFO85dkFNBb6CK", 2, 1);
INSERT INTO users(username, password, role_id, municipality_id) VALUES("vet", "$2a$12$ASYsDGbkN43zPlIlMG.df.lWRisW.w9519.J3Y8VNb47eFkK9JAOS", 3, 2);
INSERT INTO users(username, password, role_id, municipality_id) VALUES("citizen", "$2a$12$3VCO71DkbhTMwv4UfEyDSeqmG5Gr.WC9J6mkX6iYS8dNk64HexE76", 4, 1);


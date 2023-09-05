INSERT INTO "user" (id,user_name,password,full_name,enabled, search_key) VALUES (1,'administrator','$2a$10$STId1ieWQX6x4IfvBsP9EuwgLgCVxFlDSbohHnMFr8hmqxXk1RV56','Lazar','1', 'administrator');

INSERT INTO "user_role" (id, user_id, role) VALUES (1,1,'ADMIN');
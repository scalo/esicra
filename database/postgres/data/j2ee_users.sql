SET search_path = :dbschema;

DELETE FROM j2ee_users ;

INSERT INTO j2ee_users (user_name, user_pass) VALUES ('operatore', 'operatore1');
INSERT INTO j2ee_users (user_name, user_pass) VALUES ('operatore2', 'operatore2');
INSERT INTO j2ee_users (user_name, user_pass) VALUES ('amministratore', 'amministratore');
INSERT INTO j2ee_users (user_name, user_pass) VALUES ('tomcat', 'catalina4');

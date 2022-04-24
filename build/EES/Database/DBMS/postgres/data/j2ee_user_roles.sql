SET search_path = :dbschema;

DELETE FROM j2ee_user_roles ;

INSERT INTO j2ee_user_roles VALUES ('amministratore', 'amministratore');
INSERT INTO j2ee_user_roles VALUES ('operatore1', 'operatore');
INSERT INTO j2ee_user_roles VALUES ('operatore2', 'operatore');
INSERT INTO j2ee_user_roles VALUES ('tomcat', 'administrator');
INSERT INTO j2ee_user_roles VALUES ('tomcat', 'manager');



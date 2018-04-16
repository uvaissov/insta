CREATE DATABASE site
    WITH 
    OWNER = site
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;


CREATE TABLE "public"."role" (
	role_id int8 NOT NULL,
	"role" varchar(255) NOT NULL,
	PRIMARY KEY (role_id)
)
WITH (
	OIDS=FALSE
) ;

CREATE TABLE "public"."user" (
	user_id bigserial NOT NULL,
	active int4 NOT NULL,
	email varchar(255) NOT NULL,
	last_name varchar(255) NULL,
	"name" varchar(255) NULL,
	password varchar(255) NOT NULL,
	PRIMARY KEY (user_id)
)
WITH (
	OIDS=FALSE
) ;

CREATE TABLE "public".user_role (
	user_id int8 NOT NULL,
	role_id int8 NOT NULL,
	PRIMARY KEY (user_id, role_id),
	FOREIGN KEY (user_id) REFERENCES "user"(user_id),
	FOREIGN KEY (role_id) REFERENCES role(role_id),
	FOREIGN KEY (user_id) REFERENCES "user"(user_id)
)
WITH (
	OIDS=FALSE
) ;


CREATE TABLE IF NOT EXISTS user_credentials (
 id uuid DEFAULT gen_random_uuid() NOT NULL,
 email varchar(255) NOT NULL,
 password varchar(255) NOT NULL,
 role varchar(255) CHECK (role in ('ADMIN', 'MANAGER')),
 PRIMARY KEY (id),
 UNIQUE (email, password, role)
);

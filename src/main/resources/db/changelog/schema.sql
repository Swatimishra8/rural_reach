CREATE TABLE roles (
    id serial PRIMARY KEY,
    name varchar(50)
);

CREATE TABLE permissions (
    id serial PRIMARY KEY,
    name varchar(30)
);

CREATE TABLE roles_permissions (
    role_id int,
    permission_id int,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id),
    PRIMARY KEY (role_id, permission_id)
);

CREATE TABLE users (
    id serial PRIMARY KEY,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    phone_number varchar(13),
    email varchar(100) UNIQUE NOT NULL,
    password varchar(70) NOT NULL,
    address varchar(255),
    pin_code varchar(10),
    is_approved BOOLEAN DEFAULT false,
    role_id int,
    approval_status VARCHAR(50),
    CONSTRAINT fk_roleid FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE refresh_token (
  id SERIAL PRIMARY KEY,
  token VARCHAR(255) NOT NULL UNIQUE,
  expiry_date TIMESTAMP NOT NULL,
  user_id INT NOT NULL,
  CONSTRAINT fk_refresh_user FOREIGN KEY (user_id) REFERENCES users(id)
);

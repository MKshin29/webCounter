CREATE TABLE IF NOT EXISTS users(
     id BIGSERIAL PRIMARY KEY,
     username varchar(50) NOT NULL ,
     first_name varchar(50) NOT NULL,
     last_name varchar(50) NOT NULL,
     password varchar(50) NOT NULL,
     role varchar(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS sites(
     id BIGSERIAL,
     url varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS visits(
     id BIGSERIAL,
     user_id BIGSERIAL,
     site_id BIGSERIAL,
     date timestamp
);
CREATE TABLE public.app_user (
                                 id SERIAL PRIMARY KEY,
                                 first_name VARCHAR(255),
                                 last_name VARCHAR(255),
                                 username VARCHAR(255),
                                 password VARCHAR(255),
                                 email VARCHAR(255),
                                 image_url VARCHAR(255),
                                 create_date TIMESTAMP,
                                 modify_date TIMESTAMP
);
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

CREATE TABLE public.app_user_user_role (
                                           app_user_id INT NOT NULL,
                                           user_role VARCHAR(50) NOT NULL,
                                           CONSTRAINT fk_app_user FOREIGN KEY (app_user_id)
                                               REFERENCES public.app_user(id)
                                               ON DELETE CASCADE
);
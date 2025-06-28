INSERT INTO app_user (
    first_name,
    last_name,
    username,
    password,
    email,
    image_url,
    create_date,
    modify_date
) VALUES (
             'Benyamin',
             'Smith',
             'test10',
             '$2b$12$ytf8RUEeUB4imdjah/.KBuBQpPGN/9M7bMtToTP5jf1X3pRMj8kUi',
             'venya@example.com',
             'https://example.com/images/ivan.jpg',
             CURRENT_TIMESTAMP,
             CURRENT_TIMESTAMP
         );

INSERT INTO app_user_user_role (app_user_id, user_role) VALUES (

 (SELECT id FROM app_user WHERE username = 'test10'),
         'ADMIN'
                                                               );
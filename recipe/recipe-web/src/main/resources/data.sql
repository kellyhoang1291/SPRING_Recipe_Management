INSERT INTO role (name)
VALUES ('user');

INSERT INTO user (id, first_name, last_name, address, postal_code, username, password, enabled)
VALUES ('1', 'John', 'Doe', '123 Yonge St',
        '123 456', 'admin',
        '$2a$10$bN7OWEvi6rTqJEYbZfDOg.FHmG.xPTDxJR1k9LzsR4O6Nt8zuIKwq',
        '1');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

INSERT INTO RECIPE (id, name, cook_time, prep_time,total_time, ingredients, date_added, steps)
VALUES (1, 'Fried Rice', 5, 10, 15, 'rice, egg, oil, sausage, soy sauce', '2022-10-23', 'Mix ingredients into rice, Fry it!');


INSERT INTO users_recipes (user_id, recipe_id)
VALUES (1, 1);

INSERT INTO meal (id, date, name)
VALUES (1, '2022-11-07', 'Breakfast');

INSERT INTO meal_recipe (meal_id, recipe_id)
VALUES (1, 1);

INSERT INTO users_meals (meal_id, user_id)
VALUES (1, 1);

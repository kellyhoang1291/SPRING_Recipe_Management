INSERT INTO role (name) VALUES ('user');

-- add users
INSERT INTO user (id, first_name, last_name, address, postal_code, username, password, recoveryPIN, enabled)
VALUES ('1', 'sys', 'admin', 'King St',
        '000 000 0000', 'admin',
        '$2a$10$bN7OWEvi6rTqJEYbZfDOg.FHmG.xPTDxJR1k9LzsR4O6Nt8zuIKwq',
        '1234',
        '1');

INSERT INTO user (id, first_name, last_name, address, postal_code, username, password, recoveryPIN, enabled)
VALUES ('2', 'Kelly', 'Hoang', 'Queen St',
        '000 000 0000', 'kelly',
        '$2a$10$bN7OWEvi6rTqJEYbZfDOg.FHmG.xPTDxJR1k9LzsR4O6Nt8zuIKwq',
        '0000',
        '1');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1);

INSERT INTO users_roles (user_id, role_id)
VALUES (2, 1);

-- add recipes
INSERT INTO RECIPE (id, name, cook_time, prep_time,total_time, ingredients, date_added, steps)
VALUES (1, 'Fried Rice', 5, 10, 15, 'rice, egg, oil, sausage, soy sauce', '2022-10-23', 'Mix ingredients into rice, Fry it!');

INSERT INTO RECIPE (id, name, cook_time, prep_time,total_time, ingredients, date_added, steps)
VALUES (2, 'Test Recipe', 0, 0, 0,'No ingredients','2022-10-23', 'No steps to follow');

INSERT INTO RECIPE (id, name, cook_time, prep_time,total_time, ingredients, date_added, steps)
VALUES (3, 'Test Recipe 2', 0, 0, 0,'No ingredients','2022-10-23', 'No steps to follow');

INSERT INTO RECIPE (id, name, cook_time, prep_time,total_time, ingredients, date_added, steps)
VALUES (4, 'Test Recipe 3', 0, 0, 0,'Test Recipe 3','2022-10-24', 'No steps to follow');

INSERT INTO RECIPE (id, name, cook_time, prep_time,total_time, ingredients, date_added, steps)
VALUES (5, 'Test Recipe 4', 0, 0, 0,'Test Recipe 3','2022-10-24', 'No steps to follow');


INSERT INTO users_recipes (user_id, recipe_id)
VALUES (1, 1);

INSERT INTO users_recipes (user_id, recipe_id)
VALUES (1, 2);

INSERT INTO users_recipes (user_id, recipe_id)
VALUES (2, 3);

INSERT INTO users_recipes (user_id, recipe_id)
VALUES (2, 4);

INSERT INTO users_recipes (user_id, recipe_id)
VALUES (2, 5);

-- INSERT INTO recipe_like (user_id, recipe_id)
-- VALUES (1, 1);
--
-- INSERT INTO recipe_like (user_id, recipe_id)
-- VALUES (1, 2);
--
-- INSERT INTO recipe_like (user_id, recipe_id)
-- VALUES (2, 3);
--
-- INSERT INTO recipe_like (user_id, recipe_id)
-- VALUES (2, 4);
--
-- INSERT INTO recipe_like (user_id, recipe_id)
-- VALUES (2, 5);

-- add meals
INSERT INTO meal (id, date, name)
VALUES (1, '2022-11-07', 'Breakfast');

INSERT INTO meal (id, date, name)
VALUES (2, '2022-11-07', 'Lunch');

INSERT INTO meal (id, date, name)
VALUES (3, '2022-11-07', 'Dinner');

INSERT INTO meal_recipe (meal_id, recipe_id)
VALUES (1, 1);

INSERT INTO meal_recipe (meal_id, recipe_id)
VALUES (2, 2);

INSERT INTO meal_recipe (meal_id, recipe_id)
VALUES (3, 4);

INSERT INTO users_meals (meal_id, user_id)
VALUES (1, 1);

INSERT INTO users_meals (meal_id, user_id)
VALUES (2, 1);

INSERT INTO users_meals (meal_id, user_id)
VALUES (3, 1);

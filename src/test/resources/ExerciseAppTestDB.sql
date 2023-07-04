BEGIN TRANSACTION;

DROP TABLE IF EXISTS workout_list;
DROP TABLE IF EXISTS workout_event;
DROP TABLE IF EXISTS workout;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS exercise;

CREATE TABLE users (
	user_id serial NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	first varchar(25),
	last varchar(25),
	role varchar(50) NOT NULL,
	CONSTRAINT pk_user PRIMARY KEY (user_id)
);

CREATE TABLE exercise (
	exercise_id serial NOT NULL,
	exercise_name varchar(50) NOT NULL,
	CONSTRAINT pk_exercise PRIMARY KEY (exercise_id)
);

CREATE TABLE workout (
	workout_id serial NOT NULL,
	exercise_id int NOT NULL,
	num_sets int,
	num_reps int,
	weight int,
	workout_time int,
	distance numeric(4,1),
	CONSTRAINT pk_workout PRIMARY KEY (workout_id),
	CONSTRAINT fk_exercise_id FOREIGN KEY (exercise_id) REFERENCES exercise(exercise_id)
);

CREATE TABLE workout_event (
	event_id serial NOT NULL,
	user_id int NOT NULL,
	workout_id int NOT NULL,
	workout_date date NOT NULL,
	CONSTRAINT pk_workout_event PRIMARY KEY (event_id),
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT fk_workout_id FOREIGN KEY (workout_id) REFERENCES workout(workout_id)
);

CREATE TABLE workout_list (
	user_id int NOT NULL,
	workout_id int NOT NULL,
	UNIQUE (user_id, workout_id),
	CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users(user_id),
	CONSTRAINT fk_workout_id FOREIGN KEY(workout_id) REFERENCES workout(workout_id)
);

------------------------------ Test Data ---------------------------------

-- Users - all have password: 'password'
INSERT INTO users (user_id, username, password_hash, first, last, role)
	VALUES (100, 'admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'Diane', 'Gress', 'ROLE_ADMIN');
INSERT INTO users (user_id, username, password_hash, first, last, role)
	VALUES (101, 'user1','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'Stephen', 'Reale','ROLE_USER');
INSERT INTO users (user_id, username, password_hash, first, last, role)
	VALUES (102, 'user2','$2a$10$We8.y4IV/uQOPT1crppxR.aASgeKFr24ISrkHcqWWSYlxRu4oeqE6', 'Suzie', 'Q', 'ROLE_USER');

INSERT INTO exercise (exercise_name)
    VALUES ('exercise 1'),  -- 1
           ('exercise 2'),  -- 2
           ('exercise 3');  -- 3

INSERT INTO workout (exercise_id, num_sets, num_reps, weight, workout_time, distance)
    VALUES   (1, 2, 2, 2, 2, 10.5),  --1
    	     (1, 3, 3, 3, 3, 100.2), --2
    		 (2, 1, 1, 1, 1, 200.6), --3
    		 (2, 3, 4, 5, 6, 7.8);   --4

INSERT INTO workout_event (user_id, workout_id, workout_date)
    VALUES  (101, 1, '2023-07-03'),   --1
            (101, 2, '2023-07-04'),   --2
            (102, 1, '2023-07-03'),   --3
            (102, 3, '2023-07-03');   --4

INSERT INTO workout_list (user_id, workout_id)
    VALUES  (101, 1),
            (101, 2),
            (102, 1),
            (102, 3);

COMMIT;
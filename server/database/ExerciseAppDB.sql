DROP TABLE IF EXISTS workout_list;
DROP TABLE IF EXISTS workout_event;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS workout;
DROP TABLE IF EXISTS app_user;
DROP TABLE IF EXISTS exercise;

CREATE TABLE app_user (
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
	distance int,
	CONSTRAINT pk_workout PRIMARY KEY (workout_id),
	CONSTRAINT fk_exercise_id FOREIGN KEY (exercise_id) REFERENCES exercise(exercise_id)
);

CREATE TABLE event (
	event_id serial NOT NULL,
	user_id int NOT NULL,
	workout_date date NOT NULL,
	CONSTRAINT pk_workout_event PRIMARY KEY (event_id),
	CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES app_user(user_id)
);

CREATE TABLE workout_event (
	event_id int NOT NULL,
	workout_id int NOT NULL,
	CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES event(event_id),
	CONSTRAINT fk_workout_id FOREIGN KEY (workout_id) REFERENCES workout(workout_id),
	UNIQUE (event_id, workout_id)
);

CREATE TABLE workout_list (
	--list_id serial NOT NULL,
	user_id int NOT NULL,
	workout_id int NOT NULL,
	UNIQUE (user_id, workout_id),
	CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES app_user(user_id),
	CONSTRAINT fk_workout_id FOREIGN KEY(workout_id) REFERENCES workout(workout_id)
);

INSERT INTO app_user (username, password_hash, role)
    VALUES  ('new_user', '$2a$10$4ashShe3bhHl0QodoT1h8u69lmGfhvJTCnGPtidfGECSrYf9zbdKy', 'ROLE_USER'),
	        ('admin', '$2a$10$M36MeRrIJMEvuyjQDTs9qeGi/50nCBOcqNndK5sSL.Y8LShW2q68m', 'ROLE_ADMIN');
			
INSERT INTO exercise (exercise_name)
	VALUES ('Dead Lift'); --exercise_id 1
	
INSERT INTO workout (exercise_id, num_sets, num_reps, weight)
	VALUES   (1, 5, 5, 50), --workout_id 1
			 (1, 10, 10, 75); --workout_id 2
	
INSERT INTO event (user_id, workout_date)
	VALUES (1, '08-18-2023');
	
INSERT INTO workout_event (event_id, workout_id)
	VALUES  (1, 1),
			(1, 2);
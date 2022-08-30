  drop table if exists application_user, goals, users, authorities;
  
  CREATE TABLE application_user (
  email VARCHAR(255) NOT NULL,
  first_name VARCHAR(255) NULL,
  last_name VARCHAR(255) NULL,
  date_of_birth DATE NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (email));

CREATE TABLE goals (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NULL,
  image_src VARCHAR(255) NULL,
  target_date DATE NOT NULL,
  target_amount FLOAT NOT NULL,
  current_amount FLOAT NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES application_user(email),
  PRIMARY KEY (id));
  
create table users(
	username varchar(255) not null primary key,
	password varchar(255) not null,
	enabled boolean not null
);

create table authorities (
	username varchar(255) not null,
	authority varchar(255) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
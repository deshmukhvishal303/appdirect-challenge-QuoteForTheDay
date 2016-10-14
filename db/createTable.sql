drop table if exists user_master;
create table user_master(
 id int not null AUTO_INCREMENT,
 version INT,
 name varchar(20) not null,
 user_name varchar(40) not null,
 password varchar(20) not null,
 PRIMARY KEY(id),
 UNIQUE (user_name)
);


drop table if exists quote_master;
create table quote_master(
 id int not null AUTO_INCREMENT,
 quote varchar(2048) not null,
 created_at datetime not null,
 updated_at datetime not null,
 user_id int not null,

 PRIMARY KEY(id),
 FOREIGN KEY(user_id) REFERENCES user_master(id)
);
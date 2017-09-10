use db309sbb2;
create table user 
(
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR (16) NOT NULL,
username VARCHAR (16) NOT NULL,
age TINYINT NOT NULL,
password VARCHAR (16) NOT NULL,
primary key(id)
)

insert into user
values (, "matt", "cyclone1", 20, "iowastate");

select * from user;
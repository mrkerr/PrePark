select * from user;

delete from user where name = null;

delete from user;

SET SQL_SAFE_UPDATES = 0;

select username, password from user where email = "mlawlor@iastate.edu";

select username, email from user where email = "q" and username = "q";

drop table user;

create table user(
name varchar (32),
username varchar (32),
password varchar (32),
email varchar (32));

delete from user where name = " ";

insert into user values (" ", " ", " ", " ");
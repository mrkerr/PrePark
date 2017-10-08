select * from user;

delete from user;

SET SQL_SAFE_UPDATES = 0;

select username, password from user where email = "mlawlor@iastate.edu";

create table payment_history(
payment varchar (100));

insert into payment_history values ("Transaction: Price: 10.00 USD Location: Blaze Restaurant Ames IA Date: 10/05/17");

select * from payment_history;

delete from payment_history;







drop table payment_history;

select * from payment_history where user = "Matt";

insert into payment_history values ("Matt", "lot 23", '1997-02-28');

drop table user;

create table user(
name varchar (32),
username varchar (32),
password varchar (32),
email varchar (32));

create table lots(
address varchar (50),
city varchar (50),
state varchar (50),
zip varchar (50),
spots varchar (50),
time varchar (50),
rate varchar (50));

select * from lots;

delete from lots;

insert into lots values ("Brick City Grill", "Ames", "IA", "50010", "10", "60", "10");

SELECT address, city, state FROM lots;

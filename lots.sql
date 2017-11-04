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

insert into lots values ("Iowa State University", "Ames", "IA", "50010", "10", "60", "10");

SELECT address, city, state FROM lots;

delete from lots;
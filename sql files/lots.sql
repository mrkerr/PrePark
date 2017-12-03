create table lots(
username varchar (32),
address varchar (50),
city varchar (50),
state varchar (50),
zip varchar (50),
spots varchar (50),
time varchar (50),
rate varchar (50));

select * from lots;

drop table lots;

insert into lots values ("Iowa State University", "Ames", "IA", "50010", "10", "60", "10");

SELECT address, city, state FROM lots;

delete from lots where username = "rmdjawad";

SET SQL_SAFE_UPDATES = 0;

update lots set spots = 22, time = 100 where username = "mlawlor" and address = "2154 Hawthorn Ct Dr";

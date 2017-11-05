create table payment_history(
user varchar (32),
payment varchar (100));

insert into payment_history values ("Transaction: Price: 10.00 USD Location: Blaze Restaurant Ames IA Date: 10/05/17");

select * from payment_history;

delete from payment_history;

drop table payment_history;

select * from payment_history where user = "Matt";

insert into payment_history values ("Matt", "lot 23", '1997-02-28');
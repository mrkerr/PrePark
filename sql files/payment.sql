create table payment_history(
buyer varchar (32),
seller varchar (32),
payment varchar (100),
date date);

insert into payment_history values ("Transaction: Price: 10.00 USD Location: Blaze Restaurant Ames IA Date: 10/05/17");

SET SQL_SAFE_UPDATES = 0;

delete from payment_history where buyer is null;

select * from payment_history;

delete from payment_history;

drop table payment_history;

select * from payment_history where user = "Matt";

insert into payment_history values ("Matt", "lot 23", '1997-02-28');

insert into payment_history values ("Matt", "Ridwan", "lot 21", '1997-02-28');

select seller, payment, date from payment_history where month(date) = 2;

select * from payment_history where year(date) = 1997;

insert into payment_history values ("mlawlor", "mrkerr", "Transaction: Price: 5 USD Location: 1305 Georgia Ave Ames Iowa", '2017-12-04');

insert into payment_history values ("mlawlor", "mrkerr", "Transaction: Price: 6 USD Location: 4800 Mortensen Ames Iowa", '2017-12-01');

insert into payment_history values ("mlawlor", "mrkerr", "Transaction: Price: 5 USD Location: 2504 Lincoln Way Ames Iowa", '2017-05-26');

insert into payment_history values ("mlawlor", "mrkerr", "Transaction: Price: 5 USD Location: 1700 Center Drive Ames Iowa", '2017-09-29');

delete from payment_history where buyer = "mlawlor";
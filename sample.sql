--create table for storing orders data
--add partition column as part of primary key to guarantee uniqueness across all
--partitions in database and no unique constraint violation while repartitioning
create table orders (
order_id integer not null,
item_id integer,
product_id integer,
product_category varchar(30) not null,
user_id integer,
user_country varchar(20),
user_city varchar(20),
user_age integer,
primary key(order_id, product_category)
);

--table for exporting selected columns from orders table
--only insert is allowed for export tables, as data is queued and
--fetched by an export client. This feature is for incremental sync
--of data to external system
create table orders_export (
order_id integer not null,
product_id integer,
product_category varchar(30) not null,
user_id integer,
user_country varchar(20),
user_city varchar(20),
user_age integer
);

--VoltDB does not support auto increment, to implement this we can have a table
--to store max +1 as next value of identifier field and query this table in
--stored procedure. This will be a replicated table.
create table auto_increment(
table_name varchar(50) not null,
next_value integer,
primary key(table_name)
);

--Mark orders_export table as export only
EXPORT TABLE orders_export;

--Partition orders table. No need to partition export table as no data is stored for them.
partition table orders on column product_category;

--This is a small table and suied as replication table, but we need to write to this table
--while get and increment next value for a table, so partition this on primary key
partition table auto_increment on column table_name;

--register stored procedure written in Java
CREATE PROCEDURE FROM CLASS SaveOrder;
CREATE PROCEDURE FROM CLASS AutoIncrement;

--Partition stored procedure on same column as for table and provide parameter index
--for partition column in argument passed to procedure. By defaut its expected asfirst
--argument, in our procedure it will be 4rd argument (index 3).
PARTITION PROCEDURE SaveOrder ON TABLE orders COLUMN product_category PARAMETER 3;
PARTITION PROCEDURE AutoIncrement ON TABLE auto_increment COLUMN table_name;
# restaurantManager
得实现安装mysql数据库，然后在Util包下的DBUtil类中将database修改为你自己的数据库名称，密码是你自己的密码,建表完成后运行main就可以了
建表语句:
create table customer
(
  id          int auto_increment
    primary key,
  cname       varchar(50) null,
  phoneNumber varchar(50) null
)
  charset = utf8;
  create table table_
(
  id      int auto_increment
    primary key,
  number  int                         null,
  status_ varchar(50) default 'empty' null
)
  charset = utf8;
  create table reservation
(
  id          int auto_increment
    primary key,
  customer_id int      not null,
  arriveTime  datetime null,
  leftTime    datetime null,
  table_id    int      null,
  constraint reservation_customer_id_fk
  foreign key (customer_id) references customer (id)
    on update cascade,
  constraint reservation_table__id_fk
  foreign key (table_id) references table_ (id)
)
  charset = utf8;

create index reservation_customer_id_fk
  on reservation (customer_id);

create index reservation_table__id_fk
  on reservation (table_id);

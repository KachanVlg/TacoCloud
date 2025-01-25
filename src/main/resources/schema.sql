-- Удаление таблиц (если они существуют) для повторной инициализации
drop table if exists Taco_Order;
drop table if exists Ingredient_Ref;
drop table if exists Ingredient;
drop table if exists Taco;

-- Создание таблицы Taco_Order
create table if not exists Taco_Order (
    id identity primary key,
    delivery_Name varchar(50) not null,
    delivery_Street varchar(50) not null,
    delivery_City varchar(50) not null,
    delivery_State varchar(2) not null,
    delivery_Zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null
    );

-- Создание таблицы Ingredient
create table if not exists Ingredient (
    id varchar(4) not null primary key,
    name varchar(25) not null,
    type varchar(10) not null
    );

-- Создание таблицы Taco
create table if not exists Taco (
    id identity,
    name varchar(50) not null,
    taco_order bigint not null,
    taco_order_key bigint not null,
    created_at timestamp not null
    );


-- Добавление внешних ключей
alter table Taco
    add foreign key (taco_order) references Taco_Order(id);





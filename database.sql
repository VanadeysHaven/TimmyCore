drop database if exists s36706_timmy_core;
create database s36706_timmy_core;
use s36706_timmy_core;

create table users (
    uuid varchar(40) primary key,
    username varchar(40)
);

create table warps (
    name varchar(16) primary key,
    world varchar(40),
    x double,
    y double,
    z double,
    yaw float,
    pitch float,
    owner varchar(40),
    is_public boolean,
    foreign key (owner) references users(uuid)
);

insert into users(uuid, username) value (?,?) on duplicate key update username=?;
insert into warps(name, world, x, y, z, yaw, pitch, owner, is_public) value (?,?,?,?,?,?,?,?,?) on duplicate key update is_public=?;
select * from warps;
delete from warps where name=?;
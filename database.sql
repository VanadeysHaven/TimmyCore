set global log_bin_trust_function_creators = 1;
drop database if exists s36706_timmy_core;
create database s36706_timmy_core;
use s36706_timmy_core;

create table users (
    uuid varchar(40) primary key,
    username varchar(40)
);

create table user_settings (
    id int primary key,
    name varchar(40)
);

create table user_has_settings (
    setting_id int,
    uuid varchar(40),
    value varchar(40),
    primary key (setting_id, uuid),
    foreign key (setting_id) references user_settings(id),
    foreign key (uuid) references users(uuid)
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

delimiter //

create function get_user_setting_id(settingReference text)
returns int
begin
    return (select id from user_settings where name=settingReference);
end //

delimiter ;

insert into users(uuid, username) value (?,?) on duplicate key update username=?;
insert into warps(name, world, x, y, z, yaw, pitch, owner, is_public) value (?,?,?,?,?,?,?,?,?) on duplicate key update is_public=?;
select * from warps;
delete from warps where name=?;

select name from user_settings;
insert ignore into user_settings(name) value (?);
select us.name, ush.value from user_has_settings ush join user_settings us on ush.setting_id = us.id where uuid=?;
delete uhs from user_has_settings uhs join user_settings us on uhs.setting_id = us.id where uhs.uuid=? and us.name=?;
insert into user_has_settings (setting_id, uuid, value) values ((select get_user_setting_id(?)),?,?) on duplicate key update value=?;

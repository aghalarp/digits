# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table contact (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  telephone                 varchar(255),
  telephone_type            varchar(255),
  user_info_id              bigint,
  constraint pk_contact primary key (id))
;

create table user_info (
  id                        bigint not null,
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  admin                     boolean,
  constraint pk_user_info primary key (id))
;

create sequence contact_seq;

create sequence user_info_seq;

alter table contact add constraint fk_contact_userInfo_1 foreign key (user_info_id) references user_info (id) on delete restrict on update restrict;
create index ix_contact_userInfo_1 on contact (user_info_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists contact;

drop table if exists user_info;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists contact_seq;

drop sequence if exists user_info_seq;


# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "EMPLOYEE" ("id" BIGSERIAL NOT NULL PRIMARY KEY,"name" VARCHAR(254) NOT NULL,"address" VARCHAR(254) NOT NULL,"date_of_birth" BIGINT, "joining_date" BIGINT NOT NULL,"designation" VARCHAR(254));

# --- !Downs

drop table "EMPLOYEE";

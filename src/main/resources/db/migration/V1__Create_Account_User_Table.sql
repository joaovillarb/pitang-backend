create table account_user (
      id int8 generated by default as identity,
      created_at timestamp not null,
      modified_at timestamp,
      version int4,
      birth_day timestamp,
      email varchar(255),
      first_name varchar(255),
      last_name varchar(255),
      login varchar(255),
      password varchar(255),
      phone varchar(255),
      primary key (id)
)
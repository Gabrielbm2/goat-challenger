CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users (
                       id uuid default uuid_generate_v4(),
                       email text not null,
                       name text not null,
                       password text not null,
                       created_at timestamp default now(),
                       updated_at timestamp default now(),
                       primary key (id)
);

CREATE TABLE servants (
                          id uuid default uuid_generate_v4(),
                          email text not null,
                          name text not null,
                          cpf text not null,
                          registration_number text not null,
                          birth_date timestamp not null,
                          gender text not null,
                          type text not null,
                          created_at timestamp default now(),
                          updated_at timestamp default now(),
                          primary key (id)
);
CREATE TABLE specializations (
                                 id uuid DEFAULT uuid_generate_v4(),
                                 area text NOT NULL,
                                 type text NOT NULL,
                                 total_hours numeric(12,2) NOT NULL DEFAULT 1,
                                 total_coast numeric(12,2) NOT NULL DEFAULT 1,
                                 created_at timestamp DEFAULT now(),
                                 updated_at timestamp DEFAULT now(),
                                 PRIMARY KEY (id)
);

create table servant_specialization_rejections (
                                                   id uuid default uuid_generate_v4(),
                                                   servant_id uuid not null,
                                                   specialization_id uuid not null,
                                                   content text,
                                                   created_at timestamp default now(),
                                                   updated_at timestamp default now(),
                                                   primary key (id)
);

alter table servant_specialization_rejections add constraint servant_id_fk foreign key (servant_id)
    references servants(id) on delete cascade;

alter table servant_specialization_rejections add constraint specialization_id_fk foreign key (specialization_id)
    references specializations(id) on delete cascade;

create table servant_specialization_approvals (
                                                  id uuid default uuid_generate_v4(),
                                                  servant_id uuid not null,
                                                  specialization_id uuid not null,
                                                  created_at timestamp default now(),
                                                  updated_at timestamp default now(),
                                                  primary key (id)
);

alter table servant_specialization_approvals add constraint servant_id_fk foreign key (servant_id)
    references servants(id) on delete cascade;

alter table servant_specialization_approvals add constraint specialization_id_fk foreign key (specialization_id)
    references specializations(id) on delete cascade;
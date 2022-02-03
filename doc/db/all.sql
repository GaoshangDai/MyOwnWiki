drop table if exists `ebook`;
create table `ebook` (
                         `id` bigint not null comment 'id',
                         `name` varchar(50) comment 'name',
                         `category1_id` bigint comment 'category1',
                         `category2_id` bigint comment 'category2',
                         `description` varchar(200) comment 'decription',
                         `cover` varchar(200) comment 'cover',
                         `doc_count` int comment 'numDocs',
                         `view_count` int comment 'numReads',
                         `vote_count` int comment 'numVotes',
                         primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='ebooks';

insert into `ebook` (id, name, description) values (1, 'SpringBoot Intros', 'Entry Java development, the best preferred framework for enterprise-level application development');
insert into `ebook` (id, name, description) values (2, 'Vue Intros', 'Entry Vue development, the best preferred framework for enterprise-level application development');
insert into `ebook` (id, name, description) values (3, 'Python Intros', 'Entry Python development, the best preferred framework for enterprise-level application development');
insert into `ebook` (id, name, description) values (4, 'Mysql Intros', 'Entry MySQL development, the best preferred framework for enterprise-level application development');
insert into `ebook` (id, name, description) values (5, 'Oracle Intros', 'Entry Oracle development, the best preferred framework for enterprise-level application development');


drop table if exists `test`;
create table `test` (
                        `id` bigint not null,
                        `name` varchar(50),
                        `password` varchar(50),
                        primary key (`id`)
) engine=innodb default charset=utf8mb4;

insert into `test` (id, name, password) values (1, 'test', 'password');

drop table if exists `demo`;
create table `demo` (
                        `id` bigint not null,
                        `name` varchar(50),
                        primary key (`id`)
) engine=innodb default charset=utf8mb4;

insert into `demo` (id, name) values (1, 'test');
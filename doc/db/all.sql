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

insert into `ebook` (id, name, description) values (1, 'Spring Boot Intros', 'Entry Java development, the best preferred framework for enterprise-level application development');
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

drop table if exists `category`;
create table `category` (
      `id` bigint not null comment 'id',
      `parent` bigint not null default 0 comment 'parent id',
      `name` varchar(50) not null comment 'name',
      `sort` int comment 'order',
      primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='category';

insert into `category` (id, parent, name, sort) values (100, 000, 'Front End Development', 100);
insert into `category` (id, parent, name, sort) values (101, 100, 'Vue', 101);
insert into `category` (id, parent, name, sort) values (102, 100, 'HTML & CSS', 102);
insert into `category` (id, parent, name, sort) values (200, 000, 'Java', 200);
insert into `category` (id, parent, name, sort) values (201, 200, 'Basic', 201);
insert into `category` (id, parent, name, sort) values (202, 200, 'Framework', 202);
insert into `category` (id, parent, name, sort) values (300, 000, 'Python', 300);
insert into `category` (id, parent, name, sort) values (301, 300, 'Basic', 301);
insert into `category` (id, parent, name, sort) values (302, 300, 'Advanced', 302);
insert into `category` (id, parent, name, sort) values (400, 000, 'Database', 400);
insert into `category` (id, parent, name, sort) values (401, 400, 'MySQL', 401);
insert into `category` (id, parent, name, sort) values (500, 000, 'Other', 500);
insert into `category` (id, parent, name, sort) values (501, 500, 'Server', 501);
insert into `category` (id, parent, name, sort) values (502, 500, 'Dev Tools', 502);
insert into `category` (id, parent, name, sort) values (503, 500, 'Popular Server-side Languages', 503);

drop table if exists `doc`;
create table `doc` (
      `id` bigint not null comment 'id',
      `ebook_id` bigint not null default 0 comment 'ebook id',
      `parent` bigint not null default 0 comment 'parent id',
      `name` varchar(50) not null comment 'name',
      `sort` int comment 'order',
      `view_count` int default 0 comment 'numReads',
      `vote_count` int default 0 comment 'numVotes',
      primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='doc';

insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (1, 1, 0, '文档1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (2, 1, 1, '文档1.1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (3, 1, 0, '文档2', 2, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (4, 1, 3, '文档2.1', 1, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (5, 1, 3, '文档2.2', 2, 0, 0);
insert into `doc` (id, ebook_id, parent, name, sort, view_count, vote_count) values (6, 1, 5, '文档2.2.1', 1, 0, 0);

drop table if exists `content`;
create table `content` (
      `id` bigint not null comment 'doc-id',
      `content` mediumtext not null comment 'content',
      primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='doc content';
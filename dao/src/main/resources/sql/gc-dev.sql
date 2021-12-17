use gc;
create schema if not exists gc;

-- gift_certificate
create table gift_certificate(
	id int unsigned auto_increment primary key,
	name varchar(45) not null,
	description varchar(200) not null,
	price float unsigned not null,
	duration int not null,
	created_date datetime not null,
	modified_date datetime not null
);

-- tags
create table tags(
	id int unsigned auto_increment primary key,
	name varchar(45) not null,
	constraint name_UNIQUE unique (name)
);

-- gc_tag
create table gc_tag(
    gc_id  int unsigned not null,
    tag_id int unsigned not null,
    primary key (gc_id, tag_id),
    constraint fk_tag_has_gift_certificate_gift_certificate1 foreign key (gc_id) references gift_certificate (id) on delete cascade,
    constraint fk_tag_has_gift_certificate_tag foreign key (tag_id) references tags (id) on delete cascade
);
create index fk_tag_has_gift_certificate_gift_certificate1_idx on gc_tag (gc_id);
create index fk_tag_has_gift_certificate_tag_idx on gc_tag (tag_id);

-- gc_users
create table users(
	id int unsigned auto_increment primary key,
	login varchar(45) not null,
	password varchar(500) not null,
	constraint user_name_UNIQUE unique (login)
);

-- roles
create table roles(
	id int unsigned auto_increment primary key,
	name varchar(45) not null,
	constraint role_UNIQUE unique (name)
);

-- users_roles
create table users_roles(
	users_id int unsigned not null,
	roles_id int unsigned not null,
	primary key (users_id, roles_id),
	constraint fk_users_has_roles_roles1 foreign key (roles_id) references roles (id),
	constraint fk_users_has_roles_users1 foreign key (users_id) references users (id)
);
create index fk_users_has_roles_roles1_idx on users_roles (roles_id);
create index fk_users_has_roles_users1_idx on users_roles (users_id);

-- gc_orders
create table orders(
	id int unsigned auto_increment primary key,
	cost float not null,
	user_id int unsigned not null,
	cert_id int unsigned not null,
	created_date datetime not null,
	constraint fk_order_user1 foreign key (user_id) references users (id),
	constraint fk_cert_id foreign key (cert_id) references gift_certificate (id)
);
create index fk_order_user1_idx on orders (user_id);
create index fk_cert_id on orders (cert_id);

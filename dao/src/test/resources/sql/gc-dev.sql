use gc;
create schema if not exists gc;

-- gift_certificate
CREATE TABLE gift_certificate (
	id int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name varchar(45) NOT NULL,
	description varchar(200) NOT NULL,
	price float UNSIGNED NOT NULL,
	duration int NOT NULL,
	create_date datetime NOT NULL,
	last_update_date datetime NOT NULL
);

-- tag
CREATE TABLE tag (
	id int UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	name varchar(45) NOT NULL
);

-- gc_tag
CREATE TABLE gc_tag (
	gc_id int UNSIGNED NOT NULL,
	tag_id int UNSIGNED NOT NULL
);
CREATE INDEX fk_tag_has_gift_certificate_gift_certificate1_idx ON gc_tag (gc_id);
CREATE INDEX fk_tag_has_gift_certificate_tag_idx ON gc_tag (tag_id);

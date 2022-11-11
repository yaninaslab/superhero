DROP DATABASE IF EXISTS superherodb;
CREATE database superherodb;
USE superherodb;

create table SUPERPOWERS (
id int primary key AUTO_INCREMENT,
name varchar(30) not null
);

create table SUPERHERODETAILS (
id int primary key auto_increment,
name varchar(30) not null,
description varchar(100),
isvillan boolean not null default false,
superpowerid int not null,
foreign key (superpowerid) references SUPERPOWERS(id)
);

create table ORGANIZATIONDETAILS (
id int primary key auto_increment,
name varchar(30) not null,
description varchar(100),
address varchar(100),
contact varchar(50)
);

create table LOCATIONDETAILS (
id int primary key auto_increment,
name varchar(30) not null,
description varchar(100),
address varchar(100),
latitude varchar(30),
longitude varchar(30)
);

create table SUPERHEROSIGHTINGS (
id int primary key AUTO_INCREMENT,
sightingdate date not null,
superheroid int not null,
locationid int not null,
foreign key (superheroid) references SUPERHERODETAILS(id) ON UPDATE CASCADE ON DELETE CASCADE,
foreign key (locationid) references LOCATIONDETAILS(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table ORGHERODETAILS (
superheroid int not null,
organizationid int not null,
foreign key (superheroid) references SUPERHERODETAILS(id) ON UPDATE CASCADE ON DELETE CASCADE,
foreign key (organizationid) references ORGANIZATIONDETAILS(id) ON UPDATE CASCADE ON DELETE CASCADE
);



INSERT INTO `superpowers` (`id`, `name`) VALUES
	(1, 'Wallcrawling'),
	(2, 'Invisibility'),
	(3, 'Deflection'),
	(4, 'Camouflage'),
	(5, 'Heat Generation'),
	(6, 'Water Generation'),
	(7, 'Supernatural Vision'),
	(8, 'Supernatural Hearing');
	
commit;
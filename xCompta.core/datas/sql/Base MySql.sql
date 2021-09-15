drop database if exists xcompta;

create database xcompta charset=utf8;

use xcompta;

drop user if exists xcompta;

CREATE USER 'xcompta'@'127.0.0.1	' IDENTIFIED BY 'xcompta';

GRANT ALL PRIVILEGES ON xcompta.* TO 'xcompta'@'localhost';


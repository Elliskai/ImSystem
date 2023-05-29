CREATE DATABASE rezodb;
CREATE USER 'rezouser'@'localhost' IDENTIFIED BY 'Rezo_0000';
GRANT ALL ON rezodb.* TO 'rezouser'@'localhost';
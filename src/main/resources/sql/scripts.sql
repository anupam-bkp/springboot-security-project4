//For default database
create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);
//user 12345
INSERT IGNORE INTO `users` VALUES ('user', '{bcrypt}$2a$12$ChpNJ.g8D4DpuZhznsSnfOeD.9WxdYpOC64iaG2KE3DQkLDFQ1GgC', '1');
INSERT IGNORE INTO `authorities` VALUES ('user', 'read');
//admin 54321
INSERT IGNORE INTO `users` VALUES ('admin', '{bcrypt}$2a$12$2oV.eOTshycPHLUXQ1JZOeXXZkEKFwJg5D8Z4m/reoCRn2GSYHKYu', '1');
INSERT IGNORE INTO `authorities` VALUES ('admin', 'admin');


//For custom database
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT  INTO `customer` (`email`, `pwd`, `role`) VALUES ('happy@example.com', '{noop}EazyBytes@12345', 'read');
INSERT  INTO `customer` (`email`, `pwd`, `role`) VALUES ('admin@example.com', '{bcrypt}$2a$12$88.f6upbBvy0okEa7OfHFuorV29qeK.sVbB9VQ6J6dWM1bW6Qef8m', 'admin');

CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,

  PRIMARY KEY(id)
);

CREATE TABLE `municipalities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,

  PRIMARY KEY(id)
);

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `birthday` varchar(100),
  `municipality_id` int(11) NOT NULL,
  `phone` int(10),
  `email` varchar(100),
  `enabled` TINYINT NOT NULL DEFAULT 1,
  `role_id` int(11) NOT NULL,

   FOREIGN KEY (role_id) REFERENCES roles(id),
   FOREIGN KEY (municipality_id) REFERENCES municipalities(id),
   PRIMARY KEY(id)
);

CREATE TABLE `privileges` (
  id int(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,

  PRIMARY KEY (id)
);

CREATE TABLE `role_privileges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `privilege_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,

  FOREIGN KEY (privilege_id) REFERENCES privileges(id),
  FOREIGN KEY (role_id) REFERENCES roles(id),
  PRIMARY KEY (id)
);

CREATE TABLE `pets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `sex` varchar(100) NOT NULL,
  `birthday` varchar(100) NOT NULL,
  `microchip_number` int(11) NOT NULL,
  `breed` varchar(100) NOT NULL,
  `medical_history` TEXT,
  `status` varchar(100) NOT NULL DEFAULT "PENDING",
  `review_user_id` int(11),
  `owner_id` int(11),

  FOREIGN KEY (review_user_id) REFERENCES users(id),
  FOREIGN KEY (owner_id) REFERENCES users(id),
  PRIMARY KEY (id)
);
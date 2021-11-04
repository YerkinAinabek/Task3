CREATE TABLE `players` (
                           `player_id` int NOT NULL AUTO_INCREMENT,
                           `nickname` varchar(200) NOT NULL,
                           `player_lvl` int DEFAULT NULL,
                           `biography` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`player_id`),
                           UNIQUE KEY `nickname` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3
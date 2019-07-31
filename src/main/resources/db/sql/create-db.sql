
CREATE TABLE `REGISTER` (
  memberId INT(11) NOT NULL AUTO_INCREMENT,
  fname varchar(30),
  lname varchar(30),
  registeredTime varchar(30),
  email  VARCHAR(50),
  country VARCHAR(30),
  encryptedPass VARCHAR(50),
  saltKey VARCHAR(50),
  securityQuestion VARCHAR(50),
  securityAnswer VARCHAR(50),
  isActive VARCHAR(30),
  role VARCHAR(30),
  PRIMARY KEY (memberId)
) ENGINE=InnoDB;


INSERT INTO tennis.REGISTER
(fname, lname, registeredTime, email, country, encryptedPass, saltKey, securityQuestion, securityAnswer, isActive, `role`)
VALUES( 'Core Team', 'Member', '2019-07-27T23:41:30.331', 'nwatennis@gmail.com', 'United States', 'urSanssX9tZvQeyVGMIyX8iU9ZQM06akw8wNltQRK6M=', '2lyIswuTFCUmGkrzbYrK4uoqTg1YYA', 'What is your best friend name?', 'abc', 'Y', 'admin');


--DROP TABLE users IF EXISTS;

CREATE TABLE `TEAMS_MIXED` (
  `teamId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `firstName1` varchar(45) DEFAULT NULL,
  `lastName1` varchar(45) DEFAULT NULL,
  `email1` varchar(45) DEFAULT NULL,
  `phone1` varchar(45) DEFAULT NULL,
  `pool` varchar(45) DEFAULT NULL,
  `poolPosition` varchar(45) DEFAULT NULL,
  `rank` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`teamId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--DROP TABLE teams IF EXISTS;

CREATE TABLE `TEAMS_MENS` (
  `teamId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `firstName1` varchar(45) DEFAULT NULL,
  `lastName1` varchar(45) DEFAULT NULL,
  `email1` varchar(45) DEFAULT NULL,
  `phone1` varchar(45) DEFAULT NULL,
  `pool` varchar(45) DEFAULT NULL,
  `poolPosition` varchar(45) DEFAULT NULL,
  `rank` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`teamId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into TEAMS_MIXED(firstName, lastName,email,phone, firstName1, lastName1,email1, phone1, pool) VALUES('Vamsi', 'Singamaneni', 'vamsi.singamaneni@gmail.com', '617-378-1238','Sai', 'Chintapatla', 'sai.chintapatla@gmail.com', '617-3781238', 'A');


-- Schedule Table schema
CREATE TABLE `SCHEDULE_MIXED` (
  `matchNumber` int(11) NOT NULL AUTO_INCREMENT,
  `team1` varchar(45) DEFAULT NULL,
  `team2` varchar(45) DEFAULT NULL,
  `matchdate` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `winner` varchar(45) DEFAULT NULL,
  `homeTeamScore` VARCHAR(11) DEFAULT NULL,
  `awayTeamScore` VARCHAR(11) DEFAULT NULL,
  `isActive` int(11) DEFAULT TRUE,
  `matchDay` int(11) DEFAULT NULL,
  `pool` varchar(10) DEFAULT NULL,
  `matchKey` varchar(20) DEFAULT NULL,
  `referee` VARCHAR(200) DEFAULT NULL,
  `sets` INT(11) DEFAULT 1,
  PRIMARY KEY (`matchNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `SCHEDULE_MENS` (
  `matchNumber` int(11) NOT NULL AUTO_INCREMENT,
  `team1` varchar(45) DEFAULT NULL,
  `team2` varchar(45) DEFAULT NULL,
  `matchdate` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `winner` varchar(45) DEFAULT NULL,
  `homeTeamScore` VARCHAR(11) DEFAULT NULL,
  `awayTeamScore` VARCHAR(11) DEFAULT NULL,
  `isActive` BOOLEAN DEFAULT TRUE,
  `matchDay` int(11) DEFAULT NULL,
  `pool` varchar(10) DEFAULT NULL,
  `matchKey` varchar(20) DEFAULT NULL,
  `referee` VARCHAR(200) DEFAULT NULL,
  `sets` INT(11) DEFAULT 1,
  PRIMARY KEY (`matchNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `SCHEDULE_MENS_KNOCKOUTS` (
  `matchNumber` int(11) NOT NULL AUTO_INCREMENT,
  `team1` varchar(45) DEFAULT NULL,
  `team2` varchar(45) DEFAULT NULL,
  `matchdate` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `winner` varchar(45) DEFAULT NULL,
  `homeTeamScore1` VARCHAR(11) DEFAULT NULL,
  `homeTeamScore2` VARCHAR(11) DEFAULT NULL,
  `homeTeamScore3` VARCHAR(11) DEFAULT NULL,
  `awayTeamScore1` VARCHAR(11) DEFAULT NULL,
  `awayTeamScore2` VARCHAR(11) DEFAULT NULL,
  `awayTeamScore3` VARCHAR(11) DEFAULT NULL,
  `isActive` int(11) DEFAULT TRUE,
  `matchDay` int(11) DEFAULT NULL,
  `matchType` varchar(10) DEFAULT NULL,
  `matchKey` varchar(20) DEFAULT NULL,
  `sets` INT(11) DEFAULT 3,
  PRIMARY KEY (`matchNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `SCHEDULE_MIXED_KNOCKOUTS` (
  `matchNumber` int(11) NOT NULL AUTO_INCREMENT,
  `team1` varchar(45) DEFAULT NULL,
  `team2` varchar(45) DEFAULT NULL,
  `matchdate` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `winner` varchar(45) DEFAULT NULL,
  `homeTeamScore1` VARCHAR(11) DEFAULT NULL,
  `homeTeamScore2` VARCHAR(11) DEFAULT NULL,
  `homeTeamScore3` VARCHAR(11) DEFAULT NULL,
  `awayTeamScore1` VARCHAR(11) DEFAULT NULL,
  `awayTeamScore2` VARCHAR(11) DEFAULT NULL,
  `awayTeamScore3` VARCHAR(11) DEFAULT NULL,
  `isActive` int(11) DEFAULT TRUE,
  `matchDay` int(11) DEFAULT NULL,
  `matchType` varchar(10) DEFAULT NULL,
  `matchKey` varchar(20) DEFAULT NULL,
  `sets` INT(11) DEFAULT 3,
  PRIMARY KEY (`matchNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

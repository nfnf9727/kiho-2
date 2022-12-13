use user;

create table user
 (loginId char(6),
  password VARCHAR(10) NOT NULL,
  name VARCHAR(30) NOT NULL,
  lastLogin datetime,
  PRIMARY KEY (loginId));

create table postmsg
 (No INT(10) AUTO_INCREMENT,
  loginId char(6) NOT NULL,
  createdTime datetime,
  tag VARCHAR(50),
  picture VARCHAR(100),
  coments VARCHAR(200),
  iine INT(4),
  updateTime datetime,
  FOREIGN KEY fk_loginId(loginId) REFERENCES user(loginId),
  PRIMARY KEY (No));

create table comment
 (loginId char(6),
  createdTime datetime,
  commenter CHAR(6),
  commentTime datetime,
  comments VARCHAR(100),
  FOREIGN KEY fk_commenter(commenter) REFERENCES user(loginId),
  PRIMARY KEY (loginId, createdTime, commenter));
  
create table tag
 (tagID char(10),
  tag VARCHAR(20),
  PRIMARY KEY (tagID));



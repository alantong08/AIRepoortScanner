DROP TABLE IF EXISTS user_info;
CREATE TABLE user_info( 
	user_id INTEGER PRIMARY KEY,  
	mobile varchar(11) not null,
	user_name varchar(30) not null, 
	nick_name varchar(30) not null, 
	password varchar(30) not null, 
	alipay_account varchar(100) not null
	);

DROP TABLE IF EXISTS order_detail;
CREATE TABLE order_detail( 
    id INTEGER PRIMARY KEY,  
    scan_date date not null, 
    order_num varchar(50) not null, 
    merchant_name varchar(30) not null, 
    actual_amount varchar(20) not null, 
    transfer_type varchar(20) not null, 
    activity_type varchar(20) not null, 
    rate varchar(20) not null,
    comment varchar(50) default null,
    user_order_id INTEGER not NULL,
    FOREIGN KEY(user_order_id) REFERENCES user_info(user_id)
);

DROP TABLE IF EXISTS role;
CREATE TABLE role (
  role_id INTEGER PRIMARY KEY,
  role_name varchar(255) DEFAULT NULL
);


DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY(user_id) REFERENCES user_info(user_id),
  FOREIGN KEY(role_id) REFERENCES role(role_id)
);

CREATE DATABASE `kucun` /*!40100 DEFAULT CHARACTER SET utf8 */;

-- 用户表
CREATE TABLE user
(
    user_id      INT AUTO_INCREMENT PRIMARY KEY,
    user_name    VARCHAR(64)                        NOT NULL,
    password     VARCHAR(512)                       NOT NULL,
    cell_phone   VARCHAR(11)                        NOT NULL,
    email        VARCHAR(128)                       NULL,
    icon         VARCHAR(512)                       NULL,
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_time DATETIME                           NULL,
    is_deleted   BIT                                NULL,
    CONSTRAINT user_user_name_uindex
    UNIQUE (user_name),
    CONSTRAINT user_cell_phone_uindex
    UNIQUE (cell_phone)
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 颜色表
CREATE TABLE `color` (
  `color_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `rgb` varchar(16) NOT NULL,
  `user_id` INT not null,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE cascade,
  PRIMARY KEY (`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 尺码表
CREATE TABLE `size` (
  `size_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `size` int(11) NOT NULL,
  `max_height` int(11) DEFAULT NULL,
  `min_height` int(11) DEFAULT NULL,
  `user_id` INT not null,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE cascade,
  PRIMARY KEY (`size_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- 厂家表
CREATE TABLE `factory` (
  `factory_id` int(11) NOT NULL AUTO_INCREMENT,
  `factory_name` varchar(128) NOT NULL COMMENT '供货商/厂家',
  `link` varchar(1024) DEFAULT NULL COMMENT '厂家网址',
  `user_id` INT not null,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除',
  FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE cascade,
  PRIMARY KEY (`factory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 商品基本信息
CREATE TABLE `product_basic` (
  `product_basic_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(128) NOT NULL,
  `link` varchar(1024) NOT NULL COMMENT '宝贝链接',
  `user_id` INT not null,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除',
  FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE cascade,
  PRIMARY KEY (`product_basic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 供货商/厂家商品价格信息
CREATE TABLE `factory_product_basic` (
  `factory_product_basic_id` int(11) NOT NULL AUTO_INCREMENT,
  `factory_id` int(11) NOT NULL COMMENT '厂家Id',
  `product_basic_id` int(11) NOT NULL COMMENT '商品基本信息Id',
  `link` varchar(1024) DEFAULT NULL COMMENT '厂家宝贝链接',
  `market_price` decimal(10,2) NOT NULL COMMENT '批发价',
  `single_price` decimal(10,2) NOT NULL COMMENT '一件代发价',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除',
  FOREIGN KEY (factory_id) REFERENCES factory(factory_id) ON DELETE cascade,
  FOREIGN KEY (product_basic_id) REFERENCES product_basic(product_basic_id) ON DELETE cascade,
  PRIMARY KEY (`factory_product_basic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 商品详情（分颜色+尺寸）
CREATE TABLE `product_detail` (
  `product_detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_basic_id` int(11) NOT NULL,
  `color_id` int(11) NOT NULL COMMENT '颜色',
  `size_id` int(11) NOT NULL COMMENT '尺码',
  `total` int(11) NOT NULL DEFAULT '0' COMMENT '总库存',
  `sold` int(11) NOT NULL DEFAULT '0' COMMENT '已售数量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '出售单价',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  FOREIGN KEY (product_basic_id) REFERENCES product_basic(product_basic_id) ON DELETE cascade,
  FOREIGN KEY (color_id) REFERENCES color(color_id) ON DELETE cascade,
  FOREIGN KEY (size_id) REFERENCES size(size_id) ON DELETE cascade,
  PRIMARY KEY (`product_detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 销售记录
CREATE TABLE `sell_record` (
  `sell_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_detail_id` int(11) NOT NULL COMMENT '商品详情（分颜色+尺寸）Id',
  `total_number` int(11) NOT NULL COMMENT '售出数量',
  `total_price` decimal(10,2) NOT NULL COMMENT '总价',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0-进行中，1-完成，2-退货',
  `remark` varchar(256) NOT NULL COMMENT '备注',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `finished_time` datetime DEFAULT NULL COMMENT '完成时间',
  `is_deleted` bit(1) DEFAULT b'0' COMMENT '是否已删除',
  FOREIGN KEY (product_detail_id) REFERENCES product_detail(product_detail_id) ON DELETE cascade,
  PRIMARY KEY (`sell_record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO kucun.user (user_name, password, cell_phone, email, icon, created_time, updated_time, is_deleted) VALUES ('yxy', 'E10ADC3949BA59ABBE56E057F20F883E', '17603056679', null, '/avatar/16ed47a011e441a8bf705db090502b83.jpg', '2017-09-23 19:18:41', '2017-09-25 20:43:06', false);

-- 初始化数据
INSERT INTO `kucun`.`color`
(`name`,`rgb`,`user_id`)
VALUES
('红色','0xff0000',1),
('绿色','0x00ff00',1),
('蓝色','0x0000ff',1);

INSERT INTO `kucun`.`size`
(`name`,`size`)
VALUES
('90码','90',1),
('100码','100',1),
('110码','110',1),
('120码','120',1),
('130码','130',1),
('140码','140',1),
('150码','150',1),
('160码','160',1),
('170码','170',1),
('180码','180',1),
('190码','190',1);

drop table `kucun`.`color`;
drop table `kucun`.`size`;
drop table `kucun`.`factory`;
drop table `kucun`.`product_basic`;
drop table `kucun`.`factory_product_basic`;
drop table `kucun`.`product_detail`;
drop table `kucun`.`sell_record`;
drop table `kucun`.`user`;


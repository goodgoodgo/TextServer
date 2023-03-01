CREATE DATABASE Money_Management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


-- 使用sql创建用户表包含id(从1开始自增)，用户名，密码，性别，电话，创建时间，更新时间，是否删除，角色，状态
CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  gender varchar(32) NOT NULL DEFAULT 'M',
  phone VARCHAR(20) NOT NULL DEFAULT 'UNKNOWN',
  create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT 0,
  role ENUM('user', 'admin') NOT NULL DEFAULT 'user',
  status varchar(32) NOT NULL DEFAULT 'active',
  PRIMARY KEY (id)
);

-- 创建一个账本数据表，包含id字段(从1开始自增)，账本名称，支出(绑定支出表，对应id的支出数据变化时产生变化)，
-- 收入(绑定收入表，对应id的收入数据变化时产生变化)，创建时间，更新时间，是否删除，所属用户的id

CREATE TABLE ledger (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  expenditure DECIMAL(10, 2) DEFAULT 0,
  income DECIMAL(10, 2) DEFAULT 0,
  create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT 0,
  user_id INT NOT NULL,
  PRIMARY KEY (id)
  -- FOREIGN KEY (user_id) REFERENCES users(id)
);
ALTER TABLE ledger ADD COLUMN budget DECIMAL(10, 2) NOT NULL DEFAULT 0;


-- 创建支出数据表，包含id(从1开始自增），支出额度，支出类别，
-- 支出二级类别，备注，创建时间，更新时间，是否删除，创建人id，所属账单id
CREATE TABLE expenditure (
  id INT NOT NULL AUTO_INCREMENT,
  amount DECIMAL(10, 2) NOT NULL,
  category VARCHAR(50) NOT NULL,
  subcategory VARCHAR(50) DEFAULT NULL,
  comment VARCHAR(255) DEFAULT NULL,
  create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT 0,
  creator_id INT NOT NULL,
  ledger_id INT NOT NULL,
  PRIMARY KEY (id)
  -- FOREIGN KEY (creator_id) REFERENCES users(id),
  -- FOREIGN KEY (ledger_id) REFERENCES ledger(id)
);

-- 创建收入数据表，包含id(从1开始自增），收入额度，收入类别，收入二级类别，备注，
-- 创建时间，更新时间，是否删除，创建人id，所属账单id
CREATE TABLE income (
  id INT NOT NULL AUTO_INCREMENT,
  amount DECIMAL(10, 2) NOT NULL,
  category VARCHAR(50) NOT NULL,
  subcategory VARCHAR(50),
  comment VARCHAR(255),
  create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_deleted BOOLEAN NOT NULL DEFAULT 0,
  creator_id INT NOT NULL,
  ledger_id INT NOT NULL,
  PRIMARY KEY (id)
  -- CONSTRAINT fk_income_creator_id FOREIGN KEY (creator_id) REFERENCES users (id),
  -- CONSTRAINT fk_income_bill_id FOREIGN KEY (ledger_id) REFERENCES ledger (id)
);

-- 创建一个用户信息表包含用户id(自增)，打卡天数，记账总天数，获得勋章数，记账方式(用数字表示)，
-- 模块选择(用数字表示)，字体设置，日历周起始日，账单月起始日,创建时间，更新时间，是否打卡(每天晚上12:00自动变否)
CREATE TABLE user_info (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    punch_days INT DEFAULT 0,
    total_days INT DEFAULT 0,
    medals INT DEFAULT 0,
    bookkeeping_method TINYINT DEFAULT 0 comment '1,2,4,8 类别选择与linux权限设置类似',
    module_selection TINYINT DEFAULT 0,
    font_setting TINYINT DEFAULT 0,
    calendar_start_day TINYINT DEFAULT 0,
    bill_start_day TINYINT DEFAULT 1,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_checked TINYINT DEFAULT 0
);


CREATE TABLE `cache` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `key` varchar(100) NOT NULL COMMENT '缓存键',
    `value` longtext NOT NULL COMMENT '缓存值',
    `type` varchar(64) NOT NULL COMMENT '业务类型',
    `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '删除标识（0:未删除、1:已删除）',
    `create_at` datetime NOT NULL COMMENT '记录创建时间',
    `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='缓存表';

CREATE TABLE `opt_log` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `type` varchar(64) NOT NULL COMMENT '操作类型',
    `operator_id` bigint(11) NOT NULL COMMENT '操作人ID',
    `content` longtext NOT NULL COMMENT '操作内容',
    `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '删除标识（0:未删除、1:已删除）',
    `create_at` datetime NOT NULL COMMENT '记录创建时间',
    `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
    PRIMARY KEY (`id`),
    KEY `idx_operator_id` (`operator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- 是否打卡(每天晚上12:00自动变否)
CREATE EVENT reset_user_checked_status
    ON SCHEDULE EVERY 1 DAY
        STARTS CURRENT_TIMESTAMP
        ON COMPLETION PRESERVE
    DO
        UPDATE user_info SET is_checked = 0;


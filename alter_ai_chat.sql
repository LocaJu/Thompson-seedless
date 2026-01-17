-- AI 聊天：会话 & 消息
-- 表前缀遵循现有博客业务：tb_

CREATE TABLE IF NOT EXISTS `tb_ai_chat_session` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL COMMENT '归属用户ID（sys_user.user_id）',
  `title` VARCHAR(200) DEFAULT NULL COMMENT '会话标题',
  `last_message_time` DATETIME DEFAULT NULL COMMENT '最近消息时间',
  `create_by` BIGINT DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `update_by` BIGINT DEFAULT NULL,
  `update_time` DATETIME DEFAULT NULL,
  `del_flag` INT DEFAULT 0 COMMENT '0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_ai_chat_session_user` (`user_id`),
  KEY `idx_ai_chat_session_last_time` (`last_message_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI聊天会话表';


CREATE TABLE IF NOT EXISTS `tb_ai_chat_message` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `session_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `role` VARCHAR(20) NOT NULL COMMENT 'user/assistant/system',
  `content` TEXT COMMENT '消息内容',
  `create_by` BIGINT DEFAULT NULL,
  `create_time` DATETIME DEFAULT NULL,
  `update_by` BIGINT DEFAULT NULL,
  `update_time` DATETIME DEFAULT NULL,
  `del_flag` INT DEFAULT 0 COMMENT '0未删除 1已删除',
  PRIMARY KEY (`id`),
  KEY `idx_ai_chat_message_session` (`session_id`),
  KEY `idx_ai_chat_message_user` (`user_id`),
  KEY `idx_ai_chat_message_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI聊天消息表';


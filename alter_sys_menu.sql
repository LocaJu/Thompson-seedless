-- 为 sys_menu 表添加缺失的字段
-- 执行此脚本以修复 "Unknown column 'm.path' in 'field list'" 错误
-- 
-- 注意：如果字段已存在，执行时会报错 "Duplicate column name"
-- 可以忽略该错误，继续执行其他字段的添加

-- 添加 path 字段（路由地址）
ALTER TABLE sys_menu 
ADD COLUMN path VARCHAR(200) DEFAULT '' COMMENT '路由地址';

-- 添加 component 字段（组件路径）
ALTER TABLE sys_menu 
ADD COLUMN component VARCHAR(255) DEFAULT NULL COMMENT '组件路径';

-- 添加 query 字段（路由参数）
ALTER TABLE sys_menu 
ADD COLUMN query VARCHAR(255) DEFAULT NULL COMMENT '路由参数';

-- 添加 is_frame 字段（是否为外链：0是 1否）
ALTER TABLE sys_menu 
ADD COLUMN is_frame TINYINT(1) DEFAULT 1 COMMENT '是否为外链（0是 1否）';

-- 添加 is_cache 字段（是否缓存：0缓存 1不缓存）
ALTER TABLE sys_menu 
ADD COLUMN is_cache TINYINT(1) DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）';


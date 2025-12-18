-- ============================================
-- 数据库表结构修复脚本
-- 修复 sys_role 和 sys_menu 表缺失的字段
-- 
-- 注意：如果字段已存在，执行时会报错 "Duplicate column name"
-- 可以忽略该错误，继续执行其他字段的添加
-- ============================================

-- ============================================
-- 1. 修复 sys_role 表
-- ============================================
-- 添加 menu_check_strictly 字段（菜单树选择项是否关联显示）
-- 0：父子不互相关联显示 1：父子互相关联显示
ALTER TABLE sys_role 
ADD COLUMN menu_check_strictly TINYINT(1) DEFAULT 1 COMMENT '菜单树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示）';

-- 添加 dept_check_strictly 字段（部门树选择项是否关联显示）
-- 0：父子不互相关联显示 1：父子互相关联显示
ALTER TABLE sys_role 
ADD COLUMN dept_check_strictly TINYINT(1) DEFAULT 1 COMMENT '部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示）';

-- ============================================
-- 2. 修复 sys_menu 表
-- ============================================
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


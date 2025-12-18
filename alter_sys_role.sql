-- 为 sys_role 表添加缺失的字段
-- 执行此脚本以修复 "Unknown column 'r.menu_check_strictly' in 'field list'" 错误

-- 添加 menu_check_strictly 字段（菜单树选择项是否关联显示）
-- 0：父子不互相关联显示 1：父子互相关联显示
ALTER TABLE sys_role 
ADD COLUMN menu_check_strictly TINYINT(1) DEFAULT 1 COMMENT '菜单树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示）';

-- 添加 dept_check_strictly 字段（部门树选择项是否关联显示）
-- 0：父子不互相关联显示 1：父子互相关联显示
ALTER TABLE sys_role 
ADD COLUMN dept_check_strictly TINYINT(1) DEFAULT 1 COMMENT '部门树选择项是否关联显示（0：父子不互相关联显示 1：父子互相关联显示）';



package com.seed.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seed.domain.entity.Article;
import com.seed.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 77286
* @description 针对表【tb_link(友链)】的数据库操作Mapper
* @createDate 2023-12-16 21:03:57
* @Entity seed/domain.entity.Link
*/

public interface LinkMapper  extends BaseMapper<Link> {



}

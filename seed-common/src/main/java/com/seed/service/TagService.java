package com.seed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.TagDTO;
import com.seed.domain.entity.Tag;
import com.seed.domain.vo.PageVo;
import com.seed.domain.vo.TagVo;

/**
* @author 77286
* @description 针对表【tb_tag(标签)】的数据库操作Service
* @createDate 2023-12-16 22:02:52
*/
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDTO tagDTO);

    ResponseResult addTag(TagDTO tagDTO);

    ResponseResult deleteTagById(Long id);

    ResponseResult getTagById(Long id);

    ResponseResult updateTag(TagDTO tagDTO);
}

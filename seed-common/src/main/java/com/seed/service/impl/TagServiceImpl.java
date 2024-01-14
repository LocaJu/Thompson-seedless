package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.TagDTO;
import com.seed.domain.entity.Tag;
import com.seed.domain.vo.PageVo;
import com.seed.domain.vo.TagVo;
import com.seed.service.TagService;
import com.seed.mapper.TagMapper;
import com.seed.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
* @author 77286
* @description 针对表【tb_tag(标签)】的数据库操作Service实现
* @createDate 2023-12-16 22:02:52
*/

@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
implements TagService{

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagDTO tagDTO) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StringUtils.hasText(tagDTO.getName()),Tag::getName, tagDTO.getName())
                .eq(StringUtils.hasText(tagDTO.getRemark()),Tag::getRemark, tagDTO.getRemark());
        //分页查询
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        //封装数据
        List<Tag> records = page.getRecords();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(records, TagVo.class);
        PageVo pageVo = new PageVo(tagVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagDTO tagDTO) {
        baseMapper.insert(BeanCopyUtils.copyBean(tagDTO, Tag.class));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTagById(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Long id) {
        Tag tag = baseMapper.selectById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(TagDTO tagDTO) {
        baseMapper.updateById(BeanCopyUtils.copyBean(tagDTO, Tag.class));
        return ResponseResult.okResult();
    }
}

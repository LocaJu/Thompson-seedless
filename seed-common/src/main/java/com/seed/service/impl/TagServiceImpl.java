package com.seed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.entity.Tag;
import com.seed.service.TagService;
import com.seed.mapper.TagMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
* @author 77286
* @description 针对表【tb_tag(标签)】的数据库操作Service实现
* @createDate 2023-12-16 22:02:52
*/

@Slf4j
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
implements TagService{

}

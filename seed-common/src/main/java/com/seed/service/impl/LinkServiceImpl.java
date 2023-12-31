package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.constants.SystemConstants;
import com.seed.domain.ResponseResult;
import com.seed.domain.entity.Link;
import com.seed.domain.vo.LinkVo;
import com.seed.mapper.LinkMapper;
import com.seed.service.LinkService;
import com.seed.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/16 21:25
 */
@Service
@Slf4j
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Override
    public ResponseResult getAllLink() {

        //查询所有审核通过的状态
        LambdaQueryWrapper<Link> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(lambdaQueryWrapper);
        //转换为VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);

        //封装ResponseResult
        return ResponseResult.okResult(linkVos);
    }
}

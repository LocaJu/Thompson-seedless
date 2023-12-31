package com.seed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.entity.Menu;
import com.seed.mapper.MenuMapper;
import com.seed.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 77286
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-12-16 21:45:49
*/
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
implements MenuService{

}

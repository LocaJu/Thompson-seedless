//package com.seed.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.seed.constants.SystemConstants;
//import com.seed.domain.entity.Menu;
//import com.seed.mapper.MenuMapper;
//import com.seed.service.MenuService;
//import com.seed.utils.SecurityUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author 77286
// * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
// * @createDate 2023-12-16 21:45:49
// */
//@Service
//@Slf4j
//public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
//        implements MenuService {
//
//    @Override
//    public List<String> selectPermByUserId(Long id) {
//        //如果是管理员，返回所有的权限
//        if (id.equals(1L)) {
//            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
//            wrapper.in(Menu::getMenuType, SystemConstants.MENU, SystemConstants.BUTTON);
//            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
//            List<Menu> menus = list(wrapper);
//            List<String> perms = menus.stream()
//                    .map(Menu::getPerms)
//                    .collect(Collectors.toList());
//            return perms;
//        }
//        //否则返回所具有的权限
//        return getBaseMapper().selectPermsByUserId(id);
//
//    }
//
//    @Override
//    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
//        MenuMapper menuMapper = getBaseMapper();
//        List<Menu> menus = null;
//        //判断是否是管理员
//        if (SecurityUtils.isAdmin()) {
//            //如果是 获取所有符合要求的Menu
//            menus = menuMapper.selectAllRouterMenu();
//        } else {
//            //否则 获取当前用户所具有的Menu
//            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
//        }
//        //构建tree
//        //先找出第一层的菜单 然后去找他们的子菜单设置到children属性中
//        List<Menu> menuTree = builderMenuTree(menus, 0L);
//        return menuTree;
//    }
//
//    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
//        List<Menu> menuTree = menus.stream()
//                .filter(menu -> menu.getParentId().equals(parentId))
//                .map(menu -> menu.setChildren(getChildren(menu, menus)))
//                .collect(Collectors.toList());
//        return menuTree;
//    }
//
//    /**
//     * 获取存入参数的 子Menu集合
//     * @param menu
//     * @param menus
//     * @return
//     */
//    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
//        List<Menu> childrenList = menus.stream()
//                .filter(m -> m.getParentId().equals(menu.getId()))
//                .map(m->m.setChildren(getChildren(m,menus)))
//                .collect(Collectors.toList());
//        return childrenList;
//    }
//}

package com.seed.utils;

import com.seed.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2023/12/18 19:40
 */
public class SecurityUtils {
    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }
    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }
    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }

}

package com.seed.service.system.web.service;


import com.seed.domain.entity.system.SysUser;
import com.seed.ruoyi.constant.CacheConstants;
import com.seed.ruoyi.exception.user.UserPasswordNotMatchException;
import com.seed.ruoyi.exception.user.UserPasswordRetryLimitExceedException;
import com.seed.service.system.security.context.AuthenticationContextHolder;
import com.seed.utils.RedisCache;
import com.seed.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录密码方法
 * 
 * @author ruoyi
 */
@Component
public class SysPasswordService
{
    @Autowired
    private RedisCache redisCache;

    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;

    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    /**
     * 登录账户密码错误次数缓存键名
     * 
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username)
    {
        return CacheConstants.PWD_ERR_CNT_KEY + username;
    }

    public void validate(SysUser user)
    {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();

        Integer retryCount = redisCache.getCacheObject(getCacheKey(username));

        if (retryCount == null)
        {
            retryCount = 0;
        }

        if (retryCount >= Integer.valueOf(maxRetryCount).intValue())
        {
            throw new UserPasswordRetryLimitExceedException(maxRetryCount, lockTime);
        }

        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            redisCache.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new UserPasswordNotMatchException();
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(SysUser user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName)
    {
        if (redisCache.hasKey(getCacheKey(loginName)))
        {
            redisCache.deleteObject(getCacheKey(loginName));
        }
    }
}

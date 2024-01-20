package com.seed.service.impl;

import com.seed.domain.ResponseResult;
import com.seed.domain.model.LoginUser;
import com.seed.ruoyi.constant.CacheConstants;
import com.seed.ruoyi.constant.Constants;
import com.seed.ruoyi.core.domain.AjaxResult;
import com.seed.ruoyi.exception.user.CaptchaException;
import com.seed.ruoyi.exception.user.CaptchaExpireException;
import com.seed.ruoyi.manager.factory.AsyncFactory;
import com.seed.ruoyi.manager.factory.AsyncManager;
import com.seed.ruoyi.utils.MessageUtils;
import com.seed.ruoyi.utils.StringUtils;
import com.seed.service.LoginService;
import com.seed.service.system.security.context.AuthenticationContextHolder;
import com.seed.utils.JwtUtil;
import com.seed.utils.RedisCache;
import com.seed.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.seed.utils.RedisConstants.LOGIN;

/**
 * @author 77286
 * @version 1.0
 * @description: TODO
 * @date 2024/1/10 20:49
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public AjaxResult login(String username, String password, String code, String uuid) {
        AjaxResult ajax = AjaxResult.success();

        // 验证码校验
        validateCaptcha(username, code, uuid);

        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        AuthenticationContextHolder.setContext(authenticationToken);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //UserDetails loginUser1 = (UserDetails) authenticate.getPrincipal();
        String userId = loginUser.getUser().getUserId().toString();
        String token = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(LOGIN +userId,loginUser);
        //把token和userInfo封装 返回
        //将User转换为UserInfoVo
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
//        BlogUserInfoVo blogUserInfoVo=new BlogUserInfoVo(jwt,userInfoVo);
        return ajax.put(Constants.TOKEN, token);
    }

    @Override
    public ResponseResult logout() {
        /**
         * 获取token 解析获取userId
         * Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         * LoginUser loginUser = (LoginUser)authentication.getPrincipal();
         * 获取userId
         * Long userId = loginUser.getUser().getId();
         **/

        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject(LOGIN+userId);
        return ResponseResult.okResult();
    }



    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
//        boolean captchaEnabled = configService.selectCaptchaEnabled();
//        if (captchaEnabled)
//        {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (captcha == null)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            if (!code.equalsIgnoreCase(captcha))
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
//        }
    }
}

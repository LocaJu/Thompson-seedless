package com.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seed.domain.ResponseResult;
import com.seed.domain.dto.UserInfoDTO;
import com.seed.domain.dto.UserRegiterDTO;
import com.seed.domain.entity.User;
import com.seed.domain.vo.UserInfoVo;
import com.seed.enums.AppHttpCodeEnum;
import com.seed.exception.SystemException;
import com.seed.mapper.UserMapper;
import com.seed.service.UserService;
import com.seed.utils.BeanCopyUtils;
import com.seed.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @author 77286
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2023-12-16 21:51:29
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Value("${qiniu.CDN}")
    String CDN;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //格局id查询用户信息
        User user = getById(userId);
        //封装UserInfoVo返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //将用户中的路径加上域名
        /**
         *                                 /2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
         * http://s66bj54ml.bkt.clouddn.com/2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
         **/
        userInfoVo.setAvatar(CDN + userInfoVo.getAvatar());

        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(UserInfoDTO userInfoDTO) {
        //头像路径提取（去掉域名）
        String avatarUrl = userInfoDTO.getAvatar();
        //                 http://s66bj54ml.bkt.clouddn.com/2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
        //得到文件路径 如：                                   /2023/12/30/2/2eacc66309ade14644a6b9b4f9bbf0ee/2eacc66309ade14644a6b9b4f9bbf0ee.csv
        String filepath = avatarUrl.replace(CDN, "");

        User user = BeanCopyUtils.copyBean(userInfoDTO, User.class);
        user.setAvatar(filepath);

        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(UserRegiterDTO userRegiterDTO) {

        //对数据进行非空判断
        if (!StringUtils.hasText(userRegiterDTO.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!StringUtils.hasText(userRegiterDTO.getPassword())) {
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegiterDTO.getNickName())) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if (!StringUtils.hasText(userRegiterDTO.getEmail())) {
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }

        //对数据进行是否存在判断
        if (UserNameExist(userRegiterDTO)) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (NickNameExist(userRegiterDTO)) {
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        //对密码进行加密
        String encodePassword = passwordEncoder.encode(userRegiterDTO.getPassword());
        userRegiterDTO.setPassword(encodePassword);

        //类型转换并存入数据库
        User user = BeanCopyUtils.copyBean(userRegiterDTO, User.class);
        save(user);
        return ResponseResult.okResult();
    }


    /**
     * @description: 判断用户是否已存在
     * @author: 77286
     * @date: 2023/12/31 21:52
     * @param: UserRegiterDTO userRegiterDTO
     * @return: boolean
     * @version 1.0
     **/
    private boolean UserNameExist(UserRegiterDTO userRegiterDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userRegiterDTO.getUserName());
        return count(queryWrapper) > 0;
    }

    //判断昵称是否已存在
    private boolean NickNameExist(UserRegiterDTO userRegiterDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName, userRegiterDTO.getNickName());
        return count(queryWrapper) > 0;
    }
}

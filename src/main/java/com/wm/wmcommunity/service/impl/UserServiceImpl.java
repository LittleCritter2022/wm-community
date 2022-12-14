package com.wm.wmcommunity.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wm.wmcommunity.common.constant.CodeEnum;
import com.wm.wmcommunity.common.util.*;
import com.wm.wmcommunity.entity.LoginTicket;
import com.wm.wmcommunity.entity.UserEntity;
import com.wm.wmcommunity.entity.dto.LoginQuery;
import com.wm.wmcommunity.entity.dto.RegisterDto;
import com.wm.wmcommunity.entity.dto.UpdateDto;
import com.wm.wmcommunity.mapper.UserMapper;
import com.wm.wmcommunity.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> UserServiceImpl
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/29
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/29                 Gerry(0120)                 Create
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
@Service(value = "userServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService, CommunityConstants {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Value("${uim.contextPath}")
    private String contextPath;

    @Override
    public Response login(LoginQuery loginQuery, HttpServletRequest request, HttpServletResponse response) {
        String username = loginQuery.getUsername();
        String password = loginQuery.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Response.error(CodeEnum.ERROR_PARAM);
        }
        String kaptchaOwner = CookieUtil.getValue(request, "kaptChaOwner");
        String kaptcha = (String) redisTemplate.opsForValue().get(RedisKeyUtil.getKaptchaKey(kaptchaOwner));

        //???????????????????????? ????????????redis??????????????????????????? ????????????
        if (StringUtils.isBlank(loginQuery.getCode()) || StringUtils.isBlank(kaptcha)
                || !kaptcha.equalsIgnoreCase(loginQuery.getCode())) {
            return Response.error(CodeEnum.ERROR_USER_AUTH_FAILED);
        }

        UserEntity userEntity = userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getUsername, username));

        String md5Password = CommunityUtil.md5(password + userEntity.getSalt());

        if (userEntity == null) {
            return Response.error(CodeEnum.ERROR_LOGIN_NOT_EXIT);
        }

        if (!md5Password.equals(userEntity.getPassword())) {
            return Response.error(CodeEnum.ERROR_USER_AUTH_FAILED);
        }

        //??????????????????????????????????????????????????????
        hostHolder.setUser(userEntity);
        //?????????????????? ???????????? ?????????????????????
        int expiredSeconds = loginQuery.getRememberMe() ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        //??????????????????
        LoginTicket loginTicket = LoginTicket.builder()
                .userId(userEntity.getId())
                .ticket(CommunityUtil.generateUUID())
                .expired(new Date(System.currentTimeMillis() + expiredSeconds))
                .status(0)
                .build();
        redisTemplate.opsForValue().set(RedisKeyUtil.getTicketKey(loginTicket.getTicket()), loginTicket);

        //???ticket?????????cookie??? ?????????????????????
        Cookie cookie = new Cookie("ticket", loginTicket.getTicket());
        cookie.setMaxAge(expiredSeconds);
        cookie.setPath(contextPath);
        response.addCookie(cookie);
        return Response.success(loginTicket);
    }

    @Override
    public Response register(RegisterDto registerDto) {
        if (Objects.isNull(registerDto) || StringUtils.isBlank(registerDto.getUsername())
                || StringUtils.isBlank(registerDto.getPassword()) || StringUtils.isBlank(registerDto.getEmail())) {
            return Response.error(CodeEnum.ERROR_PARAM);
        }
        UserEntity userEntity = userMapper.selectOne(Wrappers.<UserEntity>lambdaQuery()
                .eq(UserEntity::getUsername, registerDto.getUsername()));
        if (userEntity != null) {
            return Response.error(CodeEnum.ERROR_DEPT_USER_EXIST);
        }
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(registerDto, user);
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(registerDto.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setCreateTime(new Date());
//        user.setHeaderUrl();
        userMapper.insert(user);

        return Response.success();
    }

    @Override
    public Response updateUser(UpdateDto updateDto) {
        UserEntity user = userMapper.selectById(updateDto.getId());
        if (user == null) {
            return Response.error(CodeEnum.ERROR_LOGIN_NOT_EXIT);
        }
        if (user.getUsername().equals(updateDto.getUsername())) {
            return Response.error(CodeEnum.ERROR_DEPT_USER_EXIST);
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(updateDto, userEntity);
        userEntity.setPassword(CommunityUtil.md5(updateDto.getPassword()));
        userMapper.updateById(userEntity);
        return Response.success();
    }

    @Override
    public Response deleteById(Integer id) {
        UserEntity user = userMapper.selectById(id);
        if (user == null) {
            return Response.error(CodeEnum.ERROR_LOGIN_NOT_EXIT);
        }
        if (ADMIN != user.getType()) {
            return Response.error(CodeEnum.ERROR_AUTHENTICATION);
        }
        userMapper.deleteById(id);
        return Response.success();
    }

    @Override
    public Response activation(String id, String code) {
        UserEntity user = userMapper.selectById(id);
        if (user == null) {
            return Response.error(CodeEnum.ERROR_LOGIN_NOT_EXIT);
        }
        if (1 == user.getStatus()) {
            return Response.error(CodeEnum.ERROR_USER_AUTH_FAILED, ACTIVATION_REPEAT);
        }
        if (!code.equals(user.getActivationCode())) {
            return Response.error(CodeEnum.ERROR_USER_AUTH_FAILED, ACTIVATION_FAILURE);
        }
        UserEntity userEntity = UserEntity.builder()
                .id(user.getId())
                .status(1)
                .build();

        userMapper.updateById(userEntity);
        String userKey = RedisKeyUtil.getUserKey(user.getId());
        redisTemplate.delete(userKey);
        return Response.success(ACTIVATION_SUCCESS);
    }

    @Override
    public void kaptCha(HttpServletResponse response) {
        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);
        //??????????????????session??? ????????????  ????????????redis??? ????????????60S
        String kaptChaOwner = CommunityUtil.generateUUID();
        Cookie cookie = new Cookie("kaptChaOwner", kaptChaOwner);
        cookie.setMaxAge(60);
        cookie.setPath(contextPath);
        response.addCookie(cookie);
        String kaptChaKey = RedisKeyUtil.getKaptchaKey(kaptChaOwner);
        redisTemplate.opsForValue().set(kaptChaKey, text, 360, TimeUnit.SECONDS);

        //???????????????????????????
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("????????????????????????" + e.getMessage());
        }

    }

}

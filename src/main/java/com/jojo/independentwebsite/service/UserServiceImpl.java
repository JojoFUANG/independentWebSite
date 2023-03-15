package com.jojo.independentwebsite.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jojo.independentwebsite.mapper.UserMapper;
import com.jojo.independentwebsite.model.User;
import com.jojo.independentwebsite.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public int registration(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setUserPassword(MD5Utils.str2MD5(user.getUserPassword()));
        int result = userMapper.insert(user);
        return result;
    }

    @Override
    public boolean selectUserByName(User user) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("user_Name", user.getUserName());
        List<User> userList = userMapper.selectList(userWrapper);
        if(!userList.isEmpty())return true;
        return false;
    }

    @Override
    public boolean login(User user) throws Exception {
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUserName,user.getUserName());
//        userWrapper.eq(User::getUserPassword,MD5Utils.str2MD5(user.getUserPassword()));

        List<User> userList = userMapper.selectList(userWrapper);
        log.info("userList ==" + userList.isEmpty());
        log.info("userPassword ==" + MD5Utils.str2MD5(user.getUserPassword()));

        if(userList.isEmpty()){
            return false;
        }

        String psw = userList.get(0).getUserPassword();
        log.info(psw + "   "+ MD5Utils.str2MD5(user.getUserPassword()));
        boolean isLogin = MD5Utils.checkPassword(user.getUserPassword(),psw);

        return isLogin;
    }


}

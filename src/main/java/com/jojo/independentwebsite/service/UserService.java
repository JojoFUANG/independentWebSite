package com.jojo.independentwebsite.service;

import com.jojo.independentwebsite.model.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public interface UserService {

    public int registration(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    public boolean selectUserByName(User user);

    public boolean login(User user) throws Exception;

}

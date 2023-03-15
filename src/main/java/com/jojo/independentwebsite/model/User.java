package com.jojo.independentwebsite.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.ws.rs.QueryParam;

@Data
public class User {

    @QueryParam("userId")
    @TableId(type = IdType.AUTO)
    private int userId;

    @QueryParam("userName")
    private String userName;

    @QueryParam("userPassword")
    private String userPassword;

}

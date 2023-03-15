package com.jojo.independentwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jojo.independentwebsite.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

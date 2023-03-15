package com.jojo.independentwebsite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jojo.independentwebsite.model.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}

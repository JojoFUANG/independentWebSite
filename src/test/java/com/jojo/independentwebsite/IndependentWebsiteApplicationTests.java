package com.jojo.independentwebsite;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jojo.independentwebsite.config.Comment;
import com.jojo.independentwebsite.mapper.ProductMapper;
import com.jojo.independentwebsite.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IndependentWebsiteApplicationTests {

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testSelect(){
        //UserMapper中的selectList()方法的参数为MP内置的条件封装器Wrapper，所以不填写就是无任何条件。
        List<Product> list = productMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void testSelectByPage(){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        //wrapper 组装where条件
        wrapper.isNotNull("product_Id");

        Page<Product> page = new Page<>(1, Comment.SEARCH_TYPE_SPLIT_PAGE);
        //select page 需要配置SQL拦截器才能做到分页效果，且带着wrapper的where条件
        productMapper.selectPage(page,wrapper);
        System.out.println(page.getPages());

    }
}

package com.jojo.independentwebsite.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jojo.independentwebsite.mapper.ProductMapper;
import com.jojo.independentwebsite.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> selectAll() {
        List<Product> products = productMapper.selectList(null);
        return products;
    }

    @Override
    public Page<Product> selectSplit(int currPage,int size,QueryWrapper<Product> wrapper) {
        Page<Product> page = new Page<>(1, size);
        productMapper.selectPage(page,wrapper);
        return page;
    }

    @Override
    public Integer addProduct(Product product) {
        return productMapper.insert(product);
    }

}

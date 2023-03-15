package com.jojo.independentwebsite.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jojo.independentwebsite.model.Product;
import java.util.List;

public interface ProductService {

    public List<Product> selectAll();

    public Page<Product> selectSplit(int currPage, int size, QueryWrapper<Product> wrapper);

    public Integer addProduct(Product product);

}

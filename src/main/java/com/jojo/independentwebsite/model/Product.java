package com.jojo.independentwebsite.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Product {

    @TableId(type = IdType.AUTO)
    private Long productId;

    @NotNull
    private String productName;

    private String productDesc;

    @NotNull
    private String productType;

    private String productImage;

    private String productDisplay;

}

package com.jojo.independentwebsite.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jojo.independentwebsite.config.Comment;
import com.jojo.independentwebsite.model.APIResponse;
import com.jojo.independentwebsite.model.Product;
import com.jojo.independentwebsite.service.ProductService;
import com.jojo.independentwebsite.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Path("/product")
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    APIResponse apiResponse;

    /*
    Method: 新增产品
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public APIResponse addProduct(@Context HttpHeaders headers, @Context HttpServletRequest request,
                                  @FormDataParam("productName") String productName,
                                  @FormDataParam("productDesc") String productDesc ,
                                  @FormDataParam("productType") String productType,
                                  @FormDataParam("productDisplay") String productDisplay,
                                  @FormDataParam("productImage") InputStream fileInputStream,
                                  @FormDataParam("productImage") FormDataContentDisposition fileMetaData) throws IOException {

        log.info("======================= Add new Product ======================");
        log.info("productName ==" + productName);
        log.info("productDesc ==" + productDesc);
        log.info("productType ==" + productType);
        log.info("productDisplay ==" + productDisplay);
        log.info("productImage == " + fileInputStream);
        log.info("productImage == " + fileMetaData);
        log.info("===============================================================");

        apiResponse = new APIResponse();

        if(productName == null || productName.equals("") || productDesc==null || productDesc.equals("") ||
                productType==null || productType.equals("")){
            apiResponse.setStatus(Response.Status.BAD_REQUEST.toString());
            apiResponse.setMessage("Product name/Type/Description/Image is blank");
        }

        String fileName = FileUtils.fileUpload(fileInputStream,fileMetaData);
        log.info(fileName);

        Product product = new Product();
        product.setProductName(productName);
        product.setProductDesc(productDesc);
        product.setProductType(productType);
        product.setProductDisplay(productDisplay);
        product.setProductImage(fileName);

        int result = productService.addProduct(product);
        if(result!=1){
            apiResponse.setStatus(Response.Status.BAD_REQUEST.toString());
            apiResponse.setMessage("Unknow Error");
        }else{
            apiResponse.setStatus(Response.Status.OK.toString());
            apiResponse.setMessage("Product added");
        }

        return apiResponse;
    }

    /*
    Method: 查询产品
    1. 按产品类型查找
        @Param : type , page, size
    2. 按图片展示类型查找 -》 用于主页展示
        @Param ： productDiplay, page ,size
    3. 按产品Id查找 用户展示详情页
        @Param ： productId
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public APIResponse getProduct(@Context HttpHeaders headers, @Context HttpServletRequest request,
                               @QueryParam("type") String type,
                               @QueryParam("page") int page,
                               @QueryParam("size") int size,
                               @QueryParam("productDisplay") String productDisplay,
                               @QueryParam("productId") int productId,
                               @QueryParam("productType") String productType){

        log.info("=====================  GET Method =======================");
        log.info("received the request : " + request.getRemoteHost());
        log.info("type == " + type );
        log.info("page == " + page);
        log.info("=========================================================");

        apiResponse = new APIResponse();

        List<Product> products = new ArrayList<>();
        Page<Product> productSplitByPage = new Page<>();
        JSONObject json = new JSONObject();
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();

        switch (type){

                //查询全部产品
            case Comment.SEARCH_TYPE_ALL:
                products = productService.selectAll();

                log.info("products==" + products.toString());
                apiResponse.setStatus(Response.Status.OK.toString());
                apiResponse.setMessage("Susscuffly to retrive all products");
                apiResponse.setResult(json.toJSONString(products));
                break;

                //按产品ID查找
            case Comment.SEARCH_TYPE_ID:
                productQueryWrapper.eq("product_Id",productId);
                productSplitByPage = productService.selectSplit(page,size,productQueryWrapper);
                log.info("products==" + products.toString());
                apiResponse.setStatus(Response.Status.OK.toString());
                apiResponse.setMessage("Susscuffly to retrive 1 page products");
                apiResponse.setResult(json.toJSONString(productSplitByPage));
                break;

                //按产品展示类型查找
            case Comment.SEARCH_TYPE_TYPE:
                productQueryWrapper.eq("product_Type",productType);
                productSplitByPage = productService.selectSplit(page,size,productQueryWrapper);
                log.info("products==" + products.toString());
                apiResponse.setStatus(Response.Status.OK.toString());
                apiResponse.setMessage("Susscuffly to retrive 1 page products");
                apiResponse.setResult(json.toJSONString(productSplitByPage));
                break;

                //按产品类型查找
            case Comment.SEARCH_TYPE_SPILT:
                productSplitByPage = productService.selectSplit(page,size,null);
                log.info("products==" + products.toString());
                apiResponse.setStatus(Response.Status.OK.toString());
                apiResponse.setMessage("Susscuffly to retrive 1 page products");
                apiResponse.setResult(json.toJSONString(productSplitByPage));
                break;

            //按产品展示类型查找
            case Comment.SEARCH_TYPE_DISPLAY:
                productQueryWrapper.eq("product_Display",productDisplay);
                productSplitByPage = productService.selectSplit(page,size,productQueryWrapper);
                log.info("products==" + products.toString());
                apiResponse.setStatus(Response.Status.OK.toString());
                apiResponse.setMessage("Susscuffly to retrive product_Display 1 page products");
                apiResponse.setResult(json.toJSONString(productSplitByPage));
                break;
        }

        return apiResponse;
    }

    /*
    Method:删除产品
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProdcut(){

        return null;
    }

}

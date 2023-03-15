package com.jojo.independentwebsite.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jojo.independentwebsite.model.APIResponse;
import com.jojo.independentwebsite.model.User;
import com.jojo.independentwebsite.service.UserService;
import com.jojo.independentwebsite.utils.JWTTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Path("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    APIResponse apiResponse;
    /*
    Method: 注册用户
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public APIResponse addUser(@Context HttpHeaders headers, @RequestBody User user, @Context HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        log.info("====================== Registration User ======================");
        log.info("user ==" +user.toString());
        log.info("===============================================================");

        apiResponse = new APIResponse();

        if(user.getUserName()==null||user.getUserName().equals("")||user.getUserPassword()==null||user.getUserPassword().equals("")){
            apiResponse.setStatus(Response.Status.BAD_REQUEST.toString());
            apiResponse.setMessage("Invalid username or password");
            return apiResponse;
        }else{
            //check if exist
            if(userService.selectUserByName(user)){
                apiResponse.setStatus(Response.Status.BAD_REQUEST.toString());
                apiResponse.setMessage("User Name exist");
            }else{
                user.setUserId(0);
                int result = userService.registration(user);
                if(result!=1){
                    apiResponse.setStatus(Response.Status.BAD_REQUEST.toString());
                    apiResponse.setMessage("NuKnow error");
                }else{
                    apiResponse.setStatus(Response.Status.OK.toString());
                    apiResponse.setMessage("Registrations successfully");
                }
            }
        }
        return apiResponse;
    }

    /*
    Method: 登入功能
     */
    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public APIResponse login(@Context HttpHeaders headers, @RequestBody User user, @Context HttpServletRequest request) throws Exception {
        log.info("====================== Login User ======================");
        log.info("user ==" +user.toString());
        log.info("token ==" + request.getParameter("Token"));
        log.info("===============================================================");

        apiResponse = new APIResponse();
        String token = null;
        if(request.getHeader("Token")!=null && !request.getHeader("Token").equals("")){
            token = request.getHeader("Token");
            if(JWTTokenUtils.checkToken(token)){
                log.info("user ==" + user.getUserName() +" login");
                apiResponse.setStatus(Response.Status.OK.toString());
                apiResponse.setMessage("Login successfully");
                return apiResponse;
            }

        }

        if(user.getUserName()==null||user.getUserName().equals("")
        ||user.getUserPassword()==null||user.getUserPassword().equals("")){
            apiResponse.setStatus(Response.Status.BAD_REQUEST.toString());
            apiResponse.setMessage("Invalid Request");
            return apiResponse;
        }else{
            if(!userService.login(user)){
                apiResponse.setStatus(Response.Status.BAD_REQUEST.toString());
                apiResponse.setMessage("User not exist");
            }else{
                log.info("user ==" + user.getUserName() +" login");
                apiResponse.setStatus(Response.Status.OK.toString());
                apiResponse.setMessage("Login successfully");

                Map<String,Object> map = new HashMap<>();
                map.put("User",user.getUserName());
                token = JWTTokenUtils.getToken(String.valueOf(user.getUserId()),map);
                log.info(token);
                apiResponse.setResult(token);
            }
        }
        return apiResponse;
    }


}

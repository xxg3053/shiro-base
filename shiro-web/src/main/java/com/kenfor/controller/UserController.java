package com.kenfor.controller;

import com.kenfor.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.controller
 * @Description: TODO
 * @date 2018/5/18 上午12:04
 */
@Controller
public class UserController {

    @RequestMapping(value = "/subLogin", method = RequestMethod.POST,
            produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User userVO){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userVO.getUsername(), userVO.getPassword());
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            return e.getMessage();
        }

        if(subject.hasRole("admin")){
            return "有admin权限";
        }else {
            return "无admin权限";
        }

        //return "登录成功";
    }

    /**
     * 注解方式
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/testRole", method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return "test role success";
    }


    @RequestMapping(value = "/testRole1", method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){
        return "test role success";
    }
}

package com.kenfor.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.test
 * @Description: 测试认证
 * @date 2018/5/14 下午9:43
 */
public class AuthenticationTest {

    SimpleAccountRealm realm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        //realm.addAccount("kenfo", "123456");
        realm.addAccount("kenfo", "123456", "admin");
    }

    @Test
    public void testAuthentication(){
        //1. 构建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);

        //2. 主体提交认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("kenfo", "123456");
        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
        //subject.logout();//退出之后，认证将失败
        //System.out.println("isAuthenticated: " + subject.isAuthenticated());
        System.out.println("hasRole: " +  subject.hasRole("admin"));



    }
}

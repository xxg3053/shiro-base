package com.kenfor.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.test
 * @Description: 练习内置realm:IniRealm
 * @date 2018/5/14 下午10:14
 */
public class IniRealmTest {

    @Test
    public void testAuthentication(){

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1. 构建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2. 主体提交认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("kenfo", "123456");
        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        System.out.println("hasRole: " + subject.hasRole("admin"));
        subject.checkPermission("user:delete");


    }
}

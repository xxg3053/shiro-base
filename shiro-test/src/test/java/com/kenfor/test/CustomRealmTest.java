package com.kenfor.test;

import com.kenfor.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.test
 * @Description: TODO
 * @date 2018/5/15 下午11:46
 */
public class CustomRealmTest {


    @Test
    public void testAuthentication(){

        CustomRealm realm = new CustomRealm();

        //1. 构建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);

        //2. 主体提交认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("kenfo", "123456");
        subject.login(token);
        System.out.println("isAuthenticated: " + subject.isAuthenticated());
//

        System.out.println("hasRole: " + subject.hasRole("admin"));
        subject.checkPermission("user:delete");


    }
}

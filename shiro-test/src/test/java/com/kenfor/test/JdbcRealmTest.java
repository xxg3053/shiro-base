package com.kenfor.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.test
 * @Description: JdbcRealm测试
 * @date 2018/5/15 上午12:05
 */
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://172.16.92.218:3306/shiro-base");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);//jdbcRealm默认不查询权限数据

        //jdbcReal默认自带了sql
        //String sql = "select role_name from user_roles where username = ?";
        //jdbcRealm.setUserRolesQuery(sql);

        //1. 构建SecurityManger环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

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

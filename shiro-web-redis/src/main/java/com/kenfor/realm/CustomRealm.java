package com.kenfor.realm;

import com.kenfor.dao.UserDao;
import com.kenfor.vo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.shiro.realm
 * @Description: 自定义的Realm
 *
 * 需要继承AuthorizingRealm
 *
 * @date 2018/5/15 下午11:37
 */
public class CustomRealm extends AuthorizingRealm{

    @Autowired
    private UserDao userDao;

    //用来做授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //1. 从主体传过来的认证信息中获得用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRoleByUserName(userName);

        Set<String> permissions = getPermissionsByUserName(userName);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        authorizationInfo.setRoles(roles);

        return authorizationInfo;
    }



    //用来做认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1. 从主体传过来的认证信息中获得用户名
        String userName = (String) authenticationToken.getPrincipal();
        //2. 通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);
        if(password == null){
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName,password,"customRealm");
        info.setCredentialsSalt(ByteSource.Util.bytes(userName));//设置盐值

        return info;

    }

    /**
     * 从数据库获得凭证
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        User user = userDao.getUserByName(userName);
        if(user != null){
            return user.getPassword();
        }
        return null;
    }

    //假设数据
    private Set<String> getRoleByUserName(String userName) {
        List<String> list = userDao.queryRoleByUserName(userName);
        Set<String> sets = new HashSet<>(list);
        return sets;
    }

    //假设数据
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }



    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456", "kenfo");
        System.out.println(md5Hash);
    }

}

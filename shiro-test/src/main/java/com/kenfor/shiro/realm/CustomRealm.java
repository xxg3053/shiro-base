package com.kenfor.shiro.realm;

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    //测试数据
    Map<String, String> userMap = new HashMap<String, String>();

    {
        //没加密
        //userMap.put("kenfo", "123456");
        //加密
        //userMap.put("kenfo", getMd5("123456", false));
        userMap.put("kenfo", getMd5("123456", true));

        super.setName("customRealm");
    }

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
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("kenfo",password,"customRealm");
        info.setCredentialsSalt(ByteSource.Util.bytes("123"));//设置盐值
        return info;

    }

    /**
     * 从数据库获得凭证
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }

    //假设数据
    private Set<String> getRoleByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    //假设数据
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }


    public String getMd5(String s, boolean hasSalt){
        Md5Hash md5Hash = null;
        if(hasSalt){
            md5Hash = new Md5Hash(s, "123");
        }else {
            md5Hash = new Md5Hash(s);
        }
        return md5Hash.toString();
    }


    public static void main(String[] args) {
        System.out.println(String.format("http://asdfasdf/adsfadf","123","a123"));
    }

}

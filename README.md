# Shiro


## Shiro认证过程

- 创建SecurityManager
- 主体提交认证
- SecurityManager认证
- Authenticator认证
- Realm获取角色权限数据

## Realm

内置Realm：  
- IniRealm
- JdbcRealm
- 自定义realm

## Shiro加密

Shiro散列配置  
- HashedCredentialsMatcher
```
//加密
HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
matcher.setHashAlgorithmName("md5");//设置加密算法
matcher.setHashIterations(1);//设置加密次数

```
- 自定义的Realm中使用散列
```
realm.setCredentialsMatcher(matcher);
```
- 盐的使用
```
SimpleAuthenticationInfo info = new SimpleAuthenticationInfo("kenfo",password,"customRealm");
info.setCredentialsSalt(ByteSource.Util.bytes("123"));//设置盐值
```


## Shiro 和 Spring

1. 添加shiro的filter

## Shiro过滤器
Shiro内置过滤器： 
- anon, authBasic, authc, user, logout
- perms, roles, ssl, port  
- 自定义过滤器

## Shiro会话管理

- SessionManager, SessionDAO
- Redis实现Session共享
- Redis实现session共享的问题

## Shiro缓存管理
一般做授权的缓存，认证的缓存没必要   

- CacheManager, Cache
- redis实现CacheManger

## Shiro自动登录
```
 <input name="rememberMe" type="checkbox"> 记住我
 
 token.setRememberMe(userVO.getRememberMe());
 
```
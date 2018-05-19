package com.kenfor.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.filter
 * @Description: 多个roles,满足一个即可
 * @date 2018/5/19 上午11:22
 */
public class RolesOrFilter extends AuthorizationFilter{
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest,
                                      ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        String[] roles = (String[]) o;
        if(roles == null || roles.length == 0){
            return true;
        }
       for(String role:roles){
            if(subject.hasRole(role)){
                return true;
            }
       }
        return false;
    }
}

package com.kenfor.dao;

import com.kenfor.vo.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author kenfo
 * @version V1.0
 * @Package com.kenfo.dao
 * @Description: TODO
 * @date 2018/5/19 上午12:15
 */
@Component
public interface UserDao {
    User getUserByName(String userName);

    List<String> queryRoleByUserName(String userName);
}

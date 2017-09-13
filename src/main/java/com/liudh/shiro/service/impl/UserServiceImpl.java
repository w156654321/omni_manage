package com.liudh.shiro.service.impl;

import com.liudh.shiro.mapper.UUserMapper;
import com.liudh.shiro.pojo.UUser;
import com.liudh.shiro.pojo.UUserExample;
import com.liudh.shiro.pojo.UUserExample.Criteria;
import com.liudh.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liudh on 2017/5/28.
 */
@Transactional
@Service
@Component("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UUserMapper userMapper;

    public UUser getUserByName(String userName) {
        UUserExample example = new UUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(userName);
        List<UUser> user = userMapper.selectByExample(example);
        if(!user.isEmpty())
        return user.get(0);
        return null;
    }
}

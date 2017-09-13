package com.liudh.shiro.service.impl;

import com.liudh.shiro.mapper.UPermissionMapper;
import com.liudh.shiro.pojo.UPermission;
import com.liudh.shiro.pojo.URole;
import com.liudh.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by liudh on 2017/5/28.
 */
@Transactional
@Service
public class PermissionServiceImpl  implements PermissionService {

    @Autowired
    private UPermissionMapper permissionMapper;

    public Set<String> selectPermissionByUserId(Long userId) {
        Set<String> set = permissionMapper.selectPermissionByUserId(userId);
        return set;
    }
}

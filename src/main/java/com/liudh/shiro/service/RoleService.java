package com.liudh.shiro.service;

import java.util.Set;

/**
 * Created by liudh on 2017/5/28.
 */
public interface RoleService {

    Set<String> selectRoleByUserId(Long userId);
}

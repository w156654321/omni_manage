package com.liudh.shiro.service;

import java.util.Set;

/**
 * Created by liudh on 2017/5/28.
 */
public interface PermissionService {

    Set<String> selectPermissionByUserId(Long userId);

}

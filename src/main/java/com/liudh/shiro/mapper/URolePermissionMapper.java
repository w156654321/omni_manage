package com.liudh.shiro.mapper;

import java.util.List;

import com.liudh.shiro.pojo.URolePermission;
import com.liudh.shiro.pojo.URolePermissionExample;
import org.apache.ibatis.annotations.Param;

public interface URolePermissionMapper {

    int countByExample(URolePermissionExample example);

    int deleteByExample(URolePermissionExample example);

    int insert(URolePermission record);

    int insertSelective(URolePermission record);

    List<URolePermission> selectByExample(URolePermissionExample example);

    int updateByExampleSelective(@Param("record") URolePermission record, @Param("example") URolePermissionExample example);

    int updateByExample(@Param("record") URolePermission record, @Param("example") URolePermissionExample example);
}
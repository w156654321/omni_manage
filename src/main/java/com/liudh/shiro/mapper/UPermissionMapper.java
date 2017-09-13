package com.liudh.shiro.mapper;

import java.util.List;
import java.util.Set;

import com.liudh.shiro.pojo.UPermission;
import com.liudh.shiro.pojo.UPermissionExample;
import org.apache.ibatis.annotations.Param;

public interface UPermissionMapper {

    int countByExample(UPermissionExample example);

    int deleteByExample(UPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UPermission record);

    int insertSelective(UPermission record);

    List<UPermission> selectByExample(UPermissionExample example);

    UPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UPermission record, @Param("example") UPermissionExample example);

    int updateByExample(@Param("record") UPermission record, @Param("example") UPermissionExample example);

    int updateByPrimaryKeySelective(UPermission record);

    int updateByPrimaryKey(UPermission record);

    Set<String> selectPermissionByUserId(Long id);

}
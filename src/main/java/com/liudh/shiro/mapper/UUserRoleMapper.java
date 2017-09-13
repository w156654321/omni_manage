package com.liudh.shiro.mapper;

import java.util.List;

import com.liudh.shiro.pojo.UUserRole;
import com.liudh.shiro.pojo.UUserRoleExample;
import org.apache.ibatis.annotations.Param;

public interface UUserRoleMapper {

    int countByExample(UUserRoleExample example);

    int deleteByExample(UUserRoleExample example);

    int insert(UUserRole record);

    int insertSelective(UUserRole record);

    List<UUserRole> selectByExample(UUserRoleExample example);

    int updateByExampleSelective(@Param("record") UUserRole record, @Param("example") UUserRoleExample example);

    int updateByExample(@Param("record") UUserRole record, @Param("example") UUserRoleExample example);

}
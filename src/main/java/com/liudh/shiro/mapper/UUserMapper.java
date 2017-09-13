package com.liudh.shiro.mapper;

import java.util.List;

import com.liudh.shiro.pojo.UUser;
import com.liudh.shiro.pojo.UUserExample;
import org.apache.ibatis.annotations.Param;

public interface UUserMapper {

    int countByExample(UUserExample example);

    int deleteByExample(UUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UUser record);

    int insertSelective(UUser record);

    List<UUser> selectByExample(UUserExample example);

    UUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UUser record, @Param("example") UUserExample example);

    int updateByExample(@Param("record") UUser record, @Param("example") UUserExample example);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);
}
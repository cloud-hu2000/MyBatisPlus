package com.CloudHu.MyBatisPlus.Mapper;

import com.CloudHu.MyBatisPlus.POJO.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    IPage<User> selectPageVo(@Param("page") Page<User> page, @Param("username") String username);
}

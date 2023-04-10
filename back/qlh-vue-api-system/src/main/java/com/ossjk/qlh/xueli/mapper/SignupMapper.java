package com.ossjk.qlh.xueli.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.xueli.entity.Signup;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.mapper
 * @ClassName: XlstudentMapper
 * @Description: 学历学生-Mapper
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
public interface SignupMapper extends BaseMapper<Signup> {

    @Select("select distinct bdyear from signup order by bdyear desc")
    String[] findAllYears();



}

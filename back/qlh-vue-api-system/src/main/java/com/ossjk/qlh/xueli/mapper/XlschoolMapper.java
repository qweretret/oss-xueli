package com.ossjk.qlh.xueli.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.xueli.entity.Xlschool;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.mapper
 * @ClassName: XlschoolMapper
 * @Description: 学历产品-Mapper
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:50 
 */
public interface XlschoolMapper extends BaseMapper<Xlschool> {

    @Select("select distinct level from xlschool where zsyear=#{zsyear}")
    String[] allLevelsByYear(@Param("zsyear") String zsyear);

    @Select("select * from xlschool where zsyear=#{zsyear} and level=#{level} and subjects like  concat('%',#{sbname},'%')")
    List<Xlschool> allLevelsByYear2(@Param("zsyear") String zsyear, @Param("level") String level, @Param("sbname") String sbname);

}

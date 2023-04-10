package com.ossjk.qlh.xueli.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.xueli.entity.Tichengstu;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Copyright  2022-04-18 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.book.mapper
 * @ClassName: TichengstuMapper
 * @Description: 业绩提成-Mapper
 * @author: linhao
 * @date:  2022-04-18 16:13:59 
 */
public interface TichengstuMapper extends BaseMapper<Tichengstu> {

    @Select("select distinct b.* from xlstudent a  inner join tichengstu b on b.stuid=a.id ${ew.customSqlSegment}")
    Page<Tichengstu> select(Page<Tichengstu> page,@Param("ew") QueryWrapper<Tichengstu> ew);

    //跟据部门查招学生
    @Select("select distinct b.* from xlstudent a  left join tichengstu b on b.stuid=a.id" +
            " where b.userid in (select name from v_user ${ew.customSqlSegment}) ")
    Page<Tichengstu> findByDpt(Page<Tichengstu> page, @Param("ew") QueryWrapper<Tichengstu> ew,@Param("um") String um);

    //跟据主管查找部门所招学生
    @Select("select distinct b.* from xlstudent a  left join tichengstu b on b.stuid=a.id" +
            "  ${ew.customSqlSegment}  and b.userid in (select name from v_user where dname=  (select dname from v_user where name=#{um})) ")
    Page<Tichengstu> findByDptmgr(Page<Tichengstu> page, @Param("ew") QueryWrapper<Tichengstu> ew,@Param("um") String um);


    //跟据招生员查找学生
    @Select(" select distinct b.* from xlstudent a  left join tichengstu b on b.stuid=a.id  ${ew.customSqlSegment} ")
    Page<Tichengstu> findByUserName(Page<Tichengstu> page, @Param("ew") QueryWrapper<Tichengstu> ew );


}

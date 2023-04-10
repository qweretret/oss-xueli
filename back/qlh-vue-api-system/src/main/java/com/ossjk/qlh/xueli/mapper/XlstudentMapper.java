package com.ossjk.qlh.xueli.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.xueli.dto.XlstudentDTO;
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
public interface XlstudentMapper extends BaseMapper<Xlstudent> {


    @Select("select id,name as value from xlstudent where name like #{sname}")
    List<Map> stuNameInduction(@Param("sname") String sname);


    //分页查询
    @Select("SELECT s.*,stu.name,stu.moblie,stu.idno,stu.sex,stu.mz, " +
            "   stu.remarks,stu.openid,stu.wxuid FROM signup AS s " +
            " INNER JOIN xlstudent AS stu ON s.idno = stu.idno ${ew.customSqlSegment} ")
    Page<XlstudentDTO> pageDto(Page<XlstudentDTO> page, @Param("ew") QueryWrapper<XlstudentDTO> ew );

    //跟据招生员查找学生
    @Select(" select distinct b.* from tichengstu a  left join xlstudent b on b.id=a.stuid  ${ew.customSqlSegment} ")
    Page<XlstudentDTO> findByUserName(Page<XlstudentDTO> page, @Param("ew") QueryWrapper<XlstudentDTO> ew );

    @Select("select distinct b.* from xlsturecord  a inner join xlstudent b on b.id = a.stuid  ${ew.customSqlSegment}  ")
    Page<Xlstudent> selectPageXlstudent(Page<Xlstudent> page,  @Param("ew") QueryWrapper<Xlstudent> ew);

    //跟据主管查找部门所招学生
    @Select(" select distinct b.* from tichengstu a  left join xlstudent b on b.id=a.stuid" +
            "  ${ew.customSqlSegment}  and a.userid in (select name from v_user where dname=  (select dname from v_user where name=#{um})) ")
    Page<Xlstudent> findByDptmgr(Page<Xlstudent> page, @Param("ew") QueryWrapper<Xlstudent> ew,@Param("um") String um);

    //微信根据姓名和手机找id
    @Update("UPDATE xlstudent SET  openid = #{openid} , wxuid = #{unionid}  where name= #{name} and moblie= #{moblie}")
    boolean selectId(@Param("openid") String openid,@Param("unionid")String unionid , @Param("name") String name, @Param("moblie")String moblie);
}

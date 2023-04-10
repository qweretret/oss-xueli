package com.ossjk.qlh.xueli.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.qlh.xueli.dto.Xlsturecord2DTO;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.entity.Xlsturecord;
import com.ossjk.qlh.xueli.vo.SouSuoVo;
import com.ossjk.qlh.xueli.vo.XlsturecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.mapper
 * @ClassName: XlsturecordMapper
 * @Description: 学历学生跟进记录-Mapper
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
public interface XlsturecordMapper extends BaseMapper<Xlsturecord> {

    @Select("SELECT  s.* ,x.name FROM  xlsturecord  s LEFT JOIN   xlstudent x ON s.stuid = x.id")
    Page<XlsturecordVo> pageVo(int tyep,Page page, QueryWrapper<XlsturecordVo> queryWrapper);

    //搜索框
    @Select("select a.*,b.name from xlsturecord a left join  xlstudent b on a.stuid = b.id ${ew.customSqlSegment}")
    List<SouSuoVo> souSuokuang(@Param("ew") QueryWrapper<SouSuoVo> ew);

    //查自己的
    @Select("select a.*,b.name from xlsturecord a left join  xlstudent b on a.stuid = b.id ${ew.customSqlSegment}")
    Page<XlsturecordVo> souziji(Page<Xlstudent> page, @Param("ew") QueryWrapper<XlsturecordVo> ew);

    //查部门
    @Select("select a.*,b.name from xlsturecord a  left join xlstudent b on b.id=a.stuid WHERE a.username in " +
            "(select name from v_user where dname=  (select dname from v_user where name=#{um}))")
    Page<XlsturecordVo> souBuMen(Page<XlsturecordVo> page,@Param("um") String um);

    @Select("select left(s.crtm,10) as crtm, s.type, count(msgtm) cntqs, s.message, count(stuid) allcnt,stu.bdyear as grade,stu.school,s.crer  " +
            "   from xlsturecord s left join xlstudent stu " +
            "        on  s.stuid = stu.id ${ew.customSqlSegment}" +
            // " where s.message is not null " +
            " GROUP BY  left(s.crtm,10) , s.type,stu.school,s.crer,stu.bdyear,s.message")
    Page<Xlsturecord2DTO> countMsgqiansou(Page<XlsturecordVo> page, @Param("ew") QueryWrapper<Xlsturecord> ew);

    @Select("select stu.school, stu.subject,  left(s.crtm,10) as crtm, s.type,stu.bdyear as grade,  s.msgtm ,stu.moblie as mobile,stu.name as stunm   " +
            "   from xlsturecord s left join xlstudent stu on  s.stuid = stu.id  ${ew.customSqlSegment} " )
        //  " where s.message is not null")
        List<Xlsturecord2DTO> qiansouStus( @Param("ew") QueryWrapper<Xlsturecord2DTO> ew);

    @Select("select   s.id,left(s.crtm,10) as crtm, s.type,  s.message   , s.msgtm   " +
        "     from xlsturecord s left join xlstudent stu on  s.stuid = stu.id  where s.stuid =#{stuid} order by s.crtm desc" )
        List<Xlsturecord2DTO> lsTuisongByStuid(  @Param("stuid") String  stuid);


   }
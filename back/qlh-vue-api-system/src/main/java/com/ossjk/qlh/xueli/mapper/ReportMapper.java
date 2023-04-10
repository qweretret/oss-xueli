package com.ossjk.qlh.xueli.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ReportMapper extends BaseMapper<Xlstudent> {

    //  1.每学年的学生信息、按学校分、级别（本、专）分
     @Select("select ${ew.sqlSelect}  from xlstudent ${ew.customSqlSegment}  ")
     List<Map> countStudent(@Param("ew") QueryWrapper<Xlstudent> ew);

     @Select("select distinct bdyear  from  xlstudent  order by bdyear desc")
     String[] allBdyears(  );

     @Select(" select distinct school from  xlstudent where bdyear=#{bdyear}  order by school " )
     String[] allSchools( @Param("bdyear")    String bdyear   );

     @Select(" select distinct level from  xlstudent where bdyear=#{bdyear} and school=#{school}   " )
     String[] allLevels(@Param("bdyear")    String bdyear , @Param("school")    String school   );

     @Select(" select distinct subject from  xlstudent where level=#{level}   " )
     String[] allSubjects( @Param("level")    String level   );

     @Select("select ${ew.sqlSelect}  from xlstudent stu ${ew.customSqlSegment} order by school,level")
     List<Map> countStudent2(@Param("ew") QueryWrapper<Xlstudent> ew);

     @Select("select ${ew.sqlSelect}  from xlstudent s left join xlsturecord r on  " +
             "  s.id = r.stuid  ${ew.customSqlSegment} ")
     List<Map> 学生缴费情况(@Param("ew") QueryWrapper<Xlstudent> ew  );

     @Select(" select  a.year ,a.userid,a.stucount,a.cost,b.ispaiedcost,a.cost - b.ispaiedcost as needpay " +
             "   from   (select left(tctm,4) as year, count(distinct stuid) stucount,userid,sum(cost) as cost " +
             "    from tichengstu  group by left(tctm,4),userid ) a," +
             " (select left(tctm,4) as year, sum(cost) as ispaiedcost,userid" +
             "    from tichengstu  where paydate is not null   group by left(tctm,4),userid ) b" +
             " where a.year = b.year  and a.userid =b.userid and a.year=#{year}")
      List<Map> 提成年统计( @Param("year")    String year  );

     @Select(" select s.bdyear, s.school, s.level,count(r.gnneedtm )  gnneedtm,count(r.gntm ) gntm from xlstudent s left join xlsturecord r on    s.id = r.stuid   \n" +
             "where  s.bdyear=#{year}  GROUP BY s.bdyear, s.school, s.level " )
      List<Xlstudent> 学生的跟进统计( @Param("year")    String year  );




}

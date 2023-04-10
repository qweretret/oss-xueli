package com.ossjk.qlh.xueli.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.service
 * @ClassName: XlstudentService
 * @Description:  学历学生-服务类接口
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
public interface IReportService extends IService<Xlstudent> {
   // 1.每学年的学生数量、按学校分、级别（本、专）分
    Map countStudent(  QueryWrapper<Xlstudent> ew,String year,String school);
    //  2.学生缴费情况，按年、学校分、级别（本、专）分
    Map countStudent2(  QueryWrapper<Xlstudent> ew,String level);

    Map 学生缴费情况(  QueryWrapper<Xlstudent> ew  ,String year,String school );

    Map 提成年统计( String year  );

    Map 学生的跟进统计(     String year  );
}

package com.ossjk.qlh.xueli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.mapper.ReportMapper;
import com.ossjk.qlh.xueli.service.IReportService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.service.impl
 * @ClassName: XlstudentServiceImpl
 * @Description: 学历学生-服务实现类
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Xlstudent> implements IReportService {

    @Override
    public  Map  countStudent( QueryWrapper<Xlstudent> ew,String year,String school){

        List<Map> list =  this.baseMapper.countStudent(ew);

        Map datas = new HashMap();
        datas.put("allyears",this.baseMapper.allBdyears());
        if(year!=null){
            datas.put("schools",this.baseMapper.allSchools(year));
            if(school!=null){
                datas.put("levels",this.baseMapper.allLevels(year,school));
            }
        }
        datas.put("list",this.baseMapper.countStudent(ew));

        return   datas;
    }

    @Override
    public  Map  countStudent2( QueryWrapper<Xlstudent> ew,String level){

        Map datas = new HashMap();

        datas.put("levels",new String[]{"全部","专升本","高起专"}  );
        if(level!=null){
            datas.put("subjects", this.baseMapper.allSubjects(level) );
        }

        datas.put("list",this.baseMapper.countStudent(ew));
        return   datas;

    }

    public  Map 学生缴费情况(  QueryWrapper<Xlstudent> ew ,String year,String school ){

        List<Map> list =  this.baseMapper.学生缴费情况(ew);

        Map datas = new HashMap();

        datas.put("allyears",this.baseMapper.allBdyears());

        if(year!=null){
            datas.put("schools",this.baseMapper.allSchools(year));
            if(school!=null){
                datas.put("levels",this.baseMapper.allLevels(year,school));
            }
        }
        datas.put("list",list);

        return   datas;
    }


    public Map 提成年统计( String year  ){

        List<Map> list =  this.baseMapper.提成年统计(year);

        Map datas = new HashMap();

        datas.put("allyears",this.baseMapper.allBdyears());
        datas.put("list",list);

        return   datas;
    }

    public Map 学生的跟进统计(     String year  ){

        Map datas = new HashMap();
        datas.put("allyears",this.baseMapper.allBdyears());
        datas.put("list",  this.baseMapper.学生的跟进统计(year));

        return   datas;
    }
}

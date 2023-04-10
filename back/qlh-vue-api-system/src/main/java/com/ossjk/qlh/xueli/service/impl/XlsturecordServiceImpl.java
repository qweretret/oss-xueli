package com.ossjk.qlh.xueli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.service.IUserService;
import com.ossjk.qlh.xueli.dto.Xlsturecord2DTO;
import com.ossjk.qlh.xueli.dto.XlsturecordDTO;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.entity.Xlsturecord;
import com.ossjk.qlh.xueli.mapper.XlsturecordMapper;
import com.ossjk.qlh.xueli.service.IXlsturecordService;
import com.ossjk.qlh.xueli.vo.SouSuoVo;
import com.ossjk.qlh.xueli.vo.XlsturecordVo;
import com.thoughtworks.xstream.mapper.Mapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.xueli.service.impl
 * @ClassName: XlsturecordServiceImpl
 * @Description: 学历学生跟进记录-服务实现类
 * @author: Rick.yang
 * @date: 2022-02-22 15:41:51
 */
@Service
public class XlsturecordServiceImpl extends ServiceImpl<XlsturecordMapper, Xlsturecord> implements IXlsturecordService {

    @Autowired
    private IUserService userService;

    //批量添加跟进计划
    @Transactional
    public boolean addAllRecords(XlsturecordDTO record) {
        Xlsturecord xst = null;
        User usr = userService.getById(record.getUserid());

        int count = 0;
        for (String stuid : record.getStuids()) {
            xst = new Xlsturecord();
            //DTO  转换成entity
            xst.setContent(record.getContent());
            xst.setType(record.getType());
            xst.setUsername(usr.getName());
            xst.setMessage(record.getMessage());
            xst.setStuid(stuid);

            try {
                xst.setGnneedtm(StringUtil.smt2.parse(record.getGnneedtm()));
                this.save(xst);
                count++;
            } catch (Exception px) {
                px.printStackTrace();
            }
        }
        return record.getStuids().length == count;
    }

    @Override
    public Page<XlsturecordVo> pageVo(int tyep,Page page, QueryWrapper<XlsturecordVo> queryWrapper,String mgs) {

        if(tyep == 1 ){  //admin
            return this.baseMapper.pageVo(tyep,page,queryWrapper);
        }else if(tyep == 2 ){ //班主任、员工
            return this.baseMapper.souBuMen(page,mgs);
        }else if(tyep == 3 ){ //班主任、员工
            return this.baseMapper.souziji(page,queryWrapper);
        }
//        return this.baseMapper.pageVo(page,queryWrapper);
        return null;
    }


    @Override
    public List<SouSuoVo> souSuokuang(QueryWrapper<SouSuoVo> queryWrapper) {
        return this.baseMapper.souSuokuang(queryWrapper);
    }

    @Override
    public Page<Xlsturecord2DTO> countMsgqiansou(Page<XlsturecordVo> page, QueryWrapper<Xlsturecord> ew){
        return this.baseMapper.countMsgqiansou(page,ew);

    }

    @Override
    public  List<Xlsturecord2DTO> qiansouStus(    QueryWrapper<Xlsturecord2DTO> ew){
        return super.baseMapper.qiansouStus(ew);
    }

    @Override
    public  List<Xlsturecord2DTO> lsTuisongByStuid(    String  stuid){
        return baseMapper.lsTuisongByStuid(stuid);
    }

    @Override
    @Transactional
    public  Boolean checkInMsg(    String  id){
        Xlsturecord msg = this.getById(id);
        if(msg.getMsgtm() == null){
            return false;
        }
        msg.setMsgtm(new Date());

        return this.updateById(msg);
    }

}

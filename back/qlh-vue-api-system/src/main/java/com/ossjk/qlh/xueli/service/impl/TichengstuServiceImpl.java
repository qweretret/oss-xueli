package com.ossjk.qlh.xueli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.xueli.dto.TichengDTO;
import com.ossjk.qlh.xueli.entity.Tichengstu;
import com.ossjk.qlh.xueli.mapper.TichengstuMapper;
import com.ossjk.qlh.xueli.mapper.XlstudentMapper;
import com.ossjk.qlh.xueli.service.ITichengstuService;
import com.ossjk.qlh.xueli.service.IXlsturecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Copyright  2022-04-18 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.book.service.impl
 * @ClassName: TichengstuServiceImpl
 * @Description: 业绩提成-服务实现类
 * @author: linhao
 * @date: 2022-04-18 16:13:59
 */
@Service
public class TichengstuServiceImpl extends ServiceImpl<TichengstuMapper, Tichengstu> implements ITichengstuService {


    @Override
    @Transactional
    public boolean fafang(String[] ids, String paytm) {

        List<Tichengstu> allTcs = this.baseMapper.selectBatchIds(Arrays.asList(ids));

        if (allTcs != null) {
            try {
                Date payDate = StringUtil.smt2.parse(paytm);
                for (Tichengstu tc : allTcs) {
                    tc.setPaydate(payDate);
                    //写入财务流水
                    this.saveOrUpdate(tc);
                }
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    @Override
    @Transactional
    public boolean save(TichengDTO record) {

        Tichengstu tichen = null;

        try {
            if (record != null) {
                if (record.getStuids() != null) {
                    for (String stuid : record.getStuids()) {
                        tichen = new Tichengstu();
                        tichen.setUserid(record.getUserid());
                        tichen.setPaydate(record.getPaydate1());
                        tichen.setCost(record.getCost1());
                        tichen.setRemark(record.getRemark1());
                        tichen.setStuid(stuid);
                        tichen.setTctm(record.getTctm1());
                        tichen.setType(BigDecimal.valueOf(1));
                        this.save(tichen);

                        //第二次支付
                        if (record.getCost2() != null) {
                            tichen = new Tichengstu();
                            tichen.setUserid(record.getUserid());
                            tichen.setPaydate(record.getPaydate2());
                            tichen.setCost(record.getCost2());
                            tichen.setRemark(record.getRemark2());
                            tichen.setStuid(stuid);
                            tichen.setTctm(record.getTctm2());
                            tichen.setType(BigDecimal.valueOf(1));
                            this.save(tichen);
                        }
                    }
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    @Override
    public Page<Tichengstu> page(int type, Page<Tichengstu> page, QueryWrapper<Tichengstu> queryWrapper, String mgr) {
        if (type == 1) {  //admin
            return this.baseMapper.select(page, queryWrapper);
        } else if (type == 2) {
            //admin选择部门
            return  this.baseMapper.findByDpt(page, queryWrapper, mgr);
        } else if (type == 3) {
            //mgr
            return this.baseMapper.findByDptmgr(page, queryWrapper, mgr);
        } else if (type == 4) {
            //班主任、员工
            return this.baseMapper.findByUserName(page, queryWrapper);
        }
        return this.baseMapper.selectPage(page, queryWrapper);
    }}

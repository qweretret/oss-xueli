package com.ossjk.qlh.xueli.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.xueli.entity.Signup;
import com.ossjk.qlh.xueli.mapper.SignupMapper;
import org.springframework.stereotype.Service;


/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.service
 * @ClassName: XlstudentService
 * @Description:  学历学生-服务类接口
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
@Service
public class SignupServiceImpl extends ServiceImpl<SignupMapper, Signup> implements com.ossjk.qlh.xueli.service.ISignupService {

    public  String[] findAllYears(){
        return  baseMapper.findAllYears();
    }

    @Override
    public Signup getOne(QueryWrapper<Signup> queryWrapper) {
        return baseMapper.selectOne(queryWrapper);
    }


}

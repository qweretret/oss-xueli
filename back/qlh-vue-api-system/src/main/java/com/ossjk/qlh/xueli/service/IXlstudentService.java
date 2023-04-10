package com.ossjk.qlh.xueli.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.xueli.dto.XlstudentDTO;
import com.ossjk.qlh.xueli.entity.Signup;
import com.ossjk.qlh.xueli.entity.Xlstudent;

import java.io.File;
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
public interface IXlstudentService extends IService<Xlstudent> {

    List<Signup> parseExcel(File file);

    String  impDatas(Signup[] stus);

    Page<XlstudentDTO> page(int type, Page<XlstudentDTO> page, QueryWrapper<XlstudentDTO> queryWrapper, String mgr);

    String[] findAllYears();

    List<Map> stuNameInduction(String sname);

    boolean selectId( String openid, String unionid, String name, String moblie);

    Xlstudent getOne(QueryWrapper<Xlstudent> queryWrapper);

    Signup selectOne(QueryWrapper<Signup> queryWrapper);


}

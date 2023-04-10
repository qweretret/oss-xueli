package com.ossjk.qlh.xueli.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.xueli.entity.Xlschool;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.vo.XlSchoolVo;

import java.util.List;
import java.util.Map;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.service
 * @ClassName: XlschoolService
 * @Description:  学历产品-服务类接口
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:50 
 */
public interface IXlschoolService extends IService<Xlschool> {

    Map listProducts();

    List<Xlschool> pageSchool(String level,String sbname);


}

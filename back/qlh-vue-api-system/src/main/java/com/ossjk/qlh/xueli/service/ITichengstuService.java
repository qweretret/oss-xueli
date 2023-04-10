package com.ossjk.qlh.xueli.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.xueli.dto.TichengDTO;
import com.ossjk.qlh.xueli.entity.Tichengstu;
import com.ossjk.qlh.xueli.entity.Xlstudent;

/**
 * Copyright  2022-04-18 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.book.service
 * @ClassName: TichengstuService
 * @Description:  业绩提成-服务类接口
 * @author: linhao
 * @date:  2022-04-18 16:13:59 
 */
public interface ITichengstuService extends IService<Tichengstu> {

   boolean save( TichengDTO record);

   boolean fafang(String[] ids,String paytm );

   Page<Tichengstu> page(int type, Page<Tichengstu> page, QueryWrapper<Tichengstu> queryWrapper, String mgr);


}

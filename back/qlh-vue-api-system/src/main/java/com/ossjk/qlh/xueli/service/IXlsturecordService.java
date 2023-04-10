package com.ossjk.qlh.xueli.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.xueli.dto.Xlsturecord2DTO;
import com.ossjk.qlh.xueli.dto.XlsturecordDTO;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.entity.Xlsturecord;
import com.ossjk.qlh.xueli.vo.SouSuoVo;
import com.ossjk.qlh.xueli.vo.XlsturecordVo;
import org.apache.ibatis.annotations.Param;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.service
 * @ClassName: XlsturecordService
 * @Description:  学历学生跟进记录-服务类接口
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
public interface IXlsturecordService extends IService<Xlsturecord> {

     boolean addAllRecords( XlsturecordDTO record) ;

     Page<XlsturecordVo> pageVo(int tyep,Page page, QueryWrapper<XlsturecordVo> queryWrapper,String mgr);

     List<SouSuoVo> souSuokuang(QueryWrapper<SouSuoVo> queryWrapper);

     Page<Xlsturecord2DTO> countMsgqiansou(Page<XlsturecordVo> page, QueryWrapper<Xlsturecord> ew);

     List<Xlsturecord2DTO> qiansouStus(    QueryWrapper<Xlsturecord2DTO> ew);

     List<Xlsturecord2DTO> lsTuisongByStuid(    String  stuid);

     Boolean checkInMsg(    String  id);
}

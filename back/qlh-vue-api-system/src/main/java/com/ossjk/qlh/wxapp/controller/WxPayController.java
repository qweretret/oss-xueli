package com.ossjk.qlh.wxapp.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.wxapp.dto.WxpayDto;
import com.ossjk.qlh.wxapp.service.IWxpayparamService;
import com.ossjk.qlh.xueli.entity.Xlschool;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.service.IXlschoolService;
import com.ossjk.qlh.xueli.service.IXlstudentService;
import io.swagger.annotations.ApiOperation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/wx/pay"})
public class WxPayController
  extends BaseController
{

  @Autowired
  private IXlstudentService xlstudentService;
  

  
  @ApiOperation("PC微信查询")
  @PostMapping({"/pcCheckPay"})
  public ResponseBean pcCheckPay(@RequestBody WxpayDto dto)
    throws Exception
  {
    if (StringUtils.isBlank(dto.getIdno())) {
      return ResponseBean.Fail("学生id不能为空");
    }
    QueryWrapper queryWrapper = new QueryWrapper();
    
    queryWrapper.eq("idno", dto.getIdno());
    
    queryWrapper.isNotNull("cost" );
    Xlstudent stu = (Xlstudent)this.xlstudentService.getOne(queryWrapper);
    if (ObjectUtil.isNotEmpty(stu)) {
      return ResponseBean.Success("付款成功", stu);
    }
    return ResponseBean.Fail("付款失败", stu);
  }


}

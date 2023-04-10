package com.ossjk.qlh.wxapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ossjk.config.utils.JsonUtils;
import com.ossjk.config.wxminiapp.WxMaConfiguration;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.system.api.IStudentCommonApi;
import com.ossjk.core.system.dto.WechatBindDto;
import com.ossjk.core.system.dto.XlstudentDto;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.wxapp.dto.StuDto;
import com.ossjk.qlh.wxapp.dto.WxpayDto;
import com.ossjk.qlh.xueli.entity.Signup;
import com.ossjk.qlh.xueli.entity.Xlschool;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.service.ISignupService;
import com.ossjk.qlh.xueli.service.IXlschoolService;
import com.ossjk.qlh.xueli.service.IXlstudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Api(tags = "微信小程序登录")
@RestController
@RequestMapping("/wx/stu")
public class WxappIndexController extends BaseController {

    @Autowired
    private IStudentCommonApi iStudentCommonApi;

    @Autowired
    private IXlstudentService iXlstudentService;

    @Autowired
    private IXlschoolService iXlschoolService;
    @Autowired
    private WxPayService wxService;

    @Autowired
    private IXlschoolService xlschoolService;
    @Autowired
    private IXlstudentService xlstudentService;
    @Autowired
    private ISignupService signupService;

    /**
     * 登陆接口
     */
    @ApiOperation(value = "登陆接口")
    @GetMapping("/getSessionInfo")
    public ResponseBean sessionInfo(@RequestParam(name = "appid")String appid, @RequestParam(name = "code")String code) {

        System.out.println("====================getSessionInfo=================");
        if (StringUtils.isBlank(code)) {
            return  ResponseBean.Fail("empty jscode");
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return ResponseBean.Success(session);
        } catch (WxErrorException e) {
            return  ResponseBean.Fail(e.toString());
        }
    }


        /**
         * <pre>
         * 获取用户信息接口
         * </pre>
         */
    @ApiOperation(value = "获取用户信息接口")
    @GetMapping("/getWxUserInfo")
    public ResponseBean getWxUserInfo(@ApiParam(value = "appid") @RequestParam(name = "appid")String appid,
                                     @ApiParam(value = "sessionKey") @RequestParam(name = "sessionKey")String sessionKey,
                                     @ApiParam(value = "signature") @RequestParam(name = "signature")String signature,
                                     @ApiParam(value = "rawData") @RequestParam(name = "rawData")String rawData,
                                     @ApiParam(value = "encryptedData") @RequestParam(name = "encryptedData")String encryptedData,
                                     @ApiParam(value = "iv") @RequestParam(name = "iv") String iv) {

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        //System.out.println("====================checkUserInfo=================");
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return  ResponseBean.Fail("user check failed");
        }

        // 解密用户信息
        WxMaUserInfo wxUserInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
        return ResponseBean.Success(wxUserInfo);

    }

    @ApiOperation(value = "小程序登录")
    @PostMapping("/wxLogin")
    @ResponseBody
    public ResponseBean login(@RequestBody WechatBindDto wechatBindDto) {
        String jwt = iStudentCommonApi.login(wechatBindDto);
        if (  jwt.length()<20) {
                               //1001
            return ResponseBean.Fail(jwt);
        } else {
                                  //1000
            return ResponseBean.Success(jwt);
        }
    }


    @ApiOperation(value = "微信-账号绑定")
    @PostMapping("/wxBind")
    @ResponseBody
    public ResponseBean wxBind(@RequestBody WechatBindDto wechatBindDto) {


        String token = iStudentCommonApi.wxBind(wechatBindDto);
        if ( "fail".equals(token)) {
            return ResponseBean.Fail(token);
        } else {
            return ResponseBean.Success(token);
        }
    }

    @LogOperation("获取用户信息")
	@ApiOperation(value = "获取用户信息")
	@PostMapping("/getInfo")
	@ResponseBody
	public ResponseBean<XlstudentDto> getXlstudentInfo(HttpServletRequest request) {
       // System.out.println("----------------------/getInfo,Toekn = "+this.getToekn(request));
	    return ResponseBean.Success(iStudentCommonApi.refreshTokenCacheXlstudent(this.getToekn(request)));
	}

    @LogOperation("注册新用户信息")
    @ApiOperation(value = "注册新用户信息")
    @PostMapping("/regist")
    @ResponseBody
    public ResponseBean<Xlstudent> register( StuDto dto) {
        Xlstudent stu = new Xlstudent();
        BeanUtil.copyProperties(dto,stu);

        if(iXlstudentService.save(stu)){
            return ResponseBean.Success(stu);
        }
        return ResponseBean.Fail("注册Fail");
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String  phone(@ApiParam(value = "appid") @RequestParam(name = "appid")String appid,
                        @ApiParam(value = "sessionKey") @RequestParam(name = "sessionKey")String sessionKey,
                        @ApiParam(value = "signature") @RequestParam(name = "signature")String signature,
                      //  @ApiParam(value = "rawData") @RequestParam(name = "rawData")String rawData,
                        @ApiParam(value = "encryptedData") @RequestParam(name = "encryptedData")String encryptedData,
                        @ApiParam(value = "iv") @RequestParam(name = "iv") String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        //if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
         //   return "user check failed" ;
         //  }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return  JsonUtils.toJson(phoneNoInfo.getPhoneNumber())   ;
    }

    @ApiOperation(value = "列出今年可报名的专业")
    @GetMapping("/listProducts")
    public ResponseBean<Map> list() {
        //列出
        return ResponseBean.Success(iXlschoolService.listProducts());
    }

    @ApiOperation(value = "显示(学校)")
    @GetMapping("/listSchool")
    public ResponseBean<List<Xlschool>> school(@ApiParam(value = "层次", required = true) @RequestParam(name = "level") String level,
                                               @ApiParam(value = "专业", required = true) @RequestParam(name = "sbname") String sbname) {

        return ResponseBean.Success(iXlschoolService.pageSchool(level, sbname));
    }

    @ApiOperation("微信支付")
    @PostMapping({"/wxPay"})
    public ResponseBean wxPay(@RequestBody WxpayDto dto, HttpServletRequest request)
            throws Exception
    {
        if (StringUtils.isBlank(dto.getSchool())) {
            return ResponseBean.Fail("报读学校id不能为空");
        }
        if (ObjectUtil.isEmpty(dto.getIdno())) {
            return ResponseBean.Fail("身份证信息不完整");
        }
        Xlschool school = (Xlschool)this.xlschoolService.getById(dto.getSchool());
        if (ObjectUtil.isEmpty(school)) {
            return ResponseBean.Fail("报读学校id错误");
        }

        QueryWrapper<Xlstudent> qw = new QueryWrapper<Xlstudent>();
        qw.eq("idno",dto.getIdno());
        Xlstudent stu = (Xlstudent)this.xlstudentService.getOne(qw);
        if(stu == null){
            //如果学生不存在
            stu = new Xlstudent();
            stu.setName(dto.getName());
            stu.setIdno(dto.getIdno());
            stu.setOpenid(dto.getOpenid());
            stu.setMoblie(dto.getMobile());
            stu.setMz(dto.getMz());
            stu.setSex(dto.getSex());

            this.iXlstudentService.save(stu);
        }
        Signup sign  = new Signup();
        sign.setStuid(stu.getId());
        sign.setSchool(dto.getSchool());
        sign.setSubject(dto.getSubject());
        sign.setStuid(stu.getId());
        sign.setLevel(dto.getLevel());
        signupService.save(sign);

        WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = new WxPayUnifiedOrderRequest();
        wxPayUnifiedOrderRequest.setBody("广州市靖凯开源软件技术有限公司");
        wxPayUnifiedOrderRequest.setOpenid(dto.getOpenid());
        wxPayUnifiedOrderRequest.setTradeType("JSAPI");

        wxPayUnifiedOrderRequest.setTotalFee(Integer.valueOf(BigDecimal.valueOf(0.01D).multiply(new BigDecimal("100")).intValue()));
        wxPayUnifiedOrderRequest.setSpbillCreateIp(request.getRemoteAddr());
        wxPayUnifiedOrderRequest.setNotifyUrl("https://xlbk.ossjk.cn/xueli/wx/notify");

        wxPayUnifiedOrderRequest.setOutTradeNo( sign.getId() );

        WxPayMpOrderResult wxPayOrder = (WxPayMpOrderResult)this.wxService.createOrder(wxPayUnifiedOrderRequest);

        return ResponseBean.Success(wxPayOrder);
    }

    @ApiOperation("支付回调通知处理")
    @PostMapping({"/notify"})
    public String parseOrderNotifyResult(HttpServletRequest request)
            throws WxPayException, IOException
    {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String resultxml = new String(outSteam.toByteArray(), "utf-8");

        WxPayOrderNotifyResult notifyResult = this.wxService.parseOrderNotifyResult(resultxml);
        if (!"SUCCESS".equals(notifyResult.getReturnCode())) {
            return WxPayNotifyResponse.fail("状态码状态为失败。");
        }
        if (!notifyResult.getAppid().equals(this.wxService.getConfig().getAppId())) {
            return WxPayNotifyResponse.fail("小程序ID不一致。");
        }
        if (!notifyResult.getMchId().equals(this.wxService.getConfig().getMchId())) {
            return WxPayNotifyResponse.fail("商户号不一致。");
        }
       Signup stu = (Signup)this.signupService.getOne((Wrapper)new QueryWrapper().eq("ordercode", notifyResult.getOutTradeNo()));
        if (ObjectUtil.isEmpty(stu)) {
            return WxPayNotifyResponse.fail("不存在改订单。");
        }

        stu.setCost(notifyResult.getTotalFee()+"");

        stu.setPytm(notifyResult.getTimeEnd());

        stu.setWxorder(notifyResult.getTransactionId());

        if (this.signupService.updateById(stu)) {
            return WxPayNotifyResponse.success("成功。");
        }
        return WxPayNotifyResponse.fail("失败。");
    }

}

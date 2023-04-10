package com.ossjk.qlh.xueli.controller;


import com.alibaba.fastjson.JSON;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.service.IUserService;
import com.ossjk.qlh.xueli.dto.Xlsturecord2DTO;
import com.ossjk.qlh.xueli.dto.XlsturecordDTO;
import com.ossjk.qlh.xueli.entity.Xlsturecord;
import com.ossjk.qlh.xueli.service.IXlstudentService;
import com.ossjk.qlh.xueli.service.IXlsturecordService;
import com.ossjk.qlh.xueli.vo.SouSuoVo;
import com.ossjk.qlh.xueli.vo.XlsturecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 *
 * @Package: com.ossjk.qlh.xueli.controller
 * @ClassName: XlstudentController
 * @Description: Xlstudent-控制器
 * @author: Rick.yang
 * @date: 2022-02-22 15:41:51
 */
@Api(tags = "跟进学生")
@RestController
@RequestMapping("/genj")
public class XlsturecordController extends BaseController {

    @Autowired
    private ResourceMappersProperties ymlUri;
    @Autowired
    private IXlstudentService iXlstudentService;
    @Autowired
    private IXlsturecordService iXlsturecordService;
    @Autowired
    private ISystemCommonApi iSystemCommonApi;
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "批量添加跟进记录")
//	@RequiresPermissions("003000050015")
    @PostMapping("/addAllRcds")
    public ResponseBean addAllRcd(@RequestBody XlsturecordDTO record, HttpServletRequest request) {
        if (iXlsturecordService.addAllRecords(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }

    @ApiOperation(value = "添加跟进记录")
//	@RequiresPermissions("003000050015")
    @PostMapping("/addRcd")
    public ResponseBean addRcd(@RequestBody Xlsturecord record, HttpServletRequest request) {

        if (record.getUsername() == null) {
            //查询策略    //自己
            LoginUser me = iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
            record.setUsername(me.getName());
            record.setGntm(new Date());
        } else if (record.getUsername() != null) {
            //根据id查名字
            User usr = userService.getById(record.getUsername());
            if (usr == null) {
                //从待跟进点击跟进是传入的是名字
                record.getUsername();
            } else {
                record.setUsername(usr.getName());
            }
        }

        if (record.getContent() != null) {
            record.setGntm(new Date());
        }

        if (record.getResult() != null && record.getResult().equals("继续跟")) {
            //根据id查询出没更改前的数据
            Xlsturecord xlt = iXlsturecordService.getById(record);
            if (xlt != null){
                Map map = new HashMap<>();
                map.put("gntm", xlt.getGntm());
                map.put("gnneedtm", xlt.getGnneedtm());
                map.put("username", xlt.getUsername());
                map.put("type", xlt.getType());
                map.put("content", xlt.getContent());
                map.put("result", xlt.getResult());
//              ArrayList<Object> list = new ArrayList<Object>(Collections.singleton(map));
                record.setRecords(JSON.toJSONString(map));
            }
        }
        if (iXlsturecordService.saveOrUpdate(record)) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }


    @ApiOperation(value = "跟进记录列表")
//	@RequiresPermissions("003000050015")
    @GetMapping("/listRcds")
    public ResponseBean listRcd(@ApiParam(value = "学生id", required = true)
                                @RequestParam(name = "stuid", required = true) String stuid,
                                @ApiParam(value = "类型:2-待跟进,1-已跟进", required = true)
                                @RequestParam(name = "type", required = true) Integer type) {
        QueryWrapper<Xlsturecord> queryWrapper = new QueryWrapper<Xlsturecord>();
        queryWrapper.eq("stuid", stuid);
        if (type == 2) {
            queryWrapper.isNull("gntm");
        } else {
            queryWrapper.isNotNull("gntm");
        }

        return ResponseBean.Success(iXlsturecordService.list(queryWrapper));
    }

    @ApiOperation(value = "查看全部记录列表")
//    @RequiresPermissions("")
    @GetMapping("/listAllRcds")
    public ResponseBean<Map> list(Page page, HttpServletRequest request) {

        //取得自己
        LoginUser me = iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
        String role = me.getRoleCode().iterator().next();

        System.out.println(role);

        QueryWrapper<XlsturecordVo> queryWrapper = new QueryWrapper<XlsturecordVo>();

        Map datds = new HashMap();

        if ("admin".equals(role)) {
            //admin
            datds.put("list", iXlsturecordService.pageVo(1, page, queryWrapper,null));
        } else if ("mgr".equals(role)) {
            //部门
            datds.put("list",iXlsturecordService.pageVo(2,page,null,me.getName()));
        } else if ("xz".equals(role)) {
            //自己+班主任
            queryWrapper.and(w -> w.eq("b.teacher", me.getName()).or().eq("a.username", me.getName()));
//            queryWrapper.eq("b.teacher",me.getName());
            datds.put("list", iXlsturecordService.pageVo(3, page, queryWrapper,null));
        } else {
            //自己
            queryWrapper.eq("a.username", me.getName());
        }

        return ResponseBean.Success(datds);
    }


    @ApiOperation(value = "搜索框查询")
//    @RequiresPermissions("")
    @GetMapping("/souSuo")
    public ResponseBean<List<SouSuoVo>> souSuo(@ApiParam(value = "被跟进人", required = false)
                                               @RequestParam(name = "name", required = false) String name,
                                               @ApiParam(value = "跟进人", required = false)
                                               @RequestParam(name = "username", required = false) String username) {
        QueryWrapper<SouSuoVo> queryWrapper = new QueryWrapper<SouSuoVo>();
        if (StrUtil.isNotBlank(name)) {
            queryWrapper.eq("b.name", name);
        }
        if (StrUtil.isNotBlank(username)) {
            queryWrapper.eq("a.username", username);
        }
        return ResponseBean.Success(iXlsturecordService.souSuokuang(queryWrapper));
    }

    @ApiOperation(value = "推送签收列表")
    @RequiresPermissions("00300010")
    @GetMapping("/listAllMsgs")
    public ResponseBean<Page<Xlsturecord2DTO>> listAllMsgs(Page page, HttpServletRequest request) {

        //取得自己
        LoginUser me = iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
        String role = me.getRoleCode().iterator().next();
        System.out.println(role);

        //动态SQL
        QueryWrapper<Xlsturecord> queryWrapper = new QueryWrapper<Xlsturecord>();
        queryWrapper.isNotNull("s.message");

        return ResponseBean.Success(iXlsturecordService.countMsgqiansou(page,queryWrapper));
    }

    @ApiOperation(value = "列出签收学生信息")
    @RequiresPermissions("00300010")
    @PostMapping("/listStuByMsg")
    public ResponseBean<List<Xlsturecord2DTO>> listStuByMsg(@RequestBody Xlsturecord2DTO stuDto, HttpServletRequest request) {

        //取得自己
        LoginUser me = iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
        String role = me.getRoleCode().iterator().next();
        //动态SQL
        QueryWrapper<Xlsturecord2DTO> queryWrapper = new QueryWrapper<Xlsturecord2DTO>();
        queryWrapper.isNotNull("s.message");
        //left(s.crtm,10) , s.type,stu.school,s.crer,stu.bdyear
        queryWrapper.eq("left(s.crtm,10)",stuDto.getCrtm());
        queryWrapper.eq("s.type",stuDto.getType());
        queryWrapper.eq("stu.school",stuDto.getSchool());
        queryWrapper.eq("s.crer",stuDto.getCrer());
        queryWrapper.eq("stu.bdyear",stuDto.getGrade());

        return ResponseBean.Success(iXlsturecordService.qiansouStus(  queryWrapper));
    }
    
    @ApiOperation(value = "删除记录")
//	@RequiresPermissions("003000050010")
    @DeleteMapping("/removeRecd")
    public ResponseBean removeRecd(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
        if (iXlsturecordService.removeByIds(Arrays.asList(ids))) {
            return ResponseBean.Success();
        } else {
            return ResponseBean.Fail();
        }
    }
}

package com.ossjk.qlh.wxapp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;

import com.ossjk.qlh.xueli.entity.Signup;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.service.ISignupService;
import com.ossjk.qlh.xueli.service.IXlschoolService;
import com.ossjk.qlh.xueli.service.impl.XlsturecordServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright  2022-05-10 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.controller
 * @ClassName: XlsignController
 * @Description: Xlsign-控制器
 * @author: linhao
 * @date:  2022-05-10 11:18:07 
 */
@Api(tags = "学历报考")
@RestController
@RequestMapping("/wx/xlsign")
public class XlsignController extends BaseController {
	@Autowired
	private IXlschoolService iXlschoolService;
	@Autowired
	private ISignupService   signService;

	@Autowired
	private XlsturecordServiceImpl rcrdService;

	@ApiOperation(value = "填写报名表")
//	@RequiresPermissions("")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Signup record) {

			if (signService.save(record)) {
				return ResponseBean.Success();
			} else {
				return ResponseBean.Fail();
			}
	}

	@ApiOperation(value = "自己报名表")
	@GetMapping("/listRcds")
	public ResponseBean listRcd(@ApiParam(value = "学生id", required = true) @RequestParam(name = "id", required = true) String id) {

		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("stuid",id);

		return ResponseBean.Success(signService.list(queryWrapper));
	}


	@ApiOperation(value = "列出推送给自己的消息")
	@GetMapping("/listMsgs")
	public ResponseBean listMsgs(@ApiParam(value = "学生id", required = true) @RequestParam(name = "id", required = true) String id) {

		return ResponseBean.Success(  this.rcrdService.lsTuisongByStuid(id) );
	}

	@ApiOperation(value = "签收推送消息")
	@GetMapping("/chkinMsgs")
	public ResponseBean checkinMsgs(@ApiParam(value = "跟进消息id", required = true) @RequestParam(name = "id", required = true) String id) {

		return ResponseBean.Success(  this.rcrdService.checkInMsg(id) );
	}


	@ApiOperation(value = "编辑")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Signup record) {
		if (signService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "审核")
	@PostMapping("/checkBatch")
	public ResponseBean checkBatch(@RequestBody Signup record) {

		if (signService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}


//		Map<String, Object> param=new HashedMap();
//		param.put("ids", Arrays.asList(ids));
//		param.put("bminfos", Arrays.asList(bminfos));
//		List<Xlstudent> listmap= (List<Xlstudent>) param;
//		List<Xlstudent> list = new ArrayList<>();
//		for (Xlstudent xls : listmap){
//			String id = xls.getId();
//			String bmingo = xls.getBminfo();
//			Xlstudent xlstudent = iXlsignService.getById(id);
//			xlstudent.setBminfo(bmingo);
//			list.add(xlstudent);
//		}
//		if (iXlsignService.updateBatchById(list)) {
//			return ResponseBean.Success();
//		} else {
//			return ResponseBean.Fail();
//		}
	}

}

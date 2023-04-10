package com.ossjk.qlh.xueli.controller;



import java.util.*;

import cn.hutool.core.bean.BeanUtil;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.myUtil.StringUtil;

import com.ossjk.qlh.system.entity.Department;
import com.ossjk.qlh.system.service.IThirdaccountService;
import com.ossjk.qlh.system.vo.User2Vo;
import com.ossjk.qlh.system.vo.UserinDeptVo;
import com.ossjk.qlh.xueli.entity.Xlschool;
import com.ossjk.qlh.xueli.service.IXlschoolService;
import com.ossjk.qlh.xueli.vo.XlSchoolVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.annotation.LogModule;
import com.ossjk.core.system.annotation.LogOperation;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.dto.UserDto;
import com.ossjk.qlh.system.entity.User;
import com.ossjk.qlh.system.entity.UserRole;
import com.ossjk.qlh.system.service.IUserRoleService;
import com.ossjk.qlh.system.service.IUserService;
import com.ossjk.qlh.system.vo.UserToUpdateVo;
import com.ossjk.qlh.system.vo.UserVo;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/** 
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.controller
 * @ClassName: XlschoolController
 * @Description: Xlschool-控制器
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:50 
 */
@Api(tags = "学历产品")
@RestController
@RequestMapping("/xueli/xlschool")
public class XlschoolController extends BaseController {
	
	@Autowired   // AOP切面（动态代理需要基于接口）
	private IXlschoolService iXlschoolService;

	@ApiOperation(value = "列表")
	@RequiresPermissions("00300000")
	@GetMapping("/list")
	public ResponseBean<Page<Xlschool>> list(Page page,@ApiParam(value = "name", required = false)
												@RequestParam(name = "name",required = false) String name) {
		//动态查下参数
		QueryWrapper<Xlschool> queryWrapper = new QueryWrapper<Xlschool>();
		//前端的数据是emp，数据库的字段是userid
		if (!StrUtil.isBlank(name)){
			queryWrapper.eq("skname", name);
		}
//		iXlschoolService.listProducts();
		return ResponseBean.Success(iXlschoolService.page(page,queryWrapper));
	}


	@ApiOperation(value = "添加")
	@RequiresPermissions("003000000005")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Xlschool record) {
		if (iXlschoolService.save(record)) {
			return ResponseBean.Success();
		} else {
			//ResponseBean.Exception()
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	@RequiresPermissions("00300000")
	@GetMapping("/toUpdate")
	public ResponseBean<Xlschool> toUpdate(@ApiParam(value = "id", required = true)
											   @RequestParam(name = "id",required = true) String id) {
		Xlschool xlschool = iXlschoolService.getById(id);
		if (ObjectUtil.isNotNull(xlschool)) {
			return ResponseBean.Success(xlschool);
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "添加专业")
	@RequiresPermissions("003000000005")
	@PutMapping("/updtSubs")  //RESTful
	public ResponseBean updateSubs(@RequestBody Xlschool record) {
		Xlschool org = iXlschoolService.getById(record.getId());
		org.setSubjects(record.getSubjects());
		if (iXlschoolService.updateById(org)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "编辑")
	@RequiresPermissions("003000000005")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Xlschool record) {
		if (iXlschoolService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	@RequiresPermissions("003000000000")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iXlschoolService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}

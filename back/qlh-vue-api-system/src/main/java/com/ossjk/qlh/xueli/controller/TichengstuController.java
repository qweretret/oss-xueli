package com.ossjk.qlh.xueli.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.system.entity.Department;
import com.ossjk.qlh.system.service.IDepartmentService;
import com.ossjk.qlh.xueli.dto.TichengDTO;
import com.ossjk.qlh.xueli.entity.Tichengstu;
import com.ossjk.qlh.xueli.service.ITichengstuService;
import com.ossjk.qlh.xueli.service.IXlstudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Copyright  2022-04-18 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.book.controller
 * @ClassName: TichengstuController
 * @Description: Tichengstu-控制器
 * @author: linhao
 * @date:  2022-04-18 16:13:59 
 */
@Api(tags = "业绩提成")
@RestController
@RequestMapping("/xueli/tichengstu")
public class TichengstuController extends BaseController {

	@Autowired
	private IDepartmentService iDepartmentService;
	@Autowired
	private ITichengstuService iTichengstuService;
	@Autowired
	private IXlstudentService studentService;
	@Autowired
	private ISystemCommonApi iSystemCommonApi;

	@ApiOperation(value = "列表")
	@RequiresPermissions("00350020")
	@GetMapping("/list")
	public ResponseBean<Map> list(Page page,
											   @ApiParam(value = "提成员工",required = false) @RequestParam(name = "emp",required = false) String emp,
											   @ApiParam(value = "提成学生",required = false) @RequestParam(name = "stu",required = false) String stu,
											   @ApiParam(value = "所选部门",required = false) @RequestParam(name = "dpt",required = false) String dpt,
											   @ApiParam(value = "支付时间",required = false) @RequestParam(name = "month",required = false) String month,
											   HttpServletRequest request) {
		//取得自己
		LoginUser me =iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
		String role = me.getRoleCode().iterator().next();

		QueryWrapper<Tichengstu> queryWrapper = new QueryWrapper<Tichengstu>();
		Map datas = new HashMap();
		datas.put("dpts",iDepartmentService.list(new QueryWrapper<Department>().orderByAsc("level","sort")));

		//前端的数据是emp，数据库的字段是userid
		if (!StrUtil.isBlank(emp)) {
			queryWrapper.like("userid", emp);
		}
		if (!StrUtil.isBlank(stu)) {
			queryWrapper.like("a.name",stu );
		}
		if (!StrUtil.isBlank(dpt)) {
			queryWrapper.like("dname",dpt );
		}
		if (!StrUtil.isBlank(month)) {
			queryWrapper.like("paydate",month );
		}


		//权限控制
		if("admin".equals(role)&dpt==null){
			// do nothing
			datas.put("list",iTichengstuService.page(1,page,queryWrapper,null));
		}else if ("mgr".equals(role)|dpt!=null){
			if(dpt!=null){
				//在admin中选择部门查询
				datas.put("list",iTichengstuService.page(2, page, queryWrapper, dpt));
			}else {
				//本部门的员工
				queryWrapper.lt("a.crtm", new Date());
				datas.put("list",iTichengstuService.page(3, page, queryWrapper, me.getName()));
			}
		}else if("xz".equals(role)){
			//自己招的+自己带班的
			queryWrapper.and(w -> w.eq("teacher", me.getName()).or().eq("b.userid", me.getName()));
			datas.put("list",iTichengstuService.page(4, page, queryWrapper, null));
		}else{//其他
			//自己的
			queryWrapper.eq("b.userid",me.getName());
			datas.put("list",iTichengstuService.page(4,page,queryWrapper,null));
		}
		return ResponseBean.Success(datas);
	}

	@ApiOperation(value = "跟进学生ids查询列表")
	//@RequiresPermissions("")
	@DeleteMapping("/listTcStus")
	public ResponseBean<List<Tichengstu>> listStusByIds(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids   ) {

		QueryWrapper<Tichengstu> queryWrapper = new QueryWrapper<Tichengstu>();
 
		queryWrapper.in("stuid",ids);

		return ResponseBean.Success(   iTichengstuService.list(queryWrapper)      );
	}


	@ApiOperation(value = "添加")
	@RequiresPermissions("003500200000")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody TichengDTO record) {
		if (iTichengstuService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	@RequiresPermissions("00350020")
	@GetMapping("/toUpdate")
	public ResponseBean<Tichengstu> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Tichengstu tichengstu = iTichengstuService.getById(id);
		if (ObjectUtil.isNotNull(tichengstu)) {
			return ResponseBean.Success(tichengstu);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
	@RequiresPermissions("003500200005")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Tichengstu record) {
		if (iTichengstuService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@GetMapping("/fafa")
	@RequiresPermissions("003500200010")
	public ResponseBean 发放( String[] ids,String paytm )   {

		if (iTichengstuService.fafang(ids ,paytm  )) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	@RequiresPermissions("003500200015")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iTichengstuService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}

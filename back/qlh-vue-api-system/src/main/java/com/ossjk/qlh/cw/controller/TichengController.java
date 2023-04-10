package com.ossjk.qlh.cw.controller;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.cw.entity.Ticheng;
import com.ossjk.qlh.cw.service.ITichengService;
import com.ossjk.qlh.oa.vo.NaviVo;

import com.ossjk.qlh.system.entity.Department;
import com.ossjk.qlh.system.service.IDepartmentService;
import com.ossjk.qlh.system.service.IUserService;
import com.ossjk.qlh.system.vo.UserinDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Package: com.ossjk.qlh.cw.controller
 * @ClassName: TichengController
 * @Description: Ticheng-控制器
 * @author: Rick.yang
 */
@Api(tags = "业绩提成")
@RestController
@RequestMapping("/cw/ticheng")
public class TichengController extends BaseController {
	
	@Autowired
	private ITichengService iTichengService;
	@Autowired
	private ISystemCommonApi iSystemCommonApi;
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IDepartmentService departmentService;

	@ApiOperation(value = "列表")
	@RequiresPermissions("00350020")
	@GetMapping("/list")
	public ResponseBean<Map> list(Page page,   @RequestParam(name = "emp",required = false)   String emp,
										               	 @RequestParam(name = "dpt",required = false)   String dpt,
											             @RequestParam(name = "month",required = false) String month,
											             @RequestParam(name = "stu",required = false) String stu,HttpServletRequest request   ) {

		LoginUser usr =iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
		String role  = usr.getRoleCode().iterator().next();
		QueryWrapper<Ticheng> queryWrapper = new QueryWrapper<Ticheng>();
		Map map = new HashMap();

		 if( month!=null && month.length()>10) {
			// 状态选择通过时的bug修正
			month =  StringUtil.calMth(month.substring(0,7),1);
		}
		if(  month!=null   ) {
			NaviVo navi = new NaviVo();
			navi.setCrnt(month);
			navi.setPrev(StringUtil.calMth(month, -1));
			navi.setNext(StringUtil.calMth(month, 1));
			map.put("navi", navi);
			queryWrapper.eq("left(crtm,7)",month);
		}else if(  stu!=null   ) {
			//QueryWrapper<Stu> queryWrapper2 = new QueryWrapper<Stu>();
			//queryWrapper2.like("name",stu);
			//List<Stu> stus = iStuService.list(queryWrapper2);
			//  if(stus!=null && stus.size()>0){
			//	List<String> stuids = new ArrayList<>();
			//	for (Stu s  :stus  ) {
			//		stuids.add(s.getId());
			//	}
			//	queryWrapper.in("stuid",stuids);
			//	}else{
				//返回空
			//		map.put("tc", page);
			//		return ResponseBean.Success( map );
			//	}
		}
		if(role.equals("admin") ||role.equals("admin2") ||role.equals("mgr")){
			map.put("dpts",departmentService.list( new QueryWrapper<Department>().orderByAsc("sort") )  );
              //看全部
			if(dpt == null || dpt.length()==0||"靖凯开源".equals(dpt)){
				if(emp!=null && emp.length()>0){
					queryWrapper.eq("userid",emp);
				}
			}else{
				List<UserinDeptVo>  emps = iUserService.selectVoByDptName( dpt );
				List<String> unames= new ArrayList();
				for ( UserinDeptVo ep:emps ) {
					unames.add(ep.getName());
				}
				queryWrapper.in("userid",unames);
			}
		}else if(role.equals("mgr2")  || role.equals("mgr3")  ){ //看部门
			//默认看整个部门
			if(emp == null || emp.length()==0){
				List<UserinDeptVo>  emps = iUserService.selectVoByDptName(usr.getDname());
				List<String> unames= new ArrayList();
				for ( UserinDeptVo ep:emps ) {
					unames.add(ep.getName());
				}
				queryWrapper.in("userid",unames);
			}else{
				queryWrapper.eq("userid",emp);
			}
		}else{//看自己的
			queryWrapper.eq("userid",usr.getName());
		}

		queryWrapper.orderByDesc("crtm");
		map.put("tc", iTichengService.page(page,queryWrapper));
		return ResponseBean.Success(map);
	}

	@ApiOperation(value = "添加")
	@RequiresPermissions("003500200000")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Ticheng record) {
		Date now = new Date();
		record.setCrtm(now);
		if (iTichengService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "去编辑")
	//@RequiresPermissions("")
	@GetMapping("/toUpdate")
	public ResponseBean<Ticheng> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Ticheng ticheng = iTichengService.getById(id);
		if (ObjectUtil.isNotNull(ticheng)) {
			return ResponseBean.Success(ticheng);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
	@RequiresPermissions("003500200005")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Ticheng record) {
		if (iTichengService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}


	@GetMapping("/fafa")
	@RequiresPermissions("003500200010")
	public ResponseBean 发放( String[] ids,String paytm )   {

		if (iTichengService.fafang(ids ,paytm  )) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
	@RequiresPermissions("003500200015")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iTichengService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}
}

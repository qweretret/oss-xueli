package com.ossjk.qlh.xueli.controller;



import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;

import com.ossjk.qlh.xueli.dto.XlstudentDTO;
import com.ossjk.qlh.xueli.entity.Signup;
import com.ossjk.qlh.xueli.entity.Xlstudent;

import com.ossjk.qlh.xueli.service.IXlstudentService;
import com.ossjk.qlh.xueli.service.IXlsturecordService;

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

import com.ossjk.core.vo.ResponseBean;

import com.ossjk.qlh.system.service.IUserService;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.servlet.http.HttpServletRequest;

/** 
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.controller
 * @ClassName: XlstudentController
 * @Description: Xlstudent-控制器
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
@Api(tags = "学历学生")
@RestController
@RequestMapping("/xueli/xlstudent")
public class XlstudentController extends BaseController {

	@Autowired
	private ResourceMappersProperties ymlUri;
	@Autowired
	private IXlstudentService iXlstudentService;
	@Autowired
	private IXlsturecordService iXlsturecordService;
	@Autowired
	private ISystemCommonApi iSystemCommonApi;
	@Autowired
	private  IUserService userService;

	@ApiOperation(value = "列表")
  	@RequiresPermissions("00300005")
	@GetMapping("/list")
	public ResponseBean<Map> list(Page page,  @ApiParam(  value = "电话或名字",required = false) @RequestParam(name = "name",required = false) String name,
		                                      @ApiParam(value = "学校名称",required = false)  @RequestParam(name = "school",required = false) String school,
											  @ApiParam(value = "招生员",required = false)  @RequestParam(name = "uname",required = false) String uname,
											  @ApiParam(value = "入学年份",required = false)  @RequestParam(name = "bdyear",required = false) String bdyear,
			                                  @ApiParam(value = "考试区域",required = false)  @RequestParam(name = "ksarea",required = false) String ksarea,
								  			  @ApiParam(value = "班主任",required = false)  @RequestParam(name = "teacher",required = false) String teacher,
								              @ApiParam(value = "状态",required = false)  @RequestParam(name = "condi",required = false) String condi,
								  HttpServletRequest request) {

        //取得自己
		LoginUser me =iSystemCommonApi.refreshTokenCacheLoginUser(this.getToekn(request));
		String role = me.getRoleCode().iterator().next();

		System.out.println(role);
		//动态SQL
		QueryWrapper<XlstudentDTO> queryWrapper = new QueryWrapper<XlstudentDTO>();
		Map datas = new HashMap();

		if("mgr".equals(role)){
			queryWrapper.lt("b.crtm",new Date());
		}

		datas.put("allYear",this.iXlstudentService.findAllYears());

		if(  "待跟进".equals(condi) ){
				queryWrapper.isNotNull("a.gnneedtm");
			    datas.put("list",iXlstudentService.page(4,page,queryWrapper,null));
			   return ResponseBean.Success(datas);
		}else{
			if(StrUtil.isNotBlank(name)){
				queryWrapper.and(  w->w.eq("moblie",name).or().eq("name",  name)   );
			}
			if(StrUtil.isNotBlank(school)){
				queryWrapper.eq("school",school);
			}
			if(StrUtil.isNotBlank(uname)){
				queryWrapper.eq("uname",uname);
			}
			if(StrUtil.isNotBlank(bdyear)){
				queryWrapper.eq("bdyear",bdyear);
			}
			if(StrUtil.isNotBlank(ksarea)){
				queryWrapper.eq("ksarea",ksarea);
			}
			if(StrUtil.isNotBlank(teacher)){
				queryWrapper.eq("teacher",teacher);
			}
		}

		//权限控制
		if("admin".equals(role)){
             // do nothing
			datas.put("list",iXlstudentService.page(1,page,queryWrapper,null));
		}else if("mgr".equals(role)){
           // 本部门的员工
			datas.put("list",iXlstudentService.page(2,page,queryWrapper,me.getName()));
		}else if("xz".equals(role)){
			//自己招的+自己带班的]

			queryWrapper.and( w->w.eq("tacher",me.getName()).or().eq("a.userid", me.getName()) );

			datas.put("list",iXlstudentService.page(3,page,queryWrapper,null));
		}else{//其他
			//自己的
			queryWrapper.eq("a.userid",me.getName());
			datas.put("list",iXlstudentService.page(3,page,queryWrapper,null));
		}

		return ResponseBean.Success(datas);
	}

	@ApiOperation(value = "添加")
 	@RequiresPermissions("003000050005")
	@PostMapping("/save")
	public ResponseBean save(@RequestBody Xlstudent record) {
		if (iXlstudentService.save(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "去编辑")
 	@RequiresPermissions("00300005")
	@GetMapping("/toUpdate")
	public ResponseBean<Xlstudent> toUpdate(@ApiParam(value = "id") @RequestParam(name = "id") String id) {
		Xlstudent xlstudent = iXlstudentService.getById(id);
		if (ObjectUtil.isNotNull(xlstudent)) {
			return ResponseBean.Success(xlstudent);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "编辑")
 	@RequiresPermissions("003000050005")
	@PutMapping("/update")
	public ResponseBean update(@RequestBody Xlstudent record) {
		if (iXlstudentService.updateById(record)) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "删除")
 	@RequiresPermissions("003000050015")
	@DeleteMapping("/remove")
	public ResponseBean remove(@ApiParam(value = "ids") @RequestParam(name = "ids") String[] ids) {
		if (iXlstudentService.removeByIds(Arrays.asList(ids))) {
			return ResponseBean.Success();
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "上传进行解析")
 	@RequiresPermissions("003000050005")
	@GetMapping("/toUpload")
	public ResponseBean<List<Signup>> toUpload(@ApiParam(value = "文件名称") @RequestParam(name = "fname") String fname  ) {
		// 读取yuml的配置数据
		List<ResourceMappersProperties.ResourceMapper> resourceMapperList = ymlUri.getMappers();
		Map<String, String> uriMap = resourceMapperList.stream().collect(Collectors.toMap(ResourceMappersProperties.ResourceMapper::getUri, ResourceMappersProperties.ResourceMapper::getFile));

		//获取上传的文件           路径
		File file = new File( uriMap.get("/statics/excels") , fname);

		//调用解析的算法
		List<Signup> datas = this.iXlstudentService.parseExcel(file);

		if ( datas!=null && datas.size()>0    ) {
			file.delete();
			return ResponseBean.Success(datas);
		} else {
			return ResponseBean.Fail();
		}
	}


	@ApiOperation(value = "导入添加")
 	@RequiresPermissions("003000050005")
	@PostMapping("/impDatas")
	public ResponseBean impDatas(@RequestBody Signup[] stus) {
		String msg =iXlstudentService.impDatas(stus) ;
		if (!msg.equals("导入失败")) {
			return ResponseBean.Success(msg);
		} else {
			return ResponseBean.Fail();
		}
	}

	@ApiOperation(value = "自动补全学生")
	@GetMapping("/autoCSnm")
	@RequiresPermissions("00300005")
	public ResponseBean<List<Map>> stuNameInduction(@ApiParam(value = "snm") @RequestParam(name = "snm") String snm){
		List<Map> stus = this.iXlstudentService.stuNameInduction(snm);
		if (stus!=null) {
			return ResponseBean.Success(stus);
		} else {
			return ResponseBean.Fail();
		}

	}
}

package com.ossjk.qlh.xueli.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.service.IReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * @date:  2022-02-22 15:41:51 
 */
@Api(tags = "报表接口")
@RestController
@RequestMapping("/xueli/report")
public class ReportController extends BaseController {

	@Autowired
	private IReportService rptService;

	@ApiOperation(value = "1.每学年的学生数量、按学校分、级别（本、专）分")
  	//@RequiresPermissions("00300005")
	@GetMapping("/lsStus")
	public ResponseBean<Map> list(@ApiParam(  value = "年级",required = true) @RequestParam(name = "bdyear",required = true) String bdyear,
								  @ApiParam(  value = "学校",required = false) @RequestParam(name = "school",required = false) String school,
								  @ApiParam(  value = "级别",required = false) @RequestParam(name = "level",required = false) String level,
								  HttpServletRequest request) {

		//动态SQL
		QueryWrapper<Xlstudent> queryWrapper = new QueryWrapper<Xlstudent>();

	    queryWrapper.eq(  "bdyear",bdyear);

		if(StrUtil.isNotBlank(school)){
			if(StrUtil.isNotBlank(level)){
				queryWrapper.select( " bdyear, level,count(id) as cnt ");
				queryWrapper.groupBy("bdyear","level");
				queryWrapper.orderByAsc("bdyear","level");
			}else{
				queryWrapper.select( " bdyear,count(id) as cnt ");
				queryWrapper.groupBy(  "bdyear");
				queryWrapper.orderByAsc("bdyear");
			}
		}else{
			if(StrUtil.isNotBlank(level)){
				queryWrapper.select( " bdyear,school,level,count(id) as cnt ");
				queryWrapper.groupBy("bdyear","school","level");
				queryWrapper.orderByAsc("bdyear","school","level");

			}else{
				queryWrapper.select( " bdyear,school,count(id) as cnt ");
				queryWrapper.groupBy(  "bdyear","school");
				queryWrapper.orderByAsc("bdyear","school");
			}
		}
		return ResponseBean.Success(rptService.countStudent(queryWrapper,bdyear,school));
	}
	// 6.
	@ApiOperation(value = "2.各专业/各学校本专科、年级，的学生分布情况")
	@GetMapping("/lsStus2")
	public ResponseBean<Map> list2(@ApiParam(  value = "级别：专升本/高起专",required = false) @RequestParam(name = "level",required = false) String level,
								   @ApiParam(    value = "专业名称",required = false) @RequestParam(name = "subject",required = false) String subject) {

		//动态SQL
		QueryWrapper<Xlstudent> queryWrapper = new QueryWrapper<Xlstudent>();

		if(StrUtil.isNotBlank(level)){
			queryWrapper.eq("level",level);
			if(StrUtil.isNotBlank(subject)){
				queryWrapper.select( "subject,school,bdyear,level,count(id) as cnt ");
				queryWrapper.groupBy("subject,school,bdyear,level");
				queryWrapper.eq("subject",subject);
				queryWrapper.orderByAsc("subject,school,bdyear,level");
			}else{
				queryWrapper.select( "subject,bdyear,level,count(id) as cnt ");
				queryWrapper.groupBy("subject,bdyear,level");
				queryWrapper.orderByAsc("subject,bdyear,level");
			}
		}else{
			if(StrUtil.isNotBlank(subject)){
				queryWrapper.select( "subject,school,bdyear,level,count(id) as cnt ");
				queryWrapper.groupBy("subject,school,bdyear,level");
				queryWrapper.eq("subject",subject);
				queryWrapper.orderByAsc("subject,school,bdyear,level");
			}else{
				queryWrapper.select( "subject,bdyear,level,count(id) as cnt ");
				queryWrapper.groupBy("subject,bdyear,level");
				queryWrapper.orderByAsc("subject,bdyear,level");
			}
		}

		return ResponseBean.Success(rptService.countStudent2(queryWrapper,level));
	}

	@ApiOperation(value = "3.学生的历史缴费情况查询")
	@GetMapping("/lsCosts")
	public ResponseBean<Map> list3(@ApiParam(  value = "年级",required = false) @RequestParam(name = "bdyear",required = false) String bdyear,
								   @ApiParam(  value = "学校",required = false) @RequestParam(name = "school",required = false) String school,
								   @ApiParam(  value = "级别",required = false) @RequestParam(name = "level",required = false) String level) {
		//动态SQL
		QueryWrapper<Xlstudent> queryWrapper = new QueryWrapper<Xlstudent>();
		queryWrapper.eq("r.type","缴费");

		if(StrUtil.isNotBlank(bdyear)){
			queryWrapper.eq("bdyear",bdyear);
			if(StrUtil.isNotBlank(school)){
				queryWrapper.eq("school",school);
				if(StrUtil.isNotBlank(level)){
					queryWrapper.eq("level",level);
					queryWrapper.select( " s.bdyear, s.school, s.level,s.name ,left(r.gnneedtm,10) as gntm,   r.cost , result");
					return ResponseBean.Success(rptService.学生缴费情况(queryWrapper ,  bdyear,  school));
				}
			}else{
				if(StrUtil.isNotBlank(level)){
					queryWrapper.eq("level",level);
				}
			}
		}else{
			if(StrUtil.isNotBlank(school)){
				queryWrapper.eq("school",school);
				if(StrUtil.isNotBlank(level)){
					queryWrapper.eq("level",level);
				}
			}else{
				if(StrUtil.isNotBlank(level)){
					queryWrapper.eq("level",level);
				}
			}
		}

		queryWrapper.select( "s.bdyear, s.school, s.level ,left(r.gnneedtm,4) as gntm,result, sum(  r.cost ) as cost ");
		queryWrapper.groupBy("s.bdyear, s.school, s.level ,left(r.gnneedtm,4),  result");

		return ResponseBean.Success(rptService.学生缴费情况(queryWrapper,  bdyear,  school));
	}

	@ApiOperation(value = "4.提成年度统计")
	@GetMapping("/lsTcs")
	public ResponseBean<Map> list4(@ApiParam(  value = "年级",required = true) @RequestParam(name = "bdyear",required = true) String bdyear ) {

		return ResponseBean.Success(rptService.提成年统计( bdyear ));
	}


	@ApiOperation(value = "5.学生的跟进统计")
	@GetMapping("/lsGJs")
	public ResponseBean<Map> list5(@ApiParam(  value = "年级",required = true) @RequestParam(name = "bdyear",required = true) String bdyear ) {

		return ResponseBean.Success(rptService.学生的跟进统计( bdyear ));
	}
}

package com.ossjk.qlh.xueli.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.entity
 * @ClassName: Xlstudent
 * @Description: 学历学生-实体类
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
@ApiModel(value = "学历学生")
@Data
public class XlstudentDTO extends BaseEntity<XlstudentDTO> {


	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;

	@ApiModelProperty(value = "openid")
	@TableId("openid")
	private String openid;

	@ApiModelProperty(value = "wxuid")
	@TableId("wxuid")
	private String wxuid;




	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	@TableField("name")
	private String name;
	/**
	 * 手机
	 */
	@ApiModelProperty(value = "手机", required = true)
	@TableField("moblie")
	private String moblie;

	/**
	 * 身份证号
	 */
	@ApiModelProperty(value = "身份证号", required = true)
	@TableField("idno")
	private String idno;
	/**
	 * 性别
	 */
	@ApiModelProperty(value = "性别")
	@TableField("sex")
	private String sex;
	/**
	 * 民族
	 */
	@ApiModelProperty(value = "民族")
	@TableField("mz")
	private String mz;


	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;


	@ApiModelProperty(value = "学生id" )
	@TableField("stuid")
	private String stuid;


	@ApiModelProperty(value = "户籍地")
	@TableField("fjaera")
	private String fjaera;


	/**
	 * 报读届
	 */
	@ApiModelProperty(value = "报读届")
	@TableField("bdyear")
	private String bdyear;

	/**
	 * 报读院校
	 */
	@ApiModelProperty(value = "报读院校")
	@TableField("school")
	private String school;
	/**
	 * 层次
	 */
	@ApiModelProperty(value = "层次")
	@TableField("level")
	private String level;
	/**
	 * 专业
	 */
	@ApiModelProperty(value = "专业")
	@TableField("subject")
	private String subject;
	/**
	 * 类别
	 */
	@ApiModelProperty(value = "类别")
	@TableField("type")
	private String type;

	/**
	 * 考试区域
	 */
	@ApiModelProperty(value = "考试区域")
	@TableField("ksarea")
	private String ksarea;
	/**
	 * 备选考区
	 */
	@ApiModelProperty(value = "备选考区")
	@TableField("ksarea2")
	private String ksarea2;
	/**
	 * 成考加分
	 */
	@ApiModelProperty(value = "成考加分")
	@TableField("plus")
	private Integer plus;
	/**
	 * 考前学历
	 */
	@ApiModelProperty(value = "考前学历")
	@TableField("kqgrd")
	private String kqgrd;
	/**
	 * 毕业学校
	 */
	@ApiModelProperty(value = "毕业学校")
	@TableField("byschool")
	private String byschool;
	/**
	 * 毕业专业
	 */
	@ApiModelProperty(value = "毕业专业")
	@TableField("bysub")
	private String bysub;
	/**
	 * 毕业时间
	 */
	@ApiModelProperty(value = "毕业时间")
	@TableField("bytm")
	private String bytm;
	/**
	 * 毕业证号
	 */
	@ApiModelProperty(value = "毕业证号", required = true)
	@TableField("byzid")
	private String byzid;

	/**
	 * 报名情况
	 */
	@ApiModelProperty(value = "报名情况")
	@TableField("bminfo")
	private String bminfo;
	/**
	 * 网报号
	 */
	@ApiModelProperty(value = "网报号", required = true)
	@TableField("wbno")
	private String wbno;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	@TableField("wbpwd")
	private String wbpwd;
	/**
	 * 报名号
	 */
	@ApiModelProperty(value = "报名号", required = true)
	@TableField("bmno")
	private String bmno;
	/**
	 * 快递地址
	 */
	@ApiModelProperty(value = "快递地址")
	@TableField("postaera")
	private String postaera;
	/**
	 * 班主任
	 */
	@ApiModelProperty(value = "班主任")
	@TableField("teacher")
	private String teacher;
	/**
	 * 费用情况
	 */
	@ApiModelProperty(value = "费用情况")
	@TableField("cost")
	private String cost;
	/**
	 * 考试情况
	 */
	@ApiModelProperty(value = "考试情况")
	@TableField("test")
	private String test;


	@Override
	protected Serializable pkVal() {
		return id;
	}
	@ApiModelProperty(value = "缴费时间")
	@TableField("pytm")
	private String pytm;

	@ApiModelProperty(value = "订单微信支付编号")
	@TableField("wxorder")
	private String wxorder;
	 
}

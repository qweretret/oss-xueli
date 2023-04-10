package com.ossjk.qlh.xueli.entity;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
public class Xlstudent extends BaseEntity<Xlstudent> {


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


	 
}

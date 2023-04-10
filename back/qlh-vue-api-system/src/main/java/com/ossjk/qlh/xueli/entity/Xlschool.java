package com.ossjk.qlh.xueli.entity;
import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.entity
 * @ClassName: Xlschool
 * @Description: 学历产品-实体类
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:50 
 */
@ApiModel(value = "学历产品")
@Data
public class Xlschool extends BaseEntity<Xlschool> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 招生学年
	 */
	@ApiModelProperty(value = "招生学年")
	@TableField("zsyear")
	private String zsyear;
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@TableField("starttm")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date starttm;
	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@TableField("endtm")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date endtm;
	/**
	 * 学校名称
	 */
	@ApiModelProperty(value = "学校名称")
	@TableField("skname")
	private String skname;
	/**
	 * 学校代码
	 */
	@ApiModelProperty(value = "学校代码")
	@TableField("code")
	private String code;
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
	@TableField("subjects")
	private String subjects;
	/**
	 * 报考条件
	 */
	@ApiModelProperty(value = "报考条件")
	@TableField("condi")
	private String condi;
	/**
	 * 报名材料
	 */
	@ApiModelProperty(value = "报名材料")
	@TableField("bmcl")
	private String bmcl;
	/**
	 * 上课地点
	 */
	@ApiModelProperty(value = "上课地点")
	@TableField("ckdd")
	private String ckdd;
	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	@TableField("state")
	private Integer state;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remarks")
	private String remarks;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}

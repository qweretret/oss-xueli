package com.ossjk.qlh.xueli.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import com.ossjk.core.system.annotation.DbColumnMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Copyright  2022-04-18 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.book.entity
 * @ClassName: Tichengstu
 * @Description: 业绩提成-实体类
 * @author: linhao
 * @date:  2022-04-18 16:13:59 
 */
@ApiModel(value = "业绩提成")
@Data
public class Tichengstu extends BaseEntity<Tichengstu> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id", required = true)
	@TableId("id")
	private String id;
	/**
	 * 提成员工
	 */
	@ApiModelProperty(value = "提成员工")
	@TableField("userid")
	private String userid;
	/**
	 * 提成学生
	 */
	@ApiModelProperty(value = "提成学生")
	@TableField("stuid")
	@DbColumnMapper(columns="name",tableName="xlstudent",condition="id")
	private String stuid;
	/**
	 * 提成类型
	 */
	@ApiModelProperty(value = "提成类型")
	@TableField("type")
	private BigDecimal type;
	/**
	 * 提成应付时间
	 */
	@ApiModelProperty(value = "提成应付时间")
	@TableField("tctm")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date tctm;
	/**
	 * 支付时间
	 */
	@ApiModelProperty(value = "支付时间")
	@TableField("paydate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date paydate;
	/**
	 * 金额
	 */
	@ApiModelProperty(value = "金额")
	@TableField("cost")
	private BigDecimal cost;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remark")
	private String remark;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}

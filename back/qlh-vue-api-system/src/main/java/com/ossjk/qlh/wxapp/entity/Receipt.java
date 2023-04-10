package com.ossjk.qlh.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Copyright  2022-05-20 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.wx.entity
 * @ClassName: Receipt
 * @Description: 收据模板-实体类
 * @author: linhao
 * @date:  2022-05-20 16:19:38 
 */
@ApiModel(value = "收据模板")
@Data
public class Receipt extends BaseEntity<Receipt> {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(value = "id", required = true)
	@TableId("id")
	private String id;
	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	@TableField("type")
	private BigDecimal type;
	/**
	 * 高度
	 */
	@ApiModelProperty(value = "高度")
	@TableField("height")
	private String height;
	/**
	 * 宽度
	 */
	@ApiModelProperty(value = "宽度")
	@TableField("width")
	private String width;
	/**
	 * ftl模板
	 */
	@ApiModelProperty(value = "ftl模板")
	@TableField("ftl")
	private String ftl;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remk")
	private String remk;
	 
	 
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}

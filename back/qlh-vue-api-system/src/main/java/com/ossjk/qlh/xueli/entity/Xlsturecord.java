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
 * @ClassName: Xlsturecord
 * @Description: 学历学生跟进记录-实体类
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
@ApiModel(value = "学历学生跟进记录")
@Data
public class Xlsturecord extends BaseEntity<Xlsturecord> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = true)
	@TableId("id")
	private String id;
	/**
	 * 跟进时间
	 */
	@ApiModelProperty(value = "跟进时间")
	@TableField("gntm")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date gntm;

	@ApiModelProperty(value = "计划跟进时间")
	@TableField("gnneedtm")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date gnneedtm;
	/**
	 * 被跟进人
	 */
	@ApiModelProperty(value = "被跟进人id")
	@TableField("stuid")
	private String stuid;
	/**
	 * 跟进人
	 */
	@ApiModelProperty(value = "跟进人")
	@TableField("username")
	private String username;
	/**
	 * 跟进类型
	 */
	@ApiModelProperty(value = "跟进类型")
	@TableField("type")
	private String type;

	/**
	 * 跟进类型
	 */
	@ApiModelProperty(value = "跟进内容")
	@TableField("content")
	private String content;

	@ApiModelProperty(value = "跟进结果：继续跟进/已完成")
	@TableField("result")
	private String result;

	@ApiModelProperty(value = "跟进记录")
	@TableField("records")
	private String records;


	@ApiModelProperty(value = "推送的消息")
	@TableField("message")
	private String message;

	@ApiModelProperty(value = "推送消息接受时间")
	@TableField("msgtm")
	private Date msgtm;

	@Override
	protected Serializable pkVal() {
				return id;
	}

}

package com.ossjk.qlh.xueli.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ossjk.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "学历学生跟进记录")
@Data
public class XlsturecordDTO extends BaseEntity<XlsturecordDTO> {

	private static final long serialVersionUID = 1L;



	@ApiModelProperty(value = "计划跟进时间")
	private String gnneedtm;
	/**
	 * 被跟进人
	 */
	@ApiModelProperty(value = "被跟进人s")
	private String[] stuids;
	/**
	 * 跟进人
	 */
	@ApiModelProperty(value = "跟进人")
	private String userid;
	/**
	 * 跟进类型
	 */
	@ApiModelProperty(value = "跟进类型")
	@TableField("type")
	private String type;

	@ApiModelProperty(value = "跟进说明")
	@TableField("result")
	private String result;

	@ApiModelProperty(value = "跟进记录")
	@TableField("records")
	private String records;

	@ApiModelProperty(value = "跟进内容")
	@TableField("content")
	private String content;

	@ApiModelProperty(value = "推送内容")
	@TableField("message")
	private String message;
	 
	@Override
	protected Serializable pkVal() {
				return id;
	}
 

	 
}

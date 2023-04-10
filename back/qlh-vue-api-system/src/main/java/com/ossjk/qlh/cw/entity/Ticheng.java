package com.ossjk.qlh.cw.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Package: com.ossjk.qlh.cw.entity
 * @ClassName: Ticheng
 * @Description: 业绩提成-实体类
 * @author: Rick.yang
 */
@ApiModel(value = "业绩提成")
@Data
@TableName("Tichengstu")
public class Ticheng extends BaseEntity<Ticheng> {

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
	@ApiModelProperty(value = "提成人")
	@TableField("userid")
	private String userid;
	/**
	 * 提成学生
	 */
	@ApiModelProperty(value = "提成学生")
	@TableField("stuid")
	@DbColumnMapper(tableName = "stu",columns={"name"},condition = "id")
	private String stuid;
	/**
	 * 提成类型
	 */
	@ApiModelProperty(value = "提成类型 1.咨询，2.技术，3.电邀，4.转介绍，5.学历I，6.学历II")
	@TableField("type")
	private Integer type;
	/**
	 * 提成应付时间
	 */
	@ApiModelProperty(value = "应付时间")
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

	@TableField("crtm")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date crtm;

	@Override
	protected Serializable pkVal() {
				return id;
	}

}

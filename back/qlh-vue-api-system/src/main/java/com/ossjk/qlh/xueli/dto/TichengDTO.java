package com.ossjk.qlh.xueli.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.core.system.annotation.DbColumnMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@ApiModel(value = "提成录入转换对象")
@Data     //Database trans Object
public class TichengDTO implements Serializable {

    @ApiModelProperty(value = "提成员工")
    private String userid;

    @ApiModelProperty(value = "提成学生s")
    private String[] stuids;

    @ApiModelProperty(value = "提成应付时间1")
    private Date tctm1;

    @ApiModelProperty(value = "提成应付时间2")
    private Date tctm2;

    @ApiModelProperty(value = "支付时间1")
    private Date paydate1;

    @ApiModelProperty(value = "支付时间2")
    private Date paydate2;

    @ApiModelProperty(value = "金额1")
    private BigDecimal cost1;

    @ApiModelProperty(value = "金额2")
    private BigDecimal cost2;

    @ApiModelProperty(value = "备注1")
    private String remark1;

    @ApiModelProperty(value = "备注2")
    private String remark2;

}

package com.ossjk.qlh.xueli.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.core.system.annotation.DbColumnMapper;
import com.ossjk.core.system.annotation.DbColumnMappers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "推送消息的DTO")
@Data
public class Xlsturecord2DTO implements Serializable {

    @ApiModelProperty(value = "消息（跟进）id")
    private String id;

    @ApiModelProperty(value = "推送日期")
    private String crtm;
    @ApiModelProperty(value = "消息类型")
    private String type;
    @ApiModelProperty(value = "已签收")
    private Integer cntqs;
    @ApiModelProperty(value = "推送总数")
    private  Integer allcnt;

    @ApiModelProperty(value = "届")
    private  String grade;
    @ApiModelProperty(value = "推送人")
    private  String school;

    @ApiModelProperty(value = "专业")
    private  String subject;

    @ApiModelProperty(value = "学生")
    private  String stunm;

    @ApiModelProperty(value = "消息")
    private  String message;

    @ApiModelProperty(value = "电话")
    private  String mobile;

    @ApiModelProperty(value = "签收时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd  HH:mm:ss")
    private Date msgtm;



    @ApiModelProperty(value = "推送人")
    @DbColumnMapper(columns="name",tableName="user",condition="id")
    private String crer;

}

package com.ossjk.qlh.wxapp.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "学历学生注册用DTO")
@Data
public class StuDto implements Serializable {

    private static final long serialVersionUID = 1L;

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

}

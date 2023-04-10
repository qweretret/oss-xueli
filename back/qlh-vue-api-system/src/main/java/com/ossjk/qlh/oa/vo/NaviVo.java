package com.ossjk.qlh.oa.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data       //   统一的导航封装
public class NaviVo {

    @ApiModelProperty(value = "上一个")
    @TableField("prev")
    private String prev;


    @ApiModelProperty(value = "当前")
    @TableField("crnt")
    private String crnt;


    @ApiModelProperty(value = "下一个")
    @TableField("next")
    private String next;


}

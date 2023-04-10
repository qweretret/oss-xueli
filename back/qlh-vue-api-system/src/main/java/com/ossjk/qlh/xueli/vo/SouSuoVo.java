package com.ossjk.qlh.xueli.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.qlh.xueli.entity.Xlsturecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(value = "搜索")
@Data
          // Value Object
public class SouSuoVo extends Xlsturecord {


    @ApiModelProperty(value = "被跟进人")
    @TableField("name")
    private String name;


}

package com.ossjk.qlh.xueli.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "学历产品的Vo")
@Data
public class XlSchoolVo implements Serializable {

    @ApiModelProperty(value = "层次")
    @TableField("level")
    private String level;

    @ApiModelProperty(value = "专业")
    @TableField("subjects")
    private List<String> subjects;

}

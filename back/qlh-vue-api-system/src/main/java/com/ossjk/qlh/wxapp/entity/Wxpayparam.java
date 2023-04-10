package com.ossjk.qlh.wxapp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ossjk.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Wxpayparam
  extends BaseEntity<Wxpayparam>
{
  private static final long serialVersionUID = 1L;

  @ApiModelProperty("")
  @TableField("id")
  private String id;
  @ApiModelProperty("订单编号")
  @TableField("ocode")
  private String ocode;
  @ApiModelProperty("随机字符串")
  @TableField("noncestr")
  private String noncestr;
  @ApiModelProperty("签名")
  @TableField("sign")

  private String sign;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getOcode()
  {
    return this.ocode;
  }
  
  public void setOcode(String ocode)
  {
    this.ocode = ocode;
  }
  
  public String getNoncestr()
  {
    return this.noncestr;
  }
  
  public void setNoncestr(String noncestr)
  {
    this.noncestr = noncestr;
  }
  
  public String getSign()
  {
    return this.sign;
  }
  
  public void setSign(String sign)
  {
    this.sign = sign;
  }
  
  protected Serializable pkVal()
  {
    return null;
  }
  
  public String toString()
  {
    return "Wxpayparam [id=" + this.id + ",ocode=" + this.ocode + ",noncestr=" + this.noncestr + ",sign=" + this.sign + "]";
  }
}

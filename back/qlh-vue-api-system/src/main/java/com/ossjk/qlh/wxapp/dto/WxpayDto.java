package com.ossjk.qlh.wxapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel("微信支付Dto")
public class WxpayDto implements Serializable {

  @ApiModelProperty("报考学校ID")
  private String school;

  @ApiModelProperty("报考学级")
  private String level;

  @ApiModelProperty("专业")
  private String subject;

  @ApiModelProperty("学生姓名")
  private String name;

  @ApiModelProperty("民族")
  private String mz;

  @ApiModelProperty("性别")
  private String sex;

  @ApiModelProperty("学生身份证号ID")
  private String idno;

  @ApiModelProperty("学生手机号")
  private String mobile;

  @ApiModelProperty("支付金额")
  private String cost;

  @ApiModelProperty("学生openid")
  private String openid;

  @ApiModelProperty("学生unionid")
  private String unionid;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdno() {
    return idno;
  }

  public void setIdno(String idno) {
    this.idno = idno;
  }

  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public String getUnionid() {
    return unionid;
  }

  public void setUnionid(String unionid) {
    this.unionid = unionid;
  }

  public String getMz() {
    return mz;
  }

  public void setMz(String mz) {
    this.mz = mz;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }
}

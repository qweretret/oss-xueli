package com.ossjk.core.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class XlstudentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String ordercode;

    @ApiModelProperty("openid")
    private String openid;

    @ApiModelProperty("wxuid")
    @TableField("wxuid")
    private String wxuid;

    @TableField("pytm")
    private String pytm;


    @TableField("wxorder")
    private String wxorder;


    @TableField("bdyear")
    private String bdyear;


    @TableField("name")
    private String name;


    @TableField("moblie")
    private String moblie;


    @TableField("school")
    private String school;


    @TableField("level")
    private String level;


    @TableField("subject")
    private String subject;


    @TableField("type")
    private String type;


    @TableField("fjaera")
    private String fjaera;


    @TableField("ksarea")
    private String ksarea;


    @TableField("ksarea2")
    private String ksarea2;


    @TableField("plus")
    private Integer plus;


    @TableField("kqgrd")
    private String kqgrd;


    @TableField("byschool")
    private String byschool;


    @TableField("bysub")
    private String bysub;


    @TableField("bytm")
    private String bytm;


    @TableField("byzid")
    private String byzid;


    @TableField("idno")
    private String idno;


    @TableField("sex")
    private String sex;


    @TableField("mz")
    private String mz;


    @TableField("bminfo")
    private String bminfo;


    @TableField("wbno")
    private String wbno;


    @TableField("wbpwd")
    private String wbpwd;


    @TableField("bmno")
    private String bmno;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(String ordercode) {
        this.ordercode = ordercode;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getWxuid() {
        return wxuid;
    }

    public void setWxuid(String wxuid) {
        this.wxuid = wxuid;
    }

    public String getPytm() {
        return pytm;
    }

    public void setPytm(String pytm) {
        this.pytm = pytm;
    }

    public String getWxorder() {
        return wxorder;
    }

    public void setWxorder(String wxorder) {
        this.wxorder = wxorder;
    }

    public String getBdyear() {
        return bdyear;
    }

    public void setBdyear(String bdyear) {
        this.bdyear = bdyear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFjaera() {
        return fjaera;
    }

    public void setFjaera(String fjaera) {
        this.fjaera = fjaera;
    }

    public String getKsarea() {
        return ksarea;
    }

    public void setKsarea(String ksarea) {
        this.ksarea = ksarea;
    }

    public String getKsarea2() {
        return ksarea2;
    }

    public void setKsarea2(String ksarea2) {
        this.ksarea2 = ksarea2;
    }

    public Integer getPlus() {
        return plus;
    }

    public void setPlus(Integer plus) {
        this.plus = plus;
    }

    public String getKqgrd() {
        return kqgrd;
    }

    public void setKqgrd(String kqgrd) {
        this.kqgrd = kqgrd;
    }

    public String getByschool() {
        return byschool;
    }

    public void setByschool(String byschool) {
        this.byschool = byschool;
    }

    public String getBysub() {
        return bysub;
    }

    public void setBysub(String bysub) {
        this.bysub = bysub;
    }

    public String getBytm() {
        return bytm;
    }

    public void setBytm(String bytm) {
        this.bytm = bytm;
    }

    public String getByzid() {
        return byzid;
    }

    public void setByzid(String byzid) {
        this.byzid = byzid;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getBminfo() {
        return bminfo;
    }

    public void setBminfo(String bminfo) {
        this.bminfo = bminfo;
    }

    public String getWbno() {
        return wbno;
    }

    public void setWbno(String wbno) {
        this.wbno = wbno;
    }

    public String getWbpwd() {
        return wbpwd;
    }

    public void setWbpwd(String wbpwd) {
        this.wbpwd = wbpwd;
    }

    public String getBmno() {
        return bmno;
    }

    public void setBmno(String bmno) {
        this.bmno = bmno;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getPostaera() {
        return postaera;
    }

    public void setPostaera(String postaera) {
        this.postaera = postaera;
    }

    @TableField("postaera")
    private String postaera;


    @TableField("teacher")
    private String teacher;


    @TableField("cost")
    private String cost;


    @TableField("test")
    private String test;

}

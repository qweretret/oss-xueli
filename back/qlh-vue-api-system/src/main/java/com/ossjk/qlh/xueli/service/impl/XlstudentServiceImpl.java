package com.ossjk.qlh.xueli.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.system.service.IUserService;
import com.ossjk.qlh.xueli.dto.XlstudentDTO;
import com.ossjk.qlh.xueli.entity.Signup;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.mapper.XlstudentMapper;
import com.ossjk.qlh.xueli.service.ISignupService;
import com.ossjk.qlh.xueli.service.IXlstudentService;
import com.ossjk.qlh.xueli.service.IXlsturecordService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.service.impl
 * @ClassName: XlstudentServiceImpl
 * @Description: 学历学生-服务实现类
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:51 
 */
@Service
public class XlstudentServiceImpl extends ServiceImpl<XlstudentMapper, Xlstudent> implements IXlstudentService {

    @Autowired
    private IXlsturecordService iXlsturecordService;
    @Autowired
    private ISignupService  isignupService;



    @Override
    public Page<XlstudentDTO> page(int type, Page<XlstudentDTO> page, QueryWrapper<XlstudentDTO> queryWrapper, String mgr){

        if(type == 1 ){  //admin
            return this.baseMapper.pageDto(page,queryWrapper);
        }else if(type == 2 ){ //mgr
//            return this.baseMapper.findByDptmgr(page,queryWrapper,mgr);
        }else if(type == 3 ){ //班主任、员工
          //  return this.baseMapper.findByUserName(page,queryWrapper);
        }
        //查询需要跟进的学生
        //return this.baseMapper.selectPageXlstudent(page,queryWrapper);
        return null;
    }

    public  String[] findAllYears(){
        return this.isignupService.findAllYears();
    }

     public Signup selectOne(QueryWrapper<Signup> queryWrapper ){
         return   isignupService.getOne( queryWrapper);
     }

    //导入数据
    @Transactional
    public  String  impDatas(Signup[] stus){
        // 重复数据 ：提示重复的数据
        String msg = "导入成功。";
        QueryWrapper<Signup> queryWrapper = new QueryWrapper<Signup>();
        //是否存在重复
        int isRepeat = 0;
        for (Signup s:stus   ) {
            queryWrapper.clear();
            queryWrapper.eq("moblie",s.getMoblie());
            Signup org = this.selectOne(queryWrapper);
            if(org!=null){
                isRepeat++;
                msg+=(org.getName()+",");
            }else{
                isignupService.save(s);
            }
        }
        if(isRepeat>0){
            msg = "本次导入存在重复数据【"+isRepeat+"】条:"+msg;
        }

        return msg;
       // return  this.saveBatch(   Arrays.asList(stus))?"导入成功":"导入失败"  ;
    }

    public  List<Map> stuNameInduction( String sname){
        return this.baseMapper.stuNameInduction(sname+"%");
    }

    //数据解析
    public List<Signup> parseExcel(File file){

        List<Signup> stus = new ArrayList<>();

        try {
            // Poi对Excel的抽象
            Workbook excel = WorkbookFactory.create(file);
            //取得第一个Sheet
            Sheet sheet= excel.getSheetAt(0);
            //读取的当前单元格
            Cell tmpCell = null;
            Row  tmpRow = null;
            Signup tmpStu = null;
            //取得总行数
            int maxRow = sheet.getLastRowNum()+1;
            System.out.println("maxRow ="+maxRow);
            //如果有数据
            if(maxRow>1){
                //取得行
                for (int i = 1; i <  maxRow ; i++) {
                    tmpRow = sheet.getRow(i);
                    tmpStu = new Signup();

                    //封装数据
                    tmpStu.setName(StringUtil.cellGetString(tmpRow,1) );
                    tmpStu.setMoblie(StringUtil.cellGetString(tmpRow,2));

                    tmpStu.setSchool(StringUtil.cellGetString(tmpRow,3));
                    tmpStu.setLevel(StringUtil.cellGetString(tmpRow,5));
                    tmpStu.setSubject(StringUtil.cellGetString(tmpRow,6));
                    tmpStu.setType(StringUtil.cellGetString(tmpRow,7));

                    tmpStu.setFjaera(StringUtil.cellGetString(tmpRow,9));
                    tmpStu.setKsarea(StringUtil.cellGetString(tmpRow,10));
                    tmpStu.setKsarea2(StringUtil.cellGetString(tmpRow,11));

                   String plus =  StringUtil.cellGetString(tmpRow,12);
                    System.out.println("plus="+plus);
                   if(plus!=null && plus.length()>0){
                       tmpStu.setPlus(Integer.parseInt(plus));
                   }
                    tmpStu.setKqgrd(StringUtil.cellGetString(tmpRow,13));
                    tmpStu.setByschool(StringUtil.cellGetString(tmpRow,14));
                    tmpStu.setBysub(StringUtil.cellGetString(tmpRow,15));
                    tmpStu.setBytm(StringUtil.cellGetString(tmpRow,16));
                    tmpStu.setByzid(StringUtil.cellGetString(tmpRow,17));
                    tmpStu.setIdno(StringUtil.cellGetString(tmpRow,18));

                    tmpStu.setSex(StringUtil.cellGetString(tmpRow,19));
                    tmpStu.setMz(StringUtil.cellGetString(tmpRow,20));
                    tmpStu.setBminfo(StringUtil.cellGetString(tmpRow,21));
                    tmpStu.setWbno(StringUtil.cellGetString(tmpRow,23));
                    tmpStu.setWbpwd(StringUtil.cellGetString(tmpRow,24));
                    tmpStu.setBmno(StringUtil.cellGetString(tmpRow,25));
                    tmpStu.setPostaera(StringUtil.cellGetString(tmpRow,26));

                    stus.add(tmpStu);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return stus;
    }


    public static void main(String[] args) {
        XlstudentServiceImpl slx = new XlstudentServiceImpl();
        System.out.println("Hello");

        List<Signup> stus = slx.parseExcel(  new File("D:\\imgs\\ca34b7e3-6c58-4619-a800-2d1954ff54a9-录入信息.xlsx"));

        for (Signup s:stus      ) {
          //  System.out.println(s.getName()+" , "+s.getPostaera());
        }

        System.out.println("the End.");

    }


    @Override
    public boolean selectId(String openid, String unionid, String name, String moblie) {
        return baseMapper.selectId(openid,unionid,name,moblie);
    }

    @Override
    public Xlstudent getOne(QueryWrapper<Xlstudent> queryWrapper) {
        return null;
    }
}

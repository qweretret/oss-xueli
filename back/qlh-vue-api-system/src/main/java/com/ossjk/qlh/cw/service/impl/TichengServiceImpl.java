package com.ossjk.qlh.cw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.cw.entity.Ticheng;
import com.ossjk.qlh.cw.mapper.TichengMapper;
import com.ossjk.qlh.cw.service.ITichengService;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

/**
 * 
 * @Package: com.ossjk.qlh.cw.service.impl
 * @ClassName: TichengServiceImpl
 * @Description: 业绩提成-服务实现类
 * @author: Rick.yang
 */
@Service
public class TichengServiceImpl extends ServiceImpl<TichengMapper, Ticheng> implements ITichengService {

    public boolean deleteByStuid( String stuid){
        return baseMapper.deleteByStuid(stuid)==1;
    }

    public boolean fafang(String[] ids,String paytm   ){
        Ticheng stu = null ;
        Date now = null;
        try {
            now = StringUtil.smt2.parse(paytm);
        } catch (ParseException e) {
            now = new Date();
        }
        for (String id: ids ) {
            stu =  this.getById(id);
            stu.setPaydate(now);
            this.updateById(stu);
        }
        return true;
    }
}

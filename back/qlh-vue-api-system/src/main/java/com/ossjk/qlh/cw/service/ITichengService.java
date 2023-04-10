package com.ossjk.qlh.cw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.cw.entity.Ticheng;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @Package: com.ossjk.qlh.cw.service
 * @ClassName: TichengService
 * @Description:  业绩提成-服务类接口
 * @author: Rick.yang
 */
public interface ITichengService extends IService<Ticheng> {

    boolean deleteByStuid(String stuid);
    boolean fafang(String[] ids, String paytm);
}

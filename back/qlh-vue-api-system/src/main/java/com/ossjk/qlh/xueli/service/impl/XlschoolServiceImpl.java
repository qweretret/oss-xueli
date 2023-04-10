package com.ossjk.qlh.xueli.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.myUtil.StringUtil;
import com.ossjk.qlh.xueli.entity.Xlschool;
import com.ossjk.qlh.xueli.mapper.XlschoolMapper;
import com.ossjk.qlh.xueli.service.IXlschoolService;
import com.ossjk.qlh.xueli.vo.XlSchoolVo;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Copyright  2022-02-22 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.xueli.service.impl
 * @ClassName: XlschoolServiceImpl
 * @Description: 学历产品-服务实现类
 * @author: Rick.yang
 * @date:  2022-02-22 15:41:50 
 */
@Service
public class XlschoolServiceImpl extends ServiceImpl<XlschoolMapper, Xlschool> implements IXlschoolService {

    @Override
    public Map listProducts() {
        //   level      专业
        Map<String, Set<String>> datas = new HashMap<>();
        String zsyear = StringUtil.smt5.format(new Date());
        //全部级别
        String[] levels = baseMapper.allLevelsByYear(zsyear);

        if (levels != null) {
            for (String level : levels) {
                //今年的所有学校
                QueryWrapper<Xlschool> queryWrapper = new QueryWrapper<Xlschool>();
                queryWrapper.eq("zsyear", zsyear);
                queryWrapper.eq("level", level);

                List<Xlschool> prods = this.baseMapper.selectList(queryWrapper);
                datas.put(level, new HashSet());

                if (prods != null) {
                    for (Xlschool s : prods) {
                        String jsonStr = s.getSubjects();
                        if (jsonStr != null) {
                            JSONArray arrs = JSONObject.parseArray(jsonStr);
                            for (int i = 0; i < arrs.size(); i++) {
                                JSONObject sbjt = (JSONObject) arrs.get(i);
                                datas.get(level).add(sbjt.getString("nm"));
                            }
                        }
                    }
                }
            }
        }
        return datas;
    }

    @Override
    public List<Xlschool> pageSchool(String level, String sbname) {
        String zsyear = StringUtil.smt5.format(new Date());
        return baseMapper.allLevelsByYear2(zsyear,level,sbname);
    }
}

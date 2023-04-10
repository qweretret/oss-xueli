package com.ossjk.qlh.cw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ossjk.qlh.cw.entity.Ticheng;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @Package: com.ossjk.qlh.cw.mapper
 * @ClassName: TichengMapper
 * @Description: 业绩提成-Mapper
 * @author: Rick.yang
 */
public interface TichengMapper extends BaseMapper<Ticheng> {

    @Delete("delete from  ticheng  where stuid = #{stuid} ")
    int deleteByStuid(@Param("stuid") String stuid);
}

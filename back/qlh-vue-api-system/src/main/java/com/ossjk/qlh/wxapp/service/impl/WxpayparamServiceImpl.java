package com.ossjk.qlh.wxapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ossjk.qlh.wxapp.entity.Wxpayparam;
import com.ossjk.qlh.wxapp.mapper.WxpayparamMapper;
import com.ossjk.qlh.wxapp.service.IWxpayparamService;
import org.springframework.stereotype.Service;

@Service
public class WxpayparamServiceImpl
  extends ServiceImpl<WxpayparamMapper, Wxpayparam>
  implements IWxpayparamService
{
  public void saveAndReomve(Wxpayparam wxpayparam)
  {
    ((WxpayparamMapper)this.baseMapper).delete((Wrapper)new QueryWrapper().eq("ocode", wxpayparam.getOcode()));
    
    ((WxpayparamMapper)this.baseMapper).insert(wxpayparam);
  }
}

package com.ossjk.qlh.wxapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ossjk.qlh.wxapp.entity.Wxpayparam;

public abstract interface IWxpayparamService
  extends IService<Wxpayparam>
{
  public abstract void saveAndReomve(Wxpayparam paramWxpayparam);
}

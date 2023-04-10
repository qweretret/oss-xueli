package com.ossjk.qlh.wxapp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.config.jwttoken.JwtTokenUtil;
import com.ossjk.config.redis.RedisUtil;
import com.ossjk.config.sms.SmsServer;
import com.ossjk.core.constant.CacheConstant;
import com.ossjk.core.system.api.IStudentCommonApi;
import com.ossjk.core.system.dto.WechatBindDto;
import com.ossjk.core.system.dto.XlstudentDto;
import com.ossjk.qlh.sms.service.ISmsService;
import com.ossjk.qlh.system.service.IDictionaryService;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.service.IXlstudentService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class StudentCommonApiImpl implements IStudentCommonApi {

	private static final Logger log = LoggerFactory.getLogger(StudentCommonApiImpl.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private IDictionaryService iDictionaryService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ISmsService iSmsService;

	@Autowired
	private SmsServer smsServer;

	@Autowired
	private MailAccount mailAccount;

	@Autowired
	private IXlstudentService studentService;

	public String generateToken(String uid) {
		return this.jwtTokenUtil.generateToken(uid);
	}

	public XlstudentDto getXlstudent(String token) {
		String sid = this.jwtTokenUtil.getUserIdFromToken(token);
		Xlstudent student = (Xlstudent)this.studentService.getById(sid);
		XlstudentDto dto = new XlstudentDto();
		BeanUtil.copyProperties(student, dto, new String[0]);
		return dto;
	}

	public XlstudentDto getCacheXlstudent(String token) {

		String cacheKey = getCacheTokenKey(token);

		XlstudentDto dto = (XlstudentDto) this.redisUtil.get(cacheKey);
		if (ObjectUtil.isEmpty(dto))
			refreshTokenCacheXlstudent(token);
		return dto;
	}

	public XlstudentDto getCacheXlstudentAndRemoveOther(String token) {
		removeCacheXlstudentByUid(this.jwtTokenUtil.getUserIdFromToken(token));
		return getCacheXlstudent(token);
	}

	public XlstudentDto refreshTokenCacheXlstudent(String token) {

		//System.out.println("token = "+token);
		String cacheKey = getCacheTokenKey(token);
	//	System.out.println("cacheKey = "+cacheKey);

		if (!this.jwtTokenUtil.isTokenExpired(token)) {
			XlstudentDto loginUser = getXlstudent(token);
			this.redisUtil.set(cacheKey, loginUser, this.jwtTokenUtil.getExpiredMilliseconFromToken(token), TimeUnit.MILLISECONDS);
			return loginUser;
		}
		log.error("token:" + token + ",失效了");
	    removeCacheXlstudentByToken(token);
		return null;
	}

	public void removeCacheXlstudentByToken(String token) {
		String cacheKey = getCacheTokenKey(token);
		this.redisUtil.del(new String[] { cacheKey });
	}

	public void removeCacheXlstudentByUid(String uid) {
		String cachePrefix = getCacheTokenPrefix(uid);
		this.redisUtil.vagueDel(new String[] { cachePrefix + "*" });
	}

	public String getCacheTokenKey(String token) {
		return CacheConstant.generateCacheJwtToken(this.jwtTokenUtil.getUserIdFromToken(token), token);
	}

	public String getCacheTokenPrefix(String uid) {
		return CacheConstant.generateCacheJwtTokenPrefix(uid);
	}

	public String refreshTokenAndCacheLoginUser(String token) {
		String newToken = refreshToken(token);
		getCacheXlstudent(token);
		return newToken;
	}

	public String refreshToken(String token) {
		String uid = this.jwtTokenUtil.getUserIdFromToken(token);
		removeCacheXlstudentByToken(token);
		return generateToken(uid);
	}

	public String getUidByToken(String token) {
		return this.jwtTokenUtil.getUserIdFromToken(token);
	}

	public String login(WechatBindDto wechatBindDto) {
		String openid = wechatBindDto.getOpenid();
		QueryWrapper<Xlstudent> queryWrapper = new QueryWrapper();
		queryWrapper.eq("openid", openid);
		Xlstudent stu = (Xlstudent)this.studentService.getOne((QueryWrapper)queryWrapper);
		if (ObjectUtil.isNotEmpty(stu)) {
			String token = generateToken(stu.getId());
			System.out.println(stu.getId());
			getCacheXlstudent(token);
			return token;
		}
		return "账号不存在";
	}

	@Transactional
	public String wxBind(WechatBindDto wechatBindDto) {

		String mobile = wechatBindDto.getMobile();
		String valicode = wechatBindDto.getValicode();

		QueryWrapper<Xlstudent> queryWrapper = new QueryWrapper();
		if (StrUtil.isNotBlank(mobile) && StrUtil.isNotBlank(valicode) && valicode.length() == 6) {
			((QueryWrapper)queryWrapper.eq("moblie", mobile)).like("idno", "%" + valicode);
			Xlstudent stu = (Xlstudent)this.studentService.getOne((QueryWrapper)queryWrapper);
			if (ObjectUtil.isNotEmpty(stu)) {
				stu.setWxuid(wechatBindDto.getUnionid());
				stu.setOpenid(wechatBindDto.getOpenid());
				this.studentService.saveOrUpdate(stu);
				String str = generateToken(stu.getId());
				getCacheXlstudent(str);
				return str;
			}
			stu = new Xlstudent();
			stu.setIdno("************" + valicode);
			stu.setMoblie(mobile);
			stu.setWxuid(wechatBindDto.getUnionid());
			stu.setOpenid(wechatBindDto.getOpenid());
			this.studentService.saveOrUpdate(stu);
			System.out.println(stu.getId());
			String token = generateToken(stu.getId());
			getCacheXlstudent(token);
			return token;
		}
		return "fail";
	}
}
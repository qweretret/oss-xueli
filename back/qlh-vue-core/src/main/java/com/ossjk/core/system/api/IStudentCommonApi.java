package com.ossjk.core.system.api;

import cn.hutool.core.util.StrUtil;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.dto.WechatBindDto;
import com.ossjk.core.system.dto.XlstudentDto;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Copyright © 2021 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.core.system.api
 * @ClassName: ISystemCommonApi
 * @Description: 小程序系统公共业务接口
 * @author: chair
 * @date: 2021年2月24日 下午4:44:16
 */
public interface IStudentCommonApi {

	String generateToken(String uid);
	XlstudentDto getXlstudent(String token) ;
	XlstudentDto getCacheXlstudent(String token);
	XlstudentDto getCacheXlstudentAndRemoveOther(String token) ;
	XlstudentDto refreshTokenCacheXlstudent(String token) ;
	void removeCacheXlstudentByToken(String token);
	void removeCacheXlstudentByUid(String uid);
	String getCacheTokenKey(String token) ;
	String getCacheTokenPrefix(String uid);
	String refreshTokenAndCacheLoginUser(String token) ;
	String refreshToken(String token);
	String getUidByToken(String token);
    String login(WechatBindDto wechatBindDto);
	String wxBind(WechatBindDto wechatBindDto);
	/**
	 * 获取token
	 * 
	 * @param request
	 * @return
	 */
	static String getRequestToekn(HttpServletRequest request) {
		String token = request.getHeader(Constant.REQUEST_HEADER_TOKEN);
		if (StrUtil.isEmpty(token)) {
			token = request.getParameter(Constant.REQUEST_HEADER_TOKEN);
		}
		return token;
	}


}

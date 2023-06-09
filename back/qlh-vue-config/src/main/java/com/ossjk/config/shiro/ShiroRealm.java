package com.ossjk.config.shiro;
//package com.example.shirojwt.shiro;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.ossjk.core.system.api.IStudentCommonApi;
import com.ossjk.core.system.dto.XlstudentDto;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ossjk.config.jwttoken.JwtTokenUtil;
import com.ossjk.core.constant.Constant;
import com.ossjk.core.system.api.ISystemCommonApi;
import com.ossjk.core.system.dto.LoginUser;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 
 * Copyright © 2020 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.config.shiro
 * @ClassName: ShiroRealm
 * @Description: shiro 认证和授权
 * @author: chair
 * @date: 2020年12月8日 下午9:01:46
 */
@Component
public class ShiroRealm extends AuthorizingRealm {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private ISystemCommonApi iSystemCommonApi;
	@Autowired
	private IStudentCommonApi iStudentCommonApi;

	/**
	 * 必须重写此方法，不然Shiro会报错
	 * 
	 * @param token
	 * @return
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}

	/**
	 * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
	 * 
	 * @param auth
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


	 	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			JwtToken jwtToken = (JwtToken)token;
			String tokenStr = (String)jwtToken.getCredentials();
			if (StrUtil.isBlank(tokenStr))
				throw new NoTokenException("requst:" + request.getRequestURI() + ",没有token。");
			try {
				LoginUser loginUser = this.iSystemCommonApi.getCacheLoginUser(tokenStr);
				if (ObjectUtil.isEmpty(loginUser))
					throw new AuthenticationException("requst:" + request.getRequestURI() + ",token没有找到。");
				if (this.jwtTokenUtil.canRefresh(tokenStr))
					throw new TokenFlushException("requst:" + request.getRequestURI() + ",token刷新。");
							request.setAttribute("uid", loginUser.getId());
				request.setAttribute("loginUser", loginUser);
				request.setAttribute("departmentCodes", loginUser.getDepartmentCode());
				request.setAttribute("departmentCode", loginUser.getDepartmentCodes());
				request.setAttribute("roleCodes", loginUser.getRoleCodes());
				request.setAttribute("roleCode", loginUser.getRoleCode());
				request.setAttribute("permissionCode", loginUser.getPermissionCode());
			} catch (ClassCastException ex) {
				XlstudentDto loginUser = this.iStudentCommonApi.getCacheXlstudent(tokenStr);
				if (ObjectUtil.isEmpty(loginUser)){
					throw new AuthenticationException("requst:" + request.getRequestURI() + ",token没有找到。");
				}

				if (this.jwtTokenUtil.canRefresh(tokenStr)){
					throw new TokenFlushException("requst:" + request.getRequestURI() + ",token刷新。");
				}

				request.setAttribute("uid", loginUser.getId());
				request.setAttribute("loginUser", loginUser);
			}
			return (AuthenticationInfo)new SimpleAuthenticationInfo(tokenStr, tokenStr, getName());
		}





	/**
	 * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions((Collection<String>) request.getAttribute(Constant.REQUEST_ATTRIBUTE_PERMISSION_CODE));
		authorizationInfo.addRoles((Collection<String>) request.getAttribute(Constant.REQUEST_ATTRIBUTE_ROLE_CODE));
		return authorizationInfo;
	}

}

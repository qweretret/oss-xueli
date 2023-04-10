package com.ossjk.qlh.wxapp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ossjk.config.mvc.ResourceMappersProperties;
import com.ossjk.core.base.controller.BaseController;
import com.ossjk.core.vo.ResponseBean;
import com.ossjk.qlh.util.PdfUtil;
import com.ossjk.qlh.wxapp.entity.Receipt;
import com.ossjk.qlh.wxapp.service.IReceiptService;
import com.ossjk.qlh.xueli.entity.Signup;
import com.ossjk.qlh.xueli.entity.Xlstudent;
import com.ossjk.qlh.xueli.service.ISignupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright  2022-05-20 QLH. Tech Ltd. All rights reserved.
 * 
 * @Package: com.ossjk.qlh.wx.controller
 * @ClassName: ReceiptController
 * @Description: Receipt-控制器
 * @author: linhao
 * @date:  2022-05-20 16:19:38 
 */
@Api(tags = "收据模板")
@RestController
@RequestMapping("/wx/receipt")
public class ReceiptController extends BaseController {
	
	@Autowired
	private IReceiptService iReceiptService;

	private ISignupService iXlsignService;
	@Autowired
	private ResourceMappersProperties ymlUri;

	@ApiOperation(value = "列表")
	@RequiresPermissions("")
	@GetMapping("/list")
	public ResponseBean<Page<Receipt>> list(Page page) {
		QueryWrapper<Receipt> queryWrapper = new QueryWrapper<Receipt>();
		return ResponseBean.Success(iReceiptService.page(page,queryWrapper));
	}


	@ApiOperation(value = "生成收据")
	@PostMapping(value = "/testGenZkz")
	public ResponseBean testGenZkz(@RequestBody Receipt receipt, HttpServletResponse response) throws IOException {

		Receipt byId = iReceiptService.getById(receipt.getId());


		// 根据获取用户信息
		Signup xlstudent = iXlsignService.getById(receipt.getId());


		List<ResourceMappersProperties.ResourceMapper> resourceMapperList = ymlUri.getMappers();

		Map<String, String> uriMap = resourceMapperList.stream().collect(Collectors.toMap(ResourceMappersProperties.ResourceMapper::getUri, ResourceMappersProperties.ResourceMapper::getFile));

		String rootPath = uriMap.get("statics")+"/tst";

		try {
		String fileName =  PdfUtil.createPDF(xlstudent, byId.getFtl(),rootPath,"testZkz.pdf");

		response.setHeader("content-Type","application/vnd.ms-pdf");

		response.setHeader("Content-Disposition", "attachment;filename=testZkz.pdf" );
		response.setCharacterEncoding("UTF-8");
		OutputStream out= response.getOutputStream();
		InputStream in=new FileInputStream(new File( fileName ));
		byte[] bytes=new byte[in.available()];
		in.read(bytes);
		in.close();

		out.write(  bytes  );
		out.flush();
		out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseBean.Success();
	}


}

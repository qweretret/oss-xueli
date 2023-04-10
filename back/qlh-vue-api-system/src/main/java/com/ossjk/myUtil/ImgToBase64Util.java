package com.ossjk.myUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ImgToBase64Util {
	/**
	 * 本地图片转换成base64字符串
	 * 
	 * @param imageName
	 *            图片名称
	 * @reture 图片Base64
	 * @author Byr
	 * @dateTime 2019-03-07
	 */
	public static String ImageToBase64ByLocal2(String imgName) throws IOException {
		InputStream inputStream = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			// 获取图片路径
			//ClassPathResource classPathResource = new ClassPathResource(  imgName);
	        if(imgName.substring(0,1).equals("/") ){
	        	// linux相对路径
				imgName = imgName.substring(1);
			}
			inputStream = new FileInputStream(imgName);

			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		Encoder encoder = Base64.getEncoder();
		String result = encoder.encodeToString(data);
		// 返回Base64编码过的字节数组字符串
		return "data:image/jpg;base64,"+result;
	}

	public static String  base64TexttoString( String  code ) {
		byte[] valueDecoded = org.apache.commons.codec.binary.Base64.decodeBase64(code);
		return new String(valueDecoded);
	}
}

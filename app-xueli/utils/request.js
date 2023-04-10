import Request from '@/plugins/luch-request/luch-request/index.js'
import {
	BASE_URL
} from "@/utils/common.js"


const http = new Request({
	baseURL: BASE_URL,
	timeout: 100000,
	header: {
		'ContentType': 'application/json'
	},
});

// 请求拦截
http.interceptors.request.use((config) => { // 可使用async await 做异步操作
	let jwttoken = uni.getStorageSync('jwttoken');

	if (jwttoken) {
		config.header.jwttoken = jwttoken
	}
	return config
}, config => { // 可使用async await 做异步操作
	return Promise.reject(config)
})

// 响应拦截
http.interceptors.response.use((response) => {
	/* 对响应成功做点什么 可使用async await 做异步操作*/
	let code = response.data.code //状态码

	switch (code) {
		case 1005: { //被登录状态
			uni.redirectTo({ //重定向回注册页
				url: '/pages/login/login'
			});
			break;
		}

		case 1006: { //刷新token
			uni.setStorageSync('jwttoken', response.data.data.jwttoken)

			// 重置配置
			let config = response.config
			config.header.jwttoken = response.data.data.jwttoken

			return http.request(config) //重新发起请求
		}


		default: { //正常
			// console.log(1000)
		}

	}

	return response
}, (response) => {
	/*  对响应错误做点什么 （statusCode !== 200）*/
	if (response.errMsg === "request:fail timeout") {
		console.log("请求超时")
		return Promise.reject(response)
	}
	console.log("网络异常，请重新刷新页面")
	return Promise.reject(response)
})

export default http

import http from "@/utils/request.js"

// get请求
export const $ajaxGet = (url,params) => {
	return http.request({
		method: 'GET',
		url,
		params
	})
}

// post请求
export const $ajaxPost = (url,data) => {
	return http.request({
		method: 'POST',
		url,
		data
	})
}
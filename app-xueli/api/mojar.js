import {
	$ajaxPost,
	$ajaxGet
} from "@/utils/methods.js"

// 今年可报名专业
export const getMajar = () => {
	const url = "/wx/stu/listProducts"
	return $ajaxGet(url)
}
// 推送给自己的消息
export const getMsgs = (data)=>{
	const url = "/wx/xlsign/listMsgs"
	return $ajaxGet(url,data)
}
// 签收推送的信息
export const chkinMyMsg = (data)=>{
	const url = "/wx/xlsign/chkinMsgs"
	return $ajaxGet(url,data)
}
// 自己报名表
export const listRcds = (data)=>{
	const url = "/wx/xlsign/listRcds"
	return $ajaxGet(url,data)
}
import {
	$ajaxPost,
	$ajaxGet
} from "@/utils/methods.js"

export const postPay = (data) => {
	const url = "/wx/stu/wxPay"
	return $ajaxPost(url,data)
}
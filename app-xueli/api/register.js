import {
	$ajaxPost,
	$ajaxGet
} from "@/utils/methods.js"

export const wxBind = (data) => {
	const url = "/wx/stu/wxBind"
	return $ajaxPost(url, data)
}

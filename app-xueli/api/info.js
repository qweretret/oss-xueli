import {
	$ajaxPost,
	$ajaxGet
} from "@/utils/methods.js"

export const postInfo = (data) => {
	const url = "/wx/xlsign/save"
	return $ajaxPost(url,data)
}
/**
  * @title: 考试接口
  * @description: 
  * @author JUN
  */

import { $ajaxPost ,$ajaxGet} from "@/utils/methods.js"

// 获取微信openid
export const getSessionInfo = (data) => {
    const url = "/wx/stu/getSessionInfo"
    return $ajaxGet(url, data)
}

// 微信小程序登录
export const wxLogin = (data) => {
    const url = "/wx/stu/wxLogin"
    return $ajaxPost(url, data)
}

// 获取微信用户信息
export const getWxUserInfo = (data) => {
    const url = "/wx/stu/getWxUserInfo"
    return $ajaxGet(url, data)
}

//获取用户信息
export const getUserInfo = () => {
    const url = "/wx/stu/getInfo"
    return $ajaxPost(url)
}

//获取用户电话
export const getWxphone = (data) => {
    const url = "/wx/stu/phone"
    return $ajaxGet(url,data)
}
import {
    ApiUtils,
    _axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
    list: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/xueli/genj/list", params, successCallback, failCallback, exceptionCallback);
    }, tuisong: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/genj/listAllMsgs", params, successCallback, failCallback, exceptionCallback);
    }, lsStuByMsg: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.postLoadingAjax(constant.serverUrl + "/genj/listStuByMsg", params, successCallback, failCallback, exceptionCallback);
    },
    save: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.postLoadingAjax(constant.serverUrl + "/xueli/xlstudent/listRcds", params, successCallback, failCallback, exceptionCallback);
    },
    toUpdate: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/xueli/xlsturecord/toUpdate", params, successCallback, failCallback, exceptionCallback);
    },
    update: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.putLoadingAjax(constant.serverUrl + "/xueli/xlsturecord/update", params, successCallback, failCallback, exceptionCallback);
    },
    remove: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.deleteLoadingAjax(constant.serverUrl + "/xueli/xlsturecord/remove", params, successCallback, failCallback, exceptionCallback);
    },
}

export default api;
import {
    ApiUtils,
    _axios
} from '@/api/apiUtil.js';
import constant from '@/util/constant.js';
const api = {
    list: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/xueli/xlstudent/list", params, successCallback, failCallback, exceptionCallback);
    },autoCSnm: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/xueli/xlstudent/autoCSnm", params, successCallback, failCallback, exceptionCallback);
    },
    save: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.postLoadingAjax(constant.serverUrl + "/xueli/xlstudent/save", params, successCallback, failCallback, exceptionCallback);
    },
    impDatas: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.postLoadingAjax(constant.serverUrl + "/xueli/xlstudent/impDatas", params, successCallback, failCallback, exceptionCallback);
    },
    toUpdate: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/xueli/xlstudent/toUpdate", params, successCallback, failCallback, exceptionCallback);
    },
    toUpload: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/xueli/xlstudent/toUpload", params, successCallback, failCallback, exceptionCallback);
    },
    update: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.putLoadingAjax(constant.serverUrl + "/xueli/xlstudent/update", params, successCallback, failCallback, exceptionCallback);
    },
    remove: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.deleteLoadingAjax(constant.serverUrl + "/xueli/xlstudent/remove", params, successCallback, failCallback, exceptionCallback);
    },
    listRcds: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/genj/listRcds", params, successCallback, failCallback, exceptionCallback);
    },
    addRcd: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.postLoadingAjax(constant.serverUrl + "/genj/addRcd", params, successCallback, failCallback, exceptionCallback);
    },
    addAllRcd: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.postLoadingAjax(constant.serverUrl + "/genj/addAllRcds", params, successCallback, failCallback, exceptionCallback);
    },
    removeRcd: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.deleteLoadingAjax(constant.serverUrl + "/genj/removeRecd", params, successCallback, failCallback, exceptionCallback);
    },
    listAllRcds: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/genj/listAllRcds", params, successCallback, failCallback, exceptionCallback);
    },
    souSuo: (params, successCallback, failCallback, exceptionCallback) => {
        ApiUtils.getLoadingAjax(constant.serverUrl + "/genj/souSuo", params, successCallback, failCallback, exceptionCallback);
    },
}
export default api;
/**
 * @title: 常量文件
 * @description: 
 * @author JUN
 */

//export const BASE_URL = "http://192.168.1.3:8086/xueli/"      //杨老
 export const BASE_URL = "https://xlbk.ossjk.cn/xueli"     //线上测试后端
// export const BASE_URL = "https://xlbk.ossjk.cn"
export const APP_ID = "wxb5f80fe319f2e2dc"

//export const BASE_URL = process.env.NODE_ENV === 'development' ? "http://192.168.199.17:8083/zyjsxy-miniapp-api/" : "https://192.168.1.176:8083/zyjsxy-miniapp-api/"
// http://test1.4yd67.cn:7788/后台地址

export const stateForm = {
	bdyear: '', //报读届
}

export const activeForm = {
	level: '', //层次
	subject: '', //专业
	school: '', //报读院校
	cost: '', //费用情况
	name: '', //姓名
	idno: '', //身份证号
	fjaera: '', //户籍地
	mz: '', //民族
	moblie: '', //手机
	sex: '男', //性别
	postaera: '', //快递地址
	remarks: '', //备注

	// bminfo:'',		//报名情况
	byschool: '', //毕业学校
	bysub: '', //毕业专业
	bytm: '', //毕业时间
	byzid: '', //毕业证号
	kqgrd: '', //考前学历
	ksarea: '', //考试区域
	ksarea2: '', //备选考区

	// plus:'',		//成考加分
	// teacher:'',		//班主任
	// test:'',		//考试情况
	// type:'',		//类别
	// wbno:'',		//网报号
	// wbpwd:'',		//密码
}

export const rules = {
	'name': {
		required: true,
		message: '请填写姓名',
		trigger: 'blur'
	},
	'idno': {
		required: true,
		message: '请填写身份证号',
		trigger: 'blur',
	},
	'mz': {
		required: true,
		message: '请选择民族',
		trigger: 'change'
	},
	'mobile': {
		type: 'string',
		required: true,
		message: '请输入手机号',
		trigger: ['blur', 'change']
	}
 

}

export const politicalOptions = [ "中共党员","中共预备党员","共青团员","民革党员","民盟盟员","民建会员","民进会员","农工党党员","致公党党员","九三学社社员","台盟盟员","无党派人士","群众"]

export const nationOptions = ["汉族","壮族","藏族","裕固族","彝族","瑶族","锡伯族","乌孜别克族","维吾尔族","佤族","土家族","土族","塔塔尔族","塔吉克族","水族","畲族","撒拉族","羌族","普米族","怒族","纳西族","仫佬族","苗族","蒙古族","门巴族","毛南族","满族","珞巴族","僳僳族","黎族","拉祜族","柯尔克孜族","景颇族","京族","基诺族","回族","赫哲族","哈萨克族","哈尼族","仡佬族","高山族","鄂温克族","俄罗斯族","鄂伦春族","独龙族","东乡族","侗族","德昂族","傣族","达斡尔族","朝鲜族","布依族","布朗族","保安族","白族","阿昌族","穿青族"]

export const educationOptions = [ "博士","硕士","本科","大专","中专","高中","初中","小学"]

export const degreeOptions = [ "暂无","学士","硕士","博士"]

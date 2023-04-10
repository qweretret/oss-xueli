<template>
	<view class="content">
		<u-popup :show="show" mode="center" :round="10">
			<view class="popup">
				<view class="content">
					<image src="../../static/images/logo.png"></image>
					<text class="head">靖凯学历小程序</text>
					<text class="cont">学历报考、软件工程师培训...</text>
				</view>
				<u-button type="primary" size="small" text="进入" @click="login"></u-button>
			</view>
		</u-popup>
		<u-popup :show="showLogin" mode="center" :round="10">
			<button type="primary" open-type="getUserInfo" withCredentials="true" lang="zh_CN"
				@click="loginOk">授权登录</button>
		</u-popup>
	</view>
</template>

<script>
	import {
		getSessionInfo,
		wxLogin,
		getWxUserInfo,
		getWxphone
	} from "@/api/login.js"
	import {
		APP_ID
	} from "@/utils/common.js"
	export default {
		data() {
			return {
				show: false,
				showLogin: false,
				data: {},
				code: '',
				openid: '',
				session_key: '',
				getuserinfo: {}
			}
		},
		onLoad() {
			this.Init()
		},
		methods: {
			async Init() {
				uni.showLoading({
					title: '加载中'
				})

				setTimeout(() => {
					if (uni.getStorageSync('jwttoken') == '') {
						uni.hideLoading()
						this.show = true
					} else {
						uni.hideLoading()
						uni.switchTab({
							url: '/pages/index/index'
						})
					}
				}, 1000)
			},
			loginOk() { //微信登录，进行授权
				// var that = this;
				return new Promise((resolve) => {
					uni.login({
						provider: 'weixin',
						success: function(loginRes) {
							// that.code = loginRes.code
							resolve(loginRes)

						}
					});
				})
			},
			getUserInfo() {
				// 获取用户信息
				return new Promise((resolve) => {
					uni.getUserInfo({
						provider: 'weixin',
						success(res) {
							//获取wx用户敏感信息
							//拿取要解密的数据和秘钥等
							resolve(res)
						}
					})
				})
			},
			async login() {
				uni.showLoading({
					title: '登录中'
				})

				//判断是否有授权获取用户信息
				const settingRes = await this.getSetting()
				console.log('-----settingRes--------',settingRes);
				const auth = settingRes.authSetting["scope.userInfo"]
				if (auth) {
					//拿取要解密的数据和秘钥等
					let {
						signature,
						rawData,
						encryptedData,
						iv
					} = await this.getUserInfo()

					// //获取授权码
					var wxloginRes = await this.loginOk()

					// //将授权码和APP_ID发送到后端，后端对微信进行访问，然后拿取openid和unionid
					var openIdRes = await this.API_getSessionInfo(APP_ID, wxloginRes.code)
					
					uni.setStorageSync("openid", openIdRes.data.data.openid)
					uni.setStorageSync("unionid", openIdRes.data.data.unionid)
					console.log("  == openid,unionid== ")
					console.log(openIdRes.data.data.openid)
					console.log(openIdRes.data.data.unionid)

					//传入后端进行解密，拿取用户信息
					let wxParams = {
						appid: APP_ID,
						sessionKey: openIdRes.data.data.sessionKey,
						signature,
						rawData,
						encryptedData,
						iv
					}
				
							
					let getWxUserInfoRes = await getWxUserInfo(wxParams) //调用接口-获取用户信息
					console.log(   getWxUserInfoRes ,'---getWxUserInfoRes---')
					
					delete wxParams.rawData
					delete wxParams.encryptedData
					delete wxParams.iv
					
					uni.setStorageSync("wxParams",wxParams)
						
					//微信登录
					let wxLoginRes = await wxLogin({
						openid: openIdRes.data.data.openid
					}) 

					//账号未绑定，跳转游客权限
					if (wxLoginRes.data.code == 1001) {
						uni.hideLoading()
						console.log("游客权限")
						
						uni.redirectTo({
							url: '/pages/index/index2'
						})
					}else if(wxLoginRes.data.code == 1000){
						uni.hideLoading()
						uni.setStorageSync("jwttoken",wxLoginRes.data.msg)			
						uni.switchTab({
							url: '/pages/index/index'
						})
					}
				} else {
					uni.hideLoading()
					
					// 关闭登录框，弹出授权框
					this.show = false;
					this.showLogin = true;
				}
				
			},
			//判断用户是否又授权
			getSetting() {
				return new Promise((resolve) => {
					uni.getSetting({
						success(res) {
							resolve(res)
						}
					})
				})
			},
			//获取微信openid接口
			async API_getSessionInfo(appid, code) {
				let result = {
					appid: appid,
					code: code
				}
				return await getSessionInfo(result)
			},
		},

	}
</script>

<style lang="scss" scoped>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.popup {
		width: 500rpx;
		height: 400rpx;
		padding: 50rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: space-around;
		
		image {
			width: 305rpx;
			height: 86rpx;
		}
	}

	.head {
		font-weight: 600;
		font-size: 45rpx;
	}

	.cont {
		font-size: 35rpx;
		color: #999999;
		margin-top: 20rpx;
	}
</style>

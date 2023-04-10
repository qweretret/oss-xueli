<template>
	<view class="content">
		<view-tabbar :current="2"></view-tabbar>
		<view class="card">
			<view class="touxiang" justify="center">
				<u--image :src="userInfo?userInfo.avatarUrl:icon" width="240rpx" height="240rpx" shape="circle"></u--image>
				<text style="color: white;font-size: 45rpx;font-weight: 400;">{{userInfo?userInfo.nickName:'昵称'}}</text>
				<u-button v-if="!userInfo" style="width: 50%;" type="success" size="small" shape="circle" @click="getUserProfile">点击获取头像和昵称</u-button>
			</view>
			<view class="box">
				<view class="dizhi" @click="address()">
					<u-gap height="1" bgColor="#d8d8d8"></u-gap>
					<u-cell size="large" title="我的地址" value="详情" isLink titleStyle="strong"></u-cell>
				</view>
				<view class="caidan">
					<view class="xuanxiang" @click="payment()">
						<image src="../../static/images/Pending_pay.png" mode=""></image>
						<text>待付款</text>
					</view>
					<view class="xuanxiang" @click="sign_up()">
						<image src="../../static/images/baoming.png" mode=""></image>
						<text>我的报名</text>
					</view>
					<view class="xuanxiang" @click="per_information()">
						<image src="../../static/images/personal.png" mode=""></image>
						<text>个人信息</text>
					</view>
					<view class="xuanxiang" @click="sign_out()">
						<image src="../../static/images/sign_out.png" mode=""></image>
						<text>退出登录</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import Tabbar from '@/components/tabbar.vue'
	export default {
		components: {
			'view-tabbar': Tabbar
		},
		onShow() {
			uni.hideTabBar({
				animation: false
			})
		},
		data() {
			return {
				icon: require('../../static/images/personal.png'),
				userInfo: null
			}
		},
		onLoad() {
			
		},
		methods: {
			getUserProfile() {
				console.log("用于完善会员资料")
				uni.getUserProfile({
					desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
					success: (res) => {
						this.userInfo = res.userInfo
						console.log(res.userInfo) //用户基本信息（头像、个性签名等）
					}
				})
			},
			address() {
				this.show = false;
				uni.navigateTo({
					url: '/pages/personal/address'
				})
			},
			payment() {
				this.show = false;
				uni.navigateTo({
					url: '/pages/personal/payment'
				})
			},
			sign_up() {
				this.show = false;
				uni.navigateTo({
					url: '/pages/personal/sign_up'
				})
			},
			per_information() {
				this.show = false;
				uni.navigateTo({
					url: '/pages/personal/per_information'
				})
			},
			sign_out() {
				this.show = false
				uni.clearStorageSync()
				uni.redirectTo({
					url: '/pages/login/login'
				})
			}
		}
	}
</script>

<style lang="scss">
	.content {
		width: 100%;
		height: 350px;
	}

	.touxiang {
		box-sizing: border-box;
		padding: 3% 30%;
		display: flex;
		width: 100%;
		height: 200px;
		flex-direction: column;
		justify-content: space-around;
		align-items: center;
		background-color: #55aaff;

		image {
			width: 50px;
			height: 50px;
		}
	}

	u-avatar {
		width: 50px;
		height: 50px;
		margin: 0 auto;
	}

	.dizhi {
		width: 100%;
		height: 50px;
	}

	.caidan {
		display: flex;
		width: 100%;
		height: 100px;
		justify-content: center;
		align-items: center;
	}

	.xuanxiang {
		flex: 1;
		display: flex;
		flex-direction: column;
		align-items: center;

		image {
			width: 25px;
			height: 25px;
		}

		text {
			margin-top: 5px;
			font-size: 10px;
			color: #96939e;
		}
	}

	.box {
		width: 100%;
		height: 100%;
		border-radius: 15px;
		background-color: rgba(153, 153, 153, .1);
		// background-color: aqua;
	}
</style>

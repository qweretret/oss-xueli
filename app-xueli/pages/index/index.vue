<template>
	<view class="content">
		<view class="myList">
			<!-- 左边列表 -->
			<view class="leftList">
				<view class="leftListItem"
					:class="{leftSelectingPre:leftCurrent-1===index,leftSelecting:leftCurrent===index,leftSelectingNext:leftCurrent+1===index}"
					v-for="(item,index) in leftList" :key="index" @click="leftCurrent=index">
					{{item}}
				</view>
				<!-- 仅供填充样式 -->
				<view class="leftListItem" v-for="it in [1,2,3,4,5,6]" :key="it"></view>
			</view>
			<!-- 右边内容 -->
			<view class="rightList">
				<view v-show="mojar" class="rightList-item" v-for="(itemR,indexR) in mojar[leftCurrent]" :key="indexR"
					@click="jump('/sub/school/school?sbname='+itemR+'&'+'level='+leftList[leftCurrent])">
					<image src="../../static/logo.png"></image>
					<view class="rightText">
						<p>{{itemR}}</p>
						<button>立即报名</button>
					</view>
				</view>
			</view>
		</view>

		<view-tabbar :current="0"></view-tabbar>
	</view>
</template>

<script>
	import Tabbar from '@/components/tabbar.vue'
	import alert from '@/components/alert.vue'
	import {
		postPay
	} from '@/api/pay.js'
	import {
		getMajar
	} from "@/api/mojar.js"
	import {
		getUserInfo,
		wxLogin
	} from "@/api/login.js"

	export default {
		components: {
			'view-tabbar': Tabbar,
			'view-alert': alert
		},
		onShow() {
			uni.hideTabBar({
				animation: false
			})
		},
		data() {
			return {
				title: '产品列表',
				alertShow: true,
				mojar: [],
				leftCurrent: 0, //左边列表当前选项
				leftList: [], //左边列表项
			}
		},
		onLoad() {
			this.Init()
		},
		methods: {
			async handlePay() {
				let payResult = await postPay({
					id: 'f0fc037821d498c553ae9e28b4f1561d',
					isXl: 1,
					stuId: 'b6030a343f165eefedf95c7e9bb5f548'
				})
				console.log(payResult)
	
				if (payResult.data.code === 1000) {
					let data = payResult.data.data
					uni.requestPayment({
						provider: 'wxpay',
						timeStamp: data.timeStamp,
						nonceStr: data.nonceStr,
						package: data.packageValue,
						signType: data.signType,
						paySign: data.paySign,
						success: function(res) {
							console.log('success:' + JSON.stringify(res));
						},
						fail: function(err) {
							console.log(err)
							console.log('fail:' + JSON.stringify(err));
						}
					});
				}
			},
			open() {},
			close() {},
			change() {},
			jump(u) {
				console.log(u)
				uni.navigateTo({
					url: u
				})
			},
			async getUser() {
				const userInfo = await getUserInfo()
				// 将用户信息放至globalData（全局信息）里面
				getApp().globalData.userInfo = userInfo.data.data;
				console.log('userInfo', userInfo)
			},
			async Init() {
				
				//自动登录
				if(!uni.getStorageSync("jwttoken")){
					let openid =  uni.getStorageSync("openid" )
					let wxLoginRes = await wxLogin({
						openid:  openid
					}) 
					 if(wxLoginRes.data.code == 1000){
						uni.hideLoading()
						uni.setStorageSync("jwttoken",wxLoginRes.data.msg)			
					}
				}
				
				this.getUser()
				const mojar = await getMajar()
				if (mojar.data.code == 1000) {
					// this.mojar = mojar.data.data
					for (let key in mojar.data.data) {
						this.mojar.push(mojar.data.data[key])
						this.leftList.push(key)
					}
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		.myList {

			// 左边列表
			.leftList {
				width: 210rpx;
				position: fixed;
				z-index: -1;
				color: white;
				overflow: scroll;

				.leftListItem {
					display: flex;
					align-items: center;
					font-family: STXihei;
					font-size: 34rpx;
					height: 150rpx;
					text-align: center;
					background-color: #28d97e;
				}

				.leftSelecting {
					background-color: white;
					color: black;
				}

				.leftSelectingPre {
					border-bottom-right-radius: 30rpx;
				}

				.leftSelectingNext {
					border-top-right-radius: 30rpx;
				}
			}

			// 右边列表
			.rightList {
				// flex: 1;
				float: right;
				padding-bottom: 100rpx;

				&-item {
					width: 444rpx;
					height: 128rpx;
					background-color: #10a556;
					box-shadow: 8px 5px lightgray;
					border-radius: 30rpx 0rpx 30rpx 0rpx;
					margin: 8rpx 29rpx 35rpx 27rpx;
					display: flex;
					padding: 26rpx 23rpx 0 26rpx;

					image {
						width: 176rpx;
						height: 103rpx;
						margin-right: 30rpx;
					}

					.rightText {
						color: white;

						p {
							font-family: YouSheBiaoTiHei;
							font-size: 30rpx;
							width: 230rpx;
							height: 40rpx;
							/*第一步： 溢出隐藏 */
							overflow: hidden;
							/* 第二步：让文本不会换行， 在同一行继续 */
							white-space: nowrap;
							/* 第三步：用省略号来代表未显示完的文本 */
							text-overflow: ellipsis;
						}

						.tightTextHaveAndGood {
							display: flex;
							justify-content: space-between;
							font-family: SimHei;
							font-size: 14rpx;
						}

						button {
							margin-top: 26rpx;
							width: 136rpx;
							height: 34rpx;
							font-family: AdobeHeitiStd-Regular;
							font-size: 18rpx;
							line-height: 30rpx;
							border-radius: 9rpx;
						}
					}
				}
			}
		}
	}
</style>

<template>
	<!-- 信息页 -->
	<view class="content">
		<!-- 每一条信息 -->
		<view @click="chkinMsg(item.id)" class="textItem" v-for="item in listMsgs" :key="item.id">
			<!-- 信息头部 -->
			<view class="itemHead">
				<b>您收到一则 <text>{{item.type}}</text> 通知</b>
				<span>{{item.crtm}}</span>
			</view>
			<!-- 信息内容 -->
			<view class="itemContent">
				<text>后端还没有该数据后端还没有该数据后端还没有该数据后端还没有该数据后端还没有该数据后端还没有该数据后端还没有该数据</text>
			</view>
		</view>
		<!-- tabbar -->
		<view-tabbar :current="1"></view-tabbar>
	</view>
</template>

<script>
	import Tabbar from '@/components/tabbar.vue'
	import {
		getMsgs,
		chkinMyMsg
	} from "@/api/mojar.js";
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
				userInfo: {}, //用户信息
				listMsgs: [], //信息列表
			}
		},
		async onLoad() {
			this.userInfo = getApp().globalData.userInfo
			const res = await getMsgs({
				id: this.userInfo.id
			});
			this.listMsgs = res.data.data;
		},
		methods: {
			async chkinMsg(msgId) {
				const res = await chkinMyMsg({
					id: msgId
				});
				console.log(res);
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		// display: flex;
		// flex-direction: column;
		// align-items: center;
		// justify-content: center;
		// width: 750rpx;
		padding-bottom: 100rpx;

		// 每一个信息item整体
		.textItem {
			width: 636rpx;
			// height: 178rpx;
			background-color: #10a556;
			box-shadow: 5px 5px black;
			border-bottom-right-radius: 30rpx;
			margin: 16rpx 20rpx 45rpx 24rpx;
			padding: 35rpx;
			padding-bottom: 59rpx;


			.itemHead {
				display: flex;
				justify-content: space-between;
				color: #fff;

				b {
					font-size: 30rpx;
					font-family: YouSheBiaoTiHei;
					font-weight: bolder;
				}

				text {
					color: darkred;
				}

				span {
					font-size: 16rpx;
					font-family: SimHei;
				}
			}

			.itemContent {
				margin-top: 19rpx;
				font-size: 22rpx;
				font-family: SimHei;
				font-weight: 100;
				color: #fff;
			}
		}
	}
</style>

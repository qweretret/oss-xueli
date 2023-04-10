<template>
	<view>
		<!-- <uni-card class="card" @click="show = true">
			<view class="left">
				<u--text bold="true" lines="1" text="广东省广州市天河区黄村长盛大厦A111"></u--text>
				<u--text mode="name" text="张三三" format="encrypt"></u--text>
				<u--text mode="phone" text="15019479320"></u--text>
			</view>
		</uni-card>
		<view>
			<u-popup :show="show" @close="close" mode="center" @open="open">
				<view>
					<view class="left">
						<u--text bold="true" text="广东省广州市天河区黄村长盛大厦A222"></u--text>
						<u--text  text="张三三" format="encrypt"></u--text>
						<u--text mode="phone" text="15019479320"></u--text>
					</view>
				</view>
			</u-popup>
		</view> -->
		<u--text text="地址"></u--text>
		<u--text :text="userInfo.postaera"></u--text>
		<u--text text="姓名"></u--text>
		<u--text :text="userInfo.name"></u--text>
		<u--text text="电话"></u--text>
		<u--text :text="userInfo.moblie"></u--text>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				show: false,
				userInfo:{}, //用户信息
			}
		},
		methods: {
			
		},
		onLoad() {
			this.userInfo = getApp().globalData.userInfo
		}
	}
</script>

<style lang="scss">
	.card {
		display: block;
	}
</style>

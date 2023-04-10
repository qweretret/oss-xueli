<template>
	<view class="container">
		<view class="touxiang">
			<image :src="src"></image>
		</view>
		<u-cell-group>
			<u-cell title="姓名" :value="userInfo.name"></u-cell>
			<u-cell title="性别" :value="userInfo.sex"></u-cell>
			<u-cell title="民族" :value="userInfo.mz"></u-cell>
			<u-cell title="身份证号" :value="userInfo.idno"></u-cell>
			<!-- <u-cell title="年龄" :value="21"></u-cell> -->
			<u-cell title="报读届" :value="userInfo.bdyear"></u-cell>
			<u-cell title="是否已报名" :value="userInfo.bminfo"></u-cell>
		</u-cell-group>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				src: '../../static/images/personal.png',
				userInfo:{},  //当前用户信息
			}
		},
		onLoad() {
			// 1当前用户信息
			this.userInfo = getApp().globalData.userInfo;
			// 2console.log(this.userInfo);
			
			 
		}
	}
</script>

<style lang="scss">
	.touxiang {
		display: flex;
		width: 100%;
		height: 100px;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		background-color: azure;

		image {
			width: 50px;
			height: 50px;
		}
	}
</style>

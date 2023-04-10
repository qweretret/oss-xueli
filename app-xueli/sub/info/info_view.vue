<template>
	<view class="content">
		<view v-if="form!=null" v-for="item,key in form" :key="key">
			<text v-if="key == 'name'">{{'姓 名: '+item}}</text>
			<text v-else-if="key=='subject'">{{'专 业: '+item}}</text>
			<text v-else-if="key=='school'">{{'学 校: '+item}}</text>
			<text v-else-if="key=='cost'">{{'学 费: '+item}}</text>
			<text v-else-if="key=='idno'">{{'身份证: '+item}}</text>
			<text v-else-if="key=='fjaera'">{{'户籍地: '+item}}</text>
			<text v-else-if="key=='mz'">{{'民 族: '+item}}</text>
			<text v-else-if="key=='sex'">{{'性 别: '+item}}</text>
			<text v-else-if="key=='moblie'">{{'手机号: '+item}}</text>
			<text v-else-if="key=='postaera'">{{'快递地址: '+item}}</text>
			<text v-else-if="key=='remarks'">{{'详细地址: '+item}}</text>
		</view>
		
		<u-button type="primary" text="提交报名费" size="small" @click="report"></u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: null,
			}
		},
		onLoad() {
			this.form = uni.getStorageSync('form')
			console.log(this.form)
		},
		methods: {
			report() {
				uni.redirectTo({
					url: '/pages/index/pay'
				})
			}
		}
	}
</script>

<style lang="scss">
	.content {
		padding: 20rpx;
	}
</style>

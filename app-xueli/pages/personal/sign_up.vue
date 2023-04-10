<template>
	<view>
		{{myList}}
	</view>
</template>

<script>
	import {
		listRcds
	} from "@/api/mojar.js"
	export default {
		data() {
			return {
				userInfo: {}, //用户信息
				myList: '', //我的报名 
			}
		},
		methods: {

		},
		async onLoad() {
			this.userInfo = getApp().globalData.userInfo
			const res = await listRcds({
				id: this.userInfo.id
			});
			console.log(res);
			this.myList = res.data
			console.log(this.myList);
		}
	}
</script>

<style lang="scss" scoped>
</style>

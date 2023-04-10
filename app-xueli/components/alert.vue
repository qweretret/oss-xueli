<template>
	<view class="content">
		<u-transition v-if="alertCreate" :show='alertShow' mode="fade-down">
			<!--type: primary,success,error,warning,info -->
			
			<u-alert :type="type" :closable="true" :show-icon="true" :title="content"></u-alert>
		</u-transition>
	</view>
</template>

<script>
	export default {
		name: 'alert',
		props: {
			type: String,
			content: String,
			time: Number
		},
		data() {
			return {
				alertCreate: false,
				alertShow: false
			}
		},
		onReady() {
			this.Init()
		},
		methods: {
			Init(){
				setTimeout(()=>{
					this.alertCreate = true
					this.alertShow = true
					setTimeout(() => {
						this.alertShow = false
						setTimeout(() =>{
							this.alertCreate = false
						},600)
					}, this.time)
				},500)
			}
		}
	}
</script>

<style lang="scss">
	.content {
		position: fixed;
		top: 50rpx;
		left: 0rpx;
		width: 100%;
	}
</style>

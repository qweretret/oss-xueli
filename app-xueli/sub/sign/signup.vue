<template>
	<view class="content">
		<view-alert v-if="alertShow" :type="'warning'" :content="alertTitle" :time='2500'></view-alert>

		<u--form labelPosition="left" :model="form" :rules="rule" ref="form1" labelWidth="135rpx">
			
			<u-form-item label="专 业" prop="subject" borderBottom>
				<u--input v-model="form.subject" border="none" disabled disabledColor="#ffffff"></u--input>
			</u-form-item>
			<u-form-item label="学 校" prop="schoolNm" borderBottom>
				<u--input v-model="form.schoolNm" border="none" disabled disabledColor="#ffffff"></u--input>
			</u-form-item>
			<u-form-item label="学 费" prop="cost" borderBottom>
				<u--input v-model="form.cost" border="none" disabled disabledColor="#ffffff"></u--input>
			</u-form-item>
			<u-form-item label="手机号"  prop="mobile" borderBottom ref="mobile">
				<u--input v-model="form.mobile" :readonly="true" border="none" placeholder='请输入手机号'></u--input>
			</u-form-item>
			
			<u-form-item label="姓 名" prop="name" borderBottom ref="name">
				<u--input v-model="form.name" border="none" placeholder='请输入姓名'></u--input>
			</u-form-item>
			<u-form-item label="性 别" borderBottom>
				<u-radio-group v-model="form.sex">
					<u-radio name="男" :labelDisabled="false">男</u-radio>男
					<u-radio name="女" :labelDisabled="false">女</u-radio>女
				</u-radio-group>
			</u-form-item>
			<u-form-item label="身份证" prop="idno" borderBottom ref="idno">
				<u--input v-model="form.idno" border="none" placeholder='请输入身份证号'></u--input>
			</u-form-item>
			<u-form-item label="民族" prop="mz" borderBottom ref="mz" @click="mz=true">
				<u--input v-model="form.mz" border="none" placeholder='请选择民族' disabled disabledColor="#ffffff">
				</u--input>
				<u-icon slot="right" name="arrow-right"></u-icon>
			</u-form-item>
			
			<u-picker :show="mz" :columns="mzColumns" @confirm="mzConfirm" @cancel="mz=false"></u-picker>
			
			<u-button type="primary" text="确定" @click="submit"></u-button>
			
		</u--form>

	</view>
</template>

<script>
 
	import alert from '@/components/alert.vue'
	import {
		activeForm,
		rules,
		nationOptions
	} from '@/utils/common.js'
	import {
		postPay
	} from '@/api/pay.js'

	
	export default {
		components: {
			'view-alert': alert
		},
		data() {
			return {
				form: {subject:""},
				show: false,
				value: [0, 0, 0],
				rule: rules,
				alertShow: false,
				alertTitle: '',
				
				mz: false,
				mzColumns: [],
				show: false,
			}
		},
		onLoad(options) {
			
			this.form.level =  options.level
			this.form.subject =  options.sbname
			this.form.schoolNm =  options.school
		    this.form.cost =  options.cost
		    this.form.mobile =  options.phone
			this.form.school =  options.skid
			
			// 获取民族数据
			this.mzColumns[0] = nationOptions
			
			this.form.openid =  uni.getStorageSync("openid" )
			this.form.unionid = uni.getStorageSync("unionid" )
			console.log(this.form)
			  
		},
		methods: {
			mzConfirm(e) {
				this.mz = false
				this.form.mz = e.value[0]
			},
			submit() {
				// console.log(this.form);
				// 身份证正则表达式
				let _IDRe18 = /^([1-6][1-9]|50)d{4}(18|19|20)d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)d{3}[0-9Xx]$/

				this.$refs.form1.validate().then(res => {

				   // if (!_IDRe18.test(this.form.idno)) {
					//  	this.alertTitle = '请输入正确的身份证号'
					//  	this.alertShow = true
					// 	this.alertClose()
					//  } 
					//else {
						//缓存user
						let stusignup = this.form
						
						//保存用户
						
					    this.handlePay(stusignup)
				//   }

					//this.alertClose()

					console.log('res' + res)
				}).catch(error => {
					console.log(error)
				})
			},	async handlePay(data) {
				
				let payResult = await postPay(data)
				
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
				 
							uni.redirectTo({
								url: '/pages/index/index'
							})
						},
						fail: function(err) {
							console.log(err)
							console.log('fail:' + JSON.stringify(err));
						}
					});
				}
			},
		
			close() {
				this.show = false;
			},	bindChange(e) {
				let changeVal = e.detail.value;
				this.value.forEach((item, i) => {
					if (item !== changeVal[i]) {
						//切换省时，县市都切换
						if (i == 0) {
							// console.log(changeVal[i])
							this.countyArr = city.countyArr[changeVal[i]]
							this.townArr = city.townArr[changeVal[i]][0]
						} else if (i == 1) { //切换县时，市切换
							this.townArr = city.townArr[changeVal[i - 1]][changeVal[i]]
						}
					}
				})
				this.value = changeVal;
				// if(this.value)
				console.log(e)
			},
			close() {
				this.show = false;
			},
			
			alertClose() {
				// 消除警告只用一次的bug
				setTimeout(() => {
					this.alertShow = false
				}, 2500 + 1000)
			}
		}
	}
</script>

<style lang="scss">
	.content {
		padding: 35rpx;
	}

	.u--form {
		width: 100%;
	}

	.u-radio {
		margin-left: 20rpx;
	}

	.u-button {
		margin-top: 50rpx;
	}

	.picker-view {
		width: 750rpx;
		height: 600rpx;
		margin-top: 20rpx;
	}

	.item {
		height: 50px;
		display: flex;
		align-items: center;
		justify-content: center;
		text-align: center;
	}

	.cancel {
		background-color: #FFFFFF;
		position: absolute;
		top: 35rpx;
		left: 45rpx;
		cursor: pointer;
	}

	.yes {
		color: #007AFF;
		background-color: #FFFFFF;
		position: absolute;
		top: 35rpx;
		right: 45rpx;
		cursor: pointer;
	}

	.yes:active,
	.cancel:active {
		opacity: 0.8;
		background-color: #FFFFFF;
	}
</style>

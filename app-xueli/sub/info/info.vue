<template>
	<view class="content">
		<view-alert v-if="alertShow" :type="'warning'" :content="alertTitle" :time='2500'></view-alert>

		<u--form labelPosition="left" :model="form" :rules="rule" ref="form1" labelWidth="135rpx">
			<u-form-item label="专 业" prop="subject" borderBottom>
				<u--input v-model="form.subject" border="none" disabled disabledColor="#ffffff"></u--input>
			</u-form-item>
			<u-form-item label="学 校" prop="school" borderBottom>
				<u--input v-model="form.school" border="none" disabled disabledColor="#ffffff"></u--input>
			</u-form-item>
			<u-form-item label="学 费" prop="cost" borderBottom>
				<u--input v-model="form.cost" border="none" disabled disabledColor="#ffffff"></u--input>
			</u-form-item>
			<u-form-item label="姓 名" prop="name" borderBottom ref="name">
				<u--input v-model="form.name" border="none" placeholder='请输入姓名'></u--input>
			</u-form-item>
			<u-form-item label="身份证" prop="idno" borderBottom ref="idno">
				<u--input v-model="form.idno" border="none" placeholder='请输入身份证号'></u--input>
			</u-form-item>
			<u-form-item label="户籍地" prop="fjaera" borderBottom ref="fjaera">
				<u--input v-model="form.fjaera" border="none" placeholder='请输入户籍地'></u--input>
			</u-form-item>
			<u-form-item label="民族" prop="mz" borderBottom ref="mz" @click="mz=true">
				<u--input v-model="form.mz" border="none" placeholder='请选择民族' disabled disabledColor="#ffffff">
				</u--input>
				<u-icon slot="right" name="arrow-right"></u-icon>
			</u-form-item>
			<u-form-item label="性 别" borderBottom>
				<u-radio-group v-model="form.sex">
					<u-radio name="男" :labelDisabled="false">男</u-radio>男
					<u-radio name="女" :labelDisabled="false">女</u-radio>女
				</u-radio-group>
			</u-form-item>
			<u-form-item label="手机号" prop="moblie" borderBottom ref="moblie">
				<u--input v-model="form.moblie" border="none" placeholder='请输入手机号'></u--input>
			</u-form-item>
			<u-form-item label="快 递 地 址" prop="postaera" borderBottom ref="postaera" @click="show = true">
				<u--input v-model="form.postaera" border="none" placeholder='请选择省市区' disabled disabledColor="#ffffff">
				</u--input>
				<u-icon slot="right" name="arrow-right"></u-icon>
			</u-form-item>

			<u-form-item label="详 细 地 址" prop="remarks" borderBottom ref="remarks">
				<u-textarea v-model="form.remarks"></u-textarea>
			</u-form-item>

			<u-button type="primary" text="确定" @click="submit"></u-button>
		</u--form>

		<u-picker :show="mz" :columns="mzColumns" @confirm="mzConfirm" @cancel="mz=false"></u-picker>

		<u-popup :show="show" @close="close">
			<picker-view :indicator-style="indicatorStyle" :value="value" @change="bindChange" class="picker-view">
				<picker-view-column>
					<view class="item" v-for="(val,v) in cityArr" :key="v">
						{{val}}
					</view>
				</picker-view-column>
				<picker-view-column>
					<view class="item" v-for="(val,v) in countyArr" :key="v">
						{{val}}
					</view>
				</picker-view-column>
				<picker-view-column>
					<view class="item" v-for="(val,v) in townArr" :key="v">
						{{val}}
					</view>
				</picker-view-column>
			</picker-view>

			<view class="cancel" @click="close">取消</view>
			<view class="yes" @click="yes">确定</view>
		</u-popup>

	</view>
</template>

<script>
	import {
		postInfo
	} from '@/api/info.js'
	import alert from '@/components/alert.vue'
	import {
		city
	} from '@/sub/city/city.js'
	import {
		activeForm,
		rules,
		nationOptions
	} from '@/utils/common.js'
	export default {
		components: {
			'view-alert': alert
		},
		data() {
			return {
				form: activeForm,
				mz: false,
				mzColumns: [],
				show: false,
				value: [0, 0, 0],
				cityArr: [],
				countyArr: [],
				townArr: [],
				rule: rules,
				alertShow: false,
				alertTitle: '',
			}
		},
		onLoad(options) {
			this.form.level =  options.level
			this.form.subject =  options.sbname
			this.form.school =  options.school
			console.log(this.form)
			// 获取民族数据
			this.mzColumns[0] = nationOptions.map(item => {
				return item.label
			})
			// 获取省市区数据
			this.cityArr = city.cityArr
			this.countyArr = city.countyArr[0]
			this.townArr = city.townArr[0][0]
		},
		methods: {
			radioGroupChange(e) {
				// console.log(e);
			},
			mzConfirm(e) {
				this.mz = false
				this.form.mz = e.value[0]
				// console.log(e)
			},
			submit() {
				// console.log(this.form);

				// 手机号正则表达式
				var reg_tel = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
				// 身份证正则表达式
				let _IDRe18 = /^([1-6][1-9]|50)d{4}(18|19|20)d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)d{3}[0-9Xx]$/

				this.$refs.form1.validate().then(res => {

					// if (!reg_tel.test(this.form.moblie)) {
					// 	this.alertTitle = '请输入正确的手机号'
					// 	this.alertShow = true
					// 	this.alertClose()
					// } else if (!_IDRe18.test(this.form.idno)) {
					// 	this.alertTitle = '请输入正确的身份证号'
					// 	this.alertShow = true
					// 	this.alertClose()
					// } else {
					this.submitInfo()
					// }

					this.alertClose()

					console.log('res' + res)
				}).catch(error => {
					console.log(error)
				})
			},
			async submitInfo(){
				let postRes = await postInfo(this.form)
				console.log(postRes)
				if (postRes.data.code==1000) {
					uni.$u.toast('报名成功，即将跳转')
					uni.setStorageSync("form", this.form)
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/index/info_view'
						})
					}, 2000)
				} else {
					uni.$u.toast('报名失败，请重新填写并提交')
				}
			},
			bindChange(e) {
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
			yes() {
				this.form.postaera = this.cityArr[this.value[0]] + '-' + this.countyArr[this.value[1]] +
					'-' + this.townArr[this.value[2]];
				this.close()
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

<template>
	<view class="content">
		<view v-if="Cardcontent" class="school" v-for="(item,i) in Cardcontent" :key="i">
			<view class="center">
				<text>{{ tuition.nm }}</text>
			</view>
			<view class="cont">
				<view class="cont_title">
					<text class="head">{{ item.skname }}</text>
					<text class="boy">{{ tuition.cs }}</text>
				</view>
				<view class="btn">
					<u-button type="primary" text="报名" size="mini" shape="circle"   open-type="getPhoneNumber" @getphonenumber="report($event,item.skname,item.id)">
					</u-button>
					<u-button type="info" text="详情" :plain="true" size="mini" shape="circle" @click="detail = true"> 
					</u-button>
				</view>
			</view>
			<view class="year">{{ item.level }}</view>
		</view>
		<u-popup :show="detail" @close="close" closeable>
			<view class="popup" v-for="(item,i) in Cardcontent" :key="i">
				<view class="head">{{ item.skname }}</view>
				<view class="boy">{{"报考条件:"}}</view>
				<view class="boy">{{ item.condi }}</view>
				<view class="boy">{{"需要材料:"}}</view>
				<view class="boy">{{ item.bmcl }}</view>
				<view class="boy">{{"学校代码：" + item.code }}</view>
				<view class="boy">{{"上课地点：" + item.ckdd }}</view>
				<view class="boy">{{"层次：" + item.level }}</view>
				<view class="boy">{{"招生学年："+ item.zsyear }}</view>
				<view class="boy">{{"备注："+ item.remarks}}</view>
				<view class="boy">{{"上课地点：" + item.ckdd }}</view>
				<view class="boy">{{ "学费：" }}</view>
				<view class="boy">{{ tuition.cs }}</view>
				<u-button type="primary" text="报名" size="small" open-type="getPhoneNumber" @getphonenumber="report($event,item.skname,item.id)"></u-button>
			</view>
		</u-popup>
	</view>

</template>
<script>
	import {
		$ajaxGet
	} from '@/utils/methods.js';
	import {
		getWxphone
	} from "@/api/login.js"
	export default {
		data() {
			return {
				detail: false,
				title: '广东理工学院',
				content: '4020元/年，共8040元，另外教材费500元（教材费两年一次性交完）\n ...',
				Cardcontent: null,
				level: null,
				sbname: null,
				tuition:{},
				isShow:false
			}
		},
		onLoad: function(options) {
			// console.log("options>>", options);
			this.level = options.level;
			this.sbname = options.sbname;
			this.getSchoolInfo(this.level, this.sbname)
		},
		methods: {
		 
	 
			// 获取层次,专业传递过来的数据
			async report(e,skname,skid) {
				console.log(e)
				if(e.detail.errMsg == "getPhoneNumber:fail user deny")  return
				let wxParams =  uni.getStorageSync("wxParams");
				wxParams.iv= e.detail.iv
				wxParams.encryptedData = e.detail.encryptedData
				  let res = await  getWxphone(wxParams)  
					if("user check failed" == res){
						
					}else{
						console.log(this.level)
						let cost =0.01
						if(this.level =='专升本(2年)'){
							cost =0.02
						}
						let data = `?level=${this.level}&sbname=${this.sbname}&school=${skname}&skid=${skid}&cost=${cost}&phone=`+res.data
						//获取电话
						uni.navigateTo({
							url: '/sub/sign/signup'+data
						})
					}
			},
			close() {
				this.detail = false;
			},
			// 获取接口数据
			async getSchoolInfo(level, sbname) {
				const result = await $ajaxGet("/wx/stu/listSchool", {
					level,
					sbname
				})
				this.Cardcontent = result.data.data
				// console.log(this.Cardcontent)
				let consts = JSON.parse(this.Cardcontent[0].subjects)
				for(let i =0;i < consts.length;i++){
					if(sbname == consts[i].nm){
						this.tuition = consts[i]
					}
				}
				// console.log(this.tuition)
			}
		}
	}
</script>

<style lang="scss">
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 25rpx 25rpx;
	}

	.school {
		width: 100%;
		height: 330rpx;
		background-color: #fffcc7;
		overflow: hidden;
		padding: 20rpx;
		box-sizing: border-box;
		border-radius: 20rpx;
		margin-bottom: 30rpx;
		box-shadow: 2px 3px 10px 1px #e2e2e2;

		.center {
			display: flex;
			align-items: center;
			justify-content: flex-start;

			text {
				margin-left: 6rpx;
			}
		}

		image {
			width: 50rpx;
			height: 50rpx;
		}

		.cont_title {
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;

			.head {
				font-size: 39rpx;
				font-weight: 600;
				color: #303133;
				margin-bottom: 10rpx;
			}

			.boy {
				font-size: 32rpx;
				padding: 0rpx 10rpx;
				color: #808080;
			}
		}

		.cont {
			display: flex;
			justify-content: space-between;
			align-items: center;

			.btn {
				padding: 15rpx 0rpx;

				button {
					margin-bottom: 25rpx;
				}
			}
		}

		.year {
			font-size: 32rpx;
			color: #808080;
			padding-top: 20rpx;
		}
	}


	.popup {
		height: 800rpx;
		padding: 20rpx 50rpx 50rpx 50rpx;
		box-sizing: border-box;
		overflow: auto;

		.head {
			font-size: 39rpx;
			font-weight: 600;
			color: #303133;
		}

		.boy {
			font-size: 32rpx;
			padding: 0rpx 10rpx;
			color: #808080;
		}
	}
</style>

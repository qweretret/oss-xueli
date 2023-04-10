<template>
	<popup ref="popup" :loading="popupLoading">
		<div slot="body">
			<el-form class="dataForm" ref="form" :disabled="disabled" :model="formParameter" :inline="true"
				label-width="80px" :rules="rules">
				<el-row>
					<el-col :span="8">
						<el-form-item label="姓名" prop="name">
							<el-input v-model="formParameter.name" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="手机" prop="moblie">
							<el-input v-model="formParameter.moblie" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="报读届" prop="bdyear">
							<el-select placeholder="请选择">
                				<el-option >
                				</el-option>
              				</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="8">
						<el-form-item label="民族" prop="mz">
							<el-select placeholder="请选择">
                				<el-option >
                				</el-option>
              				</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="身份证号" prop="idno">
							<el-input v-model="formParameter.idno" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="性别" prop="sex">
							<el-select placeholder="请选择">
                				<el-option >
                				</el-option>
              				</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="8">
						<el-form-item label="报读院校" prop="school" :inline="true">
							<el-select placeholder="请选择" :popper-append-to-body="false" >
                				<el-option >
                				</el-option>
              				</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="层次" prop="school" :inline="true">
							<el-select placeholder="请选择" :popper-append-to-body="false">
                				<el-option >
                				</el-option>
              				</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="专业" prop="school" :inline="true">
							<el-select placeholder="请选择" :popper-append-to-body="false">
                				<el-option >
                				</el-option>
              				</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="8">
						<el-form-item label="专业代码" prop="school">
							<el-input v-model="formParameter.school" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="类别" prop="type">
							<el-input v-model="formParameter.type" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="户籍地" prop="fjaera">
							<el-input v-model="formParameter.fjaera" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-row>
						<el-col :span="8">
							<el-form-item label="毕业学校" prop="byschool">
								<el-input v-model="formParameter.byschool" autocomplete="off"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="毕业专业" prop="bysub">
								<el-input v-model="formParameter.bysub" autocomplete="off"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="毕业时间" prop="bytm">
								<el-input v-model="formParameter.bytm" autocomplete="off"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-col :span="8">
						<el-form-item label="毕业证号" prop="byzid">
							<el-input v-model="formParameter.byzid" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="成考加分" prop="plus">
							<el-input v-model="formParameter.plus" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="考前学历" prop="kqgrd">
							<el-input v-model="formParameter.kqgrd" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="8">
						<el-form-item label="报名情况" prop="bminfo">
							<el-input v-model="formParameter.bminfo" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="网报号" prop="wbno">
							<el-input v-model="formParameter.wbno" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="密码" prop="wbpwd">
							<el-input v-model="formParameter.wbpwd" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="8">
						<el-form-item label="报名号" prop="bmno">
							<el-input v-model="formParameter.bmno" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="考试区域" prop="ksarea">
							<el-input v-model="formParameter.ksarea" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item label="备选考区" prop="ksarea2">
							<el-input v-model="formParameter.ksarea2" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="快递地址" prop="postaera">
							<el-input v-model="formParameter.postaera" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-form-item label="备注" prop="remarks">
							<el-input v-model="formParameter.remarks" autocomplete="off"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
		</div>
		<div slot="footer">
			<el-button v-if="!disabled" type="primary" :loading="confirmLoading" @click="confirm()">确 定</el-button>
			<el-button @click="close">关 闭</el-button>
		</div>
	</popup>
</template>

<script>
import popup from '@/components/popup/drawerPopup.vue';
import api from '@/api/xueli/xlstudent.js';
export default {
	name: 'edit',
	components: {
		popup
	},
	data() {
		return {
			disabled: false,
			popupLoading: false,
			confirmLoading: false,
			parameter: {},
			formParameter: {},
			rules: {
				//报读届
				bdyear: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 20,
						trigger: 'blur'
					}
				],
				//姓名
				name: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//手机
				moblie: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 50,
						trigger: 'blur'
					}
				],
				//报读院校
				school: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//层次
				level: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//专业
				subject: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//类别
				type: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//户籍地
				fjaera: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//考试区域
				ksarea: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//备选考区
				ksarea2: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//成考加分
				plus: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 11,
						trigger: 'blur'
					}
				],
				//考前学历
				kqgrd: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//毕业学校
				byschool: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//毕业专业
				bysub: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//毕业时间
				bytm: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						trigger: 'blur'
					}
				],
				//毕业证号
				byzid: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//身份证号
				idno: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//性别
				sex: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//民族
				mz: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//报名情况
				bminfo: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//网报号
				wbno: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//密码
				wbpwd: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//报名号
				bmno: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 32,
						trigger: 'blur'
					}
				],
				//快递地址
				postaera: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 50,
						trigger: 'blur'
					}
				],
				//备注
				remarks: [
					{
						validator: this.$validate.required,
						trigger: 'blur'
					},
					{
						validator: this.$validate.length,
						max: 200,
						trigger: 'blur'
					}
				],
			}
		};
	},
	methods: {
		//打开弹框
		open(parameter, title, disabled, size) {
			this.parameter = parameter;
			this.disabled = disabled;
			this.$refs.popup.open(title, size);
			this.$nextTick(() => {
				this.init();
			})
		},
		//关闭弹框
		close() {
			this.disabled = false;
			this.$refs.popup.close();
		},
		//提交表单
		confirm() {
			this.confirmLoading = true;
			if (!this.parameter.id) {
				//添加
				this.$utils.confirm.warning("提示", "请确定添加数据吗?", () => {
					let parameter = this.$utils.merger(this.formParameter);
					api.save(parameter,
						response => {
							this.$utils.msg.success(response.msg);
							this.confirmLoading = false;
							this.close();
							this.$parent.list();
						},
						response => {
							this.$utils.msg.warning(response.msg);
							this.confirmLoading = false;
						}
					);
				})
			} else {
				//编辑
				this.$utils.confirm.warning("提示", "确定修改数据吗?", () => {
					let parameter = this.$utils.merger(this.formParameter);
					api.update(parameter,
						response => {
							this.$utils.msg.success(response.msg);
							this.confirmLoading = false;
							this.close();
							this.$parent.list();
						},
						response => {
							this.$utils.msg.warning(response.msg);
							this.confirmLoading = false;
						}
					);
				})
			}
		},
		init() {
			this.popupLoading = true;
			//清除等待
			this.confirmLoading = false;
			//清空内容
			this.formParameter = {};
			//清楚校验
			this.$refs.form.clearValidate();

			if (this.parameter.id) {
				//编辑查询数据
				let parameter = {
					id: this.parameter.id
				};
				api.toUpdate(parameter, response => {
					this.formParameter = response.data;
					this.popupLoading = false;
				});
			} else {
				//添加
				this.popupLoading = false;
			}
		}
	}
};
</script>

<style lang="scss" scoped="scoped">
@import '~common/custom/css/common.scss';
@import '~common/custom/css/popup/popup.scss';
</style>

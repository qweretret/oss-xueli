<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form
        class="dataForm"
        ref="form"
        :disabled="disabled"
        :model="formParameter"
        :inline="true"
        label-width="80px"
        :rules="rules"
      >
        <el-row>
          <el-col :span="8">
            <el-form-item label="学校名称" prop="skname"
              ><el-input
                v-model="formParameter.skname"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="层次" prop="level">
              <select-option-dictionary
                v-model="formParameter.level"
                dkey="xli-level"
                :multiple="false"
                :stringMode="true"
              ></select-option-dictionary>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="学校代码" prop="code"
              ><el-input
                v-model="formParameter.code"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="开始时间" prop="starttm">
             
			  <el-date-picker
                v-model="formParameter.starttm"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
             </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="结束时间" prop="endtm">
			  <el-date-picker
                v-model="formParameter.endtm"
                type="date"
                placeholder="选择日期">
              </el-date-picker>   
			 </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="招生学年" prop="zsyear"
              ><el-input
                v-model="formParameter.zsyear"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="报考条件" prop="condi"
              ><el-input
                v-model="formParameter.condi"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
		    </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="报名材料" prop="bmcl"
              ><el-input
                v-model="formParameter.bmcl"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
		    </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="上课地点" prop="ckdd"
              ><el-input
                v-model="formParameter.ckdd"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remarks">
              <el-input
                type="textarea"
                v-model="formParameter.remarks"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button
        v-if="!disabled"
        type="primary"
         v-has-permission-code-and="'003000000005'" 
        :loading="confirmLoading"
        @click="confirm()"
        >确 定</el-button
      >
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/xueli/xlschool.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";

export default {
  name: "edit",
  components: {
    popup,
    selectOptionDictionary,
  },
  data() {
    return {
      disabled: false,
      popupLoading: false,
      confirmLoading: false,
      parameter: {},
      formParameter: {},
      rules: {
        //招生学年
        zsyear: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 20,
            trigger: "blur",
          },
        ],

        //学校名称
        skname: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
          {
            validator: this.$validate.length,
            max: 32,
            trigger: "blur",
          },
        ],
      },
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
      });
    },
    //关闭弹框
    close() {
      this.disabled = false;
      this.$refs.popup.close();
    },
    //提交表单
    confirm() {
      this.$utils.checkForm(this.$refs.form, () => {
        this.confirmLoading = true;
        if (!this.parameter.id) {
          //添加
          let parameter = this.$utils.merger(this.formParameter);
          api.save(
            parameter,
            (response) => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              this.close();
              this.$parent.list();
            },
            (response) => {
              this.$utils.msg.warning(response.msg);
              this.confirmLoading = false;
            }
          );
        } else {
          //编辑
          let parameter = this.$utils.merger(this.formParameter);
          api.update(
            parameter,
            (response) => {
              this.$utils.msg.success(response.msg);
              this.confirmLoading = false;
              this.close();
              this.$parent.list();
            },
            (response) => {
              this.$utils.msg.warning(response.msg);
              this.confirmLoading = false;
            }
          );
        }
      });
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
          id: this.parameter.id,
        };
        api.toUpdate(parameter, (response) => {
          this.formParameter = response.data;
          this.popupLoading = false;
        });
      } else {
        //添加
        this.popupLoading = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>

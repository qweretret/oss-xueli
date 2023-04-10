<template>
  <popup ref="popup" :loading="popupLoading">
    <div slot="body">
      <el-form
        class="dataForm"
        ref="form"
        :disabled="disabled"
        :model="formParameter"
        :inline="true"
        label-width="160px"
      >
        <el-row>
          <el-col :span="24">
            <el-form-item label="提成员工" prop="userid">
              <el-input
                v-model="formParameter.userid"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="提成学生">

              <el-autocomplete
                v-show="parameter.length == 0"
                class="inline-input"
                v-model="stuid"
                :fetch-suggestions="querySearch"
                placeholder="请输入提成学生"
                :trigger-on-focus="false"
                @select="handleSelect"
              ></el-autocomplete>

              <el-tag v-for="(v, i) in parameter" :key="i">
                {{ v.name }}
              </el-tag>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="提成金额"
              ><el-input
                :readonly="true"
                v-model="formParameter.costAll"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="提成次数" prop="type">
              <el-radio v-model="radio" label="1">一次</el-radio>
              <el-radio v-model="radio" label="2">两次</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="支付金额1" prop="cost1"
              ><el-input
                v-model="formParameter.cost1"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="应付时间1" prop="tctm">
              <el-date-picker
                v-model="formParameter.tctm1"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="支付时间1" prop="paydate">
              <el-date-picker
                v-model="formParameter.paydate1"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注1" prop="remark"
              ><el-input
                v-model="formParameter.remark1"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="radio == 2">
          <el-col :span="24">
            <el-form-item label="支付金额2" prop="cost2"
              ><el-input
                v-model="formParameter.cost2"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="radio == 2">
          <el-col :span="12">
            <el-form-item label="应付时间2" prop="tctm">
              <el-date-picker
                v-model="formParameter.tctm2"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="支付时间2" prop="paydate">
              <el-date-picker
                v-model="formParameter.paydate2"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="radio == 2">
          <el-col :span="24">
            <el-form-item label="备注2" prop="remark"
              ><el-input
                v-model="formParameter.remark2"
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
        :loading="confirmLoading"
        @click="confirm()"
        >添 加</el-button
      >
      <el-button @click="close">取 消</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/xueli/ticheng.js";
import apiStu from "@/api/xueli/xlstudent.js";
export default {
  name: "add",
  components: {
    popup,
  },
  watch: {
    "formParameter.cost1": {
      handler() {
        if (this.formParameter.cost1) {
          this.formParameter.costAll =
            this.formParameter.cost1 * 1 +
            (this.formParameter.cost2 ? this.formParameter.cost2 * 1 : 0);
        }
      },
    },
    "formParameter.cost2": {
      handler() {
        // console.log(this.formParameter)
        if (this.formParameter.cost2) {
          this.formParameter.costAll =
            (this.formParameter.cost1 ? this.formParameter.cost1 * 1 : 0) +
            this.formParameter.cost2 * 1;
        }
      },
    },
  },
  data() {
    return {
      disabled: false,
      radio: "1",
      popupLoading: false,
      confirmLoading: false,
      parameter: [],
      formParameter: {},
      stuid: "",
      stuObj :false,
      results:[]
    };
  },
  methods: {
      handleSelect(item) {
         this.stuObj = item
      }, querySearch(queryString, cb) {

         apiStu.autoCSnm( {snm:queryString},res=>{
           console.log(res)
             this.results = res.data;
             let rslts = queryString ?  this.results.filter(this.createFilter(queryString)) : this.results;
             // 调用 callback 返回建议列表的数据
            
             cb(rslts);
         });
      },
      createFilter(queryString) {
        return (snm) => {
            console.log(snm)
          return (snm.value.indexOf(queryString) === 0);
        };
      },
    //打开弹框
    open(parameter, title, disabled, size) {
      this.parameter = parameter;
      console.log(parameter);
      this.disabled = disabled;
      this.$refs.popup.open(title, "40%");
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
      //this.confirmLoading = true;
     this.formParameter.stuids = [];
     if(this.stuObj){
         this.formParameter.stuids.push(this.stuObj.id)
     }else{
         this.parameter.forEach((v) => this.formParameter.stuids.push(v.id));
     }
     
      //删除属性
      delete this.formParameter.costAll;

      //编辑
      let parameter     = this.$utils.merger(this.formParameter);

      console.log(parameter);
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

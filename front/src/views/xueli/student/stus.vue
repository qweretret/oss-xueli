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
      >
        <el-row>
          <el-col :span="24">
            <el-table :data="formParameter" style="width: 100%">
              <el-table-column prop="name" label="学生姓名" width="90">
              </el-table-column>
              <el-table-column prop="moblie" label="手机" width="110">
              </el-table-column>
              <el-table-column prop="idno" label="身份证" width="160"> 
              </el-table-column>
               <el-table-column prop="school" label="报考学学校">
                 </el-table-column>
                <el-table-column prop="level" label="等级" width="110"  >
                 </el-table-column>
            
            </el-table>

          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button type="success" @click="upld">确认上传</el-button>
      <el-button @click="close">关 闭</el-button>{{msg}}
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/xueli/xlstudent.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";

export default {
  name: "stus",
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
      msg:""
    };
  }, 
  methods: {
    //打开弹框
    open(parameter, title, disabled) {
      this.formParameter = parameter;
      this.msg = "本次解析成功"+ this.formParameter.length  +"条"
      this.disabled = disabled;
      this.$refs.popup.open(title, "65%");
    },
    //关闭弹框  ：释放资源和重置初始值
    close() {
      this.disabled = false;
      this.formParameter=[]
      this.$refs.popup.close();

    },
    upld(){

      this.$utils.confirm.warning('提示', '确定导入学生信息吗？', () => {

         //发起请求
         api.impDatas(    this.formParameter  ,res=>{
             //console.log(res)

             this.$alert(  res.msg, "导入操作",{  confirmButtonText: "确定"  });
             // 刷新父页面
             //this.$parent.list();
             this.$emit("flush");
             //关闭自己
             this.$refs.popup.close();

         },fail=>{
           this.$utils.msg.exception(fail.msg);
         })
			});
     }
    }
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>

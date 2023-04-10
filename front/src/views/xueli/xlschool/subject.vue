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
          <el-col :span="8">
            <el-form-item label="学校名称" prop="skname"
              ><el-input
                :readonly="true"
                v-model="formParameter.skname"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="层次" prop="level">
              <el-input
                :readonly="true"
                v-model="formParameter.level"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="招生学年" prop="zsyear"
              ><el-input
                :readonly="true"
                v-model="formParameter.zsyear"
                autocomplete="off"
              ></el-input
            ></el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-button   v-has-permission-code-and="'003000000005'"   @click="toEditSubs">{{toEditTxt}}</el-button>
            <el-button  v-has-permission-code-and="'003000000005'"  @click="toAddSubs" type="warning" v-show="!notEdit">添加</el-button>
            <el-button  v-has-permission-code-and="'003000000005'"  type="success"      v-show="!notEdit" @click="addSubs">完成</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">

            <el-table :data="subjects" style="width: 100%">
              <el-table-column prop="nm" label="专业名称" width="230">
                	<template slot-scope="scope">
					           <el-input size="mini"
                        :readonly="notEdit"
                        v-model="scope.row.nm"
                        autocomplete="off">
                      </el-input>
				          </template>
              </el-table-column>
              <el-table-column prop="cd" label="专业代码" width="90">
                	<template slot-scope="scope">
					           <el-input size="mini"     :readonly="notEdit"
                        v-model="scope.row.cd"
                        autocomplete="off">
                      </el-input>
				          </template>
              </el-table-column>
              <el-table-column prop="tp" label="科目类型" width="90"> 
                	<template slot-scope="scope">
					           <el-input size="mini"     :readonly="notEdit"
                        v-model="scope.row.tp"
                        autocomplete="off">
                      </el-input>
				          </template>
              </el-table-column>
               <el-table-column prop="cs" label="价格">
                 	<template slot-scope="scope">
					           <el-input size="mini"     :readonly="notEdit"
                        v-model="scope.row.cs"
                        autocomplete="off">
                      </el-input>
				          </template> </el-table-column>
                <el-table-column prop="ks" label="考试方式" width="180"  >
                  	<template slot-scope="scope">
					           <el-input size="mini"     :readonly="notEdit"
                        v-model="scope.row.ks"
                        autocomplete="off">
                      </el-input>
				          </template> </el-table-column>
                 <el-table-column  label="操作" width="80" v-if="!notEdit"  >
                  	<template slot-scope="scope">
					            <el-button  size="mini" @click="delSub(scope.$index)">删除</el-button>
				          </template> </el-table-column>

            </el-table>

          </el-col>
        </el-row>
      </el-form>
    </div>
    <div slot="footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </popup>
</template>

<script>
import popup from "@/components/popup/drawerPopup.vue";
import api from "@/api/xueli/xlschool.js";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";

export default {
  name: "subject",
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
      subjects:[],
      toEditTxt:"维护专业",
      notEdit:true
    };
  },
  methods: {
    //打开弹框
    open(parameter, title, disabled) {
      this.formParameter = parameter;
      this.subjects =  this.formParameter.subjects
   
      this.notEdit= true
      this.disabled = disabled;
      this.$refs.popup.open(title, "75%");
    },
    //关闭弹框  ：释放资源和重置初始值
    close() {
      this.disabled = false;
      this.toEditTxt = "维护专业",
      this.$refs.popup.close();
    }, toEditSubs() {
        if( this.toEditTxt == "取消维护"){
           this.toEditTxt = "维护专业"
        }else{
            this.toEditTxt = "取消维护"
        }
       this.notEdit = !this.notEdit
    },
    toAddSubs() {
       //去添加
       this.subjects.push({nm:"",cd:"",tp:"",cs:"",ks:""})
    },delSub(idx){
      //删除
      this.subjects.splice(idx,1)
    },addSubs(){

      this.$utils.confirm.warning('提示', '确定提交修改专业吗？', () => {
			   //完成添加：格式化this.subjects为json，发到后端
         let prarm = {id:this.formParameter.id,subjects:JSON.stringify(this.subjects)}
         //发起请求
         api.updateSubs( prarm,res=>{
            this.$utils.msg.success(res.msg);
            this.notEdit = true
            this.toEditTxt = "维护专业"
            // 刷新父页面
            this.$parent.list();
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

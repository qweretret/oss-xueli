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
            <el-form-item label="推送时间" prop="crtm">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.crtm"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="类型" prop="type">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.type"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="推送人" prop="dbColumn_crer">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.dbColumn_crer"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="报读院校" prop="school">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.school"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="届" prop="grade">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.grade"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="签收情况" prop="cntqs">
              {{ formParameter.cntqs }}/{{ formParameter.allcnt }}
            </el-form-item>
          </el-col>
        </el-row>
          <el-row>
          <el-col :span="24">
            <el-form-item label="推送消息" prop="message">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.message"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">

           <el-radio-group @change="changeSwitch" v-model="radio">
              <el-radio-button label="未签收"></el-radio-button>
              <el-radio-button label="已签收"></el-radio-button>
             <el-radio-button label="全部"></el-radio-button>
           </el-radio-group>
     
          </el-col>

          <el-col :span="24">
            <el-table
              class="table"
              :data="showDatas"
              stripe
              border
              v-loading="listLoading"
            >
              <el-table-column width="55" label="序号">
                <template slot-scope="scope">
                  {{ scope.$index + 1 }}
                </template>
              </el-table-column>
              <el-table-column
                prop="stunm"
                width="100"
                label="接收人"
              ></el-table-column>
               <el-table-column
                prop="mobile"
                width="130"
                label="电话"
              ></el-table-column>

              <el-table-column
                prop="msgtm"
                width="200"
                label="签收时间"
              ></el-table-column>

              <el-table-column
                prop="school"
                width="200"
                label="学校"
              ></el-table-column>

              <el-table-column
                prop="grade"
                width="95"
                label="届"
              ></el-table-column>

              <el-table-column prop="subject" label="专业"></el-table-column>
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
import api from "@/api/xueli/xlstudent.js";
import selectUserTableDialog from "@/components/biz/selectUserTableDialog/selectUserTableDialog.vue";
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";

export default {
  name: "genj",
  components: {
    popup,
    selectOptionDictionary,
    selectUserTableDialog,
  },
  data() {
    return {
      disabled: false,
      popupLoading: false,
      listLoading: false,
      confirmLoading: false,

      listRecords: [],
      showDatas: [],
      formParameter: {},

      radio: "未签收",
    };
  },
  methods: {
    changeSwitch() {
      if (this.radio == "未签收") {
         //console.log("未签收");
         this.showDatas = this.listRecords.filter(v=>{
            if(!v.msgtm){
              return v;
            }
         })
      } else if (this.radio == "已签收") {
          this.showDatas = this.listRecords.filter(v=>{
            if(v.msgtm){
              return v;
            }
         })
      } else {
          this.showDatas = this.listRecords
      }
    },

    //打开弹框
    open(prarm, title, disabled, size) {
      this.popupLoading = true;
      //清除等待
      this.confirmLoading = false;

      this.formParameter = prarm.dto;
      console.log(this.formParameter)
      this.listRecords = prarm.listData;

       this.showDatas = this.listRecords.filter(v=>{
            if(!v.msgtm){
              return v;
            }
         })

      this.disabled = disabled;
      this.$refs.popup.open(title, "50%");

      this.popupLoading = false;
    },
    //关闭弹框
    close() {
      this.disabled = false;
      this.listRecords = [];
      this.showDatas = [];
      this.formParameter = {};
      this.radio =  "未签收"
      this.$refs.popup.close();
    }
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>

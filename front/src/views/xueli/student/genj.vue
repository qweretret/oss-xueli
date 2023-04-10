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
            <el-form-item label="姓名" prop="name">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.name"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机" prop="moblie">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.moblie"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="报读届" prop="bdyear">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.bdyear"
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
            <el-form-item label="层次" prop="level">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.level"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="专业" prop="subject">
              <el-input
                :readonly="true"
                size="mini"
                v-model="formParameter.subject"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-collapse>
          <el-collapse-item title="更多.." name="1">
            <el-row>
              <el-col :span="8">
                <el-form-item label="民族" prop="mz">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.mz"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="身份证号" prop="idno">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.idno"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别" prop="sex">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.sex"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="专业代码" prop="school">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.school"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="类别" prop="type">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.type"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="户籍地" prop="fjaera">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.fjaera"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="毕业学校" prop="byschool">
                    <el-input
                      :readonly="true"
                      size="mini"
                      v-model="formParameter.byschool"
                      autocomplete="off"
                    >
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="毕业专业" prop="bysub">
                    <el-input
                      :readonly="true"
                      size="mini"
                      v-model="formParameter.bysub"
                      autocomplete="off"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="毕业时间" prop="bytm">
                    <el-input
                      :readonly="true"
                      size="mini"
                      v-model="formParameter.bytm"
                      autocomplete="off"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-col :span="8">
                <el-form-item label="毕业证号" prop="byzid">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.byzid"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="成考加分" prop="plus">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.plus"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="考前学历" prop="kqgrd">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.kqgrd"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="报名情况" prop="bminfo">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.bminfo"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="网报号" prop="wbno">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.wbno"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="密码" prop="wbpwd">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.wbpwd"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="报名号" prop="bmno">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.bmno"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="考试区域" prop="ksarea">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.ksarea"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="备选考区" prop="ksarea2">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.ksarea2"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="快递地址" prop="postaera">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.postaera"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="24">
                <el-form-item label="备注" prop="remarks">
                  <el-input
                    :readonly="true"
                    size="mini"
                    v-model="formParameter.remarks"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-collapse-item>
        </el-collapse>

        <el-row>
          <el-col :span="12">
            <el-button @click="addGenjPlan" type="warning" size="mini"
              >添加跟进计划</el-button
            >
            <el-button @click="addGenj1" type="success" size="mini"
              >添加跟进记录</el-button
            >
            <el-switch
              v-model="isGj"
              @change="changeSwitch"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="已跟进"
              inactive-text="待跟进"
            >
            </el-switch>
          </el-col>

          <el-col :span="6">
            <el-button
              v-show="curtIdx > 0"
              @click="navi(-1)"
              type="primary"
              size="mini"
            >
              《《
            </el-button>
            <el-tag>{{ curtIdx + 1 + "/" + this.listAllDatas.length }}</el-tag>
            <el-button
              v-show="curtIdx < listAllDatas.length - 1"
              @click="navi(1)"
              type="primary"
              size="mini"
              >》》
            </el-button>
          </el-col>
          <el-col :span="24">
            <el-table
              class="table"
              :data="listRecords"
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
                v-if="!isGj"
                prop="gnneedtm"
                width="130"
                label="计划跟进时间"
              ></el-table-column>

              <el-table-column
                v-if="isGj"
                prop="gntm"
                width="110"
                label="跟进时间"
              ></el-table-column>

              <el-table-column
                prop="username"
                width="95"
                label="跟进人"
              ></el-table-column>

              <el-table-column
                prop="type"
                width="95"
                label="类型"
              ></el-table-column>

              <el-table-column
                v-if="isGj"
                prop="content"
                label="内容"
              ></el-table-column>
              <el-table-column
                v-if="!isGj"
                prop="content2"
                label="跟进说明"
              ></el-table-column>

              <el-table-column label="操作" fixed="right" width="260px">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="danger"
                    @click="removeRcd(scope.$index, scope.row)"
                    >删除</el-button
                  >
                  <el-button
                    v-if="!isGj"
                    size="mini"
                    type="danger"
                    @click="addGenj(scope.row)"
                    >跟进</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-form>

      <el-dialog
        title="添加跟进计划"
        :visible.sync="dialogVisible1"
        width="30%"
        :before-close="handleClose1"
        :modal="false"
      >
        <el-form
          class="dataForm"
          ref="form01"
          :model="formRecord"
          :inline="true"
          label-width="110px"
          :rules="rules01"
        >
          <el-row>
            <el-col :span="24">
              <el-form-item label="计划跟进时间" prop="gnneedtm">
                <el-date-picker
                  v-model="formRecord.gnneedtm"
                  type="date"
                  placeholder="选择日期"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24">
              <el-form-item label="跟进类型" prop="type">
                <select-option-dictionary
                  v-model="formRecord.type"
                  dkey="xli-gjtype"
                  :multiple="false"
                  :stringMode="true"
                ></select-option-dictionary>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="选择跟进人" prop="username">
                <select-user-table-dialog v-model="formRecord.username">
                </select-user-table-dialog>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="跟进说明" prop="content2">
                <el-input
                  type="textarea"
                  v-model="formRecord.content2"
                  autocomplete="off"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel(1)">取 消</el-button>
          <el-button type="primary" @click="addGenjPlan2((isGj = false), 1)"
            >确 定</el-button
          >
        </span>
      </el-dialog>

      <el-dialog
        title="添加跟进记录"
        :visible.sync="dialogVisible2"
        width="30%"
        :before-close="handleClose2"
        :modal="false"
      >
        <el-form
          class="dataForm"
          ref="form02"
          :model="formRecord"
          :inline="true"
          label-width="100px"
          :rules="rules02"
        >
          <el-row>
            <el-col :span="24">
              <el-form-item label="跟进类型" prop="type">
                <select-option-dictionary
                  v-model="formRecord.type"
                  dkey="xli-gjtype"
                  :multiple="false"
                  :stringMode="true"
                ></select-option-dictionary>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="跟进说明" prop="content">
                <el-input
                  type="textarea"
                  v-model="formRecord.content"
                  autocomplete="off"
                  :rows="2"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="跟进结果" prop="result">
                <select-option-dictionary
                  v-model="formRecord.result"
                  dkey="xl-gjresult"
                  :multiple="false"	
                  :stringMode="true"
                ></select-option-dictionary>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="选择跟进人" prop="username">
                <select-user-table-dialog v-model="formRecord.username">
                </select-user-table-dialog>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel(2)">取 消</el-button>
          <el-button type="primary" @click="addGenjPlan2((isGj = true), 2)"
            >确 定</el-button
          >
        </span>
      </el-dialog>
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

      pageMsg: "",
      isGj: true,

      listAllDatas: [],
      curtIdx: 0,

      formParameter: {},
      dialogVisible1: false,
      dialogVisible2: false,
      formRecord: {},
      record: false,
      records: {},
      rules01: {
        type: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        gnneedtm: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        content: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        username: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
      },
      rules02: {
        type: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        content: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        result: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
        username: [
          {
            validator: this.$validate.required,
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    changeSwitch() {
      if (this.isGj) {
        this.lsRecords(1);
      } else {
        this.lsRecords(2);
      }
    },
    navi(type) {
      if (type == -1) {
        //判断有效性
        if (this.curtIdx > 0) {
          this.curtIdx--;
        }
      } else {
        //判断有效性
        if (this.curtIdx < this.listAllDatas.length - 1) {
          this.curtIdx++;
        }
      }
      this.formParameter = this.listAllDatas[this.curtIdx];
      this.lsRecords(1);
    },
    //打开弹框
    open(prarm, title, disabled, size) {
      this.popupLoading = true;
      //清除等待
      this.confirmLoading = false;
      //所有数据
      this.listAllDatas = prarm.listData;
      this.curtIdx = prarm.idx;

      this.formParameter = this.listAllDatas[this.curtIdx];

      this.popupLoading = false;
      this.disabled = disabled;
      this.$refs.popup.open(title, "50%");
      // 加载原来的跟进记录
      this.$nextTick(() => {
        this.lsRecords(1);
      });
    },

    addGenjPlan() {
      this.dialogVisible1 = true;
      this.formRecord = {};
      this.record = false;
    },
    // 添加跟进记录
    addGenj1() {
      this.dialogVisible2 = true;
      this.formRecord = {};
      this.record = false;
    },
    addGenj(row) {
      if (row) {
        this.formRecord = row;
      }
      this.dialogVisible2 = true;
      this.record = true;
    },
    removeRcd(idx, row) {
      console.log(row);
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.removeRcd(
          { ids: [row.id] },
          (res) => {
            this.$utils.msg.success(res.msg);
            this.listRecords.splice(idx, 1);
          },
          (err) => {}
        );
      });
    },
    // 添加跟进计划
    addGenjPlan2(isGj, i) {
      this.isGj = isGj;
      //合并表单
      if (i == 1) {
        var validate = this.$refs.form01;
      } else if (i == 2) {
        var validate = this.$refs.form02;
      }
      console.log(this.formParameter.id);

      this.$utils.checkForm(validate, () => {
        let parameter = this.$utils.merger(this.formRecord, {
          stuid: this.formParameter.id,
        });

        api.addRcd(parameter, (res) => {
          this.$utils.msg.success(res.msg);

          this.changeSwitch();
          //关闭弹框
          this.dialogVisible1 = false;
          this.dialogVisible2 = false;
          // 重置
          this.formRecord = {};
        });
      });
    },

    lsRecords(type) {
      this.listLoading = true;
      // 刷新数据
      api.listRcds(
        { stuid: this.formParameter.id, type: type },
        (res) => {
          this.listRecords = [];
          this.listRecords = res.data;
          this.listLoading = false;
          console.log(this.listRecords);
        },
        (res) => {
          this.$utils.msg.exception(res.msg);
        }
      );
    },

    //关闭弹框
    close() {
      this.disabled = false;
      this.listRecords = [];
      this.formParameter = {};
      this.$refs.popup.close();
    },
    handleClose1(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
          this.cancel(1);
        })
        .catch((_) => {});
    },
    handleClose2(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
          this.cancel(2);
        })
        .catch((_) => {});
    },
    cancel(formid) {
      if (formid == 1) {
        this.$nextTick(() => {
          this.$refs["form01"].resetFields(); //重置校验
        });
        this.dialogVisible1 = false;
      } else if (formid == 2) {
        this.$nextTick(() => {
          this.$refs["form02"].resetFields(); //重置校验
        });
        this.dialogVisible2 = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
@import "~common/custom/css/popup/popup.scss";
</style>

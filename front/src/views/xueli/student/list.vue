<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="80px">
        <el-row>
          <el-col :span="3">
            <el-form-item label="状态">
              <el-select @change="search" v-model="searchParams.condi" placeholder="请选择">
                <el-option v-for="item in ['全部', '待跟进']" :key="item" :label="item" :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="3"  v-show="searchParams.condi=='全部'">
            <el-form-item label="姓名/电话">
              <el-input v-model="searchParams.name"></el-input>
            </el-form-item>
          </el-col>
        <el-col :span="3"  >
            <el-form-item label="入学年份">
              <el-select v-model="searchParams.bdyear" placeholder="请选择">
                <el-option v-for="item in allYear" :key="item" :label="item" :value="item">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="3" >
            <el-form-item label="学校名称">
              <el-input v-model="searchParams.school"></el-input>
            </el-form-item>
          </el-col>
         <el-col :span="3">
            <el-form-item label="专业">
              <el-input v-model="searchParams.subject"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="3"  v-show="searchParams.condi=='全部'">
            <el-form-item label="招生员" v-if="emps">
              <el-select v-model="searchParams.empid" @change="search" placeholder="请选择">
                <el-option v-for="item in emps" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        
          <el-col :span="3"  v-show="searchParams.condi=='全部'">
            <el-form-item label="考试区域">
              <el-input v-model="searchParams.ksarea"></el-input>
            </el-form-item>
          </el-col>
          <div class="search-btn"   v-show="searchParams.condi=='全部'">
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <el-button @click="clearSearchVal">清空</el-button>
          </div>
        </el-row>
      </el-form>
    </div>
    <div class="operator">
      <el-button type="primary"  v-has-permission-code-and="'003000050005'"  size="mini" @click="toSave()">添加</el-button>
      <el-button type="warning"  v-has-permission-code-and="'003000050005'"  size="mini" @click="toImport()">导入</el-button>
      <el-button type="danger"   v-has-permission-code-and="'003000050015'"  size="mini" @click="batchRemove()">批量删除</el-button>
      <el-button type="primary"  v-has-permission-code-and="'003000050000'"  size="mini" @click="batchAddGenj()">跟进计划</el-button>
      <el-button type="primary"  v-has-permission-code-and="'003000050010'" size="mini" @click="batchAddTicheng()">添加提成</el-button>
    </div>
    <div class="dataTable autoFlex" v-if="listData">
      <el-table class="table" :data="listData.records" stripe border height="auto" @selection-change="selectionChange"
        v-loading="listLoading">
        <el-table-column type="selection" width="55" fixed="left"></el-table-column>
        <el-table-column prop="name" width="80" label="姓名"></el-table-column>
        <el-table-column prop="idno" width="110" label="个人信息">
          <template slot-scope="scope">
            <el-tag size="mini">{{ calBrith(scope.row.idno) }}</el-tag>
            <el-tag type="danger" size="mini">{{ scope.row.sex }}</el-tag>
            <el-tag size="mini">{{ scope.row.moblie }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="bdyear" width="75" label="报读届"></el-table-column>
        <el-table-column width="160" prop="school" label="报读信息">
          <template slot-scope="scope">
            <el-tag size="mini">{{ scope.row.school }}</el-tag>
            <el-tag type="warning" size="mini">{{ scope.row.level }}</el-tag>
            <el-tag type="danger" size="mini">{{ scope.row.subject }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="65" prop="fjaera" label="户籍地"></el-table-column>
        <el-table-column width="65" prop="postaera" label="现地址">
          <template slot-scope="scope">
            {{ calAdrs(scope.row.postaera) }}
          </template>
        </el-table-column>
        <el-table-column prop="kqgrd" width="95" label="考前学历">
          <template slot-scope="scope">
            <el-tag size="mini">{{ calAdrs(scope.row.kqgrd) }}</el-tag>
            <el-tag size="mini" type="warning">{{
              calAdrs(scope.row.bytm)
            }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="bminfo" label="报名情况"></el-table-column>
        <el-table-column prop="wbpwd" label="网报密码"></el-table-column>
        <el-table-column prop="ksarea" label="考试区域"></el-table-column>

        <el-table-column label="操作" fixed="right" width="260px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button   v-has-permission-code-and="'003000050000'"  size="mini" @click="genjin(scope.$index)">跟进/详情</el-button>
              <el-button   v-has-permission-code-and="'003000050005'" size="mini" @click="toUpdate(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger"  v-has-permission-code-and="'003000050015'"
                @click="remove(scope.$index, scope.row)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBar">
        <el-pagination @size-change="sizeChange" @current-change="currentChange" :total="listData.total"
          :page-size="page.size" :current-page="page.current" :layout="this.$constant.page.layout"
          :page-sizes="this.$constant.page.pageSizes"></el-pagination>
      </div>
    </div>
    <el-dialog title="上传学生信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <excel-upload drag v-model="excel" model="fileSystem" uri="/statics/excels" :size="upsize"
        v-on:change="uploadSuccess">
      </excel-upload>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

      <el-dialog title="添加跟进计划" :visible.sync="dialogVisible1" width="30%" :before-close="handleClose2" :modal="false">
        <el-form class="dataForm" ref="form01" :model="formRecord" :inline="true" label-width="110px" :rules="rules01">

          <el-row>
            <el-col :span="24">
                  <el-tag size="mini" v-for="(itm,idx) in this.selected" :key="idx">{{itm.name}}</el-tag>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="选择跟进人" prop="userid">
                <select-user-table-dialog v-model="formRecord.userid">
                </select-user-table-dialog>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="计划跟进时间" prop="gnneedtm">
                <el-date-picker value-format="yyyy-MM-dd" v-model="formRecord.gnneedtm" type="date" placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="跟进类型" prop="type">
                <select-option-dictionary v-model="formRecord.type" dkey="xli-gjtype" :multiple="false"
                  :stringMode="true"></select-option-dictionary>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="跟进说明" prop="content">
                <el-input type="textarea" v-model="formRecord.content" autocomplete="off" :rows="2"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
         <el-row>
            <el-col :span="24">
              <el-form-item label="推送消息" prop="message">
                <el-input type="textarea" v-model="formRecord.message" autocomplete="off" :rows="2"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="addGenjPlan2()">确 定</el-button>
        </span>
      </el-dialog>

    <edit ref="edit"></edit>
    <stus @flush="list()" ref="stus"></stus>
    <genj @flush="list()" ref="genj"></genj>
    <add  @flush="list()" ref="add"></add>

  </div>
</template>
<script>

import edit from "./edit.vue";
import stus from "./stus.vue";
import genj from "./genj.vue";
import add from "@/views/xueli/ticheng/add.vue";
import api from "@/api/xueli/xlstudent.js";
import apiTc from "@/api/xueli/ticheng.js";
import excelUpload from "@/components/biz/fileUpload/excelUpload.vue";
import selectUserTableDialog from '@/components/biz/selectUserTableDialog/selectUserTableDialog.vue';
import selectOptionDictionary from "@/components/biz/selectOptionDictionary/selectOptionDictionary.vue";

export default {
  name: "xlstudent",
  components: {
    edit,
    excelUpload,
    stus,
    add,
    genj,
    selectUserTableDialog,
    selectOptionDictionary
  },
  data() {
    return {
      searchParams: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      page: new this.$constant.pageObj(),
      dialogVisible: false,
      excel: "",
      allYear: [],
      upsize: "2048",
      emps: false,
      dialogVisible1: false,
      formRecord: {},
      formParameter:{},
      records: {},
      rules01: {
        type: [{
          validator: this.$validate.required,
          trigger: 'blur'
        }],
        gnneedtm: [{
          validator: this.$validate.required,
          trigger: 'blur'
        }],
        content2: [{
          validator: this.$validate.required,
          trigger: 'blur'
        }],
        userid: [{
          validator: this.$validate.required,
          trigger: 'blur',
        }],
      }
    };
  },
  methods: {
    selectCondi(){
      this.list();
    },
    batchAddTicheng(){
     if (!this.selected || this.selected.length == 0) {
        this.$utils.msg.info("请选择至少一行");
        return;
      }
 
        // 1.取得id,查询是否可以添加？
        let idArr = []
        this.selected.forEach(v=>{idArr.push(v.id)})
       

       apiTc.listTcStus( {ids:idArr},res=>{
          console.log(res)
            if( res.data.length>0  ){
              let nms =""
              res.data.forEach( v=>{nms+=(v.dbColumn_stuid+",")  })
           
              this.$utils.confirm.warning("错误", nms+"已经发过提成.", () => {});
            }else{
               // 2. 弹框 ，配置提成
              this.$refs.add.open(this.selected, "添加提成配置");

            }
          console.log(res)
        })

        // 3. 提交
 
    },
    //获取数据
    list() {
      this.listLoading = true;
      let params = this.$utils.merger(this.searchParams, this.page);

      if(this.searchParams.bdyear=='全部'){
       delete params.bdyear
      }

      api.list(params, (response) => {

        this.listData = response.data.list
        this.allYear = response.data.allYear
        this.allYear.splice(0, 0, "全部")
        console.log(response.data,"11");
        if (response.data.emps) {
          this.emps = response.data.emps
        }

        this.listLoading = false;
      });
    },
    genjin(idx) {
      //跟进学生   学习   报名  报名确认  考试   缴费  其他
      this.$refs.genj.open(
        { idx: idx, listData: this.listData.records },
        "跟进学历学生:"
      );
    },
    calBrith(idno) {
      if (idno && idno.length == 18) {
        //"440021199903030011"
        return idno.substr(6, 8);
      } else {
        return "";
      }
    },
    calAdrs(adr) {
      if (adr && adr.length > 0) {
        let shi = adr.indexOf("市");
        if (shi != -1) {
          return adr.substr(0, shi + 1);
        } else if (adr.length > 6) {
          return adr.substr(0, 6);
        } else {
          return adr;
        }
      } else {
        return "";
      }
    },
    toImport() {
      //去导入
      this.dialogVisible = true;
    },
    handleClose() { },
    uploadSuccess(res) {
      //上传成功的回调
      if (res && res.length > 32) {
        this.$utils.msg.success("上传成功");
        this.dialogVisible = false;
        //调用解析入库的控制器
        //http://127.0.0.1:8086/ossjkwin/statics/excels/562dbde2-9828-43fc-978f-c84f85c46ca0-录入信息.xlsx
        let fileName = res.substr(res.lastIndexOf("/") + 1);

        api.toUpload({ fname: fileName }, (res2) => {
          console.log(res2);
          //打开确认显示框，确认后保存数据库 ，回显失败数据
          this.$refs.stus.open(res2.data, "确认上传数据");
        });
      } else {
        this.$alert("上传失败", "请联系管理员。", {
          confirmButtonText: "确定",
        });
      }
    },
    //去添加
    toSave() {
      this.$refs.edit.open({}, "添加");
    },
    //去编辑
    toUpdate(index, row) {
      let editParameter = {
        id: row.id,
      };
      this.$refs.edit.open(editParameter, "编辑");
    },
    //去详情
    toDetail(index, row) {
      let detailParameter = {
        id: row.id,
      };
      this.$refs.edit.open(detailParameter, "详情", true);
    },
    //删除
    remove(index, row) {
      let parameter = {
        ids: row.id,
      };
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.remove(
          parameter,
          (response) => {
            this.$utils.msg.success(response.msg);
            this.list();
          },
          (response) => {
            this.$utils.msg.warning(response.msg);
          }
        );
      });
    },
    //批量删除
    batchRemove() {
      if (!this.selected || this.selected.length == 0) {
        this.$utils.msg.info("请选择至少一行");
        return;
      }
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        // 取得id
        let idArr = []
        this.selected.forEach(v=>{idArr.push(v.id)})
        api.remove(
          {
            ids: idArr,
          },
          (response) => {
            this.$utils.msg.success(response.msg);
            this.list();
          },
          (response) => {
            this.$utils.msg.warning(response.msg);
          }
        );
      });
    },
    addGenjPlan2() {
      // 被跟进人
      let idArr = []
      this.selected.forEach(v=>{idArr.push(v.id)})
      // 封装表单
      var parameter = this.$utils.merger(this.formRecord,{
        stuids: idArr
      })
      console.log(parameter);
      // this.records = parameter;
      // parameter.records = JSON.stringify(this.records);
      var validate = this.$refs.form01;
      this.$utils.checkForm(validate, () => {
        api.addAllRcd(parameter,(res)=>{
        this.$utils.msg.success(res.msg)
        this.dialogVisible1 = false
      })
      })
    },
    handleClose2(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => { });
    },
    cancel() {
      this.dialogVisible1 = false;
        this.$nextTick(() => {
          this.$refs['form01'].clearValidate();  //清除校验
        })
    },
    // 打开批量添加计划页面
    batchAddGenj(){
      if(!this.selected || this.selected.length == 0){
        this.$utils.msg.info('请选择至少一行');
        return
      }else{
        // 弹框
        this.dialogVisible1 = true
      }
    },
    //改变选择项
    selectionChange(val) {
      //清空
      this.selected = [];
      for (let item of val) {
        this.selected.push(item);
      }
    },
    //改变每页显示数量
    sizeChange(val) {
      this.page.size = val;
      this.list();
    },
    //改变现在的页码
    currentChange(val) {
      this.page.current = val;
      this.list();
    },
    //搜索
    search() {
      this.list();
    },
    //清空搜索框
    clearSearchVal() {
      this.searchParams = {condi:'全部'};
      this.list();
    },
  },
  mounted() {
    this.list();
    this.searchParams.condi='全部'
  },
};
</script>
<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
</style>


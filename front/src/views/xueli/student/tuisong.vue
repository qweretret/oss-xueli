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
       
          <div class="search-btn"   v-show="searchParams.condi=='全部'">
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <el-button @click="clearSearchVal">清空</el-button>
          </div>
        </el-row>
      </el-form>
    </div>
    <div class="operator"></div>
    <div class="dataTable autoFlex" v-if="listData">
      <el-table class="table" :data="listData.records" stripe border height="auto" @selection-change="selectionChange"
        v-loading="listLoading">
        <el-table-column type="selection" width="55" fixed="left"></el-table-column>
        <el-table-column prop="crtm" width="120" label="跟进时间"></el-table-column>
        <el-table-column prop="type" width="110" label="跟进类型">
        </el-table-column>
        <el-table-column prop="bdyear" width="120" label="签收/总数">
          <template slot-scope="scope">
            <el-tag type="success" size="mini">{{ scope.row.cntqs }}</el-tag>/
            <el-tag type="danger" size="mini">{{  scope.row.allcnt }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="160" prop="grade" label="年级">
        </el-table-column>
        <el-table-column width="200" prop="school" label="学校"></el-table-column>
        <el-table-column   prop="dbColumn_crer" label="发送人">
        </el-table-column>
     
        <el-table-column label="操作" fixed="right" width="260px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button size="mini" @click="checkin(scope.row)">签收情况</el-button>
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

    <tuis ref="tuis"></tuis>

  </div>
</template>

<script>

import api from "@/api/xueli/xlsturecord.js";
import tuis from "./lstuisong.vue";

export default {
  name: "xlstudent",
  components: {
     tuis
  },
  data() {
    return {
      searchParams: {},
      listLoading: false,
      listData: [],
      page: new this.$constant.pageObj(),

      formParameter:{},
    };
  },
  methods: {
    checkin(dto){
   

     //发请求 取数据  回显
      api.lsStuByMsg(dto, (res) => {
         console.log( res )
         this.$refs.tuis.open(  { dto , listData: res.data }, "推送消息的学生:" );
       
      });


    },
    selectCondi(){
      this.list();
    },
    //获取数据
    list() {
      this.listLoading = true;
      
      let params = this.$utils.merger(this.searchParams, this.page);
      api.tuisong(params, (response) => {
        console.log(response)
        this.listData = response.data
        this.listLoading = false;
      });
    },

    handleClose() { },

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
      this.list();
    },
  },
  mounted() {
    this.list();

  },
};
</script>
<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";
</style>


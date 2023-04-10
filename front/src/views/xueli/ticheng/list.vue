<template>
  <div class="tab-body">
    <div class="search">
      <el-form ref="form" :model="searchParams" label-width="60px">
        <el-row>
          <el-col :span="3">
            <el-switch
              v-has-permission-code-and="'003500200010'"
              change="clearSearchVal"
              v-model="swc"
              active-text="按学生"
              inactive-text="按员工"
            >
            </el-switch>
          </el-col>
          <el-col
            :span="4"
            v-has-permission-code-and="'003500200010'"
            v-show="swc"
          >
            <el-form-item label="姓名">
              <el-input v-model="searchParams.stu"></el-input>
            </el-form-item>
          </el-col>
          <el-col
            :span="3"
            v-has-permission-code-and="'003500200010'"
            v-show="!swc"
          >
            <el-form-item label="姓名">
              <el-input v-model="searchParams.emp"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="3" v-show="!swc">
            <el-form-item label="">
              <el-select
                v-model="searchParams.dpt"
                @change="search(3)"
                placeholder="选择部门"
              >
                <el-option
                  v-for="(item,v) in dpts"
                  :key="v"
                  :label="item.name"
                  :value="item.name"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" class="box" v-show="!swc">
            <div class="box2">
              <el-button
                type="success"
                size="mini"
                @click="search(-11)"
                v-if="navi && navi.prev"
              >
                《《
              </el-button>
              <el-date-picker
                v-model="month"
                type="month"
                placeholder="选择月"
                value-format="yyyy-MM"
                @change="search(1)"
              >
              </el-date-picker>

              <el-button
                type="success"
                size="mini"
                @click="search(11)"
                v-if="navi && navi.next"
                >》》</el-button
              >
            </div>
          </el-col>
          <div class="search-btn">
            <el-button
              v-has-permission-code-and="'003500200010'"
              type="primary"
              @click="search"
              >搜索</el-button
            >
            <el-button @click="clearSearchVal">清空</el-button>
          </div>
        </el-row>
      </el-form>
    </div>
    <div class="operator">
      <el-button
        type="primary"
        v-has-permission-code-and="'003500200000'"
        icon="el-icon-plus"
        @click="toSave()"
        >添加</el-button
      >
      <el-button
        type="danger"
        v-has-permission-code-and="'003500200015'"
        icon="el-icon-delete"
        @click="batchRemove()"
        >批量删除</el-button
      >
      <el-button v-has-permission-code-and="'003500200010'" @click="toPay"
        >发放</el-button
      >
      {{ countStr }}
    </div>
    <div class="dataTable autoFlex" v-if="listData">
      <el-table
        class="table"
        :data="listData.records"
        stripe
        border
        height="auto"
        @selection-change="selectionChange"
        v-loading="listLoading"
      >
        <el-table-column
          type="selection"
          width="55"
          fixed="left"
        ></el-table-column>
        <el-table-column
          width="170"
          prop="crtm"
          label="绩效生成"
        ></el-table-column>
        <el-table-column
          width="95"
          prop="userid"
          label="提成员工"
        ></el-table-column>
        <el-table-column
          width="95"
          prop="dbColumn_stuid"
          label="业绩学生"
        ></el-table-column>
        <el-table-column width="95" prop="type" label="类型">
          <template slot-scope="scope">
            <el-tag type="danger" size="mini" v-if="scope.row.type == 1"
              >学历</el-tag
            >
            <el-tag type="warning" size="mini" v-if="scope.row.type == 2"
              >培训</el-tag
            >
            <el-tag type="success" size="mini" v-if="scope.row.type == 3"
              >项目</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column
          width="110"
          prop="tctm"
          label="应付时间"
        ></el-table-column>
        <el-table-column
          width="110"
          prop="paydate"
          label="支付时间"
        ></el-table-column>
        <el-table-column width="75" prop="cost" label="金额"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>
        <el-table-column label="操作" fixed="right" width="200px">
          <template slot-scope="scope">
            <div class="dataTable-operator">
              <el-button
                size="mini"
                v-has-permission-code-and="'003500200005'"
                @click="toUpdate(scope.$index, scope.row)"
                >更新</el-button
              >
              <el-button
                size="mini"
                v-has-permission-code-and="'003500200015'"
                v-if="!scope.row.paydate"
                type="danger"
                @click="remove(scope.$index, scope.row)"
                >删除</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBar">
        <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          :total="listData.total"
          :page-size="page.size"
          :current-page="page.current"
          :layout="this.$constant.page.layout"
          :page-sizes="this.$constant.page.pageSizes"
        ></el-pagination>
      </div>
    </div>
    <edit ref="edit"></edit>
    <add ref="add"></add>
    <el-dialog
      title="批量支付"
      :visible.sync="dialogVisible"
      width="25%"
      :before-close="handleClose"
      :modal="false"
    >
      <span
        >{{ countStr }}:<br /><br />
        <el-date-picker
          v-model="paydate"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
        ></el-date-picker> </span
      ><br />
      <span slot="footer" class="dialog-footer">
        <el-button @click="pay">发 放</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import edit from "./edit.vue";
import add from "./add.vue";
import api from "@/api/xueli/ticheng.js";
export default {
  name: "ticheng",
  components: {
    edit,
    add,
  },
  data() {
    return {
      searchParams: {},
      isShowMoreSearch: false,
      listLoading: false,
      listData: [],
      selected: [],
      selected2: 0,
      selectNull: 0,
      countStr: "",
      paydate: "",
      page: new this.$constant.pageObj(),
      dialogVisible: false,
      msg: "",
      swc: false,
      month: "",
      navi: {},
      dpts: undefined,
    };
  },
  methods: {
    handleClose() {},
    toPay(index, row) {
      if (this.selected.length < 1) {
        this.$utils.msg.warning("请选择操作的行!");
        return;
      }

      if (this.selected2 > 0) {
        this.$utils.msg.warning("所选行:存在已发放!");
        return;
      }

      if (this.selectNull > 0) {
        this.$utils.msg.warning("所选行:存在未定义金额!");
        return;
      }
      this.dialogVisible = true;
    },
    pay() {
      this.$utils.confirm.warning("提示", "确定发放吗?", (res) => {
        api.fafa({ ids: this.selected, paytm: this.paydate }, (response) => {
          this.$utils.msg.success(response.msg);
          this.dialogVisible = false;
          this.list();
        });
      });
    },
    //获取数据
    list() {
      this.listLoading = true;
      let params = this.$utils.merger(this.searchParams, this.page);
      api.list(params, (response) => {
        console.log(response);
        this.dpts=response.data.dpts;
        this.listData = response.data.list;


        this.listLoading = false;
      });
    },
    //去添加
    toSave() {
      this.$refs.add.open([], "手动添加提成，注意传id不是名称");
    },
    //去编辑
    toUpdate(index, row) {
      let editParameter = {
        id: row.id,
      };
      this.$refs.edit.open(editParameter, "编辑");
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

      if (this.selected2 > 0) {
        this.$utils.msg.info("选择的行存在已支付");
        return;
      }
      let parameter = {
        ids: this.selected,
      };
      this.$utils.confirm.warning("提示", "确定删除吗？", () => {
        api.remove(
          {
            ids: this.selected,
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
    //改变选择项
    selectionChange(val) {
      //清空
      this.selected = [];
      this.selected2 = 0;
      this.selectNull = 0;
      let sumVal = 0;

      for (let item of val) {
        this.selected.push(item.id);
        this.selected2 += item.paydate ? 1 : 0;
        if (item.cost) {
          sumVal += item.cost;
        } else {
          this.selectNull += 1;
        }
      }

      if (this.selected.length > 0) {
        this.countStr = this.selected.length + " 项,需支付: " + sumVal + " 元";
      } else {
        this.countStr = "";
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
    search(type) {
      if (type == -11) {
        //上1月
        if (this.searchParams.dpt) {
          this.searchParams = {
            dpt: this.searchParams.dpt,
            month: this.navi.prev,
          };
        } else if (this.searchParams.emp) {
          this.searchParams = {
            emp: this.searchParams.emp,
            month: this.navi.prev,
          };
        } else {
          this.searchParams = { month: this.navi.prev };
        }
      } else if (type == 11) {
        //下1月
        if (this.searchParams.dpt) {
          this.searchParams = {
            dpt: this.searchParams.dpt,
            month: this.navi.next,
          };
        } else if (this.searchParams.emp) {
          this.searchParams = {
            emp: this.searchParams.emp,
            month: this.navi.next,
          };
        } else {
          this.searchParams = { month: this.navi.next };
        }
      } else if (type == 1) {
        // 时间选择器
        if (this.searchParams.dpt) {
          this.searchParams = { dpt: this.searchParams.dpt, month: this.month };
        } else if (this.searchParams.emp) {
          this.searchParams = { emp: this.searchParams.emp, month: this.month };
        } else {
          this.searchParams = { month: this.month };
        }
      } else if (type == 3) {
        if (this.month) {
          this.searchParams = { dpt: this.searchParams.dpt, month: this.month };
        } else {
          this.searchParams = { dpt: this.searchParams.dpt };
        }
      } else {
        if (this.searchParams.stu) {
          this.searchParams = { stu: this.searchParams.stu };
        } else if (this.searchParams.emp) {
          if (this.month) {
            this.searchParams = {
              emp: this.searchParams.emp,
              month: this.month,
            };
          } else {
            this.searchParams = { emp: this.searchParams.emp };
          }
        }
      }
      console.log(this.searchParams, "searchParams");
      this.list();
    },
    //清空搜索框
    clearSearchVal() {
      this.searchParams = {};
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
.box {
  display: flex;
}
.box2 {
  display: flex;
  margin: 0px 10px;
}
</style>
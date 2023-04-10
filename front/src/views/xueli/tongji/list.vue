<template>
    <div v-loading="listLoading">
        <!-- 搜索框  -->
        <el-form label-width="130px">
				<el-row>
					<el-col :span="9">
						<el-form-item label="学生姓名">
							<el-input placeholder="请输入学生姓名" v-model="searchParams.name"></el-input>
						</el-form-item>
					</el-col>
                    <el-col :span="9">
						<el-form-item label="跟进人姓名">
							<el-input  placeholder="请输入跟进人姓名"  v-model="searchParams.username"></el-input>
						</el-form-item>
					</el-col>
					<div>
						<el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
						<el-button @click="clearSearchVal">清空</el-button>
					</div>
				</el-row>
			</el-form>

        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="招生统计" name="first" id="Viewport">
                招生统计
            </el-tab-pane>

            <el-tab-pane label="待跟进统计" name="second" id="Viewport">
                <el-table
                    :data="searchDataNoGenji.length != 0 ? searchDataNoGenji.slice((currentPage - 1) * pageSize, currentPage * pageSize) : nogenjiData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
                    style="width: 100%" :default-sort="{ prop: 'gnneedtm', order: 'descending' }">
                    <el-table-column type="index" :index="indexMethod" label="序号" sortable width="80">
                    </el-table-column>
                    <el-table-column prop="name" label="学生" width="180">
                    </el-table-column>
                    <el-table-column prop="gnneedtm" label="计划跟进时间" sortable width="180">
                    </el-table-column>
                    <el-table-column label="跟进时间" width="180">
                        无
                    </el-table-column>
                    <el-table-column prop="username" label="跟进人姓名" width="180">
                    </el-table-column>
                    <el-table-column prop="content2" label="跟进说明">
                    </el-table-column>
                </el-table>
                <div class="block" style="margin-top:15px;">
                    <el-pagination align='center' @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page="currentPage" :page-sizes="[1, 5, 10, 20]" :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="searchDataNoGenji.length != 0 ? searchDataNoGenji.length : nogenjiData.length">
                    </el-pagination>
                </div>
            </el-tab-pane>

            <el-tab-pane label="已跟进统计" name="third" id="Viewport">
                <el-table
                    :data="searchDataGenji.length != 0 ? searchDataGenji.slice((currentPage - 1) * pageSize, currentPage * pageSize) : genjiData.slice((currentPage - 1) * pageSize, currentPage * pageSize)"
                    style="width: 100%" :default-sort="{ prop: 'gnneedtm', order: 'descending' }">
                    <el-table-column type="index" :index="indexMethod" label="序号" sortable width="90">
                    </el-table-column>
                    <el-table-column prop="name" label="学生姓名" width="180">
                    </el-table-column>
                    <el-table-column prop="gnneedtm" label="计划跟进时间" sortable width="180">
                    </el-table-column>
                    <el-table-column prop="gntm" label="跟进时间" width="180">
                    </el-table-column>
                    <el-table-column prop="content2" label="跟进说明" width="180">
                    </el-table-column>
                    <el-table-column prop="content" label="跟进内容" width="180">
                    </el-table-column>
                    <el-table-column prop="username" label="跟进人" width="180">
                    </el-table-column>
                    <el-table-column prop="crtm" label="跟进创建时间" width="180">
                    </el-table-column>
                    <el-table-column prop="mdtm" label="近期跟进时间" width="180">
                    </el-table-column>
                </el-table>
                <div class="block" style="margin-top:15px;">
                    <el-pagination align='center' @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :current-page="currentPage" :page-sizes="[1, 5, 10, 20]" :page-size="pageSize"
                        current-page.sync="cur_page" layout="total, sizes, prev, pager, next, jumper"
                        :total="searchDataGenji.length != 0 ? searchDataGenji.length : genjiData.length">
                    </el-pagination>
                </div>
            </el-tab-pane>
        </el-tabs>

    </div>
</template>

<script>
import api from "@/api/xueli/xlstudent.js";
export default {
    data() {
        return {
            listLoading: true,
            page: { size: 1000000, current: 1 },
            searchParams: {},
            listData: [],
            listData2: [],
            nogenjiData: [],
            genjiData: [],
            activeName: 'second',
            currentPage: 1,         // 当前页码
            total: 20,              // 总条数
            pageSize: 5,            // 每页的数据条数

            searchName: '',         // 搜索关键字，学生名字
            searchDataNoGenji: [],  // 搜索的数据，未跟进计划
            searchDataGenji: [],    // 搜索的数据，已经跟进了
        }
    },
    methods: {
        // 表格切换
        handleClick(tab, event) {
            this.currentPage = 1
        },
        //每页条数改变时触发 选择一页显示多少行
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.currentPage = 1;
            this.pageSize = val;
        },
        //当前页改变时触发 跳转其他页
        handleCurrentChange(val) {
            // console.log(`当前页: ${val}`);
            this.currentPage = val;
        },
        list() {
            this.listLoading = true;
            let params = this.$utils.merger(this.searchParams, this.page);
           console.log(this.searchParams);

            api.listAllRcds(params, (response) => {
                this.listData = response.data.list.records;
                this.listLoading = false;
                this.nogenjiData = this.listData.filter(item => {
                    return item.gntm == null;
                })
                this.genjiData = this.listData.filter(item => {
                    return item.gntm != null;
                })
            });
        },
        formatter() {

        },
        //清空搜索框
        clearSearchVal(){
            this.searchParams={};
             this.list();
            
        },
        indexMethod(index) {
            index = this.pageSize * (this.currentPage - 1) + index + 1;
            return index;
        },
        search() {
                api.souSuo((this.searchParams),(rew)=>{
                this.listData2 = rew.data;

                this.listData2.forEach((item)=>{
                    if(item.name == this.searchParams.name){
                      this.searchDataNoGenji = this.listData2;
                    }else if(item.username== this.searchParams.username){
                        this.searchDataGenji = this.listData2;
                    }else{
                      
                    }
                })
                
            });
          
            
           /* this.searchDataNoGenji = this.nogenjiData.filter(item => {
                console.log(this.searchDataNoGenji,"this.searchDataNoGenji");
              
                if(item.name){
                    return item.name == this.searchParams.name;
                }
                     return item.username  == this.searchParams.username;
                
            })
            this.searchDataGenji = this.genjiData.filter(item => {
                
                if(item.name){
                    return item.name == this.searchParams.name;
                }
                     return item.username== this.searchParams.username;
                
            })

            if (this.searchDataNoGenji.length == 0 && this.searchDataGenji.length == 0) {
                this.$utils.msg.warning('学生信息不存在，请重新输入', 2000);
            }*/
        },

    },
    mounted() {
        this.list();
    },
    watch: {
        searchName(val) {
            this.searchDataNoGenji = this.nogenjiData.filter(item => {
                return item.name.indexOf(val) != -1;
            })

            this.searchDataGenji = this.genjiData.filter(item => {
                return item.name.indexOf(val) != -1;
            })
        }
    }

}
</script>

<style lang="scss" scoped="scoped">
@import "~common/custom/css/common.scss";

.search {
    width: 100%;
    display: flex;
}
</style>
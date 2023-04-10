<template>
	<div class="tab-body">
		<div class="search">
			<el-form ref="form" :model="searchParams" label-width="80px">
				<el-row>
					<el-col :span="6">
						<el-form-item label="姓名">
							<el-input v-model="searchParams.name"></el-input>
						</el-form-item>
					</el-col>
 
					<div class="search-btn">
						<el-button type="primary"  @click="search">搜索</el-button>
						<el-button   @click="clearSearchVal">清空</el-button>
					</div>
				</el-row>
			</el-form>
		</div>
		<div class="operator">
            <el-button type="primary"  v-has-permission-code-and="'003000000005'" size="mini" @click="toSave()">添加</el-button>
            <el-button type="danger"   v-has-permission-code-and="'003000000000'"  size="mini" @click="batchRemove()">批量删除</el-button>
        </div>
        <div class="dataTable autoFlex" v-if="listData">
			<el-table class="table" :data="listData.records" stripe border height="auto" @selection-change="selectionChange" v-loading="listLoading">
						<el-table-column type="selection" width="55" fixed="left"></el-table-column>
						<el-table-column prop="zsyear"  width="80" label="招生学年"></el-table-column>
						<el-table-column prop="skname"  width="180" label="学校名称"></el-table-column>
						<el-table-column prop="level"  width="100" label="层次"></el-table-column>
						<el-table-column prop="starttm" width="100"  label="开始时间"></el-table-column>
						<el-table-column prop="endtm"  width="100" label="结束时间"></el-table-column>
						<el-table-column prop="code"  width="80" label="学校代码"></el-table-column>
						<el-table-column prop="cnt"  width="80" label="专业数量">
							     <template slot-scope="scope">
								   {{scope.row.subjects.length}}  
								</template>
						</el-table-column>
						<el-table-column prop="subjects" label="专业">
							   <template slot-scope="scope">
								   <el-tag size="mini" v-for="(o,i) in scope.row.subjects" :key="i">{{o.nm}}</el-tag>   
								</template>
							</el-table-column>					
				<el-table-column label="操作" fixed="right" width="300px">
					<template slot-scope="scope">
						<div class="dataTable-operator">
							<el-button   size="mini"  v-has-permission-code-and="'00300000'"  @click="toDetail(scope.$index, scope.row)">详情</el-button>
						    <el-button   size="mini"  v-has-permission-code-and="'003000000005'"   @click="toUpdate(scope.$index, scope.row)">编辑</el-button>
							 <el-button   size="mini" v-has-permission-code-and="'00300000'" @click="toSubject( scope.row )">专业</el-button>
                            <el-button   size="mini"  v-has-permission-code-and="'003000000000'"   type="danger" @click="remove(scope.$index, scope.row)">删除</el-button>
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
				:page-sizes="this.$constant.page.pageSizes"></el-pagination>
			</div>
		</div>
		<edit ref="edit"></edit>
	    <subject ref="subject"></subject>
	</div>
</template>

<script>
import subject from './subject.vue';
import edit from    './edit.vue';
import api  from    '@/api/xueli/xlschool.js';
import caches from 'util/caches.js';

export default {
	name: 'xlschool',
	components: {
		edit,
		subject
	},
	//页面数据
	data() {
		return {
			searchParams: {},
			isShowMoreSearch: false,
			listLoading: false,
			listData: [],
			selected: [],
			page: new this.$constant.pageObj(),
		};
	},
	//页面操作
	methods: {
		//获取数据
		list() {
			this.listLoading = true;
			let params = this.$utils.merger(this.searchParams, this.page);
			api.list(params, response => {
				//通过vue的双向绑定渲染数据
				this.listData = response.data;
                this.listData.records.forEach(rcd => {
				    if( rcd.subjects ){
                        rcd.subjects = JSON.parse(rcd.subjects)
                    }
			    });
			
			   
				
				this.listLoading = false;
			})
		},
		//去添加
		toSave() {
			this.$refs.edit.open({}, '添加学历高校');
		},
		//去编辑
		toUpdate(index, row) {
			let editParameter = {
				id: row.id
			};
			this.$refs.edit.open(editParameter, '编辑');
		},	//去配置专业
		toSubject( row) {
			this.$refs.subject.open(row, '配置 '+row.skname+" 专业");
		},
		//去详情
		toDetail(index, row) {
			let detailParameter = {
				id: row.id
			};
			this.$refs.edit.open(detailParameter, '详情', true);
		},
		//删除
		remove(index, row) {
			let parameter = {
				ids: row.id
			}
			this.$utils.confirm.warning('提示', '确定删除吗？', () => {
				api.remove(
					parameter,
					response => {
						this.$utils.msg.success(response.msg);
						this.list();
					},
					response => {
						this.$utils.msg.warning(response.msg);
					}
				);
			});
		},
		//批量删除
		batchRemove() {
			if (!this.selected || this.selected.length == 0) {
				this.$utils.msg.info('请选择至少一行');
				return;
			}
			let parameter = {
				ids: this.selected
			};
			this.$utils.confirm.warning('提示', '确定删除吗？', () => {
				api.remove({
						ids: this.selected
					},
					response => {
						this.$utils.msg.success(response.msg);
						this.list();
					},
					response => {
						this.$utils.msg.warning(response.msg);
					}
				);
			});
		},
		//改变选择项
		selectionChange(val) {
			//清空
			this.selected = [];
			for (let item of val) {
				this.selected.push(item.id);
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
			this.searchParams = {};
		}
	},
	// vue【生命周期】：装载完毕
	mounted() {
		this.list();
		console.log(caches.permissionCode)
	}
};

</script>

<style lang="scss" scoped="scoped">
	@import '~common/custom/css/common.scss';
</style>


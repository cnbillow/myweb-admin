<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-menu"></i>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>属性配置管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="handle-box">
            <el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>
            <el-select style="vertical-align: bottom;" v-model="select_cate" placeholder="筛选省份" class="handle-select mr10">
                <el-option key="1" label="广东省" value="广东省"></el-option>
                <el-option key="2" label="湖南省" value="湖南省"></el-option>
            </el-select>
            <el-input style="vertical-align: bottom;" v-model="select_word" placeholder="筛选关键词" class="handle-input mr10"></el-input>
            <el-button type="primary" icon="search" @click="search">搜索</el-button>
        </div>
        <el-table class="Ttable" v-loading.fullscreen.lock="loading" :data="tableData" border style="width: 100%" ref="multipleTable" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50"></el-table-column>
            <el-table-column prop="propKey" label="属性键值" width="180"></el-table-column>
            <el-table-column prop="propValue" label="属性参数长值" width="200"></el-table-column>
            <el-table-column prop="remark" label="备注" ></el-table-column>
            <el-table-column prop="updateTime" label="修改时间" sortable width="300"></el-table-column>
            <el-table-column label="操作" width="200">
                <template scope="scope">
                	<el-button size="small" @click="handleLook(scope.$index, scope.row)">查看</el-button>
                    <el-button size="small"  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <!--<el-pagination
                    @current-change ="handleCurrentChange"
                    layout="prev, pager, next"
                    :total="1000">
            </el-pagination>-->
            <el-pagination
		      @size-change="handleSizeChange"
		      @current-change="handleCurrentChange"
		      :current-page="currentPage"
		      :page-sizes="[10, 30, 50, 80]"
		      :page-size="10"
		      layout="total, sizes, prev, pager, next, jumper"
		      :total="15">
		    </el-pagination>
        </div>
    </div>
</template>

<script>
	import $ from 'jquery'
	var api = require('../../api/api.js')
	//allnum = null;
    export default {
        data() {
            return {
                url: './static/vuetable.json',
                tableData: [],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                currentPage: 1,
                loading: true,
                allnum: ''
                
            }
        },
        created(){
        	var _this = this;
            _this.getData();
			
//			this.$http.get('/api/'+api.propertiess).then(function(data){
//			   console.log(data)
//			})
        },
        computed: {
            data(){
                const self = this;
//              return self.tableData.filter(function(d){
//                  let is_del = false;
//                  for (let i = 0; i < self.del_list.length; i++) {
//                      if(d.name === self.del_list[i].name){
//                          is_del = true;
//                          break;
//                      }
//                  }
//                  if(!is_del){
//                      if(d.address.indexOf(self.select_cate) > -1 && 
//                          (d.name.indexOf(self.select_word) > -1 ||
//                          d.address.indexOf(self.select_word) > -1)
//                      ){
//                          return d;
//                      }
//                  }
//              })
            }
        },
        methods: {
        	handleSizeChange(val) {
		        console.log(`每页 ${val} 条`);
		      },
            handleCurrentChange(val){
                this.tableData=[]
//              $('.Ttable').empty()
                this.cur_page = val;
                this.getData(val);
            },
            getData(page){
                let self = this;
//              if(process.env.NODE_ENV === 'development'){
//                  self.url = '/ms/table/list';
//              };
//              self.$axios.post(self.url, {page:self.cur_page}).then((res) => {
//                  self.tableData = res.data.list;
//              })
                $.ajax({
				type:"get",
				url:api.propertiess,
				dataType:'json',
				data:{
					page:page,
					limit:10
				},
				async:true,
				success:function(data){
					if(data.code == '0'){
						self.allnum = data.count;
						data = data.data;
						console.log(data);
						$.each(data, function(k,v) {
							console.log(k)
							console.log(v)
							self.tableData.push(v)
						})
						console.log(self.tableData)
						console.log(self.allnum)
						
					}
					
				},
				error:function(error){
					console.log(error);
				}
			});
            },
            search(){
                this.is_search = true;
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index, row) {
                this.$message('编辑第'+(index+1)+'行');
            },
            handleDelete(index, row) {
                this.$message.error('删除第'+(index+1)+'行');
            },
            delAll(){
                const self = this,
                    length = self.multipleSelection.length;
                let str = '';
                self.del_list = self.del_list.concat(self.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += self.multipleSelection[i].name + ' ';
                }
                self.$message.error('删除了'+str);
                self.multipleSelection = [];
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            }
        },
        watch: {
        	tableData:function(){
        		this.$nextTick(function(){
        			this.loading = false;
        		})
        	}
        	
        }
    }
</script>

<style>
.handle-box{
    margin-bottom: 20px;
}
.handle-select{
    width: 120px;
}
.handle-input{
    width: 300px;
    display: inline-block;
}
.el-table .cell, .el-table th>div{
	padding-right: 0px !important;
	padding-left: 10px !important;
}
</style>
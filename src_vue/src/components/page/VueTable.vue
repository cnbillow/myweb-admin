<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-menu"></i>系统管理</el-breadcrumb-item>
                <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="plugins-tips" style="text-align: center;">
            <!--vue-datasource：一个用于动态创建表格的vue.js服务端组件。
            访问地址：<a href="https://github.com/coderdiaz/vue-datasource" target="_blank">vue-datasource</a>-->
            <div style="margin: 0 auto;">
	             <el-select v-model="value4" clearable placeholder="请选择">
				    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>			   
				 </el-select>
				 <el-select v-model="value4" clearable placeholder="请选择">
				    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>			   
				 </el-select>
				 <el-select v-model="value4" clearable placeholder="请选择">
				    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>			   
				 </el-select>
			 </div>
			 <br />
			 <div>
			 	<el-input style="width: 660px; margin-top: 10px;"  placeholder="请输入菜单名称，菜单url，菜单权限编码" v-model="input10" clearable></el-input> <el-button type="primary" icon="search">搜索</el-button>
			 </div>
			 <div style="text-align: left;">
			 	 <el-button type="primary"><i class="el-icon-plus"></i>新增</el-button>
			 </div>
        </div>
        <!--<datasource language="en" :table-data="getData" :columns="columns" :pagination="information.pagination"
                :actions="actions"
                v-on:change="changePage"
                v-on:searching="onSearch"></datasource>-->
                <el-table v-loading.fullscreen.lock="loading" :data="tableData" style="width: 100%">
				    <el-table-column prop="date" label="日期" sortable width="180"></el-table-column>
				    <el-table-column prop="name" label="姓名" width="180"></el-table-column>
				    <el-table-column prop="address" label="地址" :formatter="formatter"></el-table-column>
				    <el-table-column prop="code" label="权限编码" width="100"></el-table-column>
				    <el-table-column prop="tag" label="菜单类型" width="110" :filters="[{ text: '家', value: '家' }, { text: '公司', value: '公司' }]" :filter-method="filterTag" filter-placement="bottom-end">
				      <template slot-scope="scope">
				        <el-tag :type="scope.row.tag === '家' ? 'primary' : 'success'" close-transition>{{scope.row.tag}}</el-tag>
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
		      :total="150">
		    </el-pagination>
        </div>
                
    </div>
</template>

<script>
    import axios from 'axios';
    import Datasource from 'vue-datasource';
    export default {
        data: function(){
            const self = this;
            return {
                url: './static/datasource.json',
                information: {
                    pagination:{},
                    data:[]
                },
                columns: [
                    {
                        name: 'Id',
                        key: 'id',
                    },
                    {
                        name: 'Name',
                        key: 'name',
                    },
                    {
                        name: 'email',
                        key: 'email',
                    },
                    {
                        name: 'ip',
                        key: 'ip',
                    }
                ],
                actions: [
                    {
                        text: 'Click',
                        class: 'btn-primary',
                        event(e, row) {
                            self.$message('选中的行数： ' + row.row.id);
                        }
                    }
                ],
                query:'',
                options: [{
		          value: '选项1',
		          label: '黄金糕'
		        }, {
		          value: '选项2',
		          label: '双皮奶'
		        }, {
		          value: '选项3',
		          label: '蚵仔煎'
		        }, {
		          value: '选项4',
		          label: '龙须面'
		        }, {
		          value: '选项5',
		          label: '北京烤鸭'
		        }],
		        value4: '',
		         input10: '',
		         tableData: [{
			          date: '2016-05-02',
			          name: '王小虎',
			          address: '上海市普陀区金沙江路 1518 弄',
			          code:'#',
			          tag: '家'
			        }, {
			          date: '2016-05-04',
			          name: '王小虎',
			          address: '上海市普陀区金沙江路 1517 弄',
			          code:'#',
			          tag: '公司'
			        }, {
			          date: '2016-05-01',
			          name: '王小虎',
			          address: '上海市普陀区金沙江路 1519 弄',
			          code:'#',
			          tag: '家'
			        }, {
			          date: '2016-05-03',
			          name: '王小虎',
			          address: '上海市普陀区金沙江路 1516 弄',
			          code:'#',
			          tag: '公司'
			        }],
			        currentPage: 1,
			        cur_page: 1,
			        loading:true
            }
        },
        created(){
        	setTimeout(() => {
	          this.loading = false;
	        }, 500);
        },
        components: {
            Datasource
        },
        methods: {
        	handleSizeChange(val) {
		        console.log(`每页 ${val} 条`);
		      },
            handleCurrentChange(val){
                this.cur_page = val;
                //this.getData();
            },
            changePage(values) {
                this.information.pagination.per_page = values.perpage;
                this.information.data = this.information.data;
            },
            onSearch(searchQuery) {
                this.query = searchQuery;
            },
            formatter(row, column) {
		        return row.address;
		      },
		      filterTag(value, row) {
		        return row.tag === value;
		      }
        },
        computed:{
            getData(){
                const self = this;
                return self.information.data.filter(function (d) {
                    if(d.name.indexOf(self.query) > -1){
                        return d;
                    }
                })
            }
        },
        beforeMount(){
            if(process.env.NODE_ENV === 'development'){
                this.url = '/ms/table/source';
            };
            axios.get(this.url).then( (res) => {
                this.information = res.data;
            })
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

<style src="../../../static/css/datasource.css"></style>
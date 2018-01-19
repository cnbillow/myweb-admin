<template>
    <div class="sidebar">
        <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" theme="dark" unique-opened router>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index">
                        <template slot="title"><i :class="item.icon"></i>{{ item.title }}</template>
                        <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index"><i :class="subItem.icon"></i>{{ subItem.title }}
                        </el-menu-item>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index">
                        <i :class="item.icon"></i>{{ item.title }}
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                items: [
                    {
                        icon: 'el-icon-setting',
                        index: 'readme',
                        title: '管理首页'
                    },
                    {
                        icon: 'el-icon-menu',
                        index: '2',
                        title: '系统管理',
                        subs: [
                            {
                            	icon: 'el-icon-menu',
                                index: '/sysadminMain/properties/index.do',
                                title: '属性配置管理'
                            },
                            {
                            	icon: 'el-icon-menu',
                                index: '/vuetable',
                                title: '菜单管理'
                            },
                            {
                            	icon: 'el-icon-menu',
                                index: 'Roles',
                                title: '角色管理'
                            },
                            {
                            	icon: 'el-icon-menu',
                                index: 'admin',
                                title: '管理员管理'
                            }
                        ]
                    },
                    {
                        icon: 'el-icon-date',
                        index: '3',
                        title: '测试页面',
                        subs: [
                            {
                                index: 'baseform',
                                title: '测试1'
                            }
                        ]
                    }
                ]
            }
        },
        computed:{
            onRoutes(){
                return this.$route.path.replace('/','');
            }
        }
    }
</script>

<style scoped>
    .sidebar{
        display: block;
        position: absolute;
        width: 250px;
        left: 0;
        top: 70px;
        bottom:0;
        background: #2E363F;
    }
    .sidebar > ul {
        height:100%;
    }
</style>

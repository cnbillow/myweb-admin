<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${websiteName}</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="shortcut icon" href="${favicon}" type="image/x-icon" />
	<jsp:include page="/include/css.inc.jsp"/>
</head>
<body>
    <!-- 顶部开始 -->
    <div class="container">
        <div class="logo" title="${websiteName}"><a href="${cpathSysadminMainIndex}">${websiteName}</a></div>
        <div class="left_open">
            <i title="展开左侧栏" class="iconfont">&#xe699;</i>
        </div>
        <ul class="layui-nav layui-bg-blue left fast-add" lay-filter="">
          <li class="layui-nav-item">
            <!--<a href="javascript:;">+新增</a>
            <dl class="layui-nav-child">  二级菜单 
               <dd><a onclick="x_admin_show('管理员','http://www.baidu.com')"><i class="layui-icon">&#xe613;</i>管理员</a></dd>
              <dd><a onclick="x_admin_show('渠道','http://www.baidu.com')"><i class="layui-icon">&#xe62a;</i>渠道</a></dd>
              <dd><a onclick="x_admin_show('产品编码','http://www.baidu.com')"><i class="layui-icon">&#xe857;</i>产品编码</a></dd>
            </dl>-->
          </li>
        </ul>
        <ul class="layui-nav layui-bg-blue layui-nav-top-info right" lay-filter="">
          <li class="layui-nav-item">
            <a href="javascript:;"><b>[${roleName}:${loginName}]</b></a>
            <dl class="layui-nav-child"> <!-- 二级菜单 -->
              <dd><a onclick="x_admin_show('个人信息','${cpath}/sysadminMain/dashboard/userInfo.do')">个人信息</a></dd>
              <dd><a onclick="x_admin_show('修改密码','${cpath}/sysadminMain/dashboard/updatePaw.do')">修改密码</a></dd>
              <dd><a href="javascript:;" onclick="toLogout()"><i class="iconfont">&#xe6b8;</i>退出</a></dd>
            </dl>
          </li>
          <li class="layui-nav-item to-index"><a target="_blank" href="/">前台首页</a></li>
        </ul>
        
    </div>
    <!-- 顶部结束 -->
    <!-- 中部开始 -->
     <!-- 左侧菜单开始 -->
    <div class="left-nav">
      <div id="side-nav">
      	<ul id="nav">
      		<li>
	      		<a href="javascript:;" onclick="goindex(event)">
	      			<i class="iconfont">&#xe761;</i><cite>管理首页</cite>
	      		</a>
      		</li>
      	</ul>
        <ul id="nav" class="navcss">
        	<!-- 
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6b8;</i>
                    <cite>会员管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="member-list.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>会员列表</cite>
                            
                        </a>
                    </li >
                    <li>
                        <a _href="member-del.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>会员删除</cite>
                            
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont">&#xe70b;</i>
                            <cite>会员管理</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="xxx.html">
                                    <i class="iconfont">&#xe603;</i>
                                    <cite>会员列表</cite>
                                    
                                </a>
                            </li >
                            <li>
                                <a _href="xx.html">
                                    <i class="iconfont">&#xe603;</i>
                                    <cite>会员删除</cite>
                                    
                                </a>
                            </li>
                            <li>
                                <a _href="xx.html">
                                    <i class="iconfont">&#xe603;</i>
                                    <cite>等级管理</cite>
                                    
                                </a>
                            </li>
                            
                        </ul>
                    </li>
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe723;</i>
                    <cite>订单管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="order-list.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>订单列表</cite>
                        </a>
                    </li >
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe726;</i>
                    <cite>管理员管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="admin-list.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>管理员列表</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="admin-role.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>角色管理</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="admin-cate.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>权限分类</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="admin-rule.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>权限管理</cite>
                        </a>
                    </li >
                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="iconfont">&#xe6ce;</i>
                    <cite>系统统计</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="echarts1.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>拆线图</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="echarts2.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>柱状图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="echarts3.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>地图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="echarts4.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>饼图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="echarts5.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>雷达图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="echarts6.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>k线图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="echarts7.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>热力图</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="echarts8.html">
                            <i class="iconfont">&#xe603;</i>
                            <cite>仪表图</cite>
                        </a>
                    </li>
                </ul>
            </li>
        	 -->
        </ul>
      </div>
    </div>
    <!-- <div class="x-slide_left"></div> -->
    <!-- 左侧菜单结束 -->
    <!-- 右侧主体开始 -->
    <div class="page-content">
        <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
          <ul class="layui-tab-title">
            <li>我的桌面</li>
          </ul>
          <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='${cpath}/sysadminMain/dashboard/index.do' frameborder="0" scrolling="yes" class="x-iframe"></iframe>
            </div>
          </div>
        </div>
    </div>
    <div class="page-content-bg"></div>
    <!-- 右侧主体结束 -->
    <!-- 中部结束 -->
    <!-- 底部开始 -->
    <div class="footer">
        <div class="copyright">${copyright}</div>  
    </div>
</body>
<jsp:include page="/include/js.inc.jsp"/>
<script type="text/javascript">
//退出
function toLogout(){
	webConfirm("是否确定要退出？", function(){
		webLoading();
		doPostRequest(null,'${cpath}/sysadminLogin/toLogout.do', null, function (backData) {
			webLoading("hide");
			if(backData.success){
				webMsgSuccess("退出成功！",function(){window.location.href="${cpath}/sysadminLogin/login.do";});
			}else{
				webMsgFailed("退出失败！请稍后再试！",function(){});
			}
		});
	});
}

//菜单模版
var tmpl = '<li mpcode="{mpcode}">'+
		    '<a href="{mpUrl}">'+
				'<i class="iconfont">{mpIcon}</i>'+
				'<cite>{mpName}</cite>'+
			'</a>'+
			'</li>';
var tmplChildren = '<li mpcode="{mpcode}">'+
		    '<a href="{mpUrl}">'+
				'<i class="iconfont">{mpIcon}</i>'+
				'<cite>{mpName}</cite>'+
				'<i class="iconfont nav_right">&#xe697;</i>'+
			'</a>'+
			'<ul class="sub-menu">{childrenUl}</ul></li>';
var tmpl2 = '<li mpcode="{mpcode}">'+
		    '<a _href="{mpUrl}">'+
				'<i class="iconfont">{mpIcon}</i>'+
				'<cite>{mpName}</cite>'+
			'</a>'+
			'</li>';
var tmplChildren2 = '<li mpcode="{mpcode}">'+
		    '<a _href="{mpUrl}">'+
				'<i class="iconfont">{mpIcon}</i>'+
				'<cite>{mpName}</cite>'+
				'<i class="iconfont nav_right">&#xe697;</i>'+
			'</a>'+
			'<ul class="sub-menu">{childrenUl}</ul></li>';
//菜单触发事件
var tabcont = {
      tabAdd: function(title,url,id){
        //新增一个Tab项
        element.tabAdd('xbs_tab', {
          title: title 
          ,content: '<iframe id="iframe_'+id+'" tab-id="'+id+'" frameborder="0" src="'+url+'" scrolling="yes" class="x-iframe"></iframe>'
          ,id: id
        })
      }
      ,tabDelete: function(othis){
        //删除指定Tab项
        element.tabDelete('xbs_tab', '44'); //删除：“商品管理”
        
        
        othis.addClass('layui-btn-disabled');
      }
      ,tabChange: function(id){
        //切换到指定Tab项
        element.tabChange('xbs_tab', id); //切换到：用户管理
      }
    };
//渲染菜单    
function showMenu(){
	$(".navcss").html("");
	webLoading();
	doPostRequest(null,'${cpath}/sysadminMain/getSysadminInfo.do', null, function (backData) {
		webLoading("hide");
		if(backData.success){
			if(!isNullObj(backData.result) && !isNullObj(backData.result.mgrRole)
					 && !isNullObj(backData.result.mgrRole.menuPermissions)){
				var topmenus = backData.result.mgrRole.menuPermissions;
				for(var i in topmenus){
					var liAll = "";
					var item = topmenus[i];//顶级菜单
					var children1 = item.children;
					if(!isNullObj(children1)){
						var clis1 = getChildrenLis(children1);
						var children1 = item.children;
						liAll = liAll + replaceItem(tmplChildren,item);
						liAll = liAll.replace("{childrenUl}",clis1);
					}else{
						liAll = replaceItem(tmpl,item);
					}
					if(null != liAll && "" != liAll){
						$(".navcss").append($(liAll));
					}
				}
				$(".navcss").find("li").each(function(){
					var thiss = this;
					if($(thiss).children(".sub-menu").first().children().length <= 0){
						$(thiss).children("a").first().children("cite").next().remove();
					}
					$(thiss).click(function(event){
						$(".navcss").find("a").removeClass("font-blue");
						$(thiss).children("a").addClass("font-blue");
						var cv = $(thiss).attr("class");
						if(cv == "open"){
							 $(thiss).removeAttr("class");
							 if($(thiss).children("a").eq(0).find("i").length>1){
								 $(thiss).children("a").eq(0).find("i").last().html("&#xe697;");
							 }
							 $(thiss).children(".sub-menu").first().stop().slideUp();
						}else{
							 if($(thiss).children("a").eq(0).find("i").length>1){
								 $(thiss).children("a").eq(0).find("i").last().html("&#xe6a6;");
							 }
							 $(thiss).attr("class","open");
							 $(thiss).children(".sub-menu").first().stop().slideDown();
						}
					    event.stopPropagation();//阻止事件冒泡即可
						
						var url = $(thiss).children('a').attr('_href');
					    if(!isNull(url) && "#" != url && "javascript:;" != url){
					    	url = cpath + url;
				            var title = $(thiss).find('cite').html();
				            var index  = $('.left-nav #nav li').index($(this));
	
				            for (var i = 0; i <$('.x-iframe').length; i++) {
				                if($('.x-iframe').eq(i).attr('tab-id')==index+1){
				                	tabcont.tabChange(index+1);
				                	//菜单点击刷新页面
				                	$('.x-iframe').eq(i).attr("src",$('.x-iframe').eq(i).attr("src"));
				                	var iframeid = $('.x-iframe').eq(i).attr("id");
				                	document.getElementById(iframeid).contentWindow.location.reload(true);
				                    event.stopPropagation();
				                    return;
				                }
				            };
				            tabcont.tabAdd(title,url,index+1);
				            tabcont.tabChange(index+1);
					    }
					});
				});
			}
		}else{
			webMsgFailed("菜单加载失败！",function(){});
		}
	});
}
function replaceItem(tmpl,item){
	var out = "";
	if(null != item && item.mpType == "Menu"){
		out = tmpl.replace("{mpcode}",item.mpCode).replace("{mpName}",item.mpName).replace("{mpIcon}",item.mpIcon).replace("{mpUrl}",item.mpUrl);
	}
	return out;
}
//获取第二级菜单
function getChildrenLis(menus){
	var liAll = "";
	if(isNullObj(menus)) return liAll;
	for(var i in menus){
		var item = menus[i];//二级菜单
		var children1 = item.children;
		if(!isNullObj(children1)){
			var clis1 = getChildrenLisLast(children1);
			liAll = liAll + replaceItem(tmplChildren2,item);
			liAll = liAll.replace("{childrenUl}",clis1);
		}else{
			liAll = liAll + replaceItem(tmpl2,item);
		}
	}
	return liAll;
}
//获取第三级菜单
function getChildrenLisLast(menus){
	var lis = "";
	if(isNullObj(menus)) return lis;
	for(var i in menus){
		var itemc = menus[i];//二级菜单
		lis = lis + replaceItem(tmpl2,itemc);
	}
	return lis;
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	layui.use(['layer'], function() {
		//初始化菜单
		showMenu();
	});
});
function clickMpCode(mpcode){
	$("li[mpcode='"+mpcode+"']").click();
}
</script>	
</html>
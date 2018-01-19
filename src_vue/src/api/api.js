;
var path = process.env.NODE_ENV === 'production'?'..':'/api'
//获取管理员信息接口
var api={}
api.getSysadminInfo = path+'/mycom-mgr/sysadminMain/getSysadminInfo.do'
api.propertiess = path+'/mycom-mgr/sysadminMain/properties/interface/propertiess.do'




module.exports = api;
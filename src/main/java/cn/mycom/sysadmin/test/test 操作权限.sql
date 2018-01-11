INSERT INTO base_mgr_core_operator_log_config(oplBusinessName, oplBusinessDesc, oplMethodType, oplInterceptMethod, oplDomainName, oplPKIds, oplFindDomainMethod, oplRecordFields, oplStatus, updateTime, addTime)
  VALUES('tests管理', '新增tests', 'ADD', 'cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.addTests', 'cn.mycom.sysadmin.test.domain.Tests', 'testId', 'cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.findTestsById', 'testId,name,updateTime,addTime', 'Y', '2017-11-01 00:00:00', '2017-11-01 00:00:00');
INSERT INTO base_mgr_core_operator_log_config( oplBusinessName, oplBusinessDesc, oplMethodType, oplInterceptMethod, oplDomainName, oplPKIds, oplFindDomainMethod, oplRecordFields, oplStatus, updateTime, addTime)
  VALUES('tests管理', '更新tests', 'UPDATE', 'cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.updateTests', 'cn.mycom.sysadmin.test.domain.Tests', 'testId', 'cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.findTestsById', 'testId,name,updateTime,addTime', 'Y', '2017-11-01 00:00:00', '2017-11-01 00:00:00');
INSERT INTO base_mgr_core_operator_log_config( oplBusinessName, oplBusinessDesc, oplMethodType, oplInterceptMethod, oplDomainName, oplPKIds, oplFindDomainMethod, oplRecordFields, oplStatus, updateTime, addTime)
  VALUES('tests管理', '删除tests', 'DELETE', 'cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.deleteTests', 'cn.mycom.sysadmin.test.domain.Tests', 'testId', 'cn.mycom.sysadmin.test.mapper.SysAdminTestsDao.findTestsById', 'testId,name,updateTime,addTime', 'Y', '2017-11-01 00:00:00', '2017-11-01 00:00:00');

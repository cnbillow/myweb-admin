package cn.mycom.base.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.mycom.base.domain.SerializableNumber;
import cn.mycom.spring.context.SpringContextHelper;
import cn.mycom.utils.string.StringUtils;

@Repository
public class SerializableNumberDao {
	private static final Logger logger = LoggerFactory.getLogger(SerializableNumberDao.class);
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	protected static final String BASE_NAME_SPACE="SerializableNumberDao";
	
	/**
	 * 获取下一个序列，不存在新创建，并且返回1的序列，数据库为2的序列
	 * @param serialKey
	 * @return
	 */
	public synchronized SerializableNumber getNextSerializableNumber(String serialKey) {
		Date now = new Date();
		Map<String,Object> pm=new HashMap<>();
		pm.put("serialKey", serialKey);
		pm.put("remark", serialKey);
		pm.put("updateTime", now);
		SerializableNumber find = null;
		List<SerializableNumber> numbers = null;
		try{
			numbers =  sqlSessionTemplate.selectList(BASE_NAME_SPACE+".getSerializableNumber",pm);
		}catch(Exception e){
			logger.error("查询getSerializableNumber失败！"+StringUtils.getExceptionInfo(e));
		}
		try{
			if(numbers != null && numbers.size() > 0){
				if(sqlSessionTemplate.update(BASE_NAME_SPACE+".updateNextSerializableNumber",pm) > 0){
					find = numbers.get(0);
				}
			}else{
				int currentNumber = 1;
				find = new SerializableNumber();
				find.setSerialKey(serialKey);
				find.setCurrentNumber(currentNumber);
				find.setUpdateTime(now);
				find.setAddTime(now);
				
				pm.put("addTime", now);
				pm.put("currentNumber", currentNumber+1);
				if(!(sqlSessionTemplate.insert(BASE_NAME_SPACE+".saveSerializableNumber",pm) > 0)){
					find = null;
				}
			}
			return find;
		}catch(Exception e){
			logger.error("updateNextSerializableNumber或者saveSerializableNumber失败！"+StringUtils.getExceptionInfo(e));
		}
		return find;
	}
	/**
	 * 静态方法，获取下一个序列，返回序列编号
	 * @param key
	 * @return
	 */
	public static Integer getSeqByKey(String key) {
		Integer i = null;
		SerializableNumberDao numberDao =SpringContextHelper.getBean(SerializableNumberDao.class);
		if(null != numberDao){
			SerializableNumber number = numberDao.getNextSerializableNumber(key);
			if(null != number){
				i = number.getCurrentNumber();
			}
		}
		return i;
	}
	
}

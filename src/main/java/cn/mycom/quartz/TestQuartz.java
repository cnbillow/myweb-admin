package cn.mycom.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestQuartz定时触发器
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class TestQuartz {

	private Logger logger = LoggerFactory.getLogger(TestQuartz.class);

	public void execute() {
		logger.info("TestQuartz 定时触发器  操作 start ...");
		try {
		} catch (Exception e) {
			logger.error("TestQuartz 定时触发器  操作调度异常！ error : ", e);
		} finally {
			logger.info("TestQuartz 定时触发器  操作 end ...");
		}
	}
}

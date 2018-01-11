package cn.mycom.spring.layui.domain;

import java.io.Serializable;
import java.util.List;

import cn.mycom.utils.http.domain.Page;

/**
 * 描述信息  ajax返回结果的实体类(用于layui分页)
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class CommonLayuiResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5064861583513194534L;
	private String msg;
	private int code;
	private List<T> data;
	private long count;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	public CommonLayuiResult<T> errorResult() {
		CommonLayuiResult<T> result = new CommonLayuiResult<T>();
		result.setCode(0);
		result.setCount(0);
		result.setMsg("查询异常！");
		return result;
	}
	
	public CommonLayuiResult<T> defaultResult() {
		CommonLayuiResult<T> result = new CommonLayuiResult<T>();
		result.setCode(0);
		result.setCount(0);
		result.setMsg("暂无数据！");
		return result;
	}
	public CommonLayuiResult<T> instance(Page<T> page) {
		CommonLayuiResult<T> result = defaultResult();
		if(null != page && null != page.getResults() && page.getResults().size() > 0){
			result.setData(page.getResults());
			result.setCount(page.getTotalRecord());
			result.setMsg("查询数据成功！");
		}
		return result;
	}

}

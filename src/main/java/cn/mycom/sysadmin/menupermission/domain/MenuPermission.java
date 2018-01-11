package cn.mycom.sysadmin.menupermission.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.mycom.annotation.FileTypeAnnitation;
import cn.mycom.constants.Constants;
/**
 * 菜单-权限实体类
 * 
 * @author vinseven
 * @date 2018-01-01
 */
public class MenuPermission implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8241278267359963924L;
	public static final String mpStatus_yes = "Y";   	//启用
	public static final String mpStatus_no = "N";   	//禁用
	
	public static final String isHasPermission_yes = "Y"; 	//需要权限
	public static final String isHasPermission_no = "N";   	//不需要权限
	
	public static final String mpType_menu = "Menu";   				//菜单:Menu
	public static final String mpType_permission = "Permission";	//权限Permission
	@FileTypeAnnitation("Integer")
	private Integer mpId;
	private Integer mpPId;			//父级菜单-权限id，默认0为顶级菜单（父级只能为菜单）
	private String mpPName;		//父级菜单-权限名称
	private String mpName;       	//菜单-权限名称
	private String mpType;       	//菜单-权限类型(菜单:Menu;权限Permission)
	private String mpIcon;   		//菜单-权限图标
	private String mpCode;       	//菜单-权限 字符串编码（程序校验唯一键）
	private String mpUrl;       	//菜单-权限链接，相对路径
	private String isHasPermission;	//菜单-权限是否需要权限（需要Y;不需要N）
	private String mpStatus;   		//菜单-权限状态（启用Y;禁用N）
	private int mpSort;				//排序
	private Date updateTime;   		//修改时间
	private Date addTime;   		//添加时间
	
	//子菜单
	private List<MenuPermission> children = new ArrayList<MenuPermission>();
	
	public Integer getMpId() {
		return mpId;
	}
	public void setMpId(Integer mpId) {
		this.mpId = mpId;
	}
	public Integer getMpPId() {
		return mpPId;
	}
	public void setMpPId(Integer mpPId) {
		this.mpPId = mpPId;
	}
	public String getMpPName() {
		if(this.getMpPId() == Constants.defaultMenuPermissionTopId) {
			mpPName = Constants.defaultMenuPermissionTopName;
		}
		return mpPName;
	}
	public void setMpPName(String mpPName) {
		this.mpPName = mpPName;
	}
	public String getMpName() {
		return mpName;
	}
	public void setMpName(String mpName) {
		this.mpName = mpName;
	}
	public String getMpType() {
		return mpType;
	}
	public void setMpType(String mpType) {
		this.mpType = mpType;
	}
	public String getMpIcon() {
		return mpIcon;
	}
	public void setMpIcon(String mpIcon) {
		this.mpIcon = mpIcon;
	}
	public String getMpCode() {
		return mpCode;
	}
	public void setMpCode(String mpCode) {
		this.mpCode = mpCode;
	}
	public String getMpUrl() {
		return mpUrl;
	}
	public void setMpUrl(String mpUrl) {
		this.mpUrl = mpUrl;
	}
	public String getIsHasPermission() {
		return isHasPermission;
	}
	public void setIsHasPermission(String isHasPermission) {
		this.isHasPermission = isHasPermission;
	}
	public String getMpStatus() {
		return mpStatus;
	}
	public void setMpStatus(String mpStatus) {
		this.mpStatus = mpStatus;
	}
	public int getMpSort() {
		return mpSort;
	}
	public void setMpSort(int mpSort) {
		this.mpSort = mpSort;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public List<MenuPermission> getChildren() {
		if(null != children && children.size() == 0){
			children = null;
		}
		return children;
	}
	public void setChildren(List<MenuPermission> children) {
		this.children = children;
	}
}

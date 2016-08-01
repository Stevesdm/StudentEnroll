package cn.gov.hrss.ln.stuenroll.organization;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_OrganizationController {

	/**
	 * 查询机构信息总数
	 */
	public void searchOrganizationCount();

	/**
	 * 查询机构信息
	 */
	public void searchOrganization();

	/**
	 * 根据ID删除数据
	 */
	public void deleteById();

	/**
	 * 添加新的机构信息
	 */
	public void addOrganization();
	
	/**
	 * 修改机构信息
	 */
	public void modifyOrganization();
}

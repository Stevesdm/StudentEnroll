package cn.gov.hrss.ln.stuenroll.db;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_OrganizationDao {
	/**
	 * 
	 * 查询所有培训机构
	 */

	public List<Record> searchAllOrganization();

	/**
	 * 根据名字查询ID
	 * 
	 * @param organizationName
	 * @return
	 */
	public Long searchOrganizationId(String organizationName);

	/**
	 * 查询机构信息总数
	 * 
	 * @param map
	 * @return
	 */
	public long searchOrganizationCount(HashMap map);

	/**
	 * 查询机构信息
	 * 
	 * @param map
	 * @return
	 */
	public List<Record> searchOrganization(HashMap map, long start, long length);

	/**
	 * 根据ID删除数据
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(Long[] id);

	/**
	 * 添加新的机构信息
	 * 
	 * @param map
	 */
	public void addOrganization(HashMap map);

	/**
	 * 修改机构信息
	 * 
	 * @param map
	 */
	public void modifyOrganization(HashMap map);
}

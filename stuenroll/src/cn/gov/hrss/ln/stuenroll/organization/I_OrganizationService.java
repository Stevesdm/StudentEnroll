package cn.gov.hrss.ln.stuenroll.organization;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_OrganizationService {

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

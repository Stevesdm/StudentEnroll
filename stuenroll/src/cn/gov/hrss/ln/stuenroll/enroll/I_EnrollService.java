package cn.gov.hrss.ln.stuenroll.enroll;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_EnrollService {

	/**
	 * 查询报名记录
	 * 
	 * @param map
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Record> searchEnroll(HashMap map, long start, long length);

	/**
	 * 查询记录总数
	 * 
	 * @param map
	 * @return
	 */
	public long searchEnrollCount(HashMap map);

	public int deleteById(Long[] id);

	/**
	 * 查询所有专业记录
	 * 
	 * @return
	 */
	public List<Record> searchAllProfession();

	/**
	 * 获得所有学生状态
	 * 
	 * @return
	 */
	public List<Record> searchAllStudent_State();

	/**
	 * 查询所有培训机构
	 * 
	 * @return
	 */
	public List<Record> searchAllOrganization();
	/**
	 * 查ID
	 * @param name
	 * @return
	 */
	public Long searchOrganizationId(String name);
	/**
	 * 查ID
	 * @param name
	 * @return
	 */
	public Long searchProfessionId(String name);
	
	public void addEnroll(HashMap map);
	
	public void changeEnroll(HashMap map,Long id);

}

package cn.gov.hrss.ln.stuenroll.db;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * Enroll表Dao接口
 * 
 * @author Administrator
 *
 */
public interface I_EnrollDao {

	/**
	 * 根据条件查询报名表相关数据总数
	 * 
	 * @param year
	 * @param month
	 * @param studentstate
	 * @param organizationId
	 * @return
	 */
	public long searchCountByCondition(int year, int month, int studentstate, long organizationId);
	
	/**
	 * 根据条件查询地域数据
	 * @param year
	 * @param organization
	 * @return
	 */
	
	public  List<Record> searchCountByProfession(int year,long organizationId);
	
	/**
	 * 根据条件查询地域人数分布
	 * @param year
	 * @param organizationId
	 * @return
	 */
	
	public  List<Record> searchCountByPlace(int year,long organizationId);
	/**
	 * 根据学历查询人数
	 * @param year
	 * @param organizationId
	 * @return
	 */
	public  List<Record> searchCountByEducation(int year,long organizationId);
	/**
	 * 根据就业人数排名查询人数
	 * @param year
	 * @param stateId
	 * @param organizationId
	 * @return
	 */
	public  List<Record> searchCountByWork(int year,int stateId,long organizationId);
	/**
	 * 查询报名记录
	 * @param map
	 * @param start
	 * @param length
	 * @return
	 */
	public List<Record> searchEnroll(HashMap map, long start, long length);
	
	/**
	 * 查询记录总数
	 * @param map
	 * @return
	 */
	public long searchEnrollCount(HashMap map);
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	public int deleteById(Long[] id);

	/**
	 * 增加数据
	 * @param map
	 */
	public void addEnroll(HashMap map);
	
	
	public void changeEnroll(HashMap map,Long id);

}

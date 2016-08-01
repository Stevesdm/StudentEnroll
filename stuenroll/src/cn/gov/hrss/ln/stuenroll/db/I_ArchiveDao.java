package cn.gov.hrss.ln.stuenroll.db;

/**
 * Archive表Dao接口
 * @author Administrator
 *
 */
public interface I_ArchiveDao {
	
	/**
	 * 根据条件查询归档表相关数据总数
	 * @param year
	 * @param month
	 * @param stateId
	 * @param organization
	 * @return
	 */
	public long searchCountByCondition(int year, int month, int stateId, long organization);

}

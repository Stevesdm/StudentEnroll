package cn.gov.hrss.ln.stuenroll.db;

/**
 * UserLog表Dao接口
 * @author Administrator
 *
 */
public interface I_UserLogDao {
	
	/**
	 * 记录用户行为
	 * @param username
	 * @param operation
	 * @param type
	 */
	public void save(String username, String operation, String type);

}

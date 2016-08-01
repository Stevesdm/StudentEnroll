package cn.gov.hrss.ln.stuenroll.welcome;

/**
 * 欢迎模块网络接口
 * 
 * @author Administrator
 *
 */
public interface I_WelcomeController {

	/**
	 * 统计某一年的报名和归档数据
	 * 
	 * @param year
	 * @return
	 */
	public void statisticsInYear();
	/**
	 * 统计某一年人数前五的专业
	 * @param year
	 * @return
	 */
	public void statisticsInProfession();
	/**
	 * 统计某一年地区人数分布
	 * @param year
	 * @return
	 */
	public void statisticsInPlace();
	
	/**
	 * 统计某一年学历人数分布
	 * @param year
	 * @return
	 */
	public void statisticsInEducation();
	/**
	 * 统计某一年就业排名前六
	 */
	public void statisticsInWork();
}

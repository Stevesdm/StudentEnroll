package cn.gov.hrss.ln.stuenroll.welcome;

import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 欢迎模块业务接口
 * @author Administrator
 *
 */
public interface I_WelcomeService {
	
	/**
	 * 统计某一年的报名和归档数据
	 * @param year
	 * @param organization
	 * @return
	 */
	public HashMap statisticsInYear(int year, long organization);
	/**
	 * 统计某一年人数前五的专业
	 * @param year
	 * @param organization
	 * @return
	 */
	public List<Record> statisticsInProfession(int year, long organization);
	/**
	 * 统计某一年地区人数分布
	 * @param year
	 * @param organization
	 * @return
	 */
	public List<Record> statisticsInPlace(int year,long organization);
	/**
	 * 统计某一年学历人数分布
	 * @param year
	 * @param organization
	 * @return
	 */
	public List<Record> statisticsInEducation(int year,long organization);
	/**
	 * 统计某一年的就业排名前六人数
	 * @param year
	 * @param stateId
	 * @param organization
	 * @return
	 */
	public List<Record> statisticsInWork(int year,long organization);
	
}

package cn.gov.hrss.ln.stuenroll.welcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_ArchiveDao;
import cn.gov.hrss.ln.stuenroll.db.I_EnrollDao;

/**
 * 欢迎模块业务类
 * @author Administrator
 *
 */
public class WelcomeService implements I_WelcomeService{
	private I_EnrollDao i_EnrollDao;
	private I_ArchiveDao i_ArchiveDao;

	@Override
	public HashMap statisticsInYear(int year, long organization) {
		HashMap<String, ArrayList<Long>> map = new HashMap();
		ArrayList<Long> list_1 = new ArrayList<>();
		ArrayList<Long> list_2 = new ArrayList<>();
		ArrayList<Long> list_3 = new ArrayList<>();
		ArrayList<Long> list_4 = new ArrayList<>();
		ArrayList<Long> list_5 = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			//获取月报名总数
			long count_1 = i_EnrollDao.searchCountByCondition(year, i, 1, organization);
			//获取月审查总数
			long count_2 = i_EnrollDao.searchCountByCondition(year, i, 2, organization);
			//获取月学习人数
			long count_3 = i_EnrollDao.searchCountByCondition(year, i, 3, organization) + i_ArchiveDao.searchCountByCondition(year, i, 3, organization);
			//获取月中退人数
			long count_4 = i_EnrollDao.searchCountByCondition(year, i, 4, organization) + i_ArchiveDao.searchCountByCondition(year, i, 4, organization);
			//获取月就业人数
			long count_5 = i_EnrollDao.searchCountByCondition(year, i, 5, organization) + i_ArchiveDao.searchCountByCondition(year, i, 5, organization);
			
			list_1.add(count_1);
			list_2.add(count_2);
			list_3.add(count_3);
			list_4.add(count_4);
			list_5.add(count_5);
		}
		map.put("报名数据", list_1);
		map.put("审查数据", list_2);
		map.put("学习数据", list_3);
		map.put("中退数据", list_4);
		map.put("就业数据", list_5);
		
		return map;
	}
	@Override
	public List<Record> statisticsInProfession(int year, long organization) {
		List list = i_EnrollDao.searchCountByProfession(year, organization);
		return list;
	}
	
	@Override
	public List<Record> statisticsInPlace(int year, long organization) {
		List list = i_EnrollDao.searchCountByPlace(year, organization);
		return list;
	}
	
	@Override
	public List<Record> statisticsInEducation(int year, long organization) {
		List list = i_EnrollDao.searchCountByEducation(year, organization);
		return list;
	}
	
	@Override
	public List<Record> statisticsInWork(int year,long organization) {
		List list = i_EnrollDao.searchCountByWork(year,5, organization);
		return list;
	}
	
	public I_EnrollDao getI_EnrollDao() {
		return i_EnrollDao;
	}

	public void setI_EnrollDao(I_EnrollDao i_EnrollDao) {
		this.i_EnrollDao = i_EnrollDao;
	}

	public I_ArchiveDao getI_ArchiveDao() {
		return i_ArchiveDao;
	}

	public void setI_ArchiveDao(I_ArchiveDao i_ArchiveDao) {
		this.i_ArchiveDao = i_ArchiveDao;
	}
	

	
}

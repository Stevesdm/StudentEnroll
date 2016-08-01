package cn.gov.hrss.ln.stuenroll.enroll;

import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_EnrollDao;
import cn.gov.hrss.ln.stuenroll.db.I_OrganizationDao;
import cn.gov.hrss.ln.stuenroll.db.I_ProfessionDao;
import cn.gov.hrss.ln.stuenroll.db.I_Student_StateDao;

public class EnrollService implements I_EnrollService {
	private I_EnrollDao i_EnrollDao;
	private I_ProfessionDao i_ProfessionDao;
	private I_Student_StateDao i_Student_StateDao;
	private I_OrganizationDao i_OrganizationDao;

	public I_Student_StateDao getI_Student_StateDao() {
		return i_Student_StateDao;
	}

	public void setI_Student_StateDao(I_Student_StateDao i_Student_StateDao) {
		this.i_Student_StateDao = i_Student_StateDao;
	}

	public I_ProfessionDao getI_ProfessionDao() {
		return i_ProfessionDao;
	}

	public void setI_ProfessionDao(I_ProfessionDao i_ProfessionDao) {
		this.i_ProfessionDao = i_ProfessionDao;
	}

	@Override
	public List<Record> searchEnroll(HashMap map, long start, long length) {
		List<Record> list = i_EnrollDao.searchEnroll(map, start, length);
		return list;
	}

	@Override
	public long searchEnrollCount(HashMap map) {
		long count = i_EnrollDao.searchEnrollCount(map);
		return count;
	}

	@Before(Tx.class)
	@Override
	public int deleteById(Long[] id) {
		int i = i_EnrollDao.deleteById(id);
		return i;
	}

	public I_EnrollDao getI_EnrollDao() {
		return i_EnrollDao;
	}

	public void setI_EnrollDao(I_EnrollDao i_EnrollDao) {
		this.i_EnrollDao = i_EnrollDao;
	}

	@Override
	public List<Record> searchAllProfession() {
		List<Record> list = i_ProfessionDao.searchAllProfession();
		// TODO Auto-generated method stub
		return list;
	}

	@Override
	public List<Record> searchAllStudent_State() {
		// TODO Auto-generated method stub
		List<Record> list = i_Student_StateDao.searchAllStudent_State();
		return list;
	}

	@Override
	public List<Record> searchAllOrganization() {
		// TODO Auto-generated method stub
		List<Record> list = i_OrganizationDao.searchAllOrganization();
		return list;

	}

	public I_OrganizationDao getI_OrganizationDao() {
		return i_OrganizationDao;
	}

	public void setI_OrganizationDao(I_OrganizationDao i_OrganizationDao) {
		this.i_OrganizationDao = i_OrganizationDao;
	}

	@Override
	public Long searchOrganizationId(String name) {
		// TODO Auto-generated method stub
		Long number = i_OrganizationDao.searchOrganizationId(name);
		return number;
	}

	@Override
	public Long searchProfessionId(String name) {
		// TODO Auto-generated method stub
		Long number = i_ProfessionDao.searchProfessionId(name);
		return number;
	}

	@Before(Tx.class)
	@Override
	public void addEnroll(HashMap map) {
		// TODO Auto-generated method stub
		i_EnrollDao.addEnroll(map);
	}

	@Override
	public void changeEnroll(HashMap map, Long id) {
		// TODO Auto-generated method stub
		i_EnrollDao.changeEnroll(map, id);
	}

}

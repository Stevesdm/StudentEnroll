package cn.gov.hrss.ln.stuenroll.organization;

import java.util.HashMap;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_OrganizationDao;

public class OrganizationService implements I_OrganizationService {
	private I_OrganizationDao i_OrganizationDao;

	@Override
	public long searchOrganizationCount(HashMap map) {
		long count = i_OrganizationDao.searchOrganizationCount(map);
		return count;
	}

	@Override
	public List<Record> searchOrganization(HashMap map, long start, long length) {
		List<Record> list = i_OrganizationDao.searchOrganization(map, start, length);
		return list;
	}

	@Before(Tx.class)
	@Override
	public int deleteById(Long[] id) {
		int i = i_OrganizationDao.deleteById(id);
		return i;
	}

	@Before(Tx.class)
	@Override
	public void addOrganization(HashMap map) {
		i_OrganizationDao.addOrganization(map);
	}

	@Before(Tx.class)
	@Override
	public void modifyOrganization(HashMap map) {
		i_OrganizationDao.modifyOrganization(map);
	}

	public I_OrganizationDao getI_OrganizationDao() {
		return i_OrganizationDao;
	}

	public void setI_OrganizationDao(I_OrganizationDao i_OrganizationDao) {
		this.i_OrganizationDao = i_OrganizationDao;
	}
}

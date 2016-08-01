package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_OrganizationDao;

public class OrganizationDao implements I_OrganizationDao {

	@Override
	public List<Record> searchAllOrganization() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM organization ");
		List<Record> list = Db.find(sql.toString());
		return list;
	}

	@Override
	public Long searchOrganizationId(String organizationName) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("id ");
		sql.append("FROM ");
		sql.append("organization ");
		sql.append("WHERE ");
		sql.append("`name` = ?; ");
		Long number = Db.queryLong(sql.toString(), organizationName);
		return number;
	}

	@Override
	public long searchOrganizationCount(HashMap map) {
		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String tel = (String) map.get("tel");
		String liaison = (String) map.get("liaison");

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	organization o ");
		sql.append("WHERE ");
		sql.append("1=1 ");
		if (name != null && name.length() > 0) {
			sql.append("AND o.name = ? ");
			param.add(name);
		}
		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND o.abbreviation = ? ");
			param.add(abbreviation);
		}
		if (address != null && address.length() > 0) {
			sql.append("AND o.address = ? ");
			param.add(address);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("AND o.tel = ? ");
			param.add(tel);
		}
		if (liaison != null && liaison.length() > 0) {
			sql.append("AND o.liaison = ? ");
			param.add(liaison);
		}

		long count = Db.queryLong(sql.toString(), param.toArray());
		return count;
	}

	@Override
	public List<Record> searchOrganization(HashMap map, long start, long length) {
		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String tel = (String) map.get("tel");
		String liaison = (String) map.get("liaison");

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	a.id, ");
		sql.append("	a.`name`, ");
		sql.append("	a.abbreviation, ");
		sql.append("	a.address, ");
		sql.append("	a.tel, ");
		sql.append("	a.liaison, ");
		sql.append("	a.professionAmount, ");
		sql.append("	a.classAmount, ");
		sql.append("	sum(b.studentAmount) AS studentAmount ");
		sql.append("FROM ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			o.id, ");
		sql.append("			o.`name`, ");
		sql.append("			o.abbreviation, ");
		sql.append("			o.address, ");
		sql.append("			o.tel, ");
		sql.append("			o.liaison, ");
		sql.append("			COUNT(DISTINCT(op.id)) AS professionAmount, ");
		sql.append("			COUNT(DISTINCT(c.id)) AS classAmount ");
		sql.append("		FROM ");
		sql.append("			organization o ");
		sql.append("		LEFT JOIN ( ");
		sql.append("			SELECT ");
		sql.append("				p.id, ");
		sql.append("				oj.organization_id ");
		sql.append("			FROM ");
		sql.append("				organization_profession p ");
		sql.append("			LEFT JOIN organization_join oj ON p.organization_join_id = oj.id ");
		sql.append("		) op ON o.id = op.organization_id ");
		sql.append("		LEFT JOIN classinfo c ON o.id = c.organization_id ");
		sql.append("		GROUP BY ");
		sql.append("			o.id ");
		sql.append("	) a, ");
		sql.append("	( ");
		sql.append("		SELECT ");
		sql.append("			o.id, ");
		sql.append("			COUNT(DISTINCT(e.id)) AS studentAmount ");
		sql.append("		FROM ");
		sql.append("			organization o ");
		sql.append("		LEFT JOIN enroll e ON o.id = e.organization_id ");
		sql.append("		GROUP BY ");
		sql.append("			o.id ");
		sql.append("	) b ");
		sql.append("WHERE ");
		sql.append("	a.id = b.id ");
		if (name != null && name.length() > 0) {
			sql.append("AND a.name=? ");
			param.add(name);
		}
		if (abbreviation != null && abbreviation.length() > 0) {
			sql.append("AND a.abbreviation=? ");
			param.add(abbreviation);
		}
		if (address != null && address.length() > 0) {
			sql.append("AND a.address=? ");
			param.add(address);
		}
		if (tel != null && tel.length() > 0) {
			sql.append("AND a.tel=? ");
			param.add(tel);
		}
		if (liaison != null && liaison.length() > 0) {
			sql.append("AND a.liaison=? ");
			param.add(liaison);
		}
		sql.append("GROUP BY ");
		sql.append("	a.id ");
		sql.append("LIMIT ?, ");
		sql.append(" ?; ");
		param.add(start);
		param.add(length);

		List<Record> list = Db.find(sql.toString(), param.toArray());

		return list;
	}

	@Override
	public int deleteById(Long[] id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	organization ");
		sql.append("WHERE ");
		sql.append("	id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("? ");
			if (i != id.length - 1) {
				sql.append(", ");
			}
		}
		sql.append(") ");
		int i = Db.update(sql.toString(), id);
		return i;
	}

	@Override
	public void addOrganization(HashMap map) {
		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String tel = (String) map.get("tel");
		String liaison = (String) map.get("liaison");
		String liaison_tel = (String) map.get("liaison_tel");

		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO organization ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		next ");
		sql.append("		VALUE ");
		sql.append("			FOR MYCATSEQ_GLOBAL, ");
		sql.append("			? , ");
		sql.append("			? , ");
		sql.append("			? , ");
		sql.append("			? , ");
		sql.append("			? , ");
		sql.append("			NULL ");
		sql.append("	); ");

		param.add(name);
		param.add(abbreviation);
		param.add(address);
		param.add(tel);
		param.add(liaison);

		Db.update(sql.toString(), param.toArray());
	}

	@Override
	public void modifyOrganization(HashMap map) {
		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String abbreviation = (String) map.get("abbreviation");
		String address = (String) map.get("address");
		String tel = (String) map.get("tel");
		String liaison = (String) map.get("liaison");
		Long id = (Long) map.get("id");

		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE organization ");
		sql.append("SET `name` = ? , ");
		sql.append(" abbreviation = ? , ");
		sql.append(" address = ? , ");
		sql.append(" tel = ? , ");
		sql.append(" liaison =? ");
		sql.append("WHERE ");
		sql.append("	id = ? ; ");

		param.add(name);
		param.add(abbreviation);
		param.add(address);
		param.add(tel);
		param.add(liaison);
		param.add(id);

		Db.update(sql.toString(), param.toArray());
	}

}

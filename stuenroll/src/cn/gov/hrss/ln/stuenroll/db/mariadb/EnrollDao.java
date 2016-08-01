package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_EnrollDao;

/**
 * Enroll表Dao类
 * 
 * @author Administrator
 *
 */
public class EnrollDao implements I_EnrollDao {

	@Override
	public long searchCountByCondition(int year, int month, int studentstate, long organizationId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	COUNT(*) ");
		sql.append("FROM ");
		sql.append("	enroll NATURAL JOIN student_state ss");
		sql.append("WHERE ");
		sql.append("	YEAR (create_time) = ? ");
		sql.append("AND MONTH (create_time) = ? ");
		sql.append("AND ss.name = ? ");

		if (organizationId != -1) {
			sql.append("AND organization_id = ? ");
			long count = Db.queryLong(sql.toString(), year, month, studentstate, organizationId);
			return count;
		}
		else {
			long count = Db.queryLong(sql.toString(), year, month, studentstate);
			return count;
		}

	}

	@Override
	public List<Record> searchCountByProfession(int year, long organizationId) {
		StringBuffer sql = new StringBuffer();
		List<Record> list = new ArrayList<>();
		sql.append("SELECT ");
		sql.append("	p.`name` AS a, ");
		sql.append("	COUNT(*) AS b ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("  LEFT JOIN profession p ON e.profession_id = p.id ");
		sql.append("WHERE ");
		sql.append("	`year` = ? ");
		if (organizationId != -1) {
			sql.append("AND organization_id = ? ");
			sql.append("GROUP BY ");
			sql.append("	a ");
			sql.append("ORDER BY ");
			sql.append("	b DESC  ");
			sql.append("LIMIT 0, ");
			sql.append(" 5; ");
			list = Db.find(sql.toString(), year, organizationId);
			return list;
		}
		else {
			sql.append("GROUP BY ");
			sql.append("	a ");
			sql.append("ORDER BY ");
			sql.append("	b DESC  ");
			sql.append("LIMIT 0, ");
			sql.append(" 5; ");
			list = Db.find(sql.toString(), year);
			return list;
		}
	}

	@Override
	public List<Record> searchCountByPlace(int year, long organizationId) {
		StringBuffer sql = new StringBuffer();
		List<Record> list = new ArrayList<>();
		sql.append("SELECT ");
		sql.append("	place, ");
		sql.append("	COUNT(*) AS a ");
		sql.append("FROM ");
		sql.append("	enroll ");
		sql.append("WHERE ");
		sql.append(" 	`year` = ? ");
		if (organizationId != -1) {
			sql.append("AND organization_id = ? ");
			sql.append("GROUP BY ");
			sql.append("	place; ");
			list = Db.find(sql.toString(), year, organizationId);
			return list;
		}
		else {
			sql.append("GROUP BY ");
			sql.append("	place; ");
			list = Db.find(sql.toString(), year);
			return list;
		}
	}

	@Override
	public List<Record> searchCountByEducation(int year, long organizationId) {
		StringBuffer sql = new StringBuffer();
		List<Record> list = new ArrayList<>();
		sql.append("SELECT ");
		sql.append("	education, ");
		sql.append("	COUNT(*) AS a ");
		sql.append("FROM ");
		sql.append("	enroll ");
		sql.append("WHERE ");
		sql.append(" 	`year` = ? ");
		if (organizationId != -1) {
			sql.append("AND organization_id = ? ");
			sql.append("GROUP BY ");
			sql.append("	education; ");
			list = Db.find(sql.toString(), year, organizationId);
			return list;
		}
		else {
			sql.append("GROUP BY ");
			sql.append("	education;");
			list = Db.find(sql.toString(), year);
			return list;
		}
	}

	@Override
	public List<Record> searchCountByWork(int year, int stateId, long organizationId) {
		StringBuffer sql = new StringBuffer();
		List<Record> list = new ArrayList<>();
		sql.append("SELECT ");
		sql.append("	p.`name` AS a, ");
		sql.append("	COUNT(*) AS b ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN profession p ON e.profession_id = p.id ");
		sql.append("WHERE ");
		sql.append("	`year` = ? ");
		sql.append("AND state_id = ? ");
		if (organizationId != -1) {
			sql.append("AND organization_id = ? ");
			sql.append("GROUP BY a ");
			sql.append("ORDER BY ");
			sql.append("	b DESC ");
			sql.append("LIMIT 0, ");
			sql.append(" 6; ");
			list = Db.find(sql.toString(), year, stateId, organizationId);
			return list;
		}
		else {
			sql.append("GROUP BY a ");
			sql.append("ORDER BY ");
			sql.append("	b DESC ");
			sql.append("LIMIT 0, ");
			sql.append(" 6; ");
			list = Db.find(sql.toString(), year, stateId);
			return list;
		}
	}

	@Override
	public List<Record> searchEnroll(HashMap map, long start, long length) {
		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String pid = (String) map.get("pid");
		Integer year = (Integer) map.get("year");
		String sex = (String) map.get("sex");
		String education = (String) map.get("education");
		Long organizationId = (Long) map.get("organizationId");
		String profession = (String) map.get("profession");
		String organization = (String) map.get("organization");
		Long classinfoId = (Long) map.get("classinfoId");
		String studentstate = (String) map.get("studentstate");
		//改
		Long id = (Long) map.get("id");

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	e.id, ");
		sql.append("e.`name`, ");
		sql.append("	e.pid, ");
		sql.append("	AES_DECRYPT(UNHEX(e.tel), 'HelloHrss') AS tel, ");
		sql.append("IFNULL(o.`name`,'') AS organization, ");
		sql.append("IFNULL(p.`name`,'') AS profession, ");
		sql.append("IFNULL(c.`name`,'') AS classinfo, ");
		sql.append("	e.`year`, ");
		sql.append("	ss.`name` AS state ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN organization o ON e.organization_id = o.id ");
		sql.append(" LEFT JOIN profession p ON e.profession_id = p.id ");
		sql.append("LEFT JOIN classinfo c ON e.classinfo_id = c.id ");
		sql.append("LEFT JOIN student_state ss ON e.state_id = ss.id ");
		sql.append("WHERE ");
		sql.append("1=1 ");
		if (name != null && name.length() > 0) {
			sql.append("AND e.name = ? ");
			param.add(name);
		}
		if (pid != null && pid.length() > 0) {
			sql.append("AND e.pid = ? ");
			param.add(pid);
		}
		if (year != null) {
			sql.append("AND e.year = ? ");
			param.add(year);
		}
		if (sex != null && sex.length() > 0) {
			sql.append("AND e.sex = ? ");
			param.add(sex);
		}
		if (education != null && education.length() > 0) {
			sql.append("AND e.education = ? ");
			param.add(education);
		}
		if (organization != null && organization.length() > 0) {
			sql.append("AND o.name = ? ");
			param.add(organization);
		}
		if (organizationId != null) {
			sql.append("AND e.organization_id = ? ");
			param.add(organizationId);
		}
		if (profession != null && profession.length() > 0) {
			sql.append("AND p.name = ? ");
			param.add(profession);
		}
		if (classinfoId != null) {
			sql.append("AND e.classinfo_id = ? ");
			param.add(classinfoId);
		}
		if (studentstate != null && studentstate.length() > 0) {
			sql.append("AND ss.name = ? ");
			param.add(studentstate);
		}
		if (id != null) {
			sql.append("AND e.id = ? ");
			param.add(id);
		}
		sql.append("ORDER BY ");
		sql.append("	e.id ");
		sql.append("LIMIT ?,? ; ");
		param.add(start);
		param.add(length);

		List<Record> list = Db.find(sql.toString(), param.toArray());
		for (Record record : list) {
			byte[] tel = record.getBytes("tel");
			record.set("tel", new String(tel));
			record.set("id", record.getLong("id").toString());
		}
		return list;
	}

	@Override
	public long searchEnrollCount(HashMap map) {
		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String pid = (String) map.get("pid");
		Integer year = (Integer) map.get("year");
		String sex = (String) map.get("sex");
		String education = (String) map.get("education");
		String organization = (String) map.get("organization");
		Long organizationId = (Long) map.get("organizationId");
		String profession = (String) map.get("profession");
		Long classinfoId = (Long) map.get("classinfoId");
		String studentstate = (String) map.get("studentstate");

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		sql.append("	count(*) ");
		sql.append("FROM ");
		sql.append("	enroll e ");
		sql.append("LEFT JOIN organization o ON e.organization_id = o.id ");
		sql.append(" LEFT JOIN profession p ON e.profession_id = p.id ");
		sql.append("LEFT JOIN classinfo c ON e.classinfo_id = c.id ");
		sql.append("LEFT JOIN student_state ss ON e.state_id = ss.id ");
		sql.append("WHERE ");
		sql.append("1=1 ");
		if (name != null && name.length() > 0) {
			sql.append("AND e.name = ? ");
			param.add(name);
		}
		if (pid != null && pid.length() > 0) {
			sql.append("AND e.pid = ? ");
			param.add(pid);
		}
		if (year != null) {
			sql.append("AND e.year = ? ");
			param.add(year);
		}
		if (sex != null && sex.length() > 0) {
			sql.append("AND e.sex = ? ");
			param.add(sex);
		}
		if (education != null && education.length() > 0) {
			sql.append("AND e.education = ? ");
			param.add(education);
		}
		if (organization != null && organization.length() > 0) {
			sql.append("AND o.name = ? ");
			param.add(organization);
		}
		if (organizationId != null) {
			sql.append("AND e.organization_id = ? ");
			param.add(organizationId);
		}
		if (profession != null && profession.length() > 0) {
			sql.append("AND p.name = ? ");
			param.add(profession);
		}
		if (classinfoId != null) {
			sql.append("AND e.classinfo_id = ? ");
			param.add(classinfoId);
		}
		if (studentstate != null && studentstate.length() > 0) {
			sql.append("AND ss.name = ? ");
			param.add(studentstate);
		}

		long count = Db.queryLong(sql.toString(), param.toArray());
		return count;
	}

	@Override
	public int deleteById(Long[] id) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE ");
		sql.append("FROM ");
		sql.append("	enroll ");
		sql.append("WHERE ");
		sql.append("	id IN ( ");
		for (int i = 0; i < id.length; i++) {
			sql.append("?");
			if (i != id.length - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		int i = Db.update(sql.toString(), id);
		return i;
	}

	@Override
	public void addEnroll(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO `hrss`.`enroll` ( ");
		sql.append("`id` , ");
		sql.append("`name`, ");
		sql.append("`sex`, ");
		sql.append("`nation`, ");
		sql.append("`pid`, ");
		sql.append("`graduate_school`, ");
		sql.append("`graduate_year`, ");
		sql.append("`graduate_date`, ");
		sql.append("`education`, ");
		sql.append("`healthy`, ");
		sql.append("`major`, ");
		sql.append("`politics`, ");
		sql.append("`birthday`, ");
		sql.append("`resident_address`, ");
		sql.append("`permanent_address`, ");
		sql.append("`home_address`, ");
		sql.append("`tel`, ");
		sql.append("`home_tel`, ");
		sql.append("`email`, ");
		sql.append("`profession_id`, ");
		sql.append("`state_id`, ");
		sql.append("`organization_id`, ");
		sql.append("`place`, ");
		sql.append("`year`, ");
		sql.append("`sharding` ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("( ");
		sql.append("next VALUE FOR MYCATSEQ_GLOBAL, ");
		sql.append("?,?,?,?,?,?,?,?,?,?,?,?, ");
		sql.append("HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
		sql.append("HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
		sql.append("HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
		sql.append("HEX(AES_ENCRYPT(?, 'HelloHrss')), ");
		sql.append("HEX(AES_ENCRYPT(?, 'HelloHrss')), ");

		sql.append("?,?,1,?,?,?,?); ");

		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String sex = (String) map.get("sex");
		String nation = (String) map.get("nation");
		String pid = (String) map.get("pid");
		String graduate_school = (String) map.get("graduate_school");
		Long graduate_year = (Long) map.get("graduate_year");
		String graduate_date = (String) map.get("graduate_date");
		String education = (String) map.get("education");
		String healthy = (String) map.get("healthy");
		String major = (String) map.get("major");
		String politics = (String) map.get("politics");
		String birthday = (String) map.get("birthday");
		String resident_address = (String) map.get("resident_address");
		String permanent_address = (String) map.get("permanent_address");
		String home_address = (String) map.get("home_address");
		String tel = (String) map.get("tel");
		String home_tel = (String) map.get("home_tel");
		String email = (String) map.get("email");
		Long profession_id = (Long) map.get("profession_id");
		Long organization_id = (Long) map.get("organization_id");
		String place = (String) map.get("place");
		int year = (int) map.get("year");
		Long sharding = (Long) map.get("sharding");

		param.add(name);
		param.add(sex);
		param.add(nation);
		param.add(pid);
		param.add(graduate_school);
		param.add(graduate_year);
		param.add(graduate_date);
		param.add(education);
		param.add(healthy);
		param.add(major);
		param.add(politics);
		param.add(birthday);
		param.add(resident_address);
		param.add(permanent_address);
		param.add(home_address);
		param.add(tel);
		param.add(home_tel);
		param.add(email);
		param.add(profession_id);
		param.add(organization_id);
		param.add(place);
		param.add(year);
		param.add(sharding);

		Db.update(sql.toString(), param.toArray());

	}

	@Override
	public void changeEnroll(HashMap map, Long id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE enroll ");
		sql.append("SET ");
		sql.append("`name` = ？, ");
		sql.append("`sex` = ？, ");
		sql.append("`nation` = ？, ");
		sql.append("`pid` = ？, ");
		sql.append("`graduate_school` = ？, ");
		sql.append("`graduate_year` = ？, ");
		sql.append("`graduate_date` = ？, ");
		sql.append("`education` = ？, ");
		sql.append("`major` = ？, ");
		sql.append("`healthy` = ？, ");
		sql.append("`politics` = ？, ");
		sql.append("`birthday` = ？, ");
		sql.append("`resident_address` = ？, ");
		sql.append("`permanent_address` = ？, ");
		sql.append("`home_address` =？, ");
		sql.append("`tel` = ？, ");
		sql.append("`home_tel` = ？, ");
		sql.append("`email` = ？, ");
		sql.append("`profession_id` = ？, ");
		sql.append("`organization_id` = ？, ");
		sql.append("`place` = ？, ");
		sql.append("WHERE ");
		sql.append("(`id` = ？); ");

		ArrayList param = new ArrayList();
		String name = (String) map.get("name");
		String sex = (String) map.get("sex");
		String nation = (String) map.get("nation");
		String pid = (String) map.get("pid");
		String graduate_school = (String) map.get("graduate_school");
		Long graduate_year = (Long) map.get("graduate_year");
		String graduate_date = (String) map.get("graduate_date");
		String education = (String) map.get("education");
		String major = (String) map.get("major");
		String healthy = (String) map.get("healthy");
		String politics = (String) map.get("politics");
		String birthday = (String) map.get("birthday");
		String resident_address = (String) map.get("resident_address");
		String permanent_address = (String) map.get("permanent_address");
		String home_address = (String) map.get("home_address");
		String tel = (String) map.get("tel");
		String home_tel = (String) map.get("home_tel");
		String email = (String) map.get("email");
		Long profession_id = (Long) map.get("profession_id");
		Long organization_id = (Long) map.get("organization_id");
		String place = (String) map.get("place");
		

		param.add(name);
		param.add(sex);
		param.add(nation);
		param.add(pid);
		param.add(graduate_school);
		param.add(graduate_year);
		param.add(graduate_date);
		param.add(education);
		param.add(major);
		param.add(healthy);
		param.add(politics);
		param.add(birthday);
		param.add(resident_address);
		param.add(permanent_address);
		param.add(home_address);
		param.add(tel);
		param.add(home_tel);
		param.add(email);
		param.add(profession_id);
		param.add(organization_id);
		param.add(place);
		param.add(id);

		Db.update(sql.toString(), param.toArray());

	}

}

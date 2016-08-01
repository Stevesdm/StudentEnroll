package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_Student_StateDao;

public class Student_StateDao implements I_Student_StateDao {

	@Override
	public List<Record> searchAllStudent_State() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * ");
		sql.append("FROM student_state ");
		List<Record> list = Db.find(sql.toString());
		return list;
	}

}

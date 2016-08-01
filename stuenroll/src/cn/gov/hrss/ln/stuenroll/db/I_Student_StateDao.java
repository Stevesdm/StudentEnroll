package cn.gov.hrss.ln.stuenroll.db;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_Student_StateDao {
	/**
	 * 获得所有学生状态
	 * @return
	 */
public List<Record> searchAllStudent_State();
}

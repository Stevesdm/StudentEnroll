package cn.gov.hrss.ln.stuenroll.db;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_ProfessionDao {
	/**
	 * 
	 */
	/**
	 * 获得所有专业
	 * @return
	 */
public List<Record> searchAllProfession();

public Long searchProfessionId(String professionName);
}

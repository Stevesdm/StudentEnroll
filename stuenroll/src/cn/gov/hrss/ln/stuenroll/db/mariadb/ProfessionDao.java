package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.gov.hrss.ln.stuenroll.db.I_ProfessionDao;

public class ProfessionDao implements I_ProfessionDao{

	@Override
	public List<Record> searchAllProfession() {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT * ");
		sql.append("FROM profession ");
		List<Record> list=Db.find(sql.toString());
		return list;
	}
	
	@Override
	public Long searchProfessionId(String professionName) {
		// TODO Auto-generated method stub
		
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT ");
		sql.append("id ");
		sql.append("FROM ");
		sql.append("Profession ");
		sql.append("WHERE ");
		sql.append("`name` = ?; ");
		Long number=Db.queryLong(sql.toString(),professionName);
		return number;
	}

}

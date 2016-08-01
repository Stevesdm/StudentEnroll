package cn.gov.hrss.ln.stuenroll.db.mariadb;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;

import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;

/**
 * UserLog表Dao类
 * 
 * @author Administrator
 *
 */
public class UserLogDao implements I_UserLogDao {

	@Override
	public void save(String username, String operation, String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO user_log ( ");
		sql.append("		id, ");
		sql.append("		user_id, ");
		sql.append("		operation, ");
		sql.append("		type, ");
		sql.append("		sharding ");
		sql.append(") ");
		sql.append("VALUES ");
		sql.append("	( ");
		sql.append("		NEXT ");
		sql.append("		VALUE ");
		sql.append("			FOR MYCATSEQ_SEQ_GLOBAL, ");
		sql.append("			(SELECT id FROM `user` WHERE username = ?), ");
		sql.append("			?, ");
		sql.append("			?, ");
		sql.append("			? ");
		sql.append("	); ");

		Db.update(sql.toString(), username, operation, type, new Date().getTime());

	}

}

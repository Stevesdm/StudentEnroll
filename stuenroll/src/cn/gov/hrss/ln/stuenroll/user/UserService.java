package cn.gov.hrss.ln.stuenroll.user;

import com.alibaba.rocketmq.common.message.Message;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import cn.gov.hrss.ln.stuenroll.db.I_UserDao;
import cn.gov.hrss.ln.stuenroll.db.I_UserLogDao;
import cn.gov.hrss.ln.stuenroll.rocketmq.I_Producer;
import jodd.datetime.JDateTime;
import jodd.util.Base64;

/**
 * 用户模块业务类
 * 
 * @author Administrator
 *
 */
public class UserService implements I_UserService {

	private I_UserDao i_UserDao;
	private I_UserLogDao i_UserLogDao;
	private I_Producer i_Producer;

	public I_UserLogDao getI_UserLogDao() {
		return i_UserLogDao;
	}

	public void setI_UserLogDao(I_UserLogDao i_UserLogDao) {
		this.i_UserLogDao = i_UserLogDao;
	}

	public boolean login(String username, String password) {
		boolean bool = i_UserDao.login(username, password);
		return bool;
	}

	public I_UserDao getI_UserDao() {
		return i_UserDao;
	}

	public void setI_UserDao(I_UserDao i_UserDao) {
		this.i_UserDao = i_UserDao;
	}

	@Override
	public boolean checkRecoverPersonalInfo(String name, String pid, String organization) {
		boolean bool = i_UserDao.checkRecoverPersonalInfo(name, pid, organization);
		return bool;
	}

	@Override
	public boolean checkAccountInfo(String username, String email, String tel) {
		boolean bool = i_UserDao.checkAccountInfo(username, email, tel);
		return bool;
	}

	@Override
	public boolean checkQuestionInfo(String username, String question, String answer) {
		boolean bool = i_UserDao.checkQuestionInfo(username, question, answer);
		return bool;
	}

	@Before(Tx.class)
	@Override
	public int recoverPassword(String username, String password) {
		int count = i_UserDao.recoverPassword(username, password); // 重设密码
		i_UserLogDao.save(username, "用户重新设置密码", "业务操作"); // 记录重设密码行为记录
		Record record = i_UserDao.searchUserInfoAtRecoverPassword(username); // 查询用户基本信息
		Message message = new Message();
		message.setTopic("UserTopic");
		message.setTags("RecoverPassword");
		message.setBody("用户重置密码成功".getBytes());

		String name = record.getStr("name");
		name = Base64.encodeToString(name);
		message.putUserProperty("name", name);

		message.putUserProperty("username", username);
		message.putUserProperty("password", password);

		String tel = record.getStr("tel");
		message.putUserProperty("tel", tel);

		String wechat = record.getStr("wechat");
		message.putUserProperty("wechat", wechat);

		String email = record.getStr("email");
		message.putUserProperty("email", email);

		String dateTime = new JDateTime().toString("YYYY年MM月DD日 hh时：mm分：ss秒");
		message.putUserProperty("dateTime", Base64.encodeToString(dateTime));

		try {
			i_Producer.sendMessage(message);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	public I_Producer getI_Producer() {
		return i_Producer;
	}

	public void setI_Producer(I_Producer i_Producer) {
		this.i_Producer = i_Producer;
	}

	@Override
	public Record searchUserInfoAtLogin(String username) {
		Record record = i_UserDao.searchUserInfoAtLogin(username);
		return record;
	}

	@Before(Tx.class)
	@Override
	public void logout(String username) {
		i_UserLogDao.save(username, null, "退出");
		
	}

}

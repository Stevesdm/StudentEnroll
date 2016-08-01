package cn.gov.hrss.ln.stuenroll.user;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.jfinal.core.Controller;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

import cn.gov.hrss.ln.stuenroll.permission.I_PermissionService;

/**
 * 用户模块网络类
 * 
 * @author Administrator
 *
 */
@Spring("userController")
public class UserController extends Controller implements I_UserController {
	private I_UserService i_UserService;
	private I_PermissionService i_PermissionService;

	@SuppressWarnings("unchecked")
	@Override
	public void login() {
		String username = getPara("username");
		String password = getPara("password");

		@SuppressWarnings("rawtypes")
		HashMap map = new HashMap();

		boolean bool = i_UserService.login(username, password);
		if (bool) {
			// 用户登录成功需要执行Shiro的认证与授权
			Subject subject = SecurityUtils.getSubject(); // Shiro客户端对象
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			
			subject.login(token); // 执行Shiro认证
			if (subject.isAuthenticated()) {
				bool = true; // 认证通过
				Record record = i_UserService.searchUserInfoAtLogin(username);
				String name = record.getStr("name");
				String organization = record.getStr("organization");
				long organizationId = record.getLong("organization_id");
				String role = record.getStr("role");
				setSessionAttr("username", username);
				setSessionAttr("name", name);
				setSessionAttr("organization", organization);
				setSessionAttr("organizationId", organizationId);
				setSessionAttr("role", role);

				List<Record> list = i_PermissionService.searchPermission(username); // 权限列表
				setSessionAttr("permissions", list);

				map.put("username", username);
				map.put("name", name);
				map.put("organization", organization);
				map.put("organizationId", organizationId);
				map.put("role", role);
				map.put("permissions", list);
				
				if (role.equals("超级管理员")) {
					list = i_PermissionService.searchAllPermissions();
				}
				
				//向Shiro会话保存角色和权限
				Session session = subject.getSession();
				session.setAttribute("role", role);
				session.setAttribute("permissions", list);
				
				
			}
			else {
				bool = false; // 认证失败
			}
		}
		map.put("result", bool);
		renderJson(map); //返回JSON结果
	}

	public I_UserService getI_UserService() {
		return i_UserService;
	}

	public void setI_UserService(I_UserService i_UserService) {
		this.i_UserService = i_UserService;
	}

	@Override
	public void checkRecoverPersonalInfo() {
		String name = getPara("name");
		String pid = getPara("pid");
		String organization = getPara("organization");
		boolean bool = i_UserService.checkRecoverPersonalInfo(name, pid, organization);
		if (bool) {
			setSessionAttr("checkRecoverPersonalInfo", true);
		}
		renderJson("result", bool);

	}

	@Override
	public void checkAccountInfo() {
		String username = getPara("username");
		String email = getPara("email");
		String tel = getPara("tel");
		boolean bool = i_UserService.checkAccountInfo(username, email, tel);
		if (bool) {
			setSessionAttr("checkAccountInfo", true);
			setSessionAttr("recoverUsername", username);
		}
		renderJson("result", bool);
	}

	@Override
	public void checkQuestionInfo() {
		String username = getSessionAttr("recoverUsername");
		String question = getPara("question");
		String answer = getPara("answer");
		String password = getPara("password");
		boolean bool = i_UserService.checkQuestionInfo(username, question, answer);
		if (bool) {
			setSessionAttr("checkQuestionInfo", true);
			setSessionAttr("recoverPassword", password);
		}
		renderJson("result", bool);

	}

	@Override
	public void recoverPassword() {
		boolean bool = false;
		try {
			String username = getSessionAttr("recoverUsername");
			String password = getSessionAttr("recoverPassword");
			boolean checkRecoverPersonalInfo = getSessionAttr("checkRecoverPersonalInfo");
			boolean checkAccountInfo = getSessionAttr("checkAccountInfo");
			boolean checkQuestionInfo = getSessionAttr("checkQuestionInfo");
			if (checkRecoverPersonalInfo && checkAccountInfo && checkQuestionInfo) {
				int count = i_UserService.recoverPassword(username, password);
				bool = (count == 1);
				removeSessionAttr("recoverUsername");
				removeSessionAttr("recoverPassword");
				removeSessionAttr("checkRecoverPersonalInfo");
				removeSessionAttr("checkAccountInfo");
				removeSessionAttr("checkQuestionInfo");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			LogKit.warn("用户设置密码异常");
		}
		renderJson("result", bool);

	}

	@Override
	public void logout() {
		try {
			String username = getSessionAttr("username");
			i_UserService.logout(username);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			// 收回Shiro认证令牌
			Subject subject = SecurityUtils.getSubject();
			subject.logout();
			HttpSession session = getSession();
			Enumeration<String> enumeration = session.getAttributeNames();
			while (enumeration.hasMoreElements()) {
				String name = enumeration.nextElement();
				session.removeAttribute(name);

			}

			renderJson("logout", true);
		}

	}

	public I_PermissionService getI_PermissionService() {
		return i_PermissionService;
	}

	public void setI_PermissionService(I_PermissionService i_PermissionService) {
		this.i_PermissionService = i_PermissionService;
	}

}

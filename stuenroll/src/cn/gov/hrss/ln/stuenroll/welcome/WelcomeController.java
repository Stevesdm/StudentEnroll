package cn.gov.hrss.ln.stuenroll.welcome;

import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

/**
 * 欢迎模块网络类
 * @author Administrator
 *
 */
@Spring("welcomeController")
public class WelcomeController extends Controller implements I_WelcomeController{

	private I_WelcomeService i_WelcomeService;
	
	@RequiresPermissions({"3_4","4_4"})
	@Override
	public void statisticsInYear() {
		int year = getParaToInt("year");
		String organization = getSessionAttr("organization");
		long organizationId = getSessionAttr("organizationId");
		if (organization.equals("辽宁省就业网")) {
			organizationId = -1;
		}
		HashMap map = i_WelcomeService.statisticsInYear(year, organizationId);
		renderJson("statistics", map);
		
	}
	
	
	@Override
	public void statisticsInProfession() {
		int year = getParaToInt("year");
		String organization = getSessionAttr("organization");
		long organizationId = getSessionAttr("organizationId");
		if (organization.equals("辽宁省就业网")) {
			organizationId = -1;
		}
		List<Record> list = i_WelcomeService.statisticsInProfession(year, organizationId);
		renderJson("profession", list);
		
	}
	@Override
	public void statisticsInPlace() {
		int year = getParaToInt("year");
		String organization = getSessionAttr("organization");
		long organizationId = getSessionAttr("organizationId");
		if (organization.equals("辽宁省就业网")) {
			organizationId = -1;
		}
		List<Record> list= i_WelcomeService.statisticsInPlace(year, organizationId);
		renderJson("city",list);
	}
	@Override
	public void statisticsInEducation() {
		int year = getParaToInt("year");
		String organization = getSessionAttr("organization");
		long organizationId = getSessionAttr("organizationId");
		if (organization.equals("辽宁省就业网")) {
			organizationId = -1;
		}
		List<Record> list= i_WelcomeService.statisticsInEducation(year, organizationId);
		renderJson("education",list);
	}
	
	@Override
	public void statisticsInWork() {
		int year = getParaToInt("year");
		String organization = getSessionAttr("organization");
		long organizationId = getSessionAttr("organizationId");
		if (organization.equals("辽宁省就业网")) {
			organizationId = -1;
		}
		List<Record> list= i_WelcomeService.statisticsInWork(year, organizationId);
		renderJson("work",list);
	}
	
	public I_WelcomeService getI_WelcomeService() {
		return i_WelcomeService;
	}
	public void setI_WelcomeService(I_WelcomeService i_WelcomeService) {
		this.i_WelcomeService = i_WelcomeService;
	}
	
	

}

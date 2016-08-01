package cn.gov.hrss.ln.stuenroll.enroll;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

import cn.gov.hrss.ln.stuenroll.db.I_ProfessionDao;
import cn.gov.hrss.ln.stuenroll.test.TestValidator;
import cn.gov.hrss.ln.stuenroll.user.I_UserService;

@Spring("enrollController")
public class EnrollController extends Controller implements I_EnrollController {
	private I_EnrollService i_EnrollService;
	private int rowsInPage;

	@RequiresPermissions({ "3_4" })
	@Override
	@Before(TestValidator.class)
	public void searchEnroll() {
		String name = getPara("name");
		String pid = getPara("pid");
		Integer year = getParaToInt("year");
		String sex = getPara("sex");
		String education = getPara("education");
		String organization = getPara("organization");
		Long organizationId = getParaToLong("organizationId");
		String profession = getPara("profession");
		Long classinfoId = getParaToLong("classinfoId");
		String studentstate = getPara("studentstate");
		Long id = getParaToLong("id");

		String organizationName = getSessionAttr("organization");// HttpSession中的组织名称
		// 如果不是就业网用户，那么Java程序从HttpSession钟提取机构ID，强制使用这个机构ID查询数据，所以用户只能看到自己机构的数据
		if (organizationName.equals("辽宁省就业网") == false) {
			organizationId = getSessionAttr("organizationId");
		}

		HashMap map = new HashMap();
		map.put("name", name);
		map.put("pid", pid);
		map.put("year", year);
		map.put("sex", sex);
		map.put("education", education);
		map.put("organizationId", organizationId);
		map.put("organization", organization);
		map.put("profession", profession);
		map.put("classinfoId", classinfoId);
		map.put("studentstate", studentstate);
		map.put("id", id);

		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		long start = (page - 1) * rowsInPage;
		long length = rowsInPage;

		List<Record> list = i_EnrollService.searchEnroll(map, start, length);
		renderJson("result", list);
	}

	@RequiresPermissions({ "3_4" })
	@Override
	public void searchEnrollCount() {
		String name = getPara("name");
		String pid = getPara("pid");
		Integer year = getParaToInt("year");
		String sex = getPara("sex");
		String education = getPara("education");
		Long organizationId = getParaToLong("organizationId");
		String profession = getPara("profession");
		String organization = getPara("organization");
		Long classinfoId = getParaToLong("classinfoId");
		String studentstate = getPara("studentstate");

		String organizationName = getSessionAttr("organization");// HttpSession中的组织名称
		// 如果不是就业网用户，那么Java程序从HttpSession钟提取机构ID，强制使用这个机构ID查询数据，所以用户只能看到自己机构的数据
		if (organizationName.equals("辽宁省就业网") == false) {
			organizationId = getSessionAttr("organizationId");
		}

		HashMap map = new HashMap();
		map.put("name", name);
		map.put("pid", pid);
		map.put("year", year);
		map.put("sex", sex);
		map.put("education", education);
		map.put("organizationId", organizationId);
		map.put("organization", organization);
		map.put("profession", profession);
		map.put("classinfoId", classinfoId);
		map.put("studentstate", studentstate);

		Long count = i_EnrollService.searchEnrollCount(map);
		renderJson("result", count);

	}

	@RequiresPermissions({ "3_2" })
	@Override
	public void deleteById() {
		Long[] id = getParaValuesToLong("id");
		int i = i_EnrollService.deleteById(id);
		renderJson("deleteRows", i);

	}

	@Override
	public void searchAllProfession() {
		// TODO Auto-generated method stub
		List<Record> list = i_EnrollService.searchAllProfession();
		renderJson("result", list);

	}

	public I_EnrollService getI_EnrollService() {
		return i_EnrollService;
	}

	public void setI_EnrollService(I_EnrollService i_EnrollService) {
		this.i_EnrollService = i_EnrollService;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}

	@Override
	public void searchAllStudent_State() {
		List<Record> list = i_EnrollService.searchAllStudent_State();
		renderJson("result", list);
		// TODO Auto-generated method stub

	}

	@Override
	public void searchAllOrganization() {
		// TODO Auto-generated method stub
		List<Record> list = i_EnrollService.searchAllOrganization();
		renderJson("result", list);

	}

	@Override
	public void searchOrganizationId() {
		// TODO Auto-generated method stub
		String name = getPara("organizationName");
		Long number = i_EnrollService.searchOrganizationId(name);

	}

	@Override
	public void searchProfessionId() {
		// TODO Auto-generated method stub
		String name = getPara("professionName");
		Long number = i_EnrollService.searchProfessionId(name);
		System.out.println(number);

	}

	@Override
	public void addEnroll() {
		// TODO Auto-generated method stub

		String name = getPara("name");
		String sex = getPara("sex");
		String nation = getPara("nation");
		String pid = getPara("pid");
		String graduate_school = getPara("graduate_school");
		Long graduate_year = getParaToLong("graduate_year");
		String graduate_date = getPara("graduate_date");
		String education = getPara("education");
		String healthy = getPara("healthy");
		String major = getPara("major");
		String politics = getPara("politics");
		String birthday = getPara("birthday");
		String resident_address = getPara("resident_address");
		String permanent_address = getPara("permanent_address");
		String home_address = getPara("home_address");
		String tel = getPara("tel");
		String home_tel = getPara("home_tel");
		String email = getPara("email");
		Long profession_id = i_EnrollService.searchProfessionId(getPara("profession_name"));
		Long organization_id = i_EnrollService.searchOrganizationId(getPara("organization_name"));
		String place = getPara("place");
		int year = Calendar.getInstance().getWeekYear();
		Long sharding = new Date().getTime();

		HashMap map = new HashMap();

		map.put("name", name);
		map.put("sex", sex);
		map.put("nation", nation);
		map.put("pid", pid);
		map.put("graduate_school", graduate_school);
		map.put("graduate_year", graduate_year);
		map.put("graduate_date", graduate_date);
		map.put("education", education);
		map.put("healthy", healthy);
		map.put("major", major);
		map.put("politics", politics);
		map.put("birthday", birthday);
		map.put("resident_address", resident_address);
		map.put("permanent_address", permanent_address);
		map.put("home_address", home_address);
		map.put("tel", tel);
		map.put("home_tel", home_tel);
		map.put("email", email);
		map.put("profession_id", profession_id);
		map.put("organization_id", organization_id);
		map.put("place", place);
		map.put("year", year);
		map.put("sharding", sharding);
		
		i_EnrollService.addEnroll(map);

	}

	@Override
	public void changeEnroll() {
		// TODO Auto-generated method stub
		
	}

}

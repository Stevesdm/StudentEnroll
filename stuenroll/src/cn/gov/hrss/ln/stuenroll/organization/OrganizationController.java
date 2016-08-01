package cn.gov.hrss.ln.stuenroll.organization;

import java.util.HashMap;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;

@Spring("organizationController")
public class OrganizationController extends Controller implements I_OrganizationController {
	private I_OrganizationService i_OrganizationService;
	private int rowsInPage;

	@Override
	public void searchOrganizationCount() {
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		String address = getPara("address");
		String tel = getPara("tel");
		String liaison = getPara("liaison");

		HashMap map = new HashMap();
		map.put("name", name);
		map.put("abbreviation", abbreviation);
		map.put("address", address);
		map.put("tel", tel);
		map.put("liaison", liaison);

		Long count = i_OrganizationService.searchOrganizationCount(map);
		renderJson("result", count);

	}

	@Override
	public void searchOrganization() {
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		String address = getPara("address");
		String tel = getPara("tel");
		String liaison = getPara("liaison");

		HashMap map = new HashMap();
		map.put("name", name);
		map.put("abbreviation", abbreviation);
		map.put("address", address);
		map.put("tel", tel);
		map.put("liaison", liaison);

		Long page = getParaToLong("page");
		if (page == null) {
			page = 1L;
		}
		long start = (page - 1) * rowsInPage;
		long length = rowsInPage;

		List<Record> list = i_OrganizationService.searchOrganization(map, start, length);
		renderJson("result", list);

	}

	@Override
	public void deleteById() {
		Long[] id = getParaValuesToLong("id");
		int i = i_OrganizationService.deleteById(id);
		renderJson("deleteRows", i);
	}

	@Override
	public void addOrganization() {
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		String address = getPara("address");
		String tel = getPara("tel");
		String liaison = getPara("liaison");

		HashMap map = new HashMap();

		map.put("name", name);
		map.put("abbreviation", abbreviation);
		map.put("address", address);
		map.put("tel", tel);
		map.put("liaison", liaison);

		i_OrganizationService.addOrganization(map);

	}

	@Override
	public void modifyOrganization() {
		String name = getPara("name");
		String abbreviation = getPara("abbreviation");
		String address = getPara("address");
		String tel = getPara("tel");
		String liaison = getPara("liaison");
		Long id = getParaToLong("id");

		HashMap map = new HashMap();

		map.put("name", name);
		map.put("abbreviation", abbreviation);
		map.put("address", address);
		map.put("tel", tel);
		map.put("liaison", liaison);
		map.put("id", id);

		i_OrganizationService.modifyOrganization(map);
	}

	public I_OrganizationService getI_OrganizationService() {
		return i_OrganizationService;
	}

	public void setI_OrganizationService(I_OrganizationService i_OrganizationService) {
		this.i_OrganizationService = i_OrganizationService;
	}

	public int getRowsInPage() {
		return rowsInPage;
	}

	public void setRowsInPage(int rowsInPage) {
		this.rowsInPage = rowsInPage;
	}
}

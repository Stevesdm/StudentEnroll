package cn.gov.hrss.ln.stuenroll.enroll;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public interface I_EnrollController {

	/**
	 * 查询报名记录
	 */
	public void searchEnroll();

	/**
	 * 查询记录总数
	 */
	public void searchEnrollCount();

	/**
	 * 根据id删除记录
	 */
	public void deleteById();

	/**
	 * 查询所有专业
	 */
	public void searchAllProfession();

	/**
	 * 获得所有学生状态
	 */
	public void searchAllStudent_State();
	/**
	 * 获得所有机构
	 */
	public void searchAllOrganization();
	/**
	 * 获得ID
	 */
	public void searchOrganizationId();
	/**
	 * 获得ID
	 */
	public void searchProfessionId();
	/**
	 * 加入Enroll
	 */
	public void addEnroll();
	public void changeEnroll();


}

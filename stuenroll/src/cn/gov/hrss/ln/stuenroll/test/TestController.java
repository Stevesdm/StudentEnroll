package cn.gov.hrss.ln.stuenroll.test;

import cn.gov.hrss.ln.stuenroll.plugin.MongoKit;
import cn.gov.hrss.ln.stuenroll.test.TestValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.spring.Spring;
import org.eclipse.xtend.lib.annotations.Accessors;

/**
 * 测试类
 */
@Spring("testController")
@Accessors
@SuppressWarnings("all")
public class TestController extends Controller {
  @Before(TestValidator.class)
  public void test1() {
    String username = this.getPara("username");
    String sex = this.getPara("sex");
    Record record = new Record();
    record.set("username", username);
    record.set("sex", sex);
    MongoKit.save("student", record);
    this.renderHtml("OK");
  }
  
  public void test2() {
    Page<Record> page = MongoKit.paginate("student", 1, 20);
    this.renderJson(page);
  }
  
  @Before(TestValidator.class)
  public void test3() {
    Integer int_ = this.getParaToInt("year");
    this.renderHtml("OK");
  }
}

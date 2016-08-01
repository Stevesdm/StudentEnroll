package cn.gov.hrss.ln.stuenroll.test;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

@SuppressWarnings("all")
public class TestValidator extends Validator {
  @Override
  protected void handleError(final Controller controller) {
    controller.redirect("/validator.html");
  }
  
  @Override
  protected void validate(final Controller controller) {
    String _actionKey = this.getActionKey();
    boolean _equals = _actionKey.equals("/test/test1");
    if (_equals) {
      this.validateRegex("username", "^[a-zA-Z0-9]{3,}$", "errorMsg", "姓名错误");
      this.validateRegex("sex", "^[一-龥]{1}$", "errorMsg", "性别错误");
    } else {
      String _actionKey_1 = this.getActionKey();
      boolean _equals_1 = _actionKey_1.equals("/test/test3");
      if (_equals_1) {
        this.validateRegex("year", "^[1-9]\\d{3}$", "errorMsg", "年份错误");
      }
    }
  }
}

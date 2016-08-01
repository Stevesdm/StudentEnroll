/**
 * 抽象的下拉接口
 */
var I_Dropdown = function(){

}
/**
 * 下拉表展开胡抽象方法
 */
I_Dropdown.prototype.startDropdown = function(){
	throw "抽象方法";
}

I_Dropdown.prototype.closeDropdown = function(){
	throw "抽象方法";
}

I_Dropdown.prototype.clearAll = function(){
	throw "抽象方法";
}

var Dropdown = function(){
	
}
/**
 * 继承父类
 */
Dropdown.prototype = new I_Dropdown();

Dropdown.prototype.startDropdown = function(){
}
/**
 * 关闭列表
 */
Dropdown.prototype.closeDropdown = function() {
	var $allDropDown = $(".dropdown"); //全部列表
	var $allList = $(".dropdown-list"); //全部列表的列表项
	$allDropDown.removeClass("dropdown-active");
	$allList.removeClass("dropdown-list-active");
}

Dropdown.prototype.clearAll =function(){
	
}
function factory_1(key){
	if(key=="dropDown"){
		return new Dropdown();
	}
}

$(".dropdown").click(function(){
	var drop = $(this);
	$(drop).toggleClass("dropdown-active");
	//显示下拉选项
	var list = $(drop).find(".dropdown-list");
	$(list).toggleClass("dropdown-list-active");
	
	//点击选择项
	var item = $(list).find(".dropdown-item");
	$(item).click(function(){
		var value = $(this).text();
		$(drop).find(".value").text(value);
	});
});


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

Dropdown.prototype.clearAll - function(){
	
}

function factory(key) {
	if(key == "dropdown"){
		return new Dropdown();
	}
}

$(".dropdown").click(function(e){
	e.stopPropagation();
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


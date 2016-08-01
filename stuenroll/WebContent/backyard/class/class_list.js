$(function(){
	
	var I_Class=function(){
	
	}
	I_Class.prototype.state="班级列表";
	/**
	 * 初始化抽象方法
	 */
	I_Class.prototype.init=function(){
		throw "抽象方法";
	}
	/**
	 * 切换选项卡抽象方法
	 * @param {Object} $scope
	 * @param {Object} index
	 */
	I_Class.prototype.changeTab=function($scope,index){
		throw "抽象方法";
	}
	/**
	 * 加载数据抽象方法
	 * @param {Object} $scope
	 */
	I_Class.prototype.loadData=function($scope){
		throw "抽象方法";
	}
	/**
	 * 显示菜单抽象方法
	 * 
	 * @param {Object} $scope
	 * @param {Object} event
	 */
	I_Class.prototype.showMenu=function($scope,event){
		throw "抽象方法";
	}
	/**
	 * 隐藏菜单方法抽象方法
	 */
	I_Class.prototype.hideMenu=function(){
		throw "抽象方法";
	}
	/**
	 * 查询班级列表抽象方法
	 */
	I_Class.prototype.searchClass=function(json){
		throw "抽象方法";
	}
	
	/**
	 * 班级管理抽象类
	 */
	var Class=function(){
		this.init();		
	}
	//工厂方法
	function factory(key){
		
		if(key=="Class"){
			return new Class();
		}
		else if(key="Tab"){
			return new Tab();
		}
	}
	Class.prototype=new I_Class();
	/**
	 * 初始化实现方法
	 */
	Class.prototype.init=function(){
		//Menu之外的地方点击鼠标，Menu鼠标消失
		var obj=this;
		$(document).click(function(){
			obj.hideMenu();
		});
		
		//Menu内部点击鼠标阻止Menu消失
		$(".menu").click(function(event){
			event.stopPropagation();
		});
	}
	
	//切换面板的实现方法
	var tab=factory("Tab");
	var item=$(".class-container .class-content");
	$(".tab-list .tab-item").click(function(){
		
		var temp = $(this).data("index");
		tab.showTab(temp);
		// 切换选项卡，重新查询数据
		if (temp == "班级列表") {
			class_manage.searchClass();
		}
	});
	
	
	/**
	 * 显示菜单实现方法
	 * @param {Object} $scope
	 * @param {Object} event
	 */
	$(".operation-item").click(function(event){
		//$(".menu").removeClass("show-display");
		var $obj =$(this).find(".menu");		
		$(".menu").fadeOut(); //隐藏所有菜单
		$obj.fadeIn();
		event.stopPropagation();
	});
	/**
	 * 隐藏菜单实现方法
	 */
	var dropDown=factory_1("dropDown");
	Class.prototype.hideMenu=function(){
		var $obj = $(".menu");
		$obj.fadeOut();
		dropDown.closeDropdown();
	}
	/**
	 * 查询班级列表实现方法
	 */
	Class.prototype.searchClass=function(json){
//		$.ajax({
//			"url" : "/stuenroll/enroll/searchEnroll",
//			"type" : "post",
//			"dataType" : "json",
//			"data" : json,
//			"success" : function(json) {
//				var data = json.result;
//				var table = $(".tab-container .tab-content[data-index='全部学员'] .data-table");
//				// 清空表格数据
//				table.find("tr:gt(0)").remove();
//
//				// 获得当前页数
//				var currentPage = $(".tab-container .tab-content[data-index='全部学员'] #currentPage").text();
//				// 转化成数字类型
//				currentPage = new Number(currentPage);
//				// 当前页数的行号起始数字
//				var start = (currentPage - 1) * 35;
//
//				var temp = "";
//				for (var i = 0; i < data.length; i++) {
//					var one = data[i];
//					console.log(one.name);
//					temp += "<tr>";
//					temp += "<td><input type='checkbox' name='id' value='" + one.id + "' /></td>"
//					temp += "<td>" + (start + i + 1) + "</td>";
//					temp += "<td>" + one.name + "</td>";
//					temp += "<td>" + one.pid + "</td>";
//					temp += "<td>" + one.tel + "</td>";
//					temp += "<td>" + one.organization + "</td>";
//					temp += "<td>" + one.profession + "</td>";
//					temp += "<td>" + one.classinfo + "</td>";
//					temp += "<td>" + one.year + "</td>";
//					temp += "<td>" + one.state + "</td>";
//					temp += "</tr>";
//				}
//				table.append(temp);
//			},
//			"error" : function() {
//				toastr.error("系统异常");
//			}
//		});
			//TODO 此处执行Ajax查找全部学员
			var temp="";
			var start=0;
			var table = $(".data-content[data-index='class-content'] .data-table");					
			for (var i = 1; i <= 35; i++) {
					temp += "<tr>";
					temp += "<th><input type='checkbox' name='id' value='' /></th>"
					temp += "<th>" + (start + i ) + "</th>";
					temp += "<th>" + "WJC" + "</th>";
					temp += "<th>" + 1111111+ "</th>";
					temp += "<th>" + 2222222222 + "</th>";
					temp += "<th>" + 3333 + "</th>";
					temp += "<th>" + "软件工程" + "</th>";
					temp += "<th>" + "GT1602" + "</th>";
					temp += "<th>" + 2016+ "</th>";
					temp += "<th>" + 1 + "</th>";
					temp += "</tr>";
					
			}
			table.append(temp);
	}
	var class_manage=factory("Class");
	class_manage.init();
})


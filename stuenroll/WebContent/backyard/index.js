$(function() {

	/**
	 * 抽象的边栏接口
	 */
	var I_Sidebar = function() {

	}

	/**
	 * 切换栏目抽象方法
	 */
	I_Sidebar.prototype.changeSection = function() {
		throw "抽象方法";
	}
	
	/**
	 * 验证权限抽象方法
	 */
	I_Sidebar.prototype.init = function() {
		throw "抽象方法";
	}

	/**
	 * 边栏类
	 */
	var Sidebar = function() {
		$(".sidebar-container .username").text(sessionStorage.username);
		$("#user-organization").text(sessionStorage.organization);
		$("#user-role").text(sessionStorage.role);
	}

	Sidebar.prototype = new I_Sidebar() // 继承父类

	Sidebar.prototype.changeSection = function(url) {
		$(".main-container .page").attr("src", url);
	}
	
	Sidebar.prototype.init = function() {
		var item = $(".sidebar-container .sidebar-item-hidden");
		var permissions = sessionStorage.permissions.split(","); //权限数组
		
		//判断超级管理员权限
		if (permissions.length == 1 && permissions[0] == 0) {
			item.removeClass("sidebar-item-hidden");
			return;
		}
		
		for (var i = 0; i < item.length; i++) {
			var one = item[i];
			var resource = $(one).data("resource");
			var flag = false;
			for (var j = 0; j < permissions.length; j++) {
				if (permissions[j].indexOf(resource+"_") != -1) {
					flag = true;
					break;
				}
			}
			if (flag) {
				$(one).removeClass("sidebar-item-hidden");
			}
		}
	}

	/**
	 * 顶部导航条接口
	 */
	var I_Navbar = function() {

	}
	I_Navbar.prototype.logout = function() {
		throw "抽象方法";
	}

	var Navbar = function() {

	}

	Navbar.prototype = new I_Navbar();

	Navbar.prototype.logout = function() {
		$.ajax({
			"url" : "/stuenroll/user/logout",
			"type" : "post",
			"dataType" : "json",
			"data" : null,
			"success" : function(json) {
				if (json.logout) {
					toastr.success("成功退出系统");
					setTimeout(function(){
						location.href = "/stuenroll/backyard/login.html"; // 跳转到登录页面
					}, 1000);
					
					
				}
				
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	function factory(key) {
		if (key == "Sidebar") {
			return new Sidebar();
		}
		if (key == "Navbar") {
			return new Navbar();
		}
	}

	var sidebar = factory("Sidebar");
	//sidebar.init();

	// 切换栏目事件
	$(".sidebar-container .section").click(function() {
		var url = $(this).data("url");
		sidebar.changeSection(url);

		$(".sidebar-container .section").removeClass("section-active");
		$(this).addClass("section-active");

	});
	
	var navbar = factory("Navbar");
	$("#logout").click(function(){
		navbar.logout();
	});

});
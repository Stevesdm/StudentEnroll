$(function() {
	/**
	 * 机构管理抽象接口
	 */
	var I_Organization = function() {

	}
	I_Organization.prototype.searchOrganization = function(json) {
		throw "抽象方法";
	}
	I_Organization.prototype.searchOrganizationCount = function(json) {
		throw "抽象方法";
	}
	I_Organization.prototype.deleteById = function() {
		throw "抽象方法";
	}
	I_Organization.prototype.addOrganization = function(json) {
		throw "抽象方法";
	}
	I_Organization.prototype.modifyOrganization = function(json) {
		throw "抽象方法";
	}

	var Organization = function() {

	}

	Organization.prototype = new I_Organization();

	/**
	 * 查询机构
	 */
	Organization.prototype.searchOrganization = function(json) {
		$.ajax({
			"url" : "/stuenroll/organization/searchOrganization",
			"type" : "post",
			"dataType" : "text",
			"data" : json,
			"success" : function(json) {
				// 转换成字符串型
				json = json.replace(/id\":(\d+),/g, "id\":\"$1\",");
				json = JSON.parse(json);

				var data = json.result;
				var table = $(".tab-container .tab-content[data-index='机构列表'] .data-table");
				// 清空表格数据
				table.find("tr:gt(0)").remove();

				// 获得当前页数
				var currentPage = $(".tab-container .tab-content[data-index='机构列表'] #currentPage").text();
				// 转化成数字类型
				currentPage = new Number(currentPage);
				// 当前页数的行号起始数字
				var start = (currentPage - 1) * 35
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><input type='checkbox' name='id' value='" + one.id + "' /></td>";
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.abbreviation + "</td>";
					temp += "<td>" + one.address + "</td>";
					temp += "<td>" + one.tel + "</td>";
					temp += "<td>" + one.liaison + "</td>";
					temp += "<td>" + one.professionAmount + "</td>";
					temp += "<td>" + one.classAmount + "</td>";
					temp += "<td>" + one.studentAmount + "</td>";
					temp += "</tr>";
				}
				table.append(temp);

				var totalPages = $("#totalPages").text();

				if (currentPage > totalPages) {
					$(".tab-container .tab-content[data-index='机构列表'] #currentPage").text(1);
				}
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});

	}

	/**
	 * 获取机构数量
	 */
	Organization.prototype.searchOrganizationCount = function(json) {
		$.ajax({
			"url" : "/stuenroll/organization/searchOrganizationCount",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"async" : false,
			"success" : function(json) {
				var count = json.result; // 总记录数
				var content = $(".tab-container .tab-content[data-index='机构列表']");
				content.find("#totalRows").text(count);
				var totalPages = (count % 35 == 0) ? count / 35 : Math.floor(count / 35) + 1;
				content.find("#totalPages").text(totalPages);
			},
			"error" : function() {
				toast.error("系统异常");
			}
		});
	}

	/**
	 * 根据ID删除机构
	 */
	Organization.prototype.deleteById = function() {
		// 获得被选中的记录
		var content = $(".tab-container .tab-content[data-index='机构列表']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框

		if (checkbox.length == 0) {
			alert("每次至少选中一条记录，请重新选择！");
			return;
		}

		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}

		// 弹出确认对话框
		var bool = confirm("是否删除选中的记录？");
		if (bool == false) {
			return;
		}

		$.ajax({
			"url" : "/stuenroll/organization/deleteById",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true, // 发送数组JSON格式
			"async" : false,
			"data" : {
				"id" : id
			},
			"success" : function() {
				toastr.success("成功删除了1条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 添加新机构
	 */
	Organization.prototype.addOrganization = function(json) {
		$.ajax({
			"url" : "/stuenroll/organization/addOrganization",
			"type" : "post",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"success" : function(json) {
				if (json) {
					toastr.success("成功添加了1条记录");
				}
				else {
					toastr.warning("填写的内容不对，请重新核对");
				}
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	/**
	 * 修改机构信息
	 */
	Organization.prototype.modifyOrganization = function(json) {
		$.ajax({
			"url" : "/stuenroll/organization/modifyOrganization",
			"type" : "post",
			"dataType" : "json",
			"async" : false,
			"data" : json,
			"success" : function(json) {
				toastr.success("成功修改1条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	function factory(key) {
		if (key == "Tab") {
			return new Tab();
		}
		else if (key == "Organization") {
			return new Organization();
		}
	}

	var tab = factory("Tab");
	var organization = factory("Organization");

	$(".tab-list .tab-item").click(function() {
		var temp = $(this).data("index");
		tab.showTab(temp);
		// 切换选项卡，重新查询数据
		if (temp == "机构列表") {
			$(".tab-container .tab-content[data-index='全部学员'] #currentPage").text(1);
			enroll.searchEnroll();
			enroll.searchEnrollCount();
		}

	});

	organization.searchOrganization();
	organization.searchOrganizationCount();

	// menu菜单切换
	$(".operation-item").click(function() {
		$(".menu").hide();
		$(this).find(".menu").show();
	});

	var element = $(".tab-container .tab-content[data-index='机构列表']");

	element.find("*[name='prevBtn']").click(function() {
		var temp = $(this).parents(".page-list").find("#currentPage");
		var currentPage = temp.text();
		currentPage = new Number(currentPage);
		if (currentPage > 1) {
			// TODO 根据隐藏的查询面板的设置条件查询数据

			// 请求Ajax并更新数据
			enroll.searchEnroll({
				"page" : currentPage - 1
			});
			temp.text(currentPage - 1);// 当前页数减1
		}
	});

	element.find("*[name='nextBtn']").click(function() {
		var temp = $(this).parents(".page-list").find("#currentPage");
		var currentPage = temp.text();
		var totalPages = $(this).parents(".page-list").find("#totalPages").text();
		currentPage = new Number(currentPage);
		totalPages = new Number(totalPages);
		if (currentPage < totalPages) {
			// TODO 根据隐藏的查询面板的设置条件查询数据

			// 请求Ajax并更新数据
			enroll.searchEnroll({
				"page" : currentPage + 1
			});
			temp.text(currentPage + 1);// 当前页数加1
		}
	});

	// 删除信息选项
	element.find("*[name='delete']").click(function() {
		// 先删除
		organization.deleteById();
		// 再查询
		organization.searchOrganizationCount();
		var totalPages = $(this).parents(".tab-container").find("#totalPages").text();
		var currentPage = $(this).parents(".tab-container").find("#currentPage").text();
		totalPages = new Number(totalPages);
		currentPage = new Number(currentPage);
		if (currentPage > totalPages) {
			currentPage = totalPages;
		}

		$(this).parents(".tab-container").find("#currentPage").text(currentPage); // 更新当前页数
		organization.searchOrganization({
			"page" : currentPage
		});
	});

	// 查询菜单-查找按钮
	element.find(".btn[name='query']").click(function(f) {
		f.stopPropagation();

		var name = element.find(".operation-item[name='search'] #organization-name").val();
		var abbreviation = element.find(".operation-item[name='search'] #organization-abbreviation").val();
		var address = element.find(".operation-item[name='search'] #organization-address").val();
		var liaison = element.find(".operation-item[name='search'] #liaison").val();
		var tel = element.find(".operation-item[name='search'] #tel").val();

		var json = {
			"name" : name,
			"abbreviation" : abbreviation,
			"address" : address,
			"liaison" : liaison,
			"tel" : tel
		}

		organization.searchOrganization(json);
		organization.searchOrganizationCount(json);

		$(".operation-item[name='search'] .menu").hide();
	});

	// 清空填写内容按钮
	element.find(".btn[name='clean']:eq(0)").click(function(f) {
		f.stopPropagation();

		// 清空文本框内容
		element.find(".input").val("");

	});
	
	element.find(".btn[name='clean']:eq(1)").click(function(f) {
		f.stopPropagation();

		// 清空文本框内容
		element.find(".input").val("");

	});
	
	element.find(".btn[name='clean']:eq(3)").click(function(f) {
		f.stopPropagation();

		// 清空文本框内容
		element.find(".input").val("");

	});

	// 添加菜单-保存数据按钮
	element.find(".operation-item[name='add'] .btn[name='save']").click(function(f) {
		f.stopPropagation();

		var name = element.find(".operation-item[name='add'] #organization-name").val();
		var abbreviation = element.find(".operation-item[name='add'] #organization-abbreviation").val();
		var address = element.find(".operation-item[name='add'] #organization-address").val();
		var liaison = element.find(".operation-item[name='add'] #liaison").val();
		var tel = element.find(".operation-item[name='add'] #tel").val();

		var json = {
			"name" : name,
			"abbreviation" : abbreviation,
			"address" : address,
			"liaison" : liaison,
			"tel" : tel
		}

		organization.addOrganization(json);
		organization.searchOrganization();
		organization.searchOrganizationCount();

		$(".operation-item[name='add'] .menu").hide();
	});

	// 点击修改，菜单页面显示被选中机构的信息
	// 先解绑，再绑定
	element.find(".operation-item[name='modify']").unbind('click');
	element.find(".operation-item[name='modify']").click(function() {
		// 获得被选中的记录
		var content = $(".tab-container .tab-content[data-index='机构列表']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框

		// 判断选中的个数
		if (checkbox.length == 0) {
			alert("每次至少选中一条记录，请重新选择！");
			return;
		}
		else if (checkbox.length > 1) {
			alert("每次只能修改一条记录，请重新选择！");
			return;
		}

		$(".menu").hide();
		$(this).find(".menu").show();

		// 获取机构信息显示到修改菜单
		var information = checkbox.parents("tr");
		var name = information.find("td:eq(2)").text();
		var abbreviation = information.find("td:eq(3)").text();
		var address = information.find("td:eq(4)").text();
		var tel = information.find("td:eq(5)").text();
		var liaison = information.find("td:eq(6)").text();

		element.find(".operation-item[name='modify'] #organization-name").attr('value', name);
		element.find(".operation-item[name='modify'] #organization-abbreviation").attr('value', abbreviation);
		element.find(".operation-item[name='modify'] #organization-address").attr('value', address);
		element.find(".operation-item[name='modify'] #tel").attr('value', tel);
		element.find(".operation-item[name='modify'] #liaison").attr('value', liaison);
	});

	element.find(".btn[name='modify']").click(function(f) {
		f.stopPropagation();

		// 获得被选中的记录
		var content = $(".tab-container .tab-content[data-index='机构列表']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框

		var id = checkbox.val();
		var name = element.find(".operation-item[name='modify'] #organization-name").val();
		var abbreviation = element.find(".operation-item[name='modify'] #organization-abbreviation").val();
		var address = element.find(".operation-item[name='modify'] #organization-address").val();
		var liaison = element.find(".operation-item[name='modify'] #liaison").val();
		var tel = element.find(".operation-item[name='modify'] #tel").val();

		var json = {
			"id" : id,
			"name" : name,
			"abbreviation" : abbreviation,
			"address" : address,
			"liaison" : liaison,
			"tel" : tel
		}

		organization.modifyOrganization(json);
		organization.searchOrganization();
		organization.searchOrganizationCount();

		$(".operation-item[name='modify'] .menu").hide();
	});

	// 清除修改内容
	element.find(".btn[name='clean']:eq(2)").unbind('click');
	element.find(".btn[name='clean']:eq(2)").click(function(f) {
		f.stopPropagation();

		// 清空文本框内容
		element.find(".operation=item[name='modify'] .input").attr('value', '');
	});
});
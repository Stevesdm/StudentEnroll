$(function() {
	// if (!checkPermission(["3_4"])) {
	// return;
	// }
	// var tabContainer = $(".tab-container");
	// if (checkPermission(["3_4"])) {
	// tabContainer.find(".operation-item[name='search']").show();
	// }
	// if (checkPermission(["3_2"])) {
	// tabContainer.find(".operation-item[name='delete']").show();
	// }
	/**
	 * 报名管理抽象接口
	 */
	var I_Enroll = function() {

	}

	I_Enroll.prototype.searchEnroll = function(json) {
		throw "抽象方法";
	}
	I_Enroll.prototype.searchEnrollCount = function(json) {
		throw "抽象方法";
	}
	I_Enroll.prototype.deleteById = function() {
		throw "抽象方法";
	}
	I_Enroll.prototype.searchAllProfession = function() {
		throw "抽象方法";
	}
	I_Enroll.prototype.searchAllStudent_State = function() {
		throw "抽象方法";
	}
	I_Enroll.prototype.searchAllOrganization = function() {
		throw "抽象方法";
	}

	I_Enroll.prototype.addEneoll = function(json) {
		throw "抽象方法";
	}

	I_Enroll.prototype.changeEneoll = function(json) {
		throw "抽象方法";
	}
	
	I_Enroll.prototype.showEnrollById = function(id,div) {
		throw "抽象方法";
	}

	var Enroll = function() {

	}
	Enroll.prototype = new I_Enroll();
	Enroll.prototype.searchEnroll = function(json) {
		$.ajax({
			"url" : "/stuenroll/enroll/searchEnroll",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				var data = json.result;
				var table = $(".tab-container .tab-content[data-index='全部学员'] .data-table");
				// 清空表格数据
				table.find("tr:gt(0)").remove();

				// 获得当前页数
				var currentPage = $(".tab-container .tab-content[data-index='全部学员'] #currentPage").text();
				// 转化成数字类型
				currentPage = new Number(currentPage);
				// 当前页数的行号起始数字
				var start = (currentPage - 1) * 35;

				var temp = "";
				for (var i = 0; i < data.length; i++) {
					var one = data[i];
					temp += "<tr>";
					temp += "<td><input type='checkbox' name='id' value='" + one.id + "' /></td>"
					temp += "<td>" + (start + i + 1) + "</td>";
					temp += "<td>" + one.name + "</td>";
					temp += "<td>" + one.pid + "</td>";
					temp += "<td>" + one.tel + "</td>";
					temp += "<td>" + one.organization + "</td>";
					temp += "<td>" + one.profession + "</td>";
					temp += "<td>" + one.classinfo + "</td>";
					temp += "<td>" + one.year + "</td>";
					temp += "<td>" + one.state + "</td>";
					temp += "</tr>";
				}
				table.append(temp);
				// 瞎鸡巴改
				var totalPages = $("#totalPages").text();

				if (currentPage > totalPages) {
					$(".tab-container .tab-content[data-index='全部学员'] #currentPage").text(1);
				}

			},
			"error" : function() {
				toast.error("系统异常");
			}
		});
	}
	Enroll.prototype.searchEnrollCount = function(json) {
		$.ajax({
			"url" : "/stuenroll/enroll/searchEnrollCount",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
				var count = json.result; // 总记录数
				var content = $(".tab-container .tab-content[data-index='全部学员']");
				content.find("#totalRows").text(count);
				var totalPages = (count % 35 == 0) ? count / 35 : Math.floor(count / 35) + 1;
				content.find("#totalPages").text(totalPages);
			},
			"error" : function() {
				toast.error("系统异常");
			}
		});
	}

	Enroll.prototype.deleteById = function() {
		// 弹出确认对话框
		var bool = confirm("是否删除选中的记录？");
		if (bool == false) {
			return;
		}
		// 获得被选中的记录
		var content = $(".tab-container .tab-content[data-index='全部学员']");
		var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框

		var id = [];
		for (var i = 0; i < checkbox.length; i++) {
			id.push($(checkbox[i]).val());
		}

		$.ajax({
			"url" : "/stuenroll/enroll/deleteById",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true, // 发送数组JSON格式
			"async" : false,
			"data" : {
				"id" : id
			},
			"success" : function(json) {
				toastr.success("删除了" + json.deleteRows + "条记录");
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

	Enroll.prototype.searchAllProfession = function() {
		var div = $(".dropdown[name='profession'] .dropdown-list ");
		div.find(".dropdown-item").remove();

		$.ajax({
			"url" : "/stuenroll/enroll/searchAllProfession",
			"type" : "post",
			"dataType" : "json",
			"data" : null,
			"success" : function(json) {
				var data = json.result;
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					temp += "<li class=\"dropdown-item\">";
					temp += data[i].name;
					temp += "</li>";
				}
				div.append(temp);
			},
			"error" : function() {
				toastr.error("系统异常");

			}
		});

	}

	Enroll.prototype.searchAllStudent_State = function() {
		var div = $("#searchItem .dropdown[name='studentstate'] .dropdown-list ");
		div.find(".dropdown-item").remove();

		$.ajax({
			"url" : "/stuenroll/enroll/searchAllStudent_State",
			"type" : "post",
			"dataType" : "json",
			"data" : null,
			"success" : function(json) {
				var data = json.result;
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					temp += "<li class=\"dropdown-item\">";
					temp += data[i].name;
					temp += "</li>";
				}
				div.append(temp);
			},
			"error" : function() {
				toastr.error("系统异常");

			}
		});

	}
	Enroll.prototype.searchAllOrganization = function() {
		var div = $(" .dropdown[name='organization'] .dropdown-list ");
		div.find(".dropdown-item").remove();

		$.ajax({
			"url" : "/stuenroll/enroll/searchAllOrganization",
			"type" : "post",
			"dataType" : "json",
			"data" : null,
			"success" : function(json) {
				var data = json.result;
				var temp = "";
				for (var i = 0; i < data.length; i++) {
					temp += "<li class=\"dropdown-item\">";
					temp += data[i].name;
					temp += "</li>";
				}
				div.append(temp);
			},
			"error" : function() {
				toastr.error("系统异常");

			}
		});

	}

	Enroll.prototype.addEnroll = function(json) {
		var bool = confirm("是否确认添加？");
		if (bool == false) {
			return;
		}

		$.ajax({
			"url" : "/stuenroll/enroll/addEnroll",
			"type" : "post",
			"dataType" : "json",
			"data" : json,
			"success" : function(json) {
			},
			"error" : function() {
				toastr.error("加入失败");

			}
		});

	}

	Enroll.prototype.changeEneoll = function() {
		// 获得被选中的记录

		// 弹出确认对话框
		var bool = confirm("是否确认修改选中的记录？");
		if (bool == false) {
			return;
		}

		var id=checkbox.val();
		$.ajax({
			"url" : "/stuenroll/enroll/searchEnroll",
			"type" : "post",
			"dataType" : "json",
			"traditional" : true, // 发送数组JSON格式
			"async" : false,
			"data" : {
				"id" : id
			},
			"success" : function(json) {
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
		else if (key == "Enroll") {
			return new Enroll();
		}
	}
	
	
	Enroll.prototype.showEnrollById = function(id,div) {
		$.ajax({
			"url" : "/stuenroll/enroll/searchEnroll",
			"type" : "post",
			"dataType" : "json",
			"data" : {
				"id":id,
			},
			"success" : function(json) {
				var name=div.find("input[name='name']");
				var pid=div.find("input[name='pid']");
				var graduate_date=div.find("input[name='graduate_date']");
				var healthy=div.find(".dropdown[name='healthy'] .value");
				var resident_address=div.find("input[name='resident_address']");
				var tel=div.find("input[name='tel']");
				var organization=div.find(".dropdown[name='organization'] .value");
				var sex=div.find(".dropdown[name='sex'] .value");
				var graduate_school=div.find("input[name='graduate_school']");
				var education=div.find(".dropdown[name='education'] .value");
				var politics=div.find(".dropdown[name='politics'] .vlaue");
				var home_address=div.find("input[name='home_address']");
				var home_tel=div.find("input[name='home_tel']");
				var profession=div.find(".dropdown[name='profession'] .value");
				var nation=div.find(".dropdown[name='nation'] .value");
				var graduate_year=div.find(".dropdown[name='graduate_year' .value]");
				var major=div.find(".dropdown[name='major'] .value");
				var birthday=div.find("input[name='birthday']");
				var permanent_address=div.find("input[name='permanent_address']");
				var email=div.find("input[name='email']");
				var place=div.find(".dropdown[name='place'] .value");
				
				
				alert(1);
				var data = json.result;
				name.val(data[0].name);
				pid.val(data[0].pid);
				graduate_date.val(data[0].graduate_date);
				healthy.text(data[0].healthy);
				resident_address.val(data[0].resident_address);
				tel.val(data[0].tel);
				organization.text(data[0].organization);
				sex.text(data[0].sex);
				graduate_school.val(data[0].graduate_school);
				education.text(data[0].education);
				politics.text(data[0].politics);
				home_address.val(data[0].home_address);
				home_tel.val(data[0].home_tel);
				profession.text(data[0].profession);
				nation.text(data[0].nation);
				graduate_year.text(data[0].graduate_year);
				major.text(data[0].major);
				birthday.val(data[0].birthday);
				permanent_address.val(data[0].permanent_address);
				email.val(data[0].email);
				place.text(data[0].place);
//				alert(data[0].name);
			},
			"error" : function() {
				toast.error("系统异常");
			}
		});
	}
	
	
	
	
	
	

	var tab = factory("Tab");
	var enroll = factory("Enroll");

	$(".tab-list .tab-item").click(function() {
		var temp = $(this).data("index");
		tab.showTab(temp);
		// 切换选项卡，重新查询数据
		if (temp == "全部学员") {
			$(".tab-container .tab-content[data-index='全部学员'] #currentPage").text(1);
			enroll.searchEnroll();
			enroll.searchEnrollCount();
		}

	});

	enroll.searchEnroll();
	enroll.searchEnrollCount();

	var element = $(".tab-container .tab-content[data-index='全部学员']");
	element.find("*[name='prevBtn']").click(function() {
		var temp = $(this).parents(".page-list").find("#currentPage");
		var currentPage = temp.text();
		currentPage = new Number(currentPage);
		if (currentPage > 1) {
			// TODO 根据隐藏的查询面板的设置条件查询数据
			// 请求Ajax并更新数据

			var div = $(".content button[type='submit'][name='search']");

			var divs = div.parents(".item-list ").find(".dropdown .value");
			$(divs).each(function(i, one) {
				if ($(one).text() == '- 选择 -')
					$(one).text(null);
			});

			var name = div.parents(".item-list ").find("input[name='name']").val();
			var sex = div.parents(".item-list ").find(".dropdown[name='sex'] .value").text();
			var profession = div.parents(".item-list ").find(".dropdown[name='profession'] .value").text();
			var pid = div.parents(".item-list ").find("input[name='pid']").val();
			var education = div.parents(".item-list ").find(".dropdown[name='education'] .value").text();
			var classinfoId = div.parents(".item-list ").find(".dropdown[name='classinfoId'] .value").text();
			var year = div.parents(".item-list ").find("input[name='year']").val();
			var organization = div.parents(".item-list ").find(".dropdown[name='organization'] .value").text();
			var studentstate = div.parents(".item-list ").find(".dropdown[name='studentstate'] .value").text();

			enroll.searchEnroll({
				"name" : name,
				"sex" : sex,
				"profession" : profession,
				"pid" : pid,
				"education" : education,
				"classinfoId" : classinfoId,
				"year" : year,
				"organization" : organization,
				"studentstate" : studentstate,
				"page" : currentPage - 1
			});
			temp.text(currentPage - 1); // 当前页数减去1页
		}

		$(divs).each(function(i, one) {
			if ($(one).text() == '') {
				$(one).text("- 选择 -");
			}
		});

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
			var div = $(".content button[type='submit'][name='search']");

			var divs = div.parents(".item-list ").find(".dropdown .value");
			$(divs).each(function(i, one) {
				if ($(one).text() == '- 选择 -')
					$(one).text(null);
			});

			var name = div.parents(".item-list ").find("input[name='name']").val();
			var sex = div.parents(".item-list ").find(".dropdown[name='sex'] .value").text();
			var profession = div.parents(".item-list ").find(".dropdown[name='profession'] .value").text();
			var pid = div.parents(".item-list ").find("input[name='pid']").val();
			var education = div.parents(".item-list ").find(".dropdown[name='education'] .value").text();
			var classinfoId = div.parents(".item-list ").find(".dropdown[name='classinfoId'] .value").text();
			var year = div.parents(".item-list ").find("input[name='year']").val();
			var organization = div.parents(".item-list ").find(".dropdown[name='organization'] .value").text();
			var studentstate = div.parents(".item-list ").find(".dropdown[name='studentstate'] .value").text();

			enroll.searchEnroll({
				"name" : name,
				"sex" : sex,
				"profession" : profession,
				"pid" : pid,
				"education" : education,
				"classinfoId" : classinfoId,
				"year" : year,
				"organization" : organization,
				"studentstate" : studentstate,
				"page" : currentPage + 1
			});
			temp.text(currentPage + 1); // 当前页数加上1页
		}
		$(divs).each(function(i, one) {
			if ($(one).text() == '') {
				$(one).text("- 选择 -");
			}
		});
	});
	element.find("*[name='delete']").click(function() {
		// 先删除
		enroll.deleteById();
		// 再查询
		enroll.searchEnrollCount();
		var totalPages = $(this).parents(".tab-container").find("#totalPages").text();
		var currentPage = $(this).parents(".tab-container").find("#currentPage").text();
		totalPages = new Number(totalPages);
		currentPage = new Number(currentPage);
		if (currentPage > totalPages) {
			currentPage = totalPages;
		}

		$(this).parents(".tab-container").find("#currentPage").text(currentPage); // 更新当前页数
		enroll.searchEnroll({
			"page" : currentPage
		});

	});
	$("input").keyup(function() {
		if (this.name != "pid" && this.checkValidity()) {
			$(this).removeClass("error");
		}
		else {
			$(this).addClass("error");
		}

		if (this.name == "pid") {
			if (checkPid($(this).val())) {
				$(this).removeClass("error");
			}
			else {
				$(this).addClass("error");
			}
		}

	});
	$(".operation-item").click(function(e) {
		e.stopPropagation();
		$(".operation-ex-item").hide();
		enroll.searchAllOrganization();
		enroll.searchAllProfession();
		enroll.searchAllStudent_State();
		var div = $(this);
		if(div.attr("name")=='modify'){
			var content = $(".tab-container .tab-content[data-index='全部学员']");
			var checkbox = content.find("*[name='id']:checked"); // 被选中的复选框
			var id=checkbox.val();
			var number = checkbox.length;
			if(number!=1){
				alert("请选中一条记录");
				return;
			}
			enroll.showEnrollById(id,div);
			
		}
		div.find(".operation-ex-item").show();

	});

	$(".content button[type='submit'][name='search']").click(function(e) {
		e.stopPropagation();
		var div = $(this);

		var divs = div.parents(".item-list ").find(".dropdown .value");
		$(divs).each(function(i, one) {
			if ($(one).text() == '- 选择 -')
				$(one).text(null);
		});

		var name = div.parents(".item-list ").find("input[name='name']").val();
		var sex = div.parents(".item-list ").find(".dropdown[name='sex'] .value").text();
		var profession = div.parents(".item-list ").find(".dropdown[name='profession'] .value").text();
		var pid = div.parents(".item-list ").find("input[name='pid']").val();
		var education = div.parents(".item-list ").find(".dropdown[name='education'] .value").text();
		var classinfoId = div.parents(".item-list ").find(".dropdown[name='classinfoId'] .value").text();
		var year = div.parents(".item-list ").find("input[name='year']").val();
		var organization = div.parents(".item-list ").find(".dropdown[name='organization'] .value").text();
		var studentstate = div.parents(".item-list ").find(".dropdown[name='studentstate'] .value").text();

		var json = {
			"name" : name,
			"sex" : sex,
			"profession" : profession,
			"pid" : pid,
			"education" : education,
			"classinfoId" : classinfoId,
			"year" : year,
			"organization" : organization,
			"studentstate" : studentstate

		};
		enroll.searchEnrollCount(json);
		enroll.searchEnroll(json);

		$(divs).each(function(i, one) {
			if ($(one).text() == '') {
				$(one).text("- 选择 -");
			}
		});
		div.parents(".operation-ex-item ").hide();
		enroll.showOrganizationId();

	});

	$(".content button[type='submit'][name='save']").click(function(e) {
		e.stopPropagation();
		var div = $(this);

		var divs = div.parents(".item-list ").find(".dropdown .value");
		$(divs).each(function(i, one) {
			if ($(one).text() == '- 选择 -')
				$(one).text(null);
		});

		var name = div.parents(".item-list ").find("input[name='name']").val();
		var pid = div.parents(".item-list ").find("input[name='pid']").val();
		var graduate_date = div.parents(".item-list ").find("input[name='graduate_date']").val();
		var healthy = div.parents(".item-list ").find(".dropdown[name='healthy'] .value").text();
		var permanent_address = div.parents(".item-list ").find("input[name='permanent_address']").val();
		var tel = div.parents(".item-list ").find("input[name='tel']").val();
		var organization_name = div.parents(".item-list ").find(".dropdown[name='organization'] .value").text();
		var sex = div.parents(".item-list ").find(".dropdown[name='sex'] .value").text();
		var graduate_school = div.parents(".item-list ").find("input[name='graduate_school']").val();
		var education = div.parents(".item-list ").find(".dropdown[name='education'] .value").text();
		var politics = div.parents(".item-list ").find(".dropdown[name='politics'] .value").text();
		var home_address = div.parents(".item-list ").find("input[name='home_address']").val();
		var home_tel = div.parents(".item-list ").find("input[name='home_tel']").val();
		var profession_name = div.parents(".item-list ").find(".dropdown[name='profession'] .value").text();
		var nation = div.parents(".item-list ").find(".dropdown[name='nation'] .value").text();
		var graduate_year = div.parents(".item-list ").find(".dropdown[name='graduate_year'] .value").text();
		var major = div.parents(".item-list ").find(".dropdown[name='major'] .value").text();
		var birthday = div.parents(".item-list ").find("input[name='birthday']").val();
		var resident_address = div.parents(".item-list ").find("input[name='resident_address']").val();
		var email = div.parents(".item-list ").find("input[name='email']").val();
		var place = div.parents(".item-list ").find(".dropdown[name='place'] .value").text();
		$(divs).each(function(i, one) {
			if ($(one).text() == '') {
				$(one).text("- 选择 -");
			}
		});

		var json = {
			"name" : name,
			"sex" : sex,
			"nation" : nation,
			"pid" : pid,
			"graduate_school" : graduate_school,
			"graduate_year" : graduate_year,
			"graduate_date" : graduate_date,
			"education" : education,
			"healthy" : healthy,
			"major" : major,
			"politics" : politics,
			"birthday" : birthday,
			"resident_address" : resident_address,
			"permanent_address" : permanent_address,
			"home_address" : home_address,
			"tel" : tel,
			"home_tel" : home_tel,
			"email" : email,
			"profession_name" : profession_name,
			"organization_name" : organization_name,
			"place" : place,
		};

		enroll.addEnroll(json);

		div.parents(".operation-ex-item ").hide()

	});
	
	$(".content button[name='change']").click(function(e){
		e.stopPropagation();
		
		enroll.changeEneoll();
	});

	$(".content button[name='cancle']").click(function(e) {
		e.stopPropagation();
		var div = $(this);
		var divs = div.parents(".item-list ").find(".dropdown .value");
		var divs2 = div.parents(".item-list ").find("input");
		$(divs).each(function(i, one) {
			$(one).text('- 选择 -');

		});
		$(divs2).each(function(i, one) {
			$(one).val('');

		});
	});

});
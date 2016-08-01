$(function() {

	require.config({
		paths : {
			echarts : '../../js/echarts'
		}
	});

	/**
	 * 年度统计抽象接口
	 */
	var I_AnnualStatistics = function() {

	}

	I_AnnualStatistics.prototype.init = function() {
		throw "抽象方法";
	}

	var AnnualStatistics = function() {

	}

	AnnualStatistics.prototype = new I_AnnualStatistics();

	AnnualStatistics.prototype.init = function() {
		//如果不具备相应权限，下列程序将不会执行
		if (!checkPermission(["3_4", "4_4"] )) {
			return;
		}
		
		var date = new Date();
		var year = date.getFullYear();
		var statistics = $(".annual-statistics");
		statistics.find(".year").text(year);

		// 更新进度
		var month = date.getMonth() + 1;
		items = statistics.find(".step-list .step-item");
		items.removeClass("step-active");
		if (month >= 1 && month < 3) {
			$(items[0]).addClass("step-active");
		}
		else if (month >= 3 && month < 7) {
			$(items[1]).addClass("step-active");
		}
		else if (month >= 7 && month < 9) {
			$(items[2]).addClass("step-active");
		}
		else if (month >= 9 && month < 11) {
			$(items[3]).addClass("step-active");
		}
		else if (month >= 11) {
			$(items[4]).addClass("step-active");
		}

		// Ajax查询年度数据
		$.ajax({
			"url" : "/stuenroll/welcome/statisticsInYear",
			"type" : "post",
			"dataType" : "json",
			"data" : {
				"year" : year
			},
			"success" : function(json) {
				var list_1 = json.statistics.报名数据;
				var list_2 = json.statistics.审查数据;
				var list_3 = json.statistics.学习数据;
				var list_4 = json.statistics.中退数据;
				var list_5 = json.statistics.就业数据;

				// 使用
				require([ 'echarts', 'echarts/chart/line' ], function(ec) {
					// 基于准备好的dom，初始化echarts图表
					var myChart = ec.init($(".statistics")[0], 'macarons');

					var option = {
						tooltip : {
							trigger : 'axis'
						},
						legend : {
							data : [ '报名人数', '审查人数', '学习人数', '中退人数', '就业人数' ]
						},
						toolbox : {
							show : true,
							feature : {
								saveAsImage : {
									show : true
								}
							}
						},
						calculable : false,
						xAxis : [ {
							type : 'category',
							boundaryGap : false,
							data : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月' ]
						} ],
						yAxis : [ {
							type : 'value'
						} ],
						series : [ {
							name : '报名人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list_1
						}, {
							name : '审查人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list_2
						}, {
							name : '学习人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list_3
						}, {
							name : '中退人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list_4
						}, {
							name : '就业人数',
							type : 'line',
							stack : '总量',
							itemStyle : {
								normal : {
									areaStyle : {
										type : 'default'
									}
								}
							},
							data : list_5
						} ]
					};

					// 为echarts对象加载数据
					myChart.setOption(option);
				});
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
		

		//查询培训专业数据
		$.ajax({
			
			"url" : "/stuenroll/welcome/statisticsInProfession",
			"type" : "post",
			"dataType" : "json",
			"data" : {
				"year":year-1
			},
			"success" : function(json) {
				var data = json.profession;
				var list_1 = data[0].a;
				var list_2 = data[1].a;
				var list_3 = data[2].a;
				var list_4 = data[3].a;
				var list_5 = data[4].a;
				
				var list_6 = data[0].b;
				var list_7 = data[1].b;
				var list_8 = data[2].b;
				var list_9 = data[3].b;
				var list_10 = data[4].b;
				
				// 使用
				require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
					// 基于准备好的dom，初始化echarts图表
					var myChart = ec.init($(".profession")[0], 'macarons');

					var option = {
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        x : 'left',
						        data:[list_1,list_2,list_3,list_4,list_5]
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            magicType : {
						                show: true, 
						                type: [],
						                option: {
						                    funnel: {
						                        x: '25%',
						                        width: '50%',
						                        funnelAlign: 'center',
						                        max: data[0].b
						                    }
						                }
						            },
						        }
						    },
						    calculable : true,
						    series : [
						        {
						            name:'',
						            type:'pie',
						            radius : ['50%', '70%'],
						            itemStyle : {
						                normal : {
						                    label : {
						                        show : false
						                    },
						                    labelLine : {
						                        show : false
						                    }
						                },
						                emphasis : {
						                    label : {
						                        show : true,
						                        position : 'center',
						                        textStyle : {
						                            fontSize : '30',
						                            fontWeight : 'bold'
						                        }
						                    }
						                }
						            },
						            data:[
						                {value:list_6, name:list_1},
						                {value:list_7, name:list_2},
						                {value:list_8, name:list_3},
						                {value:list_9, name:list_4},
						                {value:list_10, name:list_5}
						            ]
						        }
						    ]
						};
					// 为echarts对象加载数据
					myChart.setOption(option);
				});
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
		
		//查询培训地域数据
		$.ajax({
			"url" : "/stuenroll/welcome/statisticsInPlace",
			"type" : "post",
			"dataType" : "json",
			"data" : {
				"year":year-1
			},
			"success" : function(json) {
				var data = json.city;
				var list_1 = data[0].place;
				var list_2 = data[1].place;
				
				var list_3 = data[0].a;
				var list_4 = data[1].a;
				// 使用
				require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
					// 基于准备好的dom，初始化echarts图表
					var myChart = ec.init($(".city")[0], 'macarons');

					var option = {
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        x : 'left',
						        data:[list_1,list_2]
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            magicType : {
						                show: true, 
						                type: [],
						                option: {
						                    funnel: {
						                        x: '25%',
						                        width: '50%',
						                        funnelAlign: 'center',
						                        max: data[0].a
						                    }
						                }
						            },
						        }
						    },
						    calculable : true,
						    series : [
						        {
						            name:'',
						            type:'pie',
						            radius : ['50%', '70%'],
						            itemStyle : {
						                normal : {
						                    label : {
						                        show : false
						                    },
						                    labelLine : {
						                        show : false
						                    }
						                },
						                emphasis : {
						                    label : {
						                        show : true,
						                        position : 'center',
						                        textStyle : {
						                            fontSize : '30',
						                            fontWeight : 'bold'
						                        }
						                    }
						                }
						            },
						            data:[
						                {value:list_3, name:list_1},
						                {value:list_4, name:list_2},
						            ]
						        }
						    ]
						};
					// 为echarts对象加载数据
					myChart.setOption(option);
				});
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
		
		//查询培训学历数据
		$.ajax({
			"url" : "/stuenroll/welcome/statisticsInEducation",
			"type" : "post",
			"dataType" : "json",
			"data" : {
				"year":year-1
			},
			"success" : function(json) {
				var data = json.education;
				var list_1 = data[0].education;
				var list_2 = data[1].education;
				
				var list_3 = data[0].a;
				var list_4 = data[1].a;
				// 使用
				require([ 'echarts', 'echarts/chart/pie' ], function(ec) {
					// 基于准备好的dom，初始化echarts图表
					var myChart = ec.init($(".education")[0], 'macarons');

					var option = {
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    },
						    legend: {
						        x : 'left',
						        data:[list_1,list_2]
						    },
						    toolbox: {
						        show : true,
						        feature : {
						            magicType : {
						                show: true, 
						                type: [],
						                option: {
						                    funnel: {
						                        x: '25%',
						                        width: '50%',
						                        funnelAlign: 'center',
						                        max: data[0].a
						                    }
						                }
						            },
						        }
						    },
						    calculable : true,
						    series : [
						        {
						            name:'',
						            type:'pie',
						            radius : ['50%', '70%'],
						            itemStyle : {
						                normal : {
						                    label : {
						                        show : false
						                    },
						                    labelLine : {
						                        show : false
						                    }
						                },
						                emphasis : {
						                    label : {
						                        show : true,
						                        position : 'center',
						                        textStyle : {
						                            fontSize : '30',
						                            fontWeight : 'bold'
						                        }
						                    }
						                }
						            },
						            data:[
						                {value:list_3, name:list_1},
						                {value:list_4, name:list_2},
						            ]
						        }
						    ]
						};
					// 为echarts对象加载数据
					myChart.setOption(option);
				});
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
		
		
		//查询培训专业数据
		$.ajax({
			
			"url" : "/stuenroll/welcome/statisticsInWork",
			"type" : "post",
			"dataType" : "json",
			"data" : {
				"year":year-1
			},
			"success" : function(json) {
				var data = json.work;
				var list_1 = data[0].a;
				var list_2 = data[1].a;
				var list_3 = data[2].a;
				var list_4 = data[3].a;
				var list_5 = data[4].a;
				var list_6 = data[5].a;
				
				var list_7 = data[0].b;
				var list_8 = data[1].b;
				var list_9 = data[2].b;
				var list_10 = data[3].b;
				var list_11 = data[4].b;
				var list_12 = data[5].b;
				
				// 使用
				require([ 'echarts', 'echarts/chart/radar' ], function(ec) {
					// 基于准备好的dom，初始化echarts图表
					var myChart = ec.init($(".work")[0], 'macarons');

					option = {
						    tooltip : {
						        trigger: 'axis'
						    },
						    legend: {
						        x : 'center',
						        data:['']
						    },

						    calculable : true,
						    polar : [
						        {
						            indicator : [
						                {text : list_1, max  : 100},
						                {text : list_2, max  : 100},
						                {text : list_3, max  : 100},
						                {text : list_4, max  : 100},
						                {text : list_5, max  : 100},
						                {text : list_6, max  : 100}
						            ],
						            radius : 130
						        }
						    ],
						    series : [
						        {
						            name: '',
						            type: 'radar',
						            itemStyle: {
						                normal: {
						                    areaStyle: {
						                        type: 'default'
						                    }
						                }
						            },
						            data : [
						             
						                {
						                    value : [list_7,list_8 ,list_9 ,list_10 ,list_11,list_12 ],
						                    name : ''
						                }
						            ]
						        }
						    ]
						};
						                    
					// 为echarts对象加载数据
					myChart.setOption(option);
				});
			},
			"error" : function() {
				toastr.error("系统异常");
			}
		});
	}

		
	function factory(key) {
		if (key == "AnnualStatistics") {
			return new AnnualStatistics();
		}
	}

	var annualStatistics = factory("AnnualStatistics");
	annualStatistics.init();
});
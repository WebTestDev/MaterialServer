$(function(){
	$('header>ul').on('click','li',function(){
		$( $(this).attr('name') ).show().siblings().hide();
		sessionStorage.page = $(this).attr('name');
	});
});
var accountTrlist, galleryTrlist, productTrlist, producterTrlist;//tr列表
var page = 100;//每页数量
//账户管理
function accountLoad(){
	$('#loading').show();//loading显示
	var nowDate = new Date().getTime();
	$.ajax({
		type:"get",
		url: '/TestServer/rest/materail/reqUserList?nowDate=' + nowDate,
//		url: 'php/account.php',
		async: false,
		dataType:'json',
		success: function(e){
			accountSec(e);
		},
		error: function(){
			$('#loading').hide();//loading隐藏
			maskShow('连接服务器错误!');
		}
	});
}
function accountSec(e){
	var dataLth = e.data.length;
	var dataPage = Math.ceil(dataLth/page);//页数
	var trlist = '';
	for( var i=0; i<dataLth; i++ ){
		trlist += '<tr name="'+ e.data[i].userID +'"><td><input type="radio" name="ad"/></td><td>'+ 
				  e.data[i].username +'</td><td>'+ e.data[i].passwd +'</td><td>'+
				  e.data[i].employname +'</td><td>'+ e.data[i].authority +'</td></tr>';
	}
	$('#account .tfooter span').html('1');
	$('#account .tfooter b').html( dataPage );//页数
	$('#account tbody').html( trlist );
	accountTrlist = $('#account tbody>tr');//改变全局变量
	for( var j=0; j<page; j++ ){
		accountTrlist.eq(j).show();
	}
	$('#loading').hide();//loading隐藏
}
$(function(){
	//账户增加
	$('#adduser').click(function(){
		if( $('#table1>table>tbody>tr').length != 0 ){
			$('#modal').attr('name','add');//添加标记
			modalShow();
		}else{
			maskShow('账户列表不能为空!');
		}
	});
	//表单验证
	$('#adminAccount').focusout(function(){//账户
		if( $(this).val() != '' ){
			if( $('#modal').attr('name') == 'add' ){//如果是添加账户
				var No = true;
				for( var i=0; i<accountTrlist.length; i++ ){
					if( $(this).val() == accountTrlist.eq(i).children('td').eq(1).html() ){
						No = false;
					}
				}
				if(No){
					form(this,'正确!',1);
				}else{
					form(this,'账号已存在!',0);
				}
			}else{
				form(this,'正确!',1);
			}
		}else{
			form(this,'输入不能为空!',0);
		}
	});
	$('#adminPsw').focusout(function(){//密码
		if( $(this).val() != '' ){
			form(this,'正确!',1);
		}else{
			form(this,'输入不能为空!',0);
		}
	});
	$('#adminName').focusout(function(){//姓名
		if( $(this).val() != '' ){
			form(this,'正确!',1);
		}else{
			form(this,'输入不能为空!',0);
		}
	});
	$('.modal_submit').click(function(){//提交
		var arr = [ '#adminAccount', '#adminPsw', '#adminName' ];
		for( var a=0; a<3; a++ ){
			$(arr[a]).val() != '' ? form(arr[a],'正确!',1) : form(arr[a],'输入不能为空!',0);
		}
		if( $('#adminAccount').hasClass('iptSec') && $('#adminPsw').hasClass('iptSec') && $('#adminName').hasClass('iptSec') ){
			var checkList = $('#modalbox label>input:checked');
			if( checkList.length != 0 ){
				$('#loading').show();//loading显示
				for( var i=0,rights=''; i<checkList.length; i++ ){//获取rights
					rights += "<span id='"+ checkList.eq(i).attr('mr') +"'>"+ checkList.eq(i).attr('mc') + "</span> ";
				}
				if( $('#modal').attr('name') == 'add' ){//如果是添加账户
					var addObj = {
						"add_account": '' + $('#adminAccount').val(),
						"add_name": '' + $('#adminPsw').val(),
						"add_password": '' + $('#adminName').val(),
						"add_rights": rights
					};
					$.ajax({
						type: "post",
						url: "/TestServer/rest/materail/reqAddUser",
						contentType: "application/json",
						data: JSON.stringify(addObj),
						dataType : "json",
						success: function(backdata){
							modalClose();//关闭
							$('#loading').hide();//loading隐藏
							if (backdata.data.length > 0) {//返回数据的处理
								//将新账户添加到table中
								$('#account .tfooter>button').click();
								maskShow('提交成功!');
							}else{
								maskShow('提交出错!');
							}
						},
						error: function(){
							modalClose();//关闭
							$('#loading').hide();//loading隐藏
							maskShow('连接服务器错误!');
						}
					});
				}else{//如果是编辑账户提交
					var editObj = {
						"edit_id" : $('input[name="ad"]:checked').parent().parent().attr('name'),
						"edit_name" : '' + $('#adminAccount').val(),
						"edit_account" : '' + $('#adminPsw').val(),
						"edit_password" : '' + $('#adminName').val(),
						"edit_rights" : rights
					};
					$.ajax({
						type: "post",
						url: "/TestServer/rest/materail/reqUpdateUser",
						data: JSON.stringify(editObj),
						contentType: "application/json",
						dataType: "json",
						success: function(backdata){
							modalClose();//关闭
							$('#loading').hide();//loading隐藏
							if(backdata.data.length > 0){
								//将新账户添加到table中
								$('#account .tfooter>button').click();
		                        maskShow('提交成功!');
							}else{
								maskShow('提交出错!');
							}
						},
						error: function(){
							modalClose();//关闭
							$('#loading').hide();//loading隐藏
							maskShow('连接服务器错误!');
						}
					});
				}
			}
		}
	});
	//账户修改
	$('#change').click(function(){
		if( $('#table1>table tr').length != 0 ){
			if( $('#table1>table input[name="ad"]:checked').length != 0 ){
				var changeTr = $('input[name="ad"]:checked').parent();
				$('#adminAccount').val( changeTr.siblings('td').eq(0).html() );
				$('#adminPsw').val( changeTr.siblings('td').eq(1).html() );
				$('#adminName').val( changeTr.siblings('td').eq(2).html() );
				if( changeTr.siblings('td').eq(3).html().indexOf('rights1') != -1 ){
					$('#modalbox label').eq(0).children('input').prop('checked',true);
				}
				if( changeTr.siblings('td').eq(3).html().indexOf('rights2') != -1 ){
					$('#modalbox label').eq(1).children('input').prop('checked',true);
				}
				if( changeTr.siblings('td').eq(3).html().indexOf('rights3') != -1 ){
					$('#modalbox label').eq(2).children('input').prop('checked',true);
				}
				modalShow();
			}else{
				maskShow('请选中你要修改的行!');
			}
		}else{
			maskShow('账户列表不能为空!');
		}
	});
	
	//账户删除
	$('#delete').click(function(){
		if( $('#table1>table tr').length != 0 ){
			if( $('#table1>table input[name="ad"]:checked').length != 0 ){
				$('#loading').show();//loading显示
				var delTrid = $('input[name="ad"]:checked').parent().parent();
				$.ajax({
	                type: "post",
	                url: "/TestServer/rest/materail/reqDelUser",
	                data: JSON.stringify({"id" : ''+delTrid.attr('name')}),
	                contentType: "application/json",
	                dataType: "json",
	                success: function(backdata) {
	                	$('#loading').hide();//loading隐藏
	                    if(backdata){
	                        //删除选中行###
	                        $('#account .tfooter>button').click();
	                        maskShow('删除成功!');
	                    }else{
	                        maskShow('删除失败!');
	                    }
	                },
	                error: function(){
	                	$('#loading').hide();//loading隐藏
	                    maskShow('连接服务器错误!');
	                }
	            });
			}else{
				maskShow('请选中你要删除的行!');
			}
		}else{
			maskShow('账户列表不能为空!');
		}
	});
});

//辅料管理
function galleryLoad(){
	$('#loading').show();//loading显示
	var nowDate = new Date().getTime();
	$.ajax({
		type: "get",
		url: '/TestServer/rest/materail/reqAuxiliaryInfoList?nowDate=' + nowDate,
//		url: 'php/grallery.php',
		async: false,
		dataType: 'json',
		success: function(e){
			if( e.return_code == 'success' ){
				gallerySec(e);
			}else{
				$('#loading').hide();//loading隐藏
				maskShow('没有记录!');
			}
		},
		error: function(){
			$('#loading').hide();//loading隐藏
			maskShow('连接服务器错误!');
		}
	});
}
function gallerySec(e){
	var dataLth = e.data.length;
	var dataPage = Math.ceil(dataLth/page);//页数
	var trlist = '';
	for( var i=0; i<dataLth; i++ ){
		trlist += '<tr><td>'+ e.data[i].invcode +'</td><td>'+ e.data[i].invname
				+'</td><td>'+ e.data[i].invstd +'</td><td>'+ e.data[i].validityperiod
				+'</td><td>'+ e.data[i].brand +'</td><td>'+ e.data[i].origin
				+'</td><td>'+ e.data[i].models +'</td></tr>';
	}
	$('#gallery .tfooter span').html('1');
	$('#gallery .tfooter b').html( dataPage );
	$('#gallery tbody').html( trlist );
	galleryTrlist = $('#gallery tbody>tr');//改变全局变量
	for( var j=0; j<page; j++ ){
		galleryTrlist.eq(j).show();
	}
	$('#loading').hide();//loading隐藏
}

//仓库管理
function productLoad(){
	if( $('#date').val() != '' ){
		$('#loading').show();//loading显示
		$.ajax({
			type:"post",
			url: '/TestServer/rest/materail/reqAllUnLoadingInfo',
//			url: 'php/product.php',
			data: JSON.stringify({"date" : $('#date').val()}),
			contentType: "application/json",
			async: false,
			dataType:'json',
			success: function(e){
				if( e.return_code == 'success' ){
					productSec(e);
				}else if( e.return_code == 'fail' ){
					$('#loading').hide();//loading隐藏
					maskShow('没有记录!');
				}
			},
			error: function(){
				$('#loading').hide();//loading隐藏
				maskShow('连接服务器错误!');
			}
		});
	}else{
		maskShow('请选择日期!');
	}
}
function productSec(e){
	var dataLth = e.data.length;
	var dataPage = Math.ceil(dataLth/page);//页数
	var trlist = '';
	for( var i=0; i<dataLth; i++ ){
		trlist += '<tr><td>'+ e.data[i].shopnum +'</td><td>'+ e.data[i].cInvCode
				+'</td><td>'+ e.data[i].cInvName +'</td><td>'+ e.data[i].cBatch
				+'</td><td>'+ e.data[i].executor +'</td><td>'+ e.data[i].iQuantity
				+'</td><td>'+ e.data[i].cInvDefine8 +'</td><td>'+ e.data[i].cInvStd +'</td></tr>';
	}
	$('#table3 .tfooter span').html('1');
	$('#table3 .tfooter b').html( dataPage );
	$('#table3 tbody').html( trlist );
	productTrlist = $('#table3 tbody>tr');//改变全局变量
	for( var j=0; j<page; j++ ){
		productTrlist.eq(j).show();
	}
	$('#loading').hide();//loading隐藏
}
//仓管人员
function producterLoad(){
	$('#loading').show();//loading显示
	var nowDate = new Date().getTime();
	$.ajax({
		type:"get",
		url: '/TestServer/rest/materail/reqStorekeeperInfo?nowDate='+ nowDate,
//		url:'php/producter.php',
		async: false,
		dataType:'json',
		success: function(e){
			if( e.return_code == 'success' ){
				producterSec(e);
			}else if( e.return_code == 'fail' ){
				$('#loading').hide();//loading隐藏
				maskShow('加载失败!');
			}
		},
		error: function(){
			$('#loading').hide();//loading隐藏
			maskShow('连接服务器错误!');
		}
	});
}
function producterSec(e){
	var dataLth = e.data.length;
	var dataPage = Math.ceil(dataLth/page);//页数
	var trlist = '';
	for( var i=0; i<dataLth; i++ ){
		trlist += '<tr><td>'+ e.data[i].storekeeper +'</td><td>'+ e.data[i].identifier
				+'</td><td>'+ e.data[i].spec +'</td><td>'+ e.data[i].location
				+'</td><td>'+ e.data[i].materialname +'</td><td>'+ e.data[i].materialnumber
				+'</td><td>'+ e.data[i].repository +'</td></tr>';
	}
	$('#table4 .tfooter span').html('1');
	$('#table4 .tfooter b').html( dataPage );
	$('#table4 tbody').html( trlist );
	producterTrlist = $('#table4 tbody>tr');//改变全局变量
	for( var j=0; j<page; j++ ){
		producterTrlist.eq(j).show();
	}
	$('#loading').hide();//loading隐藏
}
//翻页
$(function(){
	$('.scc').click(function(){//上页
		var thisPage = $(this).siblings('span');//当前页数
		if( thisPage.html() != 1 ){
			var divName = $(this).parent().attr('name');
			var nowList =  divName == '#table1' ? accountTrlist :
						   divName == '#table2' ? galleryTrlist :
						   divName == '#table3' ? productTrlist :
						   producterTrlist;
			nowList.hide();
			thisPage.html( Number(thisPage.html()) - 1 );
			if( $(this).parent().parent().attr('name') != 'searching' ){//不是搜索翻页
				for( var i = (thisPage.html()-1)*page; i < thisPage.html()*page; i++ ){//显示tr行数控制
					nowList.eq(i).show();
				}
			}else{//搜索
				var nowSearchList = $( divName + ' tbody>tr.search' );
				for( var i = (thisPage.html()-1)*page; i < thisPage.html()*page; i++ ){//显示tr行数控制
					nowSearchList.eq(i).show();
				}
			}
		}
	});
	$('.add').click(function(){//下页
		var thisPage = $(this).siblings('span');//当前页数
		var sumPage = $(this).siblings('b').html();//总页数
		if( thisPage.html() != sumPage ){
			var divName = $(this).parent().attr('name');
			var nowList =  divName == '#table1' ? accountTrlist :
						   divName == '#table2' ? galleryTrlist :
						   divName == '#table3' ? productTrlist :
						   producterTrlist;
			nowList.hide();
			thisPage.html( Number(thisPage.html()) + 1 );
			if( $(this).parent().parent().attr('name') != 'searching' ){//不是搜索翻页
				for( var i = (thisPage.html()-1)*page; i < thisPage.html()*page; i++ ){//显示tr行数控制
					nowList.eq(i).show();
				}
			}else{//搜索
				var nowSearchList = $( divName + ' tbody>tr.search' );
				for( var i = (thisPage.html()-1)*page; i < thisPage.html()*page; i++ ){//显示tr行数控制
					nowSearchList.eq(i).show();
				}
			}
		}
	});
	//搜索优化
	var cpLock = false;
	$('.search').on('compositionstart', function(){
	    cpLock = true;
	});
	$('.search').on('compositionend', function(){
	    cpLock = false;
	    searchInput(this);
	});
	$('.search').on('input',function(){
		if(!cpLock){
			searchInput(this);
		}
	});
});
function searchInput(ele){
	var footSearch = $(ele).parent().parent().siblings('.tfooter');//定位底部栏的位置
	var thisSearch = $(ele).attr('name') == '#table1' ? accountTrlist :
					 $(ele).attr('name') == '#table2' ? galleryTrlist :
					 $(ele).attr('name') == '#table3' ? productTrlist :
					 producterTrlist;
	if( $(ele).val().length != 0 ){//输入内容不为0的时候
		footSearch.attr('name','searching');//添加搜索标示
		var val = $(ele).val();
		var searchlist = '';
		for( var i=0; i<thisSearch.length; i++ ){
			thisSearch.eq(i).hide();
			if( thisSearch.eq(i).hasClass('search') ){//如果有search去掉class
				thisSearch.eq(i).removeClass('search');
			}
			if( thisSearch.eq(i).html().indexOf( val ) != -1 ){//找到
				thisSearch.eq(i).addClass('search');
			}
		}
		searchlist = $( $(ele).attr('name') + ' tbody>tr.search');//选取搜索标记的
		footSearch.children('div').children('span').html('1');//当前页
		footSearch.children('div').children('b').html( Math.ceil( searchlist.length / page ) );//页数
		for( var j=0; j<page; j++ ){
			searchlist.eq(j).show();
		}
	}else{//搜索项为0
		footSearch.removeAttr('name');//去除搜索标示
		for( var i=0; i<thisSearch.length; i++ ){
			thisSearch.eq(i).hide();
			if( thisSearch.eq(i).hasClass('search') ){//如果有search去掉class
				thisSearch.eq(i).removeClass('search');
			}
		}
		for( var i=0; i<page; i++ ){
			thisSearch.eq(i).show();
		}
		footSearch.children('div').children('span').html('1');//当前页
		footSearch.children('div').children('b').html( Math.ceil( thisSearch.length / page ) );//页数
	}
}
$(function(){
	//选择文件
	$('.form button').click(function(e){
		e.preventDefault();
	});
	$('.filelook').click(function(){
		$(this).prev().click();
	});
	// 文件上传名字显示
	$('input[type="file"]').change(function(){
        var filePath=$(this).val();
        var fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
        if(filePath != '') {
        	$(this).siblings('.name').html(fileName).show();
        }else{
        	$(this).siblings('.name').html('').hide();
        }
    });
});
//文件提交
function fileloadon(ele,URL){
	if( $(ele).siblings('input').val() == '' ){
		maskShow('请选择文件!');
		return false;
	}
	$('#loading').show();//loading显示
	$(ele).parent().ajaxSubmit({
		type: "post",
		url: URL,
		success: function(e){
			$('#loading').hide();//loading隐藏
			maskShow(e.return_msg);
		},
		error: function(){
			$('#loading').hide();//loading隐藏
			maskShow('连接服务器错误!');
		}
	});
	$(".name").hide();
	$(ele).siblings('input').val('');
}
$(function(){
	//关闭增加输入框
	$('.modal_close').click(function(){
		modalClose();
	});
	//关闭提示框
	$('.mask_close').click(function(){
		maskClose();
	});
});
function modalShow(){
	$('#modal').fadeIn(300);
	$('#modal>div').animate({
		"margin-top": "70px"
	},300);
}
function modalClose(){
	$('#modalbox input').val('');
	$("#modalbox>span").html('');
	$("#modalbox>input").removeClass('iptSec').removeClass('iptErr');
	$("#modal input").prop('checked',false);
	$("#modal").fadeOut(300);
	$('#modal>div').animate({
		"margin-top": "-40px"
	},300);
	$('#modal').removeAttr('name');//去除标记
}
function maskShow(txt){
	$('#mask>div>div').html(txt);
	$('#mask').fadeIn(300);
	$('#mask>div').animate({
		"margin-top": "70px"
	},300);
}
function maskClose(){
	$('#mask').fadeOut(300);
	$('#mask>div').animate({
		"margin-top": "-40px"
	},300);
}
//表单验证
function form(ele,txt,a){
	$(ele).removeClass('iptSec').removeClass('iptErr');
	$(ele).prev('span').html(txt);
	$(ele).addClass( a == 1 ? 'iptSec' : 'iptErr' );
}
window.onload = function(){
	laydate.skin('molv');
	if( sessionStorage.page != undefined ){
		$( sessionStorage.page ).show().siblings().hide();
	}else{
		$('#account').show();
	}
	accountLoad();
}

$(function() {
    $('#side-menu').metisMenu();
});


$(function() {
    $(window)
            .bind(
                    "load resize",
                    function() {
                        var topOffset = 50;
                        var width = (this.window.innerWidth > 0) ? this.window.innerWidth
                                : this.screen.width;
                        if (width < 768) {
                            $('div.navbar-collapse').addClass('collapse');
                            topOffset = 100; // 2-row-menu
                        } else {
                            $('div.navbar-collapse').removeClass('collapse');
                        }

                        var height = ((this.window.innerHeight > 0) ? this.window.innerHeight
                                : this.screen.height) - 1;
                        height = height - topOffset;
                        if (height < 1)
                            height = 1;
                        if (height > topOffset) {
                            $("#page-wrapper").css("min-height",
                                    (height) + "px");
                        }
                    });

    var url = window.location;
    // var element = $('ul.nav a').filter(function() {
    // return this.href == url;
    // }).addClass('active').parent().parent().addClass('in').parent();
    var element = $('ul.nav a').filter(function() {
        return this.href == url;
    }).addClass('active').parent();

    while (true) {
        if (element.is('li')) {
            element = element.parent().addClass('in').parent();
        } else {
            break;
        }
    }
});

// 账户管理
$(document).ready(function() {
    // 初始化账户管理表格
    var table = $('#dataTables-account').DataTable({
            "ajax" : "/TestServer/rest/materail/reqUserList",
            "bDestroy":true,
            "responsive" : true,
            "iDisplayLength" : 100, //默认显示的记录数
            "language" : {
                "url" : "Chinese.json"
            },
            "columns": [
                { "data": null, "title":"","defaultContent":"<input type='checkbox' name='' value='0' />"},
                { "data": "userID", "title":"id","defaultContent":""},
                { "data": "username", "title":"账号","defaultContent":""},
                { "data": "passwd", "title":"密码","defaultContent":""},
                { "data": "employname", "title":"仓管员","defaultContent":""},
                { "data": "authority", "title":"权限","defaultContent":""}
            ]
    });
    // 表格行选中
    $('#dataTables-account tbody').on('click', 'tr', function() {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
            $(this).find("input").prop('checked', false);
        } else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            $(this).find("input").prop('checked', true);
            $(this).siblings().find("input").prop('checked', false);
        }
    });
    var br = Array();
    br[0] = "<span id='rights1'>仓库</span>";
    br[1] = "<span id='rights2'>配料</span>";
    br[2] = "<span id='rights3'>产线</span>";
    // 模态框数据不能为空，提示设置
    $('[type=text]:input').blur(function(){
        if($(this).val() == ""){
            $(this).next().show();
            $(this).addClass("inputbdc");
        }
    })
    $('[type=text]:input').focus(function(){
        if($(this).val() == ""){
            $(this).next().hide();
            $(this).removeClass("inputbdc");
        }
    })
    // 添加
    $('#add-button').click(function() {
        var add_name = $('#add_name').val();
        var add_account = $('#add_account').val();
        var add_password = $('#add_password').val();
        var add_rights = '';
        $('[name=items]:checkbox:checked').each(function() {
            add_rights += br[$(this).val()] + " ";
        })
        // 如果数据为空提示
        if((add_name == "") || (add_account == "") || (add_password == "")){
            $('[id^=add_]:input').each(function(){
                if($(this).val() == ""){
                    $(this).next().show();
                    $(this).addClass("inputbdc");
                }
            })
        }
        else{
            // 如果数据不为空添加
            $('#add-Modal').modal('hide');
            // 模态框清空
            $('[type=text]:input').val("");
            $('[name=items]:checkbox').prop('checked', false);
            // 添加的数据
            var jsonData = {
                "add_name" : add_name,
                "add_account" : add_account,
                "add_password" : add_password,
                "add_rights" : add_rights
            };
            $.ajax({
                url : "/TestServer/rest/materail/reqAddUser",
                data : JSON.stringify(jsonData),
                type : "post",
                contentType : "application/json",
                dataType : "json",
                success : function(backdata) {
                    if (backdata.data.length > 0) {
                        table.row.add(backdata.data[0]).draw();
                    } else {
                        $("#ifo").html("插入失败！");
                        $('#ifo-Modal').modal();
                    }
                },
                error : function(error) {
                    console.log(error);
                }
            });
        }
    });
    // 删除
    $('#delete').click(function() {
        // 如果有行被选中
        if (table.row('.selected').length > 0) {
            // 获取选中行的id
            var tid = table.row('.selected').data().userID;
            $.ajax({
                url : "/TestServer/rest/materail/reqDelUser",
                data : JSON.stringify({
                    "id" : tid
                }),
                type : "post",
                contentType : "application/json",
                dataType : "json",
                success : function(backdata) {
                    if (backdata) {
                        table.row('.selected').remove().draw(false);
                        $("#ifo").html("删除成功！");
                        $('#ifo-Modal').modal();
                    } else {
                        $("#ifo").html("删除失败！");
                        $('#ifo-Modal').modal();
                    }
                },
                error : function(error) {
                    console.log(error);
                }
            });
        // 如果没有行被选中，提示
        } else {
            $("#ifo").html("请选中你要删除的行！");
            $('#ifo-Modal').modal();
        }
    });
    // 编辑模态框显示
    $('#edit').click(function() {
        // 如果有行被选中
        if (table.row('.selected').length > 0) {
            $('#edit-Modal').modal();
            //选中行的数据在模态框中显示
            var tdata = table.row('.selected').data();
            $('#edit_name').val(tdata.userID);
            $('#edit_account').val(tdata.username);
            $('#edit_password').val(tdata.passwd);
            for (var i = 1; i <= 3; i++) {
                var s = tdata.authority.indexOf(i);
                if (s > 0) {
                    $('[name=item]:checkbox').eq(i - 1).prop('checked', true);
                }
            }
        // 如果没有行被选中，提示
        } else {
            $("#ifo").html("请选中你要修改的行！");
            $('#ifo-Modal').modal();
        }
    });
    // 编辑
    $('#edit-button').click(function() {
        var edit_id = table.row('.selected').data().userID;
        var edit_name = $('#edit_name').val();
        var edit_account = $('#edit_account').val();
        var edit_password = $('#edit_password').val();
        var edit_rights = '';
        $('[name=item]:checkbox:checked').each(function() {
            edit_rights += br[$(this).val()] + " ";
        })
        // 如果数据为空提示
        if((edit_name == "")|| (edit_account == "") || (edit_password == "")){
            $('[id^=edit_]:input').each(function(){
                if($(this).val() == ""){
                    $(this).next().show();
                    $(this).addClass("inputbdc");
                }
            })
        }
        // 如果数据不为空添加
        else{
            $('#edit-Modal').modal('hide');
            $('[type=text]:input').val("");
            $('[name=items]:checkbox').prop('checked', false);
            // 编辑的数据
            var jsonData = {
                "edit_id" : edit_id,
                "edit_name" : edit_name,
                "edit_account" : edit_account,
                "edit_password" : edit_password,
                "edit_rights" : edit_rights
           };
            $.ajax({
                url : "/TestServer/rest/materail/reqUpdateUser",
                data : JSON.stringify(jsonData),
                type : "post",
                contentType : "application/json",
                dataType : "json",
                success : function(backdata) {
                    if (backdata.data.length > 0) {
                        table.row('.selected').remove().draw(false);
                        table.row.add(backdata.data[0]).draw();
                    } else {
                        $("#ifo").html("修改失败！");
                        $('#ifo-Modal').modal();
                    }
                },
                error : function(error) {
                    console.log(error);
                }
            });
        }
    });
    // 关闭清空模态框数据
    $('#add-Modal .empty').click(function() {
        $('[type=text]:input').val("");
        $('[name=items]:checkbox').prop('checked', false);
        $('[id^=add_]:input').each(function(){
            $(this).next().hide();
            $(this).removeClass("inputbdc");
        });
    });
    // 关闭清空模态框数据
    $('#edit-Modal .empty').click(function() {
        $('[type=text]:input').val("");
        $('[name=item]:checkbox').prop('checked', false);
        $('[id^=edit_]:input').each(function(){
            $(this).next().hide();
            $(this).removeClass("inputbdc");
        });
    });

});

//显示遮罩层
function showMask(){
    $("#mask").css("height",$(document).height());
    $("#mask").css("width",$(document).width());
    $("#mask").show();
}
//隐藏遮罩层
function hideMask(){
    $("#mask").hide();
}

// 仓库管理文件上传
function fileloadon() {
        if($("input[type='file']").val() == ""){
            $("#ifo").html("请选择文件!");
            $('#ifo-Modal').modal();
            return false;
        }
        showMask();
        $("#_fileForm").ajaxSubmit({
            type: "post",
            url: "/TestServer/rest/materail/uploadProductionInfoForStore",
            success: function (backdata) {
                if(backdata.return_code == "fail"){
                    hideMask();
                    $("#ifo").html(backdata.return_msg);
                    $('#ifo-Modal').modal();
                }
                if(backdata.return_code == "success")
                {
                    hideMask();
                    $("#ifo").html("上传成功！");
                    $('#ifo-Modal').modal();
                }
            },
            error: function (msg) {
                hideMask();
                $("#ifo").html("文件上传失败!");
                $('#ifo-Modal').modal();
            }
        });
        $(".name").hide();
        $("input[type='file']").val("");
}

// 辅料管理文件上传
function fileloadon2() {
        if($("input[type='file']").val() == ""){
            $("#ifo").html("请选择文件!");
            $('#ifo-Modal').modal();
            return false;
        }
        showMask();
        $("#_fileForm2").ajaxSubmit({
            type: "post",
            url: "/TestServer/rest/materail/uploadAuxiliaryInfo",
            success: function (backdata) {
                if(backdata.return_code == "fail"){
                    hideMask();
                    $("#ifo").html(backdata.return_msg);
                    $('#ifo-Modal').modal();
                }
                if(backdata.return_code == "success"){
                    hideMask();
                    $("#ifo").html("上传成功！");
                    $('#ifo-Modal').modal();
                }
            },
            error: function (msg) {
                hideMask();
                $("#ifo").html("文件上传失败!");
                $('#ifo-Modal').modal();
            }
        });
        $(".name").hide();
        $("input[type='file']").val("");
}
$(document).ready(function() {
    // 文件上传名字显示
    $(".file").on("change","input[type='file']",function(){
        var filePath=$(this).val();
        var fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
        if(filePath != "") {
            $(".name").html(fileName).show();
        }
    });
})

!function(){
    // 仓库管理表格初始化
    var table_gallery=$('#dataTables-gallery').DataTable({
        "data" :  [],
        "bDestroy":true,
        "responsive" : true,
        "iDisplayLength" : 100, //默认显示的记录数
        "language" : {
            "url" : "Chinese.json"
        },
        "columns": [
            { "data": "shopnum" },
            { "data": "cInvCode" },
            { "data": "cInvName" },
            { "data": "cInvStd" },
            { "data": "iQuantity" },
            { "data": "cInvDefine8" },
            { "data": "cBatch" },
            { "data": "executor" }
        ]
    });
    laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
    laydate({elem: '#date-gallery'});//绑定元素
    // 仓库管理
    $("#datebutton-gallery").on("click",function(){
        if($("#date-gallery").val()){
            $.ajax({
                "url" : "/TestServer/rest/materail/reqAllUnLoadingInfo",
                "data" : JSON.stringify({
                    "date":$("#date-gallery").val()}
                ),
                "type" : "post",
                "contentType" : "application/json",
                "dataType" : "json",
                success : function(backdata) {
                    console.log(backdata);
                if(backdata.return_code == "fail"){
                    $("#ifo").html(backdata.return_msg);
                    $('#ifo-Modal').modal();
                    table_gallery.row().clear().draw();
                }
                if(backdata.return_code == "success")
                {
                    $('#dataTables-gallery').DataTable({
                        "data" :  backdata.data,
                        "bDestroy":true,
                        "responsive" : true,
                        "iDisplayLength" : 100, //默认显示的记录数
                        "language" : {
                            "url" : "Chinese.json"
                        },
                        "columns": [
                            { "data": "shopnum" },
                            { "data": "cInvCode" },
                            { "data": "cInvName" },
                            { "data": "cInvStd" },
                            { "data": "iQuantity" },
                            { "data": "cInvDefine8" },
                            { "data": "cBatch" },
                            { "data": "executor" }
                        ]
                    });
                }
                },
                error : function(error) {
                    console.log(error);
                    $("#ifo").html("出错了!");
                    $('#ifo-Modal').modal();
                    table_gallery.row().clear().draw();
                }
            });
        }
        else{
            $("#ifo").html("请选择日期!");
            $('#ifo-Modal').modal();
        };
    })
}();

$(document).ready(function(){
    // 辅料管理
    $("#product").on("click",function(){
        var table_product=$('#dataTables-product').DataTable({
            "ajax" : "/TestServer/rest/materail/reqAuxiliaryInfoList",
            "bDestroy":true,
            "responsive" : true,
            "iDisplayLength" : 100, //默认显示的记录数
            "language" : {
                "url" : "Chinese.json"
            },
            "columns": [
                { "data": "invcode", "title":"物料编码","defaultContent":"" },
                { "data": "invname", "title":"物料名称","defaultContent":"" },
                { "data": "invstd", "title":"规格型号","defaultContent":"" },
                { "data": "validityperiod", "title":"保质期(月份)","defaultContent":"" },
                { "data": "brand", "title":"品牌","defaultContent":"" },
                { "data": "origin", "title":"产地","defaultContent":"" },
                { "data": "models", "title":"涉及机型","defaultContent":"" }
            ]
        });
    });
    // 管理人员
    $("#person").on("click",function(){
        var table = $('#dataTables-keeper').DataTable({
           "ajax": "/TestServer/rest/materail/reqStorekeeperInfo",
           "bDestroy":true,
           "responsive" : true,
           "iDisplayLength" : 100, //默认显示的记录数
           "columns": [
               { "data": "storekeeper", "title":"仓管员","defaultContent":""},
               { "data": "identifier", "title":"编号","defaultContent":""},
               { "data": "spec", "title":"规格","defaultContent":""},
               { "data": "location", "title":"位置","defaultContent":""},
               { "data": "materialname", "title":"物料名称","defaultContent":""},
               { "data": "materialnumber", "title":"物料编码","defaultContent":""},
               { "data": "repository", "title":"仓库名称","defaultContent":""}
//               { "data": null, "title":"操作","defaultContent": "<button class='edit-btn btn btn-primary' type='button'>编辑</button>"}
           ],
           "language" : {
                "url" : "Chinese.json"
            }
       });
//        $("#dataTables-keeper tbody").on("click",".edit-btn",function(){
//            var tds=$(this).parents("tr").children().first();
//            var jqob=$(tds);
//            var txt=jqob.text();
//            var put=$("<input type='text'>");
//            put.val(txt);
//            jqob.html(put);
//           $(this).html("保存");
//           $(this).toggleClass("edit-btn");
//           $(this).toggleClass("save-btn");
//        });
//        $("#dataTables-keeper tbody").on("click",".save-btn",function(){
//           var row=table.row($(this).parents("tr"));
//           var tds=$(this).parents("tr").children().first();
//           var jqob=$(tds);
//           var txt=jqob.children("input").val();
//           jqob.html(txt);
//           table.cell(jqob).data(txt);//修改DataTables对象的数据
//            var data=row.data();
//            console.log(data);
//            $.ajax({
//                "url":"data1.json",
//                "data" : JSON.stringify(data),
//                "type" : "post",
//                "contentType" : "application/json",
//                "dataType" : "json",
//                "error":function(){
//                    alert("服务器未正常响应，请重试");
//                },
//                "success":function(response){
//                    alert("修改成功");
//                }
//            });
//           $(this).html("编辑");
//           $(this).toggleClass("edit-btn");
//           $(this).toggleClass("save-btn");
//       });
    });
})

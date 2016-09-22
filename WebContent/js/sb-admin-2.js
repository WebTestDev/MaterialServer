/*!
 * Start Bootstrap - SB Admin 2 v3.3.7+1 (http://startbootstrap.com/template-overviews/sb-admin-2)
 * Copyright 2013-2016 Start Bootstrap
 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap/blob/gh-pages/LICENSE)
 */
$(function() {
    $('#side-menu').metisMenu();
});

// Loads the correct sidebar on window load,
// collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
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

$(document).ready(function() {
    $('#dataTables-example').DataTable({
        "ajax" : "/TestServer/rest/materail/reqUserList",
        "responsive" : true,
        "language" : {
            "url" : "Chinese.json"
        }
    });
    var table = $('#dataTables-example').DataTable();
    $('#dataTables-example tbody').on('click', 'tr', function() {
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
    var check = "<input type='checkbox' value=''>";
    // br[3] = '<span id="rights4">权限4</span>';
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
    $('#add-button').click(function() {
        var add_name = $('#add_name').val();
        var add_num = $('#add_num').val();
        var add_account = $('#add_account').val();
        var add_password = $('#add_password').val();
        var add_rights = '';
        $('[name=items]:checkbox:checked').each(function() {
            add_rights += br[$(this).val()] + " ";
        })
        if((add_name == "") || (add_num == "") || (add_account == "") || (add_password == "")){
            $('[id^=add_]:input').each(function(){
                if($(this).val() == ""){
                    $(this).next().show();
                    $(this).addClass("inputbdc");
                }
            })
        }
        else{
            $('#myModal').modal('hide');
            $('[type=text]:input').val("");
            $('[name=items]:checkbox').prop('checked', false);
            var jsonData = {
                "add_name" : add_name,
                "add_num" : add_num,
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
                    if (backdata.data[0].length > 0) {
                        table.row.add(backdata.data[0]).draw();
                    } else {
                        $("#ifo").html("插入失败！");
                        $('#myModal3').modal();
                    }
                },
                error : function(error) {
                    console.log(error);
                }
            });
        }
    });
    $('#delete').click(function() {
        if (table.row('.selected').length > 0) {
            var tid = table.row('.selected').data()[1];
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
                        $('#myModal3').modal();
                    } else {
                        $("#ifo").html("删除失败！");
                        $('#myModal3').modal();
                    }
                },
                error : function(error) {
                    console.log(error);
                }
            });
        } else {
            $("#ifo").html("请选中你要删除的行！");
            $('#myModal3').modal();
        }
    });
    $('#edit').click(function() {
        if (table.row('.selected').length > 0) {
            $('#myModal2').modal();
        } else {
            $("#ifo").html("请选中你要修改的行！");
            $('#myModal3').modal();
        }
        var tdata = new Array();
        tdata = table.row('.selected').data();
        $('#edit_name').val(tdata[2]);
        $('#edit_num').val(tdata[3]);
        $('#edit_account').val(tdata[4]);
        $('#edit_password').val(tdata[5]);
        for (var i = 1; i <= 3; i++) {
            var s = tdata[6].indexOf(i);
            if (s > 0) {
                $('[name=item]:checkbox').eq(i - 1).prop('checked', true);
            }
        }
    });
    $('#edit-button').click(function() {
        var edit_id = table.row('.selected').data()[1];
        var edit_name = $('#edit_name').val();
        var edit_num = $('#edit_num').val();
        var edit_account = $('#edit_account').val();
        var edit_password = $('#edit_password').val();
        var edit_rights = '';
        $('[name=item]:checkbox:checked').each(function() {
            edit_rights += br[$(this).val()] + " ";
        })
        if((edit_name == "") || (edit_num == "") || (edit_account == "") || (edit_password == "")){
            $('[id^=edit_]:input').each(function(){
                if($(this).val() == ""){
                    $(this).next().show();
                    $(this).addClass("inputbdc");
                }
            })
        }
        else{
            $('#myModal2').modal('hide');
            $('[type=text]:input').val("");
            $('[name=items]:checkbox').prop('checked', false);
            var jsonData = {
                "edit_id" : edit_id,
                "edit_name" : edit_name,
                "edit_num" : edit_num,
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
                    if (backdata.data[0].length > 0) {
                        table.row('.selected').remove().draw(false);
                        table.row.add(backdata.data[0]).draw();
                    } else {
                        $("#ifo").html("修改失败！");
                        $('#myModal3').modal();
                    }
                },
                error : function(error) {
                    console.log(error);
                }
            });
        }
    });
    $('#myModal .empty').click(function() {
        $('[type=text]:input').val("");
        $('[name=items]:checkbox').prop('checked', false);
        $('[id^=add_]:input').each(function(){
            $(this).next().hide();
            $(this).removeClass("inputbdc");
        });
    });
    $('#myModal2 .empty').click(function() {
        $('[type=text]:input').val("");
        $('[name=item]:checkbox').prop('checked', false);
        $('[id^=edit_]:input').each(function(){
            $(this).next().hide();
            $(this).removeClass("inputbdc");
        });
    });

});



function fileloadon() {
        $("#_fileForm").ajaxSubmit({
            type: "post",
            url: "/TestServer/rest/materail/uploadProductionInfoForStore",
            success: function (backdata) {
                if(backdata.return_code == "fail"){
                    $("#ifos").html(backdata.return_msg);
                    $('#myModal4').modal();
                }
                if(backdata.return_code == "success")
                {
                    $("#ifos").html("上传成功！");
                    $('#myModal4').modal();
                }
            },
            error: function (msg) {
                $("#ifos").html("文件上传失败!");
                $('#myModal4').modal();
            }
        });
        $(".name").hide();
        $("input[type='file']").val("");
}
function fileloadon2() {
        $("#_fileForm2").ajaxSubmit({
            type: "post",
            url: "/TestServer/rest/materail/uploadProductionInfo",
            success: function (backdata) {
                console.log(backdata);
                if(backdata.return_code == "fail"){
                    $("#ifos").html(backdata.return_msg);
                    $('#myModal4').modal();
                }
                if(backdata.return_code == "success"){
                    $("#ifos").html("上传成功！");
                    $('#myModal4').modal();
                }
            },
            error: function (msg) {
                $("#ifos").html("文件上传失败!");
                $('#myModal4').modal();
            }
        });
        $(".name").hide();
        $("input[type='file']").val("");
}
$(document).ready(function() {
    $(".file").on("change","input[type='file']",function(){
        var filePath=$(this).val();
        var fileName=filePath.substring(filePath.lastIndexOf("\\")+1);
        if(filePath != "") {
            $(".name").html(fileName).show();
        }
    });
})

!function(){
    laydate.skin('molv');//切换皮肤，请查看skins下面皮肤库
    laydate({elem: '#date-product'});//绑定元素
    laydate({elem: '#date-gallery'});//绑定元素
    $("#datebutton-product").on("click",function(){
        console.log($("#date-product").val());
        if($("#date-product").val()){
            $.ajax({
                "url" : "/TestServer/rest/materail/reqAllProductionInfo",
                "data" : JSON.stringify({
                    "date":$("#date-product").val()}
                ),
                "type" : "post",
                "contentType" : "application/json",
                "dataType" : "json",
                success : function(backdata) {
                    console.log(backdata);
                if(backdata.return_code == "fail"){
                    $("#ifos").html(backdata.return_msg);
                    $('#myModal4').modal();
                    $('#dataTables-product').DataTable({
                        "data" :  [],
                        "bDestroy":true,
                        "responsive" : true,
                        "language" : {
                            "url" : "Chinese.json"
                        },
                        "columns": [
                            { "data": "workshop" },
                            { "data": "productline" },
                            { "data": "tasknumber" },
                            { "data": "productcode" },
                            { "data": "spec" },
                            { "data": "schedulednum" },
                            { "data": "dailynum" },
                            { "data": "date" },
                            { "data": "remark" }
                        ]
                    });
                }
                if(backdata.return_code == "success")
                {
                    $('#dataTables-product').DataTable({
                        "data" :  backdata.data,
                        "bDestroy":true,
                        "responsive" : true,
                        "language" : {
                            "url" : "Chinese.json"
                        },
                        "columns": [
                            { "data": "workshop" },
                            { "data": "productline" },
                            { "data": "tasknumber" },
                            { "data": "productcode" },
                            { "data": "spec" },
                            { "data": "schedulednum" },
                            { "data": "dailynum" },
                            { "data": "date" },
                            { "data": "remark" }
                        ]
                    });
                }
                },
                error : function(error) {
                    console.log(error);
                    $("#ifos").html("出错了!");
                    $('#myModal4').modal();
                    $('#dataTables-product').DataTable({
                        "data" :  [],
                        "bDestroy":true,
                        "responsive" : true,
                        "language" : {
                            "url" : "Chinese.json"
                        },
                        "columns": [
                            { "data": "workshop" },
                            { "data": "productline" },
                            { "data": "tasknumber" },
                            { "data": "productcode" },
                            { "data": "spec" },
                            { "data": "schedulednum" },
                            { "data": "dailynum" },
                            { "data": "date" },
                            { "data": "remark" }
                        ]
                    });
                }
            });
        }
        else{
            $("#ifos").html("请选择日期!");
            $('#myModal4').modal();
        };
    });
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
                    $("#ifos").html(backdata.return_msg);
                    $('#myModal4').modal();
                    $('#dataTables-gallery').DataTable({
                        "data" :  [],
                        "bDestroy":true,
                        "responsive" : true,
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
                if(backdata.return_code == "success")
                {
                    $('#dataTables-gallery').DataTable({
                        "data" :  backdata.data,
                        "bDestroy":true,
                        "responsive" : true,
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
                    $("#ifos").html("出错了!");
                    $('#myModal4').modal();
                    $('#dataTables-product').DataTable({
                        "data" :  [],
                        "bDestroy":true,
                        "responsive" : true,
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
            });
        }
        else{
            $("#ifos").html("请选择日期!");
            $('#myModal4').modal();
        };
    })
}();

$(document).ready(function(){
    $("#person").on("click",function(){
        var table = $('#dataTables-keeper').DataTable({
           "ajax": "/TestServer/rest/materail/reqStorekeeperInfo",
           "bDestroy":true,
           "responsive" : true,
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
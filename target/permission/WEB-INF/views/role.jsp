<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色</title>
    <jsp:include page="/common/backend_common.jsp" />
    <link rel="stylesheet" href="/ztree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/assets/css/bootstrap-duallistbox.min.css" type="text/css">
    <script type="text/javascript" src="/ztree/jquery.ztree.all.min.js"></script>
    <script type="text/javascript" src="/assets/js/jquery.bootstrap-duallistbox.min.js"></script>
    <style type="text/css">
        .bootstrap-duallistbox-container .moveall, .bootstrap-duallistbox-container .removeall {
            width: 50%;
        }
        .bootstrap-duallistbox-container .move, .bootstrap-duallistbox-container .remove {
            width: 49%;
        }
    </style>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        角色管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护角色与用户, 角色与权限关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            角色列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 role-add"></i>
            </a>
        </div>
        <div id="roleList"></div>
    </div>
    <div class="col-sm-9">
        <div class="tabbable" id="roleTab">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" href="#roleAclTab">
                        角色与权限
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#roleUserTab">
                        角色与用户
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="roleAclTab" class="tab-pane fade in active">
                    <ul id="roleAclTree" class="ztree"></ul>
                    <button class="btn btn-info saveRoleAcl" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        保存
                    </button>
                </div>

                <div id="roleUserTab" class="tab-pane fade" >
                    <div class="row">
                        <div class="box1 col-md-6">待选用户列表</div>
                        <div class="box1 col-md-6">已选用户列表</div>
                    </div>
                    <select multiple="multiple" size="10" name="roleUserList" id="roleUserList" >
                    </select>
                    <div class="hr hr-16 hr-dotted"></div>
                    <button class="btn btn-info saveRoleUser" type="button">
                        <i class="ace-icon fa fa-check bigger-110"></i>
                        保存
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<!--弹出框-->
<div id="dialog-role-form" style="display: none;">
    <form id="roleForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td><label for="roleName">名称</label></td>
                <td>
                    <input type="text" name="name" id="roleName" value="" class="text ui-widget-content ui-corner-all">
                    <input type="hidden" name="id" id="roleId"/>
                </td>
            </tr>
            <tr>
                <td><label for="roleStatus">状态</label></td>
                <td>
                    <select id="roleStatus" name="status" data-placeholder="状态" style="width: 150px;">
                        <option value="1">可用</option>
                        <option value="0">冻结</option>
                    </select>
                </td>
            </tr>
            <td><label for="roleRemark">备注</label></td>
            <td><textarea name="remark" id="roleRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<script id="roleListTemplate" type="x-tmpl-mustache">
<ol class="dd-list ">
    {{#roleList}}
        <li class="dd-item dd2-item role-name" id="role_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green role-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red role-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/roleList}}
</ol>
</script>

<script id="selectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}" selected="selected">{{username}}</option>
{{/userList}}
</script>

<script id="unSelectedUsersTemplate" type="x-tmpl-mustache">
{{#userList}}
    <option value="{{id}}">{{username}}</option>
{{/userList}}
</script>

<script type="text/javascript">
    $(function() {
        var roleListTemplate = $("#roleListTemplate").html();
        Mustache.parse(roleListTemplate);
        loadAllRoleList();


        function loadAllRoleList() {
            var url = "/sys/role/all.json"
            $.get(url,function(result) {
                if(result.ret) {
                    var roleList = result.data;
                    var rendered = Mustache.render(roleListTemplate,{roleList:roleList});
                    $("#roleList").html(rendered);
                    bindRoleClick();
                }
            });
        }

        function bindRoleClick() {
            $(".role-add").on("click",function () {
                $("#dialog-role-form").dialog({
                    modal:true,
                    title:"增加角色",
                    open:function() {
                        $("#roleForm")[0].reset();
                    },
                    buttons:{
                        "保存":function() {
                            saveRole();
                            $(this).dialog("close");
                        },
                        "取消":function() {
                            $(this).dialog("close");
                        }
                    }
                });
            });

            $(".role-edit").on("click",function (e) {
                e.preventDefault();
                e.stopPropagation();
                var id = $(this).attr("data-id");
                $("#dialog-role-form").dialog({
                    modal:true,
                    title:"修改角色",
                    open:function() {
                        $.post("/sys/role/find.json",{id:id},function(result) {
                            if(result.ret) {
                                $("#roleName").val(result.data.name);
                                $("#roleId").val(result.data.id);
                                $("#roleStatus").val(result.data.status);
                                $("#roleRemark").val(result.data.remark);
                            }
                        });
                    },
                    buttons:{
                        "修改":function () {
                            var data = $("#roleForm").serializeArray();
                            $.post("/sys/role/update.json",data,function(result) {
                                if(result.ret) {
                                    showMessage("修改角色", "操作成功", true);
                                    loadAllRoleList();
                                } else {
                                    showMessage("修改角色", result.msg, false);
                                }
                            });
                        },
                        "取消":function () {
                            $(this).dialog("close");
                        },
                    }
                });
            });

            $(".role-delete").on("click",function () {
                var id = $(this).attr("data-id");
                var roleName = $(this).attr("data-name");
                if(confirm("确定要删除[" + roleName + "]吗?")) {
                    var url = "/sys/role/delete.json";
                    $.post(url,{id:id},function (result) {
                        if(result.ret) {
                            showMessage("删除角色[" + roleName + "]", "操作成功", true);
                            loadAllRoleList();
                        } else {
                            showMessage("删除角色[" + roleName + "]", result.msg, false);
                        }
                    });
                }
            });
        }

        function saveRole() {
            var data = $("#roleForm").serializeArray();
            var url = "/sys/role/save.json";
            $.post(url,data,function(result){
                if(result.ret) {
                    showMessage("增加角色", "操作成功", true);
                    loadAllRoleList();
                } else {
                    showMessage("增加角色", result.msg, false);
                }
            });
        }
    })
</script>
</body>
</html>

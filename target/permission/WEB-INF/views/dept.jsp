<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门管理</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white;">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        用户管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护部门与用户关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            部门列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 dept-add"></i>
            </a>
        </div>
        <div id="deptList">
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                用户列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 user-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                展示
                                <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> 条记录 </label>
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                姓名
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                所属部门
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                邮箱
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                电话
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="userList"></tbody>
                    </table>
                    <div class="row" id="userPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--增加部门弹出的form框-->
<div id="dialog-dept-form" style="display: none;">
    <form id="deptForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">上级部门</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="选择部门" style="width: 200px;"></select>
                    <input type="hidden" name="id" id="deptId"/>
                </td>
            </tr>
            <tr>
                <td><label for="deptName">名称</label></td>
                <td><input type="text" name="name" id="deptName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptSeq">顺序</label></td>
                <td><input type="text" name="seq" id="deptSeq" value="1" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="deptRemark">备注</label></td>
                <td><textarea name="remark" id="deptRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<!--增加用户弹出的form框-->
<div id="dialog-user-form" style="display: none;">
    <form id="userForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所在部门</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="选择部门" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="userName">姓名</label></td>
                <input type="hidden" name="id" id="userId"/>
                <td><input type="text" name="username" id="userName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userMail">邮箱</label></td>
                <td><input type="text" name="mail" id="userMail" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userTelephone">电话</label></td>
                <td><input type="text" name="telephone" id="userTelephone" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userStatus">状态</label></td>
                <td>
                    <select id="userStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                        <option value="2">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userRemark">备注</label></td>
                <td><textarea name="remark" id="userRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<script id="deptListTemplate" type="x-tmpl-mustache">
<ol class="dd-list">
    {{#deptList}}
        <li class="dd-item dd2-item dept-name" id="dept_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green dept-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red dept-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/deptList}}
</ol>
</script>
<script id="userListTemplate" type="x-tmpl-mustache">
{{#userList}}
<tr role="row" class="user-name odd" data-id="{{id}}"><!--even -->
    <td><a href="javascript:void(0);" class="user-edit" data-id="{{id}}">{{username}}</a></td>
    <td>{{showDeptName}}</td>
    <td>{{mail}}</td>
    <td>{{telephone}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td> <!-- 此处套用函数对status做特殊处理 -->
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green user-edit" href="javascript:void(0);" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red user-acl" href="javascript:void(0);" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/userList}}
</script>

<script type="application/javascript">
    $(function () {
        var deptList;//存储树形部门列表
        var deptMap = {}; // 存储map格式的部门信息
        var userMap = {}; // 存储map格式的用户信息
        var optionStr = ""; // 存储部门列表的下拉选项
        var lastClickDeptId = -1;
        var deptListTemplate = $("#deptListTemplate").html();
        Mustache.parse(deptListTemplate);
        var userListTemplate = $("#userListTemplate").html();
        Mustache.parse(userListTemplate);
        loadDeptTree();
        loadAllUserList();
        function loadDeptTree() {
            $.get("/sys/dept/tree.json", {}, function (result) {
                if (result.ret) {
                    deptList = result.data;
                    var rendered = Mustache.render(deptListTemplate, {deptList: result.data});
                    $("#deptList").html(rendered);
                    recursiveRenderDept(result.data);
                    bindDeptClick();
                } else {
                    showMessage("加载部门列表", result.msg, false);
                }
            });
        }

        function recursiveRenderDept(deptList) {
            if(deptList && deptList.length > 0) {
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    if (dept.deptList.length > 0) {
                        var rendered = Mustache.render(deptListTemplate, {deptList: dept.deptList});
                        $("#dept_" + dept.id).append(rendered);
                        recursiveRenderDept(dept.deptList);
                    }
                })
            }
        }

        //部门点击事件
        function bindDeptClick() {
            $(".dept-name").click(function(e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                handleDeptSelected(deptId);
            });

            $(".dept-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                var deptName = $(this).attr("data-name");
                if (confirm("确定要删除部门[" + deptName + "]吗?")) {
                    $.ajax({
                        url: "/sys/dept/delete.json",
                        data: {
                            id: deptId
                        },
                        success: function (result) {
                            if (result.ret) {
                                showMessage("删除部门[" + deptName + "]", "操作成功", true);
                                loadDeptTree();
                            } else {
                                showMessage("删除部门[" + deptName + "]", result.msg, false);
                            }
                        }
                    });
                }
            });

            $(".dept-edit").click(function(e) {
                e.preventDefault();
                e.stopPropagation();
                var deptId = $(this).attr("data-id");
                $("#dialog-dept-form").dialog({
                    model: true,
                    title: "编辑部门",
                    open: function(event, ui) {
                        //$(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        optionStr = "<option value=\"0\">---</option>";
                        recursiveRenderDeptSelect(deptList, 1);
                        $("#deptForm")[0].reset();
                        $("#parentId").html(optionStr);
                        $("#deptId").val(deptId);
                        var targetDept = deptMap[deptId];
                        if (targetDept) {
                            $("#parentId").val(targetDept.parentId);
                            $("#deptName").val(targetDept.name);
                            $("#deptSeq").val(targetDept.seq);
                            $("#deptRemark").val(targetDept.remark);
                        }
                    },
                    buttons : {
                        "更新": function(e) {
                            e.preventDefault();
                            updateDept(false, function (data) {
                                $("#dialog-dept-form").dialog("close");
                            }, function (data) {
                                showMessage("更新部门", data.msg, false);
                            })
                        },
                        "关闭": function () {
                            $("#dialog-dept-form").dialog("close");
                        }
                    }
                });
            })
        }

        function handleDeptSelected(deptId) {
            if (lastClickDeptId != -1) {
                var lastDept = $("#dept_" + lastClickDeptId + " .dd2-content:first");
                lastDept.removeClass("btn-yellow");
                lastDept.removeClass("no-hover");
            }
            var currentDept = $("#dept_" + deptId + " .dd2-content:first");
            currentDept.addClass("btn-yellow");
            currentDept.addClass("no-hover");
            lastClickDeptId = deptId;
            loadUserList(deptId);
        }



        //部门列表 ﹢按钮
        $(".dept-add").click(function() {
            $("#dialog-dept-form").dialog({
                model: true,
                title: "新增部门",
                open: function(event, ui) {
                   // $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    optionStr = "<option value=\"0\">-</option>";
                    recursiveRenderDeptSelect(deptList, 1);
                    $("#deptForm")[0].reset();
                    $("#parentId").html(optionStr);
                },
                buttons : {
                    "添加": function(e) {
                        e.preventDefault();
                        updateDept(true, function (data) {
                            $("#dialog-dept-form").dialog("close");
                        }, function (data) {
                            showMessage("新增部门", data.msg, false);
                        })
                    },
                    "关闭": function () {
                        $("#dialog-dept-form").dialog("close");
                    }
                }
            });
        });

        //递归加载上级部门列表,并存储到optionStr中
        function recursiveRenderDeptSelect(deptList, level) {
            level = level | 0;
            if (deptList && deptList.length > 0) {
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    var blank = "";
                    if (level > 1) {
                        for(var j = 3; j <= level; j++) {
                            blank += "　";
                        }
                        blank += "∟";
                    }
                    optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>", {id: dept.id, name: blank + dept.name});
                    if (dept.deptList && dept.deptList.length > 0) {
                        recursiveRenderDeptSelect(dept.deptList, level + 1);
                    }
                });
            }
        }
        //更新部门
        function updateDept(isCreate,successCallBack,failCallback) {
            $.ajax({
                url: isCreate ? "/sys/dept/save.json" : "/sys/dept/update.json",
                data: $("#deptForm").serializeArray(),
                type: 'POST',
                success: function(result) {
                    if (result.ret) {
                        loadDeptTree();
                        if (successCallBack) {
                            successCallBack(result);
                        }
                    } else {
                        if (failCallback) {
                            failCallback(result);
                        }
                    }
                }
            })
        }

        function loadUserList(deptId) {
            var url = "/sys/user/list.json?deptId=" + deptId;
            $.get(url,function(result){
                if(result.ret) {
                    renderedUserList(result,deptId,url);
                } else {
                    showMessage("加载用户列表失败",result.msg,false);
                }
            });
        }

        function renderedUserList(result,deptId,url) {
            if(result.data) {
                var userList = result.data.list;
                var rendered = Mustache.render(userListTemplate,{
                    userList:userList,
                    "showDeptName":function(){
                        return deptMap[deptId].name;
                    },
                    "showStatus":function() {
                        return this.status==1?"有效":(this.status==0?"删除":"无效");
                    },
                    "bold":function() {
                        return function(text,render){
                            var status  = render(text);
                            if(status == "有效") {
                                return "<span class='label label-sm label-success'>有效</span>";
                            } else if(status == "无效") {
                                return "<span class='label label-sm label-warning'>无效</span>";
                            } else {
                                return "<span class='label label-sm'>删除</span>";
                            }
                        };
                    }
                });
                $("#userList").html(rendered);
                var pageSize = $("#pageSize").val();
                var pageNo = $("#userPage pageNo").val() || 1;
                var total = "";
                total = result.data.total;
                var currentSize = total > 0 ? result.data.list.length : 0;//如果总数大于0,则取结果集的条数
                bindUserClick();
                renderPage(url,total,pageNo,pageSize,currentSize,"userPage",loadAllUserList);
            } else {
                showMessage("获取用户列表",result.msg,false);
            }
        }

        //加载全部用户
        function loadAllUserList() {
            var pageSize = $("#pageSize").val();
            var pageNo = $("#userPage pageNo").val() || 1;
            var total = "";
            var url = "/sys/user/allList.json";
            $.get(url,function(result){
                if(result.ret) {
                    var allUserList = result.data.list;
                    var rendered = Mustache.render(userListTemplate,{
                        userList:allUserList,
                        "showDeptName":function () {
                            return this.dept.name;
                        },
                        "showStatus":function() {
                            return this.status==1?"有效":(this.status==0?"删除":"无效");
                        },
                        "bold":function() {
                            return function(text,render){
                                var status  = render(text);
                                if(status == "有效") {
                                    return "<span class='label label-sm label-success'>有效</span>";
                                } else if(status == "无效") {
                                    return "<span class='label label-sm label-warning'>无效</span>";
                                } else {
                                    return "<span class='label label-sm'>删除</span>";
                                }
                            };
                        }
                    });
                    $("#userList").html(rendered);
                    bindUserClick();
                    total = result.data.total;
                    var currentSize = total > 0 ? result.data.list.length : 0;//如果总数大于0,则取结果集的条数
                    renderPage(url,total,pageNo,pageSize,currentSize,"userPage",loadAllUserList);
                } else {
                    showMessage("获取用户列表",result.msg,false);
                }
            });
        }

        //用户列表添加
        $(".user-add").click(function(){
            $("#dialog-user-form").dialog({
                modal:true,
                title:"添加用户",
                open:function() {
                    //下拉框 所在部门 初始化 树形数据
                    initDeptList();
                },
                buttons:{
                    "保存":function(){
                        saveUser();
                    },
                    "关闭":function(){
                        $("#dialog-user-form").dialog("close");
                    }
                }
            });
        });

        function initDeptList() {
            optionStr = "<option>---</option>";
            recursiveRenderDeptSelect(deptList,1);
            $("#deptSelectId").append(optionStr);
        }
        
        function saveUser() {
            var data = $("#userForm").serializeArray();
            var url = "/sys/user/save.json";
            $.post(url,data,function (result) {
                if(result.ret) {
                    $("#dialog-user-form").dialog("close");
                    loadUserList(data[0].value);
                }
            });
        }

       function bindUserClick() {
            $(".user-edit").on('click',function(e) {
                var id = $(this).attr("data-id");
                var url = "/sys/user/findByUserId.json";
                var user = "";
                $.ajaxSettings.async = false;
                $.post(url,{id:id},function(result){
                    if(result.ret) {
                        user = result.data;
                    }
                });
                $.ajaxSettings.async = true;
                $("#dialog-user-form").dialog({
                    modal:true,
                    title:"修改用户",
                    open:function() {
                        initDeptList();
                        $("#deptSelectId").val(user.deptId);
                        $("#userName").val(user.username);
                        $("#userMail").val(user.mail);
                        $("#userTelephone").val(user.telephone);
                        $("#userStatus").val(user.status);
                        $("#userRemark").val(user.remark);
                        $("#userId").val(user.id);
                    },
                    buttons:{
                        "修改":function () {
                            updateUser();
                        },
                        "取消":function () {
                            $(this).dialog("close");
                        }
                    }
                });
            });
       }

       function updateUser() {
            var data = $("#userForm").serializeArray();

            var url = "/sys/user/update.json";
            $.post(url,data,function(result){
                if(result.ret) {
                    alert("修改成功");
                    $("#dialog-user-form").dialog("close");
                    loadUserList(data[0].value);
                } else {
                    showMessage("修改用户",result.msg,false);
                }
            });
       }

    })




</script>
</body>
</html>

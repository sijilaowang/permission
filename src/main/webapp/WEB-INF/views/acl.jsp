<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>权限</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        权限模块管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护权限模块和权限点关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            权限模块列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 aclModule-add"></i>
            </a>
        </div>
        <div id="aclModuleList">
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                权限点列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 acl-add"></i>
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
                                权限名称
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                权限模块
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                类型
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                URL
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                顺序
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="aclList"></tbody>
                    </table>
                    <div class="row" id="aclPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-aclModule-form" style="display: none;">
    <form id="aclModuleForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">上级模块</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="选择模块" style="width: 200px;"></select>
                    <input type="hidden" name="id" id="aclModuleId"/>
                </td>
            </tr>
            <tr>
                <td><label for="aclModuleName">名称</label></td>
                <td><input type="text" name="name" id="aclModuleName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclModuleSeq">顺序</label></td>
                <td><input type="text" name="seq" id="aclModuleSeq" value="1" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclModuleStatus">状态</label></td>
                <td>
                    <select id="aclModuleStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                        <option value="2">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclModuleRemark">备注</label></td>
                <td><textarea name="remark" id="aclModuleRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-acl-form" style="display: none;">
    <form id="aclForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所属权限模块</label></td>
                <td>
                    <select id="aclModuleSelectId" name="aclModuleId" data-placeholder="选择权限模块" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="aclName">名称</label></td>
                <input type="hidden" name="id" id="aclId"/>
                <td><input type="text" name="name" id="aclName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclType">类型</label></td>
                <td>
                    <select id="aclType" name="type" data-placeholder="类型" style="width: 150px;">
                        <option value="1">菜单</option>
                        <option value="2">按钮</option>
                        <option value="3">其他</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclUrl">URL</label></td>
                <td><input type="text" name="url" id="aclUrl" value="1" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclStatus">状态</label></td>
                <td>
                    <select id="aclStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="aclSeq">顺序</label></td>
                <td><input type="text" name="seq" id="aclSeq" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="aclRemark">备注</label></td>
                <td><textarea name="remark" id="aclRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<script id="aclModuleListTemplate" type="x-tmpl-mustache">
<ol class="dd-list ">
    {{#aclModuleList}}
        <li class="dd-item dd2-item aclModule-name {{displayClass}}" id="aclModule_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            &nbsp;
            <a class="green {{#showDownAngle}}{{/showDownAngle}}" href="#" data-id="{{id}}" >
                <i class="ace-icon fa fa-angle-double-down bigger-120 sub-aclModule"></i>
            </a>
            <span style="float:right;">
                <a class="green aclModule-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red aclModule-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/aclModuleList}}
</ol>
</script>

<script id="aclListTemplate" type="x-tmpl-mustache">
{{#aclList}}
<tr role="row" class="acl-name odd" data-id="{{id}}"><!--even -->
    <td><a href="#" class="acl-edit" data-id="{{id}}">{{name}}</a></td>
    <td>{{showAclModuleName}}</td>
    <td>{{showType}}</td>
    <td>{{url}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td>
    <td>{{seq}}</td>
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green acl-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red acl-role" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/aclList}}
</script>

<script type="text/javascript">
    $(function() {
        var aclModuleListTemplate = $("#aclModuleListTemplate").html();
        Mustache.parse(aclModuleListTemplate);

        var aclModuleId_ = -1;
        loadAllSysAclModule();
        function loadAllSysAclModule() {
            var url = "/sys/aclModule/tree.json";
            $.get(url,function(result) {
                if(result.ret) {
                    var aclModuleList = result.data;
                    var render = Mustache.render(aclModuleListTemplate,{
                        aclModuleList:aclModuleList,
                        "showDownAngle":function() {
                            return (this.aclModuleList && this.aclModuleList.length > 0) ? "":"hidden";
                        },
                        "displayClass":function() {
                            return "";
                        }
                    });
                    $("#aclModuleList").html(render);
                    recurRenderSysAclModule(result.data);
                    initAclModuleDownAngle();
                    bindClick();
                } else {
                    showMessage("加载权限",result.msg,false);
                }
            });
        }

        //权限模块点击选中事件
        function aclModuleSelected(aclModuleId) {
            if(aclModuleId_ != -1) {
                $("#aclModule_" + aclModuleId_ + " .dd2-content:first").removeClass("btn-yellow");
            }
            if(aclModuleId != aclModuleId_) {
                $("#aclModule_" + aclModuleId + " .dd2-content:first").addClass("btn-yellow");
            }
            aclModuleId_ = aclModuleId;

        }

        //初始化权限模块箭头,默认第一个展开,其他全部缩起
        function initAclModuleDownAngle() {
            //隐藏父节点下的所有子节点
            $(".dd-item").children(".dd-list").hide();
            //显示第一个父节点下的子节点
            $(".dd-item:first .dd-list").show();
            $("#aclModuleList").children().children(".dd-item:not(:first)").find("i:first").removeClass("fa-angle-double-down");
            $("#aclModuleList").children().children(".dd-item:not(:first)").find("i:first").addClass("fa-angle-double-up");
        }

        //循环渲染权限模块树
        function recurRenderSysAclModule(data) {
            for(var i = 0;i < data.length; i++) {
                if(data[i].sysAclModuleList.length > 0) {
                    var aclModuleList = data[i].sysAclModuleList;
                    var render = Mustache.render(aclModuleListTemplate,{aclModuleList:aclModuleList});
                    $("#aclModule_" + data[i].id).append(render);
                    recurRenderSysAclModule(aclModuleList);
                }
            }
        }

        function bindClick() {
            $(".aclModule-add").on("click",function(e) {
                e.preventDefault();
                e.stopPropagation();
                $("#dialog-acl-form").dialog({
                    modal:true,
                    title:"增加权限模块",
                    buttons:{
                        "保存":function(){
                            saveSysAclModule();
                            $("#dialog-acl-form").dialog("close");
                            loadAllSysAclModule();
                        },
                        "取消":function() {
                            $(this).dialog("close");
                        }
                    }
                });
            });
            $(".aclModule-name").on("click",function(e){
                e.preventDefault();
                e.stopPropagation();
                var aclModuleId = $(this).attr("data-id");
                aclModuleSelected(aclModuleId);
            });
        }

        function saveSysAclModule() {
            var url = "/sys/aclModule/save.json";
            var data = $("#aclForm").serializeArray();
            $.post(url,data,function(result) {
                if(result.ret) {
                    showMessage("新增权限模块","success",true);
                } else {
                    showMessage("新增权限模块",result.msg,flase);
                }
            });
        }

    })
</script>

</body>
</html>

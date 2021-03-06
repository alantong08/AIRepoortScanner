<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>报单管理</title>
	<base href="value">
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="easyui/themes/demo.css">
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.edatagrid.js"></script>
    
    <!-- EasyUIEx -->
	<link rel="stylesheet" type="text/css" href="easyuiex/css/easyuiex.css">
	<script type="text/javascript" src="easyuiex/easy.easyuiex.js"></script>
	<script type="text/javascript" src="easyuiex/easy.easyuiex-validate.js"></script>
	<script type="text/javascript" src="easyuiex/easy.jquery.edatagrid.js"></script>
</head>
<body>
    <h2>报单查询</h2>
    
    <table id="orderEDataGridAutoSave"  title="报单记录" style="width:80%;height:700px"
            toolbar="#toolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true">
        <thead>
            <tr>
                <th field="id">序号</th>
                <th field="userName" width="30" editor="{type:'validatebox',options:{required:true}}">姓名</th>
                <th field="nickName" width="50" editor="{type:'validatebox',options:{required:true}}">群昵称</th>
                 <th field="groupName" width="50" editor="{type:'validatebox',options:{required:true}}">所在群</th>
                <th field="scanDate" width="50" editor="{type:'validatebox',options:{required:true}}">扫码日期</th>
                <th field="orderNum" width="100" editor="{type:'validatebox',options:{required:true,validType:'number'}}">订单号</th>
                <th field="merchantName" width="50" editor="{type:'validatebox',options:{required:true}}">商户名</th>
                <th field="actualPrice" width="50" editor="{type:'validatebox',options:{required:true}}">扫码金额</th>
                <th field="transferType" width="30" editor="{type:'validatebox',options:{required:true}}">交易方式</th>
                <th field="activityType" width="50" editor="{type:'validatebox',options:{required:true}}">参与助攻</th>
                <th field="rate" width="30" editor="{type:'validatebox',options:{required:true}}">费率</th>
                <th field="alipayAccount" width="70" editor="{type:'validatebox',options:{required:true}}">支付宝</th>
                <th field="comment" width="50" editor="{type:'validatebox',options:{required:false}}">备注</th>
 				<th field="createdDate" width="50" editor="{type:'validatebox',options:{required:false}}">报单时间</th>
 				<th field="imageName" align="center" data-options="width:50, formatter: imgFormatter" >订单截图</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
    <form id="ff" method="post" action="admin/download" >
		<div>
			<span>序号:</span>
			<input id="id" name="id" class="easyui-textbox" /> 
			<span>姓名:</span>
			<input id="name2" name="name2" class="easyui-textbox" /> 
			<span>报单开始日期:</span> 
			<input id="scanDateFrom" name="scanDateFrom" class="easyui-datebox" /> 
			<span>报单结束日期:</span> 
			<input id="scanDateTo" name="scanDateTo" class="easyui-datebox" />
		</div>
		<div>
		<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">查询</a>
		<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteSelectedRow()">删除</a> 
		<a class="easyui-linkbutton" iconCls="icon-export-to-excel" plain="true" onclick="doExport()">导出</a>
		</div>

	</form>
    </div>
<script type="text/javascript">

	function myformatter(date){
      var y = date.getFullYear();
      var m = date.getMonth()+1;
      var d = date.getDate();
      return y+'/'+ (m < 10 ? '0' + m : m)+'/'+ (d < 10 ? '0' + d : d);
  	}
	
	function formatterFirstDay(date){
	      var y = date.getFullYear();
	      var m = date.getMonth()+1;
	      return y+'/'+ (m < 10 ? '0' + m : m)+'/01';
	  	}
	
	function myparser(s){
          if (!s) return new Date();
          var ss = s.split('/');
          var y = parseInt(ss[0],10);
          var m = parseInt(ss[1],10);
          var d = parseInt(ss[2],10);
          if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
              return new Date(y,m-1,d)
          } else {
              return new Date();
          }
      }

  	$(function() {
  		$('.easyui-datebox').datebox({
  	        formatter : myformatter,
  	        parser : myparser
  	    });
  		
  		$('#scanDateTo').datebox('setValue', myformatter(new Date()));
  		$('#scanDateFrom').datebox('setValue', formatterFirstDay(new Date()));

  		$("#orderEDataGridAutoSave").initEdatagrid({
  			url : "admin/getOrderList",
  			updateUrl : "admin/saveOrder",
  			autoSave : true, //auto save the editing row when click out of datagrid!
  			iconCls:'icon-group',
  			onLoadSuccess:function(){
  			//	$(this).datagrid("enableDnd");
  			},
  			showHeaderContextMenu : true, //表头添加右键菜单，可选择显示的列
  			checkbox : true,
  			checkOnSelect : true,
  			singleSelect : true,
  			clickEdit : false, //单击编辑
  			showMsg : false, // 显示操作消息
  			/*
  			 * 分页控制
  			 */
  			pageSize:20,
  			pageList: [20, 30, 40, 50]
  		});
  	
  	});
  	
	 function doSearch() {
			$("#orderEDataGridAutoSave").edatagrid("load", {
				name : $("#name2").val(),
				id: $("#id").val(),
				scanDateFrom: $("#scanDateFrom").datebox("getValue"),
				scanDateTo: $("#scanDateTo").datebox("getValue")
			});
	 }
	 
	 function deleteSelectedRow() {
		 var row = $('#orderEDataGridAutoSave').datagrid('getSelected');
		 if(row){
			 $.messager.confirm('确认','确认删除选中行吗?',function(r){
				    if (r){	 
						$.ajax({
						      url: 'admin/deleteOrder',
						      type: 'POST',
						      data : {id : row.id},
						      success: function(response){
						    	  $('#orderEDataGridAutoSave').datagrid('reload');
						    	}
						});
				    }
				}); 
		 }else{
			 $.messager.alert('Warning','请选择需要删除的记录');
		 }

	 }
	 
	 function imgFormatter(value,row,index){	 
		var element = '<a href='+value+'  target="_blank"><img style="width:30px; height:30px" src=' + value + '/></a>';
		return element;
	 }
	 
	 function doExport(){
	   var scanDateFrom= $("#scanDateFrom").datebox("getValue");
		if(!scanDateFrom){
			alert("请选择需要导出数据的开始日期");
 			$("#scanDateFrom").focus();
 			return;
 		}
		 var scanDateTo= $("#scanDateTo").datebox("getValue");
		if(!scanDateTo){
			alert("请选择需要导出数据的结束日期");
 			$("#scanDateTo").focus();
 			return;
 		}
		
		 $('#ff').submit();
	 }
	 
	 

</script>
    
</body>
</html>
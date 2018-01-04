<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>报单管理</title>

	<link rel="stylesheet" type="text/css" href="/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="/easyui/themes/demo.css">
    <script type="text/javascript" src="/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/easyui/jquery.edatagrid.js"></script>
    
    <!-- EasyUIEx -->
	<link rel="stylesheet" type="text/css" href="/easyuiex/css/easyuiex.css">
	<script type="text/javascript" src="/easyuiex/easy.easyuiex.js"></script>
	<script type="text/javascript" src="/easyuiex/easy.easyuiex-validate.js"></script>
	<!-- 使用 EasyUIEx的 easy.jquery.edatagrid.js 代替 jquery.edatagrid.js -->
	<script type="text/javascript" src="/easyuiex/easy.jquery.edatagrid.js"></script>
</head>
<body>
    <h2>报单查询</h2>
    
    <table id="orderEDataGridAutoSave"  title="报单记录" style="width:80%;height:500px"
            toolbar="#toolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true">
        <thead>
            <tr>
                <th field="id" hidden="true">ID</th>
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
                <th field="comments" width="50" editor="{type:'validatebox',options:{required:false}}">备注</th>
 				<th field="createdDate" width="50" editor="{type:'validatebox',options:{required:false}}">报单时间</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
		<div>
			<span>姓名:</span>
			<input id="name2" name="name2" class="easyui-textbox" /> 
			<span>扫码日期:</span> 
			<input id="scanDate" name="scanDate" class="easyui-datebox" /> 
			<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="doSearch()">Search</a>
		</div>
    </div>
<script type="text/javascript">
  	$(function() {
  		$('.easyui-datebox').datebox({
  	        formatter : function(date){
  	            var y = date.getFullYear();
  	            var m = date.getMonth()+1;
  	            var d = date.getDate();
  	            return y+'-'+ (m < 10 ? '0' + m : m)+'-'+ (d < 10 ? '0' + d : d);
  	        },
  	        parser : function(s){
  	            if (!s) return new Date();
  	            var ss = s.split('-');
  	            var y = parseInt(ss[0],10);
  	            var m = parseInt(ss[1],10);
  	            var d = parseInt(ss[2],10);
  	            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
  	                return new Date(y,m-1,d)
  	            } else {
  	                return new Date();
  	            }
  	        }

  	    });
  		
  		$("#orderEDataGridAutoSave").initEdatagrid({
  			url : "/admin/getOrderList",
  			updateUrl : "/admin/saveOrder",
  			autoSave : true, //auto save the editing row when click out of datagrid!
  			iconCls:'icon-group',
  			destroyMsg : {
  				norecord : { // when no record is selected
  					title : 'Warning',
  					msg : '请选择要删除的行.'
  				},
  				confirm : { // when select a row
  					title : 'Confirm',
  					msg : '确定要删除该行数据吗?'
  				}
  			},
  			onLoadSuccess:function(){
  			//	$(this).datagrid("enableDnd");
  			},
  			showHeaderContextMenu : true, //表头添加右键菜单，可选择显示的列
  			checkbox : true,
  			checkOnSelect : true,
  			singleSelect : true,
  			autoSave : true,
  			//queryParam:{"rows":dg.datagrid("options").pageSize},
  			clickEdit : true, //单击编辑
  			showMsg : true, // 显示操作消息
  			/*
  			 * 分页控制
  			 */
  			pageSize:5,
  			pageList: [5, 10, 15,20]
  		});
  	
  	});
  	
	 doSearch=function() {
			$("#orderEDataGridAutoSave").edatagrid("load", {
				name : $("#name2").val(),
				scanDate : $("#scanDate").combobox("getValue")
			});
		}
    	        
        
</script>
    
</body>
</html>
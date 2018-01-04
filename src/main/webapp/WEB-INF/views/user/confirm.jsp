<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>报单系统</title>
<link rel="stylesheet" type="text/css"
	href="/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="/easyui/themes/mobile.css">
<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
<script type="text/javascript" src="/easyui/jquery.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.mobile.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var orderDetail = jQuery.parseJSON('${billDetail}');
		$("#date").datebox('setValue', orderDetail.date);
		$("#name").textbox('setValue', orderDetail.name);
		$("#nickName").textbox('setValue', orderDetail.nickName);
		$("#orderNum").textbox('setValue', orderDetail.orderNum);
		$("#merchantName").textbox('setValue', orderDetail.merchantName);
		$("#actualPrice").textbox('setValue', orderDetail.actualPrice);
		$("#discountedPrice").textbox('setValue', orderDetail.discountedPrice);
		$("#transferType").combobox('setValue', orderDetail.transferType);
		$("#activityType").combobox('setValue', orderDetail.activityType);
		$("#rate").textbox('setValue', orderDetail.rate);
		$("#alipayAccount").textbox('setValue', orderDetail.alipayAccount);
		$("#comments").textbox('setValue', orderDetail.comments);
	});
	
	$(function() {
		$('#ff').submit(function(){
		    $.ajax({
		      url: '/user/tess4j/saveBillOrder/',
		      type: 'POST',
		      data : $('#ff').serialize(),
		      success: function(response){
		    	  if(response.status == true){
		    		  window.location.href = response.view;
		    	 }else{
		    		 alert("出现异常，请联系管理员");
		    	 }
		    	}
		    });
		    return false;
		});
	});
</script>
</head>
<body>
	<div class="easyui-navpanel" style="position: relative; padding: 20px">
		<header>
			<div class="m-toolbar">
				<div class="m-title">报单数据确认</div>
		</header>
		<form id="ff" method="post">
			<div style="margin-bottom: 10px">
				<input id="date" name="date" class="easyui-datebox" label="扫码日期:"
					data-options="required:true" style="width: 100%"></input>
			</div>

			<div style="margin-bottom: 10px">
				<input id="nickName" name="nickName" class="easyui-textbox"
					data-options="required:true" label="群昵称:" style="width: 100%"></input>
			</div>
			<div style="margin-bottom: 10px">
				<input id="orderNum" name="orderNum" class="easyui-textbox"
					data-options="required:true" label="订单号:" style="width: 100%"></input>
			</div>
			<div style="margin-bottom: 10px">
				<input id="merchantName" name="merchantName" class="easyui-combobox"
					 data-options="valueField:'merchantId',textField:'merchantName',url:'/user/tess4j/merchant'" data-options="required:true" label="商户名" style="width: 100%"></input>
			</div>
			<div style="margin-bottom: 10px">
				<input id="actualPrice" name="actualPrice" class="easyui-textbox"
					data-options="required:true" label="扫码金额:" style="width: 100%"></input>
			</div>
			<div style="margin-bottom: 10px">
				<input id="discountedPrice" name="discountedPrice" class="easyui-textbox"
					data-options="required:true" label="优惠金额:" style="width: 100%"></input>
			</div>
			<div style="margin-bottom: 10px">
				<select id="transferType" name="transferType"
					class="easyui-combobox" label="交易方式:" data-options="required:true"
					style="width: 100%">
					<option value="1">融E联</option>
					<option value="2">微信</option>
					<option value="3">支付宝</option>
				</select>
			</div>
			<div style="margin-bottom: 10px">
				<select id="activityType" name="activityType"
					class="easyui-combobox" label="是否参与助攻:"
					data-options="required:true" style="width: 100%">
					<option value="1">是，记入月底助攻金额</option>
					<option value="2">否，选择高费率自用</option>
					<option value="3">幸运星待遇</option>
					<option value="4">高端群特供撸卡</option>
					<option value="5">小娇周周特别活动0.1</option>
				</select>
			</div>
			<div style="margin-bottom: 10px">
				<input id="rate" name="rate" class="easyui-textbox"
					data-options="required:true" label="费率:" style="width: 100%"></input>
			</div>
			<div style="margin-bottom: 10px">
				<input id="alipayAccount" name="alipayAccount"
					class="easyui-textbox" data-options="required:true" label="支付宝:"
					style="width: 100%"></input>
			</div>
			<div style="margin-bottom: 10px">
				<input id="comments" name="comments"
					class="easyui-textbox" data-options="required:false" label="备注:"
					style="width: 100%"></input>
			</div>

			<div style="margin-bottom: 20px">
				<a id="submit" class="easyui-linkbutton" style="width: 100%"
					onclick="$('#ff').submit()">提交</a>
			</div>
		</form>
	</div>
</body>
</html>
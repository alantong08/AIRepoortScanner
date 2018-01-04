<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>报单系统</title>
<link rel="stylesheet" type="text/css" href="/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="/easyui/themes/mobile.css">
<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
<script type="text/javascript" src="/easyui/jquery.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.mobile.js"></script>
</head>
<body>
	<div class="easyui-navpanel" style="position: relative; padding: 20px">
		<header>
			<div class="m-toolbar">
				<div class="m-title">报单系统</div>
		</header>
		<form id="ff" action="/user/tess4j/submit/" method="post"
			enctype="multipart/form-data">
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
				<input id="file-order" name="file-order" class="easyui-filebox"
					label="订单截图:" data-options="required:true" prompt=""
					style="width: 100%">
			</div>
			<div style="margin-bottom: 20px">
				<a class="easyui-linkbutton" style="width: 100%"
					onclick="$('#ff').submit()">提交</a>
			</div>
		</form>
	</div>
</body>
</html>
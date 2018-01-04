<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="/easyui/themes/mobile.css">
<link rel="stylesheet" type="text/css" href="/easyui/themes/icon.css">
<script type="text/javascript" src="/easyui/jquery.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.mobile.js"></script>
</head>
<body>
	<div class="easyui-navpanel">
 <header>
            <div class="m-toolbar">
                <div class="m-title">报单成功</div>
                <div class="m-left">
                    <a href="／user/confirm" class="easyui-linkbutton m-back" plain="true" outline="true">后退</a>
                </div>
            </div>
        </header>

		<div style="text-align: center; margin-top: 30px">
			<a href="/user/uploadOrderChart" id="register" class="easyui-linkbutton"
				style="width: 200px; height: 35px"><span style="font-size: 16px">再报一单</span></a>
		</div>
		<div style="text-align: center; margin-top: 30px">
			<a href="/user/uploadOrderChart" id="check" class="easyui-linkbutton"
				style="width: 200px; height: 35px"><span style="font-size: 16px">查询报单记录</span></a>
		</div>

	</div>
</body>
</html>
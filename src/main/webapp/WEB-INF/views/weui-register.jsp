<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title>报单系统</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width, initial-scale=1, user-scalable=no">
<link rel="stylesheet" href="/lib/weui.css">
<link rel="stylesheet" href="/css/jquery-weui.css">
<link rel="stylesheet" href="/css/demos.css">

</head>

<body ontouchstart>


	<header class='demos-header'>
		<h1 class="demos-title">注册报单系统</h1>
	</header>
	<form id="registerForm">
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">手机号</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="mobile" name="mobile" type="number" pattern="[0-9]*" 
						placeholder="请输入手机号">
				</div>
			</div>

			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">密码</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="password" name="password" type="password"  placeholder="请输入密码">
				</div>
			</div>
			
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">姓名</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="userName" name="userName" type="text"  placeholder="请输入姓名">
				</div>
			</div>
			
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">群昵称</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="nickName" name="nickName" type="text" placeholder="请输入群昵称">
				</div>
			</div>

			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">所在群</label>
				</div>
	 			<div class="weui-cell__bd">	          
		          <select class="weui-select" id="groupName" name="groupName">
		          		<option value="">请选择</option>
						<option value="高级群">高级群</option>
						<option value="中级群">中级群</option>
						<option value="初级群">初级群</option>
						<option value="入门群">入门群</option>
						<option value="会计群">会计群</option>
		          </select>
		         </div>
			</div>			
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">支付宝</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="alipayAccount" name="alipayAccount" type="text" placeholder="请输入支付宝">
				</div>
			</div>

		</div>

		<div class="weui-btn-area">
			<a id="showTooltips" class="weui-btn weui-btn_primary">注册</a>
		</div>
	</form>
	<div class="weui-footer weui-footer_fixed-bottom">
  	<p class="weui-footer__text">Copyright © 2008-2018 兔少</p>
	</div>

<script src="/lib/jquery-2.1.4.js"></script>
<script src="/lib/fastclick.js"></script>
<script>
	$(function() {
		FastClick.attach(document.body);
	});
</script>
<script src="/js/jquery-weui.js"></script>
<script> 	

 	$("#showTooltips").click(function() {
	    
 		var mobileNO = String($("#mobile").val());
 		if(mobileNO.length!=11 || !/1[3|4|5|7|8]\d{9}/.test(mobileNO)){
 			$.toptip('手机号格式不正确', 'error');
 			return;
 		}
 		var password = $("#password").val();
 		if(!password || password.length<6){
 			$("#password").focus();
 			$.toptip('密码至少6位', 'error');
 			return;
 		}
 		var name = $("#userName").val();
 		if(!name){
 			$.toptip('用户名不能为空', 'error');
 			$("#userName").focus();
 			return;
 		}
 		var nickName = $("#nickName").val();
 		if(!nickName){
 			$.toptip('群昵称不能为空', 'error');
 			$("#nickName").focus();
 			return;
 		}
 		
 		var groupName = $("#groupName").val();
 		if(!groupName){
 			$.toptip('所在组不能为空', 'error');
 			$("#groupName").focus();
 			return;
 		}
 		
 		var alipayAccount = $("#alipayAccount").val();
 		if(!alipayAccount){
 			$.toptip('支付宝不能为空', 'error');
 			$("#alipayAccount").focus();
 			return;
 		}
 		$.ajax({
		      url: '/loginRegister/register',
		      type: 'POST',
		      data : $('#registerForm').serialize(),
		      success: function(response){
		    	 if(response.registered == true){
		    		 $.toptip('手机号已被注册', 'error');
		    	 }else{
		    		 $.toptip('注册成功', 'success');
		    		 window.location.href = response.view;
		    	 }
		    	}
		    });
	}); 
	
</script>
</body>
</html>

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
		<p class="demos-title">我的个人信息</p>
	</header>
	<form id="updateForm">
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">手机号</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="mobile" name="mobile" type="number" readonly>
				</div>
			</div>
			
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">姓名</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="userName" name="userName" type="text" readonly>
				</div>
			</div>

			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">密码</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="password" name="password" type="password" >
				</div>
			</div>
			

			
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">群昵称</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="nickName" name="nickName" type="text" >
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">所在组</label>
				</div>
			 <div class="weui-cell__bd">	          
		          <select class="weui-select" id="groupName" name="groupName">
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
					<input class="weui-input" id="alipayAccount" name="alipayAccount" type="text" >
				</div>
			</div>

		</div>

		<div class="weui-btn-area">
			<a id="updateBtn" class="weui-btn weui-btn_primary">修改</a>
		</div>
	</form>
 <%@ include file="footer.jsp"%>

<script src="/lib/jquery-2.1.4.js"></script>
<script src="/lib/fastclick.js"></script>
<script>
	$(function() {
		FastClick.attach(document.body);
	});
</script>
<script src="/js/jquery-weui.js"></script>
<script> 	
$(document).ready(function() {
	
	var userInfo = $.parseJSON('${userInfo}');
	if(userInfo){
		$("#mobile").val(userInfo.mobile);
		$("#userName").val(userInfo.userName);
		$("#password").val(userInfo.password);
		$("#nickName").val(userInfo.nickName);
		$("#groupName").val(userInfo.groupName);
		$("#alipayAccount").val(userInfo.alipayAccount);
	}
});


 	$("#updateBtn").click(function() {
 		var password = $("#password").val();
 		if(!password || password.length<6){
 			$("#password").focus();
 			$.toptip('密码至少6位', 'error');
 			return;
 		}
 		var nickName = $("#nickName").val();
 		if(!nickName){
 			$.toptip('群昵称不能为空', 'error');
 			$("#nickName").focus();
 			return;
 		}
 		var alipayAccount = $("#alipayAccount").val();
 		if(!alipayAccount){
 			$.toptip('支付宝不能为空', 'error');
 			$("#alipayAccount").focus();
 			return;
 		}
 		$.ajax({
		      url: '/loginRegister/updateUserInfo',
		      type: 'POST',
		      data : $('#updateForm').serialize(),
		      success: function(){
		    		 $.toptip('修改成功', 'success');
		    	  },
		    	  error:function(){
		    		  $.toptip('修改失败', 'error');
		    	  }});
		
	}); 
	
</script>
</body>
</html>

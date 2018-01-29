<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>	
<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>报单系统</title>
    <meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

<link rel="stylesheet" href="lib/weui.min.css">
<link rel="stylesheet" href="css/jquery-weui.css">
<link rel="stylesheet" href="css/demos.css">

  </head>

  <body ontouchstart>
      <header class='demos-header'>
      	<p class='demos-sub-title'>报单数据确认</p>
      </header>

   	<form id="ff"  method="post" >
		<div class="weui-cells weui-cells_form">
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">扫码日期：</label></div>
	        <div class="weui-cell__bd">
	        <input class="weui-input" type="date" id="scanDate" name="scanDate" />
	        <input type="text" id="imageName" name="imageName" hidden="true"/>
	        </div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">姓名:</label></div>
	        <div class="weui-cell__bd"><input class="weui-input" type="text" id="userName" name="userName"/></div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">群昵称:</label></div>
	        <div class="weui-cell__bd"><input class="weui-input" type="text" id="nickName" name="nickName"/></div>
	      </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">订单号:</label></div>
	        <div class="weui-cell__bd"><input class="weui-input" type="number" id="orderNum" name="orderNum"/></div>
	      </div>
	      <div class="weui-cell weui-cell_select weui-cell_select-after">
	        <div class="weui-cell__hd"><label class="weui-label">商户名:</label></div>
	        <div class="weui-cell__bd">
	         	<select class="weui-select"  id="merchantName" name="merchantName"></select>
	      	</div>
		  </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">扫码金额:</label></div>
 			<div class="weui-cell__bd"><input class="weui-input" type="text" id="actualPrice" name="actualPrice"/></div>
		  </div>
<!-- 	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">优惠金额:</label></div>
 			<div class="weui-cell__bd"><input class="weui-input" type="text" id="discountedPrice" name="discountedPrice"/></div>
		  </div> -->
		  <div class="weui-cell  weui-cell_select weui-cell_select-after">
	        <div class="weui-cell__hd"><label class="weui-label">交易方式:</label></div>
 			<div class="weui-cell__bd">	          
 			<select class="weui-select" id="transferType" name="transferType">
					<option value="1">融E联</option>
					<option value="2">微信</option>
					<option value="3">支付宝</option>
	          </select>
	          </div>
		  </div>	
		 <div class="weui-cell  weui-cell_select weui-cell_select-after">
				<div class="weui-cell__hd">
					<label class="weui-label">所在群:</label>
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
		  <div class="weui-cell weui-cell_select weui-cell_select-after">
	        <div class="weui-cell__hd"><label class="weui-label">参与助攻:</label></div>
 			<div class="weui-cell__bd">	          
	          <select class="weui-select" id="activityType" name="activityType">
					<option value="1">月底助攻</option>
					<option value="2">月底不助攻</option>
					<option value="3">幸运星</option>
					<option value="4">新人福利</option>
					<option value="5">邀请福利下月可用</option>
					<option value="6">公众号福利</option>
					<option value="7">回血活动返利</option>
					<option value="8">微信周末摇摇乐</option>
	          </select>
	          </div>
		  </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">费率:</label></div>
 			<div class="weui-cell__bd"><input class="weui-input" type="text" id="rate" name="rate"/></div>
		  </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">支付宝:</label></div>
 			<div class="weui-cell__bd"><input class="weui-input" type="text" id="alipayAccount" name="alipayAccount"/></div>
		  </div>
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">备注:</label></div>
 			<div class="weui-cell__bd"><input class="weui-input" type="text" id="comment" name="comment"/></div>
		  </div>		  		  		  		  
		<div class="weui-btn-area">
			<a class="weui-btn weui-btn_primary" id="showTooltips">提交</a>
			<a href="tabbar" class="weui-btn weui-btn_default">取消</a>
		</div>
		</div>
	</form>

<script src="lib/jquery-2.1.4.js"></script>
<script src="lib/fastclick.js"></script>
<script>
  $(function() {
    FastClick.attach(document.body);
  });
</script>
<script src="js/jquery-weui.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		
		var orderDetail = $.parseJSON('${billDetail}');
		
		$("#scanDate").val(orderDetail.scanDate);
		$("#userName").val(orderDetail.userName);
		$("#nickName").val(orderDetail.nickName);
		$("#orderNum").val(orderDetail.orderNum);
		$("#groupName").val(orderDetail.groupName);
		$("#actualPrice").val(orderDetail.actualPrice);
		$("#discountedPrice").val(orderDetail.discountedPrice);
		$("#transferType").val(orderDetail.transferType);
		$("#activityType").val(orderDetail.activityType);
		$("#rate").val(orderDetail.rate);
		$("#alipayAccount").val(orderDetail.alipayAccount);
		$("#comment").val(orderDetail.comment);
		$("#imageName").val(orderDetail.imageName);
		
		$.post("user/tess4j/merchant", function(data){
			for(var i=0;i<data.length;i++){
				$("#merchantName").append("<option value='"+data[i].merchantId+"'>"+data[i].merchantName+"</option>");
			}
			$("#merchantName").val(orderDetail.merchantName);
		});
	});
	
	$("#showTooltips").click(function() {
		var scanDate= $("#scanDate").val();
 		if(!scanDate){
 			$("#scanDate").focus();
 			$.toptip('扫码日期不能为空', 'error');
 			return;
 		}

 		var orderNum = $("#orderNum").val();
 		if(!orderNum){
 			$.toptip('订单号不能为空', 'error');
 			$("#orderNum").focus();
 			return;
 		}
 		
 		if(orderNum.length<27){
 			$.toptip('订单号长度小于27位， 请核对！', 'error');
 			$("#orderNum").focus();
 			return;
 		}
 		var merchantName = $("#merchantName").val();
 		if(!merchantName){
 			$.toptip('商户名不能为空', 'error');
 			$("#merchantName").focus();
 			return;
 		}
 		
 		var actualPrice = $("#actualPrice").val();
 		if(!actualPrice){
 			$.toptip('扫码金额不能为空', 'error');
 			$("#actualPrice").focus();
 			return;
 		}
 		
 		var rate = $("#rate").val();
 		if(!rate){
 			$.toptip('费率不能为空', 'error');
 			$("#rate").focus();
 			return;
 		}

		$.ajax({
	      url: 'user/tess4j/saveBillOrder/',
	      type: 'POST',
	      data : $('#ff').serialize(),
	      success: function(response){
	    	  if(response.status == true){
	    		  window.location.href = response.view;
	    	  }
	    	}
	    });
	});
	
</script>
  </body>
</html>

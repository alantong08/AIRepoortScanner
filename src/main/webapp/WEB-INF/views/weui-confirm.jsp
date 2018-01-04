<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>报单系统</title>
    <meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">

<link rel="stylesheet" href="/lib/weui.min.css">
<link rel="stylesheet" href="/css/jquery-weui.css">
<link rel="stylesheet" href="/css/demos.css">

  </head>

  <body ontouchstart>
      <header class='demos-header'>
      	<p class='demos-sub-title'>报单数据确认</p>
      </header>

   	<form id="ff"  method="post" >
		<div class="weui-cells weui-cells_form">
	      <div class="weui-cell">
	        <div class="weui-cell__hd"><label class="weui-label">扫码日期：</label></div>
	        <div class="weui-cell__bd"><input class="weui-input" type="date" id="scanDate" name="scanDate" /></div>
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
 			<div class="weui-cell__bd"><input class="weui-input" type="text" id="comments" name="comments"/></div>
		  </div>		  		  		  		  
		<div class="weui-btn-area">
			<a class="weui-btn weui-btn_primary" id="showTooltips" onclick="$('#ff').submit()">提交</a>
		
		</div>
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
		$("#comments").val(orderDetail.comments);
		
		$.post("/user/tess4j/merchant", function(data){
			for(var i=0;i<data.length;i++){
				$("#merchantName").append("<option value='"+data[i].merchantId+"'>"+data[i].merchantName+"</option>");
			}
			$("#merchantName").val(orderDetail.merchantName);
		});
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
  </body>
</html>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>  
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
      	<p class='demos-sub-title'>查询报单数据</p>
      </header>
    
	<form id="searchForm" method="post" action="/user/tess4j/searchBillOrder">
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
		</div>

		<div class="weui-btn-area">
			 <a class="weui-btn weui-btn_primary" id="searchButton">查询</a>
		</div>
	</form>
	<br>
	<c:choose>
	    <c:when test="${empty data && searchFlag}">
	         <h2 class="demos-title">未查询到结果</h2>
	    </c:when>
	    <c:otherwise>
	    <c:forEach items="${data}" var="item">
	    <div class="weui-form-preview">
        <div class="weui-form-preview__hd">
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">付款金额</label>
          <em class="weui-form-preview__value">¥${item.actualPrice}</em>
        </div>
      </div>
      <div class="weui-form-preview__bd">
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">扫码日期</label>
          <span class="weui-form-preview__value">${item.scanDate}</span>
        </div>
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">姓名</label>
          <span class="weui-form-preview__value">${item.userName}</span>
        </div>
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">群昵称</label>
          <span class="weui-form-preview__value">${item.nickName}</span>
        </div>
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">订单号</label>
          <span class="weui-form-preview__value">${item.orderNum}</span>
        </div>
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">商户名</label>
          <span class="weui-form-preview__value">${item.merchantName}</span>
        </div>  
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">交易方式</label>
          <span class="weui-form-preview__value">${item.transferType}</span>
        </div> 
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">参与助攻</label>
          <span class="weui-form-preview__value">${item.activityType}</span>
        </div> 
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">费率</label>
          <span class="weui-form-preview__value">${item.rate}</span>
        </div> 
        <div class="weui-form-preview__item">
          <label class="weui-form-preview__label">支付宝</label>
          <span class="weui-form-preview__value">${item.alipayAccount}</span>
        </div>                                      
      </div>
    </div>
    <br>
	</c:forEach>
	    </c:otherwise>
	</c:choose>	
	<br><br>
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
		$("#mobile").val('${mobileNo}');
	});
	
	$("#searchButton").click(function() {
		searchResult();
	}); 
	

	function searchResult(){	
	var mobileNO = String($("#mobile").val());
	if (mobileNO.length != 11 || !/1[3|4|5|7|8]\d{9}/.test(mobileNO)) {
		$.toptip('手机号格式不正确', 'error');
		return;
	}
	$("#searchForm").submit();
	
	}
</script>
  </body>
</html>

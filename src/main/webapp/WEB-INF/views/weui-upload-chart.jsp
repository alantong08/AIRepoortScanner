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
      <h3 class="demos-title">上传订单</h3>
    </header>

   	<form id="ff" method="post" action="/user/tess4j/submit/" enctype="multipart/form-data">
		<div class="weui-cells weui-cells_form">
	      <div class="weui-cell weui-cell_select weui-cell_select-after">
	      
	        <div class="weui-cell__hd">
	          <label for="" class="weui-label">参与助攻:</label>
	        </div>
	        <div class="weui-cell__bd">
	          <select class="weui-select" id="activityType" name="activityType">
					<option value="1">月底助攻</option>
					<option value="2">月底不助攻</option>
	          </select>
	        </div>
	      </div>

			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">订单截图:</label>
				</div>
				<div class="weui-uploader__input-box">
					<input class="weui-uploader__input" id="file-order" name="file-order"  type="file"  placeholder="请上传文件">
				</div>
			</div>

		</div>

		<div class="weui-btn-area">
			<a class="weui-btn weui-btn_primary" id="showTooltips" >提交</a>
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
$("#showTooltips").click(function() {
	$.showLoading("订单处理中...");
	$('#ff').submit();
});
</script>
  </body>
</html>

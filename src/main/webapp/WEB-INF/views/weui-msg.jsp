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

    <div class="weui-msg">
      <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
      <div class="weui-msg__text-area">
        <h2 class="weui-msg__title">报单成功</h2>
       
      </div>
      <div class="weui-msg__opr-area">
        <p class="weui-btn-area">
          <a href="/weuiUploadChart" class="weui-btn weui-btn_primary">再报一单</a>
          <a href="/weiuiSearchBar" class="weui-btn weui-btn_default">查询报单</a>
        </p>
      </div>
    </div>
    
     <%@ include file="footer.jsp"%>
<script src="/lib/jquery-2.1.4.js"></script>
<script src="/lib/fastclick.js"></script>
<script>
  $(function() {
    FastClick.attach(document.body);
  });
</script>
<script src="/js/jquery-weui.js"></script>

  </body>
</html>

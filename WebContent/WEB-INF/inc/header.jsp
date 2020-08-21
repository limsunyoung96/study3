<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- header.jsp 
	 js, css, 일부 헤더 --%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%
	/* String hate = "배동환"; */
%>
<title>부트스트랩 101 템플릿</title>

    <!-- 부트스트랩 css-->
    <link href="<%=request.getContextPath()%>/bootstrap-3.3.2/css/bootstrap.css" rel="stylesheet">
        <!-- 폰트어 css-->
    <link href="<%=request.getContextPath()%>/fontawesome-free-5.14.0-web/css/all.css" rel="stylesheet">
    
    
   <!-- jQuery  -->
   <script src="<%=request.getContextPath()%>/js/jquery1.11.2.min.js"></script>
   
    <!-- 부트스트랩 js -->
   <script src="<%=request.getContextPath()%>/bootstrap-3.3.2/js/bootstrap.js"></script>
   
	<!-- 어플리케이션에서 요청하는 것은 study/를 쓰지 않아도 괜찮지만 main.css(header.jsp안에 있음)는 study/가 필요함 -->
	<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet">
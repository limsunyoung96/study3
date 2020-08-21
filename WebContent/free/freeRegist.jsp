<%@page import="com.study.free.service.FreeBoardServiceImpl"%>
<%@page import="com.study.free.service.IFreeBoardService"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
 	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html >
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>freeRegister.jsp</title>
</head>
<body>
<%@ include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<h3>글 등록</h3>
		<jsp:useBean id="board" class="com.study.free.vo.FreeBoardVO" />
		<jsp:setProperty property="*" name="board" />
		${board}
		<%
			IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
			board.setBoIp(request.getRemoteAddr());
			freeBoardService.registBoard(board);
		%>
			<div class="alert alert-warning">
				<h4> 글이 등록되었습니다. </h4>
				정상적으로 글이 등록되었습니다.
			</div>	

	
</body>
</html>
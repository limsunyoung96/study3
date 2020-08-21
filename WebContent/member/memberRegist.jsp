<%@page import="com.study.exception.BizDuplicateKeyException"%>
<%@page import="com.study.member.service.MemberServiceImpl"%>
<%@page import="com.study.member.service.IMemberService"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>memberList.jsp</title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<h3>회원등록</h3>
		<jsp:useBean id="member" class="com.study.member.vo.MemberVO" />
		<jsp:setProperty property="*" name="member" />
		${member}
		<%
			IMemberService memberService = new MemberServiceImpl();
		try{
			memberService.registMember(member);
		%>
			<div class="alert alert-warning">
				<h4> 회원이 등록되었습니다. </h4>
				정상적으로 회원이 가입되었습니다.
			</div>	
		<%
		}catch(BizDuplicateKeyException ex){
		%>
			<div class="alert alert-warning">
				<h4> 아이디 중복입니다. </h4>
				이미 사용중인 아이디 입니다.
			</div>	
		<%
		}
		%>
	
</body>
</html>
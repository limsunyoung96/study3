<%@page import="com.study.exception.BizNotFoundException"%>
<%@page import="com.study.exception.BizNotEffectedException"%>
<%@page import="com.study.member.service.MemberServiceImpl"%>
<%@page import="com.study.member.service.IMemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("utf-8");%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>memberModify.jsp</title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<h3>회원 정보 수정</h3>
		
		<jsp:useBean id="member" class="com.study.member.vo.MemberVO" />
		<jsp:setProperty property="*" name="member" />
		<%
		IMemberService memberService = new MemberServiceImpl();
		try{ // 성공
			memberService.modifyMember(member);
		%>
			<div class="alert alert-warning">
				<h4>수정이 완료되었습니다.</h4>
				정상적으로 회원 정보를 수정했습니다.	
			</div>
		<%
		}catch(BizNotEffectedException ex){
		%>
			<div class="alert alert-warning">
				<h4>수정이 안되었습니다.</h4>
				아이디나 비밀번호를 확인해 주세요.	
			</div>
		<%
			
		}catch(BizNotFoundException ex){
		%>
			<div class="alert alert-warning">
				<h4>회원이 존재하지 않습니다.</h4>
				올바르게 접근해 주세요.	
			</div>
		<%	
		}
		%>
		<a href="memberList.jsp" class="btn btn-info">
			<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
			&nbsp;목록
		</a>

</body>
</html>
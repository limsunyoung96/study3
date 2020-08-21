<%@page import="com.study.free.vo.FreeBoardVO"%>
<%@page import="com.study.exception.BizPasswordNotMatchedException"%>
<%@page import="com.study.exception.BizNotFoundException"%>
<%@page import="com.study.exception.BizNotEffectedException"%>
<%@page import="com.study.free.service.FreeBoardServiceImpl"%>
<%@page import="com.study.free.service.IFreeBoardService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
 	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html >
<html lang="ko">
<html>
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>freeModify</title>
</head>
<body>
<%@ include file ="/WEB-INF/inc/top.jsp" %>
<div class = "container">
<h3>회원 정보 수정</h3>
		
		<jsp:useBean id="board" class="com.study.free.vo.FreeBoardVO" />
		<jsp:setProperty property="*" name="board" />
		<%
			IFreeBoardService boardService = new FreeBoardServiceImpl();
			try{ // 성공
				boardService.modifyBoard(board);
			
		%>
			<div class="alert alert-warning">
				<h4>수정이 완료되었습니다.</h4>
				정상적으로 글을 수정했습니다.	
			</div>
		<%
		}catch(BizNotEffectedException ex){
		%>
			<div class="alert alert-warning">
				<h4>수정이 안되었습니다.</h4>
				변경할 내용이 없습니다.	
			</div>
		<%
			
		}catch(BizNotFoundException ex){
		%>
			<div class="alert alert-warning">
				<h4>회원이 존재하지 않습니다.</h4>
				올바르게 접근해 주세요.	
			</div>
		<%	
		}catch(BizPasswordNotMatchedException ex){
		%>
			<div class="alert alert-warning">
				<h4>비밀번호가 틀립니다.</h4>
				비밀번호를 다시 확인해 주세요.	
			</div>
		<%
		}
		%>
		<a href="freeList.jsp" class="btn btn-info">
			<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
			&nbsp;목록
		</a>
</div> <!-- container -->
</body>
</html>
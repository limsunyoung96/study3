<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>freeDelete.jsp</title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<h3>회원 정보 수정</h3>

		<c:if test="${empty exNotFound && empty exPassword}">
			<div class="alert alert-warning">
				<h4>삭제가 완료되었습니다.</h4>
				정상적으로 글을 삭제했습니다.
			</div>
		</c:if>

		<c:if test="${not empty exNotFound}">
			<div class="alert alert-warning">
				<h4>글이 존재하지 않습니다.</h4>
				올바르게 접근해 주세요.
			</div>

		</c:if>
		<c:if test="${not empty exPassword}">
			<div class="alert alert-warning">
				<h4>비밀번호가 틀립니다.</h4>
				비밀번호를 다시 확인해 주세요.
			</div>
		</c:if>
		<a href="freeList.wow" class="btn btn-info"> <span
			class="glyphicon glyphicon-list" aria-hidden="true"></span> &nbsp;목록
		</a>
	</div>
	<!-- container -->
</body>
</html>
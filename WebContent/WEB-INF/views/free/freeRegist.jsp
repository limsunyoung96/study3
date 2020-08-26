<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>freeRegist.jsp</title>
</head>
<body>
	<div class="container">
		<h3>글 등록</h3>
		<%-- <c:if test="${empty ex}">
			<div class="alert alert-warning">
				<h4>글이 등록되었습니다.</h4>
				정상적으로 글이 등록되었습니다.
			</div>
		</c:if>

		<c:if test="${not empty ex}">
			<div class="alert alert-warning">
				<h4>글이 이미 존재합니다.</h4>
			</div>
		</c:if> --%>
		<a href="freeList.wow" class="btn btn-info btn-sm"> <span
			class="glyphicon glyphicon-list" aria-hidden="true"></span>
			&nbsp;&nbsp;목록
		</a>
</body>
</html>
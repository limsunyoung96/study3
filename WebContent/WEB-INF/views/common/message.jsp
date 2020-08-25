<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>Sunyoung's Site - ${messageVO.title}</title>
</head>
<body>
	<div class="container">
		<div class="row col-md-8 col-md-offset-2">
			<div class="page-header">
				<h3>${messageVO.title}</h3>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<p>${messageVO.message}</p>
				</div>
				<div class="panel-body">
					<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
						<spanclass="glyphicon glyphicon-home" aria-hidden="true"></span>
						&nbsp;Home
					</a>
					<div class="pull-right">
						<a href="#" onclick="history.back()" class="btn btn-default">
							<span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span>
							&nbsp;뒤로가기
						</a> &nbsp;&nbsp;
						<c:if test="${not empty messageVO.url}">
							<a href="<c:url value='${messageVO.url}'/>"class="btn btn-warning"> 
								<spanclass="glyphicon glyphicon-new-window aria-hidden="true"></span> 
								&nbsp;${messageVO.urlTitle}
							</a>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
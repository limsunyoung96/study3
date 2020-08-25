<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>freeView.jsp</title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<div class="page-header">
			<h3>
				자유게시판 - <small>상세보기</small>
			</h3>
		</div>

		<table class="table table-striped table-bordered">
			<tbody>
				<tr>
					<th>글번호</th>
					<td>${boardVo.boNo}</td>
				</tr>
				<tr>
					<th>글제목</th>
					<td>${boardVo.boTitle}</td>
				</tr>
				<tr>
					<th>글분류</th>
					<td>${boardVo.boCategoryNm}</td>
				</tr>
				<tr>
					<th>작성자명</th>
					<td>${boardVo.boWriter}</td>
				</tr>
				<!-- 비밀번호는 보여주지 않음  -->
				<tr>
					<th>내용</th>
					<td><pre>${boardVo.boContent}</pre></td>
				</tr>
				<tr>
					<th>등록자 IP</th>
					<td>${boardVo.boIp}</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${boardVo.boHit}</td>
				</tr>
				<tr>
					<th>등록일자</th>
					<td>${boardVo.boRegDate}</td>
				</tr>
				<tr>
					<th>삭제여부</th>
					<td>${boardVo.boDelYn}</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="pull-left">
							<a href="freeList.wow" class="btn btn-default btn-sm"> <span
								class="glyphicon glyphicon-list" aria-hidden="true"></span>
								&nbsp;&nbsp;목록
							</a>
						</div>
						<div class="pull-right">
							<a href="freeEdit.wow?boNo=${boardVo.boNo}"
								class="btn btn-success btn-sm"> <span
								class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
								&nbsp;&nbsp;수정
							</a>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- container -->
</body>
</html>







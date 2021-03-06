<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>memberView.jsp</title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<h3>회원 상세 정보</h3>
		<table class="table table-striped ">
			<tbody>
				<tr>
					<th>아이디</th>
					<td>${mem.memId}</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>${mem.memPass}</td>
				</tr>
				<tr>
					<th>회원명</th>
					<td>${mem.memName}</td>
				</tr>
				<tr>
					<th>우편번호</th>
					<td>${mem.memZip}</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>${mem.memAdd1}</td>
				</tr>
				<tr>
					<th>생일</th>
					<td>${mem.memBir}</td>
				</tr>
				<tr>
					<th>메일</th>
					<td>${mem.memMail}</td>
				</tr>
				<tr>
					<th>헨드폰</th>
					<td>${mem.memHp}</td>
				</tr>
				<tr>
					<th>직업</th>
					<td>${mem.memJobNm}  ${mem.memJob}</td>
				</tr>
				<tr>
					<th>취미</th>
					<td>${mem.memLikeNm}  ${mem.memLike}</td>
				</tr>
				<tr>
					<th>마일리지</th>
					<td>${mem.memMileage}</td>
				</tr>
				<tr>
					<th>탈퇴여부</th>
					<td>${mem.memDelete}</td>
				</tr>
				<tr>

					<td colspan="2"><a href="memberList.wow" class="btn btn-info">
							<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
							&nbsp;목록
					</a> 
					
					<mytag:sec hasRole="MANAGER">
							<a href="memberEdit.wow?memId=${mem.memId}" class="btn btn-info btn-sm"> 
								<span class="glyphicon glyphicon-knight" aria-hidden="true"></span>
								&nbsp;수정
							</a>
						</mytag:sec>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	
</body>
</html>
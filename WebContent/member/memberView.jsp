<%@page import="com.study.exception.BizNotFoundException"%>
<%@page import="com.study.member.vo.MemberVO"%>
<%@page import="com.study.member.service.MemberServiceImpl"%>
<%@page import="com.study.member.service.IMemberService"%>
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
<%
		IMemberService memberService = new MemberServiceImpl();
	try{
		String memId = request.getParameter("memId");
		MemberVO mem = memberService.getMember(memId);
		request.setAttribute("mem", mem);
		System.out.println(mem);
	}catch(BizNotFoundException ex){
		request.setAttribute("ex", ex);
	}
%>
		<h3>회원 상세 정보</h3>
		<c:if test="${not empty ex}">
			<div class = "alert alert-warning">
				해당 회원이 존재하지 않습니다.<br>
				<a href="memberList.jsp" class="btn btn-info">
					<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
					&nbsp;목록
				</a>
			</div>
			</c:if>
			<c:if test="${empty ex}">
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

					<td colspan="2"><a href="memberList.jsp" class="btn btn-info">
							<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
							&nbsp;목록
					</a> <!-- <button type="submit" class="btn btn-default">
							<span class="glyphicon glyphicon-circle-arrow-right"
								aria-hidden="true"></span> 회원가입
					</button> --> <!--   --> <a
						href="memberEdit.jsp?memId=${param.memId}" class="btn btn-info">
							<span class="glyphicon glyphicon-apple" aria-hidden="true"></span>
							수정
					</a></td>
				</tr>
			</tbody>
		</table>
				</c:if>
	</div>
	
</body>
</html>
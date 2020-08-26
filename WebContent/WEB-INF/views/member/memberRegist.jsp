<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>memberRegist.jsp</title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp"%>
	<div class="container">
		<h3>회원등록</h3>
		<%-- <jsp:useBean id="member" class="com.study.member.vo.MemberVO" />
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
		%> --%>
		
		<a href="memberList.wow" class="btn btn-info btn-sm"> <span
			class="glyphicon glyphicon-list" aria-hidden="true"></span>
			&nbsp;&nbsp;목록
		</a>
	
</body>
</html>
<%@page import="com.study.code.vo.CodeVO"%>
<%@page import="com.study.code.service.CommonCodeServiceImpl"%>
<%@page import="com.study.code.service.ICommonCodeService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<%@ include file="/WEB-INF/inc/header.jsp" %>
	<title>memberForm.jsp </title>
</head>
<body>
<%request.setCharacterEncoding("UTF-8"); %>
<%@ include file="/WEB-INF/inc/top.jsp" %>
 <div class="container">
 <%
 	ICommonCodeService codeService = new CommonCodeServiceImpl();
	 List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
	 List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
	 request.setAttribute("jobList", jobList);
	 request.setAttribute("hobbyList", hobbyList);
 %>
	<h3>회원가입</h3>		
	<form action="memberRegist.jsp" method="post" >
	<table class="table table-striped ">
		<tbody>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="memId" class="form-control input-sm" 
							required="required" pattern="\w{4,}" title="알파벳과 숫자로 4글자 이상 입력"></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="memPass" class="form-control input-sm"
					required="required" pattern="\w{4,}" title="알파벳과 숫자로 4글자 이상 입력"></td>
			</tr>
			<tr>
				<th>회원명</th>
				<td><input type="text" name="memName" class="form-control input-sm"
					required="required" pattern="[가-힣]{2,}" title="한글로 2글자 이상 입력"></td>
			</tr>
			<tr>
				<th>우편번호</th>
				<td><input type="text" name="memZip" class="form-control input-sm"></td>
			</tr>
			<tr>
				<th>주소</th>
				<td><input type="text" name="memAdd1" class="form-control input-sm">
					 <input type="text" name="memAdd2" class="form-control input-sm">
				</td>
			</tr>
			<tr>
				<th>생일</th>
				<td><input type="date" name="memBir" class="form-control input-sm"></td>
			</tr>
			<tr>
				<th>메일</th>
				<td><input type="email" name="memMail" class="form-control input-sm"></td>
			</tr>
			<tr>
				<th>핸드폰</th>
				<td><input type="tel" name="memHp" class="form-control input-sm"></td>
			</tr>
			<tr>
				<th>직업</th>
				<td>
					<select name="memJob" class="form-control input-sm" required="required">
						<option value="">-- 직업 선택 --</option>
						<!-- 
							for(int i=0; i<jobList.size(); i++){
								CodeVO code = jobList.get(i);
								out.print("<option value='"+code.getCommCd()+"'>"+code.getCommNm()+"</option>");
							}
						
						 -->
						<c:forEach items="${jobList}" var="code">
							<option value="${code.commCd}">${code.commNm}</option>
						</c:forEach>
						<!-- <option value="JB01">주부</option> -->
					</select>				
				</td>
			</tr>
			<tr>
				<th>취미</th>
				<td>
					<select name="memLike" class="form-control input-sm" required="required">
						<option value="">-- 취미 선택 --</option>
						<c:forEach items="${hobbyList}" var="code">
						<option value="${code.commCd}">${code.commNm}</option>
						</c:forEach>
						<!-- <option value="JB01">주부</option> -->
					</select>				
				</td>
			</tr>			
			<tr>
			
				<td colspan="2"><button type="submit" class="btn btn-default" >
				<span class="glyphicon glyphicon-circle-arrow-right" aria-hidden="true"></span>
				회원가입</button>
				<a href="#" class="btn btn-info">
				<span class="glyphicon glyphicon-apple" aria-hidden="true"></span>
				그냥 링크</a>
				</td>
			</tr>
		</tbody>	
	</table>
	</form>
</div>

</body>
</html>
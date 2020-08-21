<%@page import="com.study.common.util.CookieUtils"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.study.login.vo.UserVO"%>
<%@page import="com.study.common.util.UserList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html >
<html>
<head>
<meta charset=UTF-8">
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>14/ loginCheck.jsp</title>
</head>
<body>

	<%
		String id = request.getParameter("userId");
		String pw = request.getParameter("userPass");
		// 1. id, pw가 null인지 체크
		// <jsp:forward, msg 파라미터에 "ID 또는 비밀번호가 비었습니다." 
		if (id==null || id.isEmpty() || pw==null || pw.isEmpty()){
	%>
		<jsp:forward page="login.jsp">
			<jsp:param value="ID 또는 비밀번호가 비었습니다." name="msg"/>
		</jsp:forward>		
	<%
		}
		// 2. userList의 id를 통해 조회
		UserList users = new UserList();
		UserVO user = users.getUser(id);
		
		// 2.1 조회된 객체가 null
		// 현재 화면에 해당 회원은 존재하지 않습니다.
		if(user==null){
	%>
			<h3>해당 회원은 존재하지 않습니다.</h3>
			<a href="#" class="btn btn-default" onclick="history.go(-1)">뒤로가기</a>
	<%
		}else{
			if(user.getUserPass().equals(pw)){
				// 2.2 조회된 객체가 존재하고 pw가 맞으면
				// response.sendRedirect 사용해서 "/" 또는 "/index.jsp"로
				String [] check_values = request.getParameterValues("rememberMe");
				if(check_values!=null&&check_values[0].equals("Y")){ //values[0].equals("Y")
					/* Cookie cookie = new Cookie("SAVE_ID",URLEncoder.encode(request.getParameter("userId"),"utf-8")); */
					/* cookie.setMaxAge(60*60*24*31); */
					Cookie cookie = CookieUtils.createCookie("SAVE_ID",URLEncoder.encode(request.getParameter("userId"),"utf-8"),"/",60*60*24*31);	
					response.addCookie(cookie);
					
				}else{
					// 2.3 조회된 객체가 존재하고 pw가 틀리면
					// pageContext의 forward 사용하여 "비밀번호를 확인해 주세요"
					/* Cookie cookie = new Cookie("SAVE_ID","");
					cookie.setMaxAge(0); */
					response.addCookie(CookieUtils.createCookie("SAVE_ID","","/",0));
				}
				// 현재 사용자 정보(UserVO)를 세션에 저장
				System.out.println("세션에 정보 저장: "+ user);
				session.setAttribute("USER_INFO", user); //key: USER_INFO 
				
				response.sendRedirect(request.getContextPath() + "/");
			}else{
				pageContext.forward("login.jsp?msg=비밀번호를 확인해 주세요");
			}
		}
		
		
		
		
	%>


</body>
</html>
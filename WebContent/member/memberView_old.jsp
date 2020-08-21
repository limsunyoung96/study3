<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			//1. 드라이버 로딩
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		//2. 커넥션 구하기
		conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "java", "oracle");

		// 3. 구름 객체 생성
		stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

		// 4. 실행
		// String vs StringBuffer
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT mem_id    , mem_pass  , mem_name       ");
		sb.append("     , mem_bir   , mem_zip   , mem_add1       ");
		sb.append("     , mem_add2  , mem_hp    , mem_mail       ");
		sb.append("     , mem_job   , mem_like  , mem_mileage    ");
		sb.append("     , mem_delete                             ");
		sb.append("  FROM member                                 ");
		sb.append(" WHERE mem_id = '" + request.getParameter("memId") + "'");

		System.out.println(sb.toString());
		rs = stmt.executeQuery(sb.toString());
		%>

		<h3>회원가입</h3>
		<table class="table table-striped ">
			<tbody>
				<%
					if (rs.next()) {
				%>
				<tr>
					<th>아이디</th>
					<td><%=rs.getString("mem_id")%></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><%=rs.getString("mem_pass")%></td>
				</tr>
				<tr>
					<th>회원명</th>
					<td><%=rs.getString("mem_name")%></td>
				</tr>
				<tr>
					<th>우편번호</th>
					<td><%=rs.getString("mem_zip")%></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><%=rs.getString("mem_add1")%></td>
				</tr>
				<tr>
					<th>생일</th>
					<td><%=rs.getString("mem_bir")%></td>
				</tr>
				<tr>
					<th>헨드폰</th>
					<td><%=rs.getString("mem_hp")%></td>
				</tr>
				<tr>
					<th>직업</th>
					<td>
						<%
							String job = rs.getString("mem_job");
						%> <select name="memJob"
						class="form-control input-sm">
							<option value="">-- 직업 선택 --</option>
							<option value="JB01"
								<%="JB01".equals(job) ? "selected='selected'" : ""%>>주부</option>
							<option value="JB02"
								<%="JB02".equals(job) ? "selected='selected'" : ""%>>은행원</option>
							<option value="JB03"
								<%="JB03".equals(job) ? "selected='selected'" : ""%>>공무원</option>
							<option value="JB04"
								<%="JB04".equals(job) ? "selected='selected'" : ""%>>축산업</option>
							<option value="JB05"
								<%="JB05".equals(job) ? "selected='selected'" : ""%>>회사원</option>
							<option value="JB06"
								<%="JB06".equals(job) ? "selected='selected'" : ""%>>농업</option>
							<option value="JB07"
								<%="JB07".equals(job) ? "selected='selected'" : ""%>>자영업</option>
							<option value="JB08"
								<%="JB08".equals(job) ? "selected='selected'" : ""%>>학생</option>
							<option value="JB09"
								<%="JB09".equals(job) ? "selected='selected'" : ""%>>교사</option>
					</select>
					</td>
				</tr>
				<tr>
					<th>취미</th>
					<td>
						<%
							String hobby = rs.getString("mem_like");
						%> <select
						name="memLike" class="form-control input-sm">
							<option value="">-- 취미 선택 --</option>
							<option value="HB01"
								<%="HB01".equals(hobby) ? "selected='selected'" : ""%>>서예</option>
							<option value="HB02"
								<%="HB02".equals(hobby) ? "selected='selected'" : ""%>>장기</option>
							<option value="HB03"
								<%="HB03".equals(hobby) ? "selected='selected'" : ""%>>수영</option>
							<option value="HB04"
								<%="HB04".equals(hobby) ? "selected='selected'" : ""%>>독서</option>
							<option value="HB05"
								<%="HB05".equals(hobby) ? "selected='selected'" : ""%>>당구</option>
							<option value="HB06"
								<%="HB06".equals(hobby) ? "selected='selected'" : ""%>>바둑</option>
							<option value="HB07"
								<%="HB07".equals(hobby) ? "selected='selected'" : ""%>>볼링</option>
							<option value="HB08"
								<%="HB08".equals(hobby) ? "selected='selected'" : ""%>>스키</option>
							<option value="HB09"
								<%="HB09".equals(hobby) ? "selected='selected'" : ""%>>만화</option>
							<option value="HB10"
								<%="HB10".equals(hobby) ? "selected='selected'" : ""%>>낚시</option>
							<option value="HB11"
								<%="HB11".equals(hobby) ? "selected='selected'" : ""%>>영화감상</option>
							<option value="HB12"
								<%="HB12".equals(hobby) ? "selected='selected'" : ""%>>등산</option>
							<option value="HB13"
								<%="HB13".equals(hobby) ? "selected='selected'" : ""%>>개그</option>
							<option value="HB14"
								<%="HB14".equals(hobby) ? "selected='selected'" : ""%>>카레이싱</option>
					</select>
					</td>
				</tr>
				<tr>
					<th>마일리지</th>
					<td><%=rs.getString("mem_mileage")%></td>
				</tr>
				<tr>
					<th>탈퇴여부</th>
					<td>Y<input type="radio" name="memDelete" value="Y" <%="Y".equals(rs.getString("mem_delete")) ? "checked='checked'" : ""%>>
						N<input type="radio" name="memDelete" value="N" <%=!"Y".equals(rs.getString("mem_delete")) ? "checked='checked'" : ""%>> </td>
				</tr>
				<tr>

					<td colspan="2"><button type="submit" class="btn btn-default">
							<span class="glyphicon glyphicon-circle-arrow-right"
								aria-hidden="true"></span> 회원가입
						</button> <a href="#" class="btn btn-info"> <span
							class="glyphicon glyphicon-apple" aria-hidden="true"></span> 그냥
							링크
					</a></td>
				</tr>
				<%}%>
			</tbody>
		</table>
	</div>
	<%
		// 자원 종료
	if (rs != null)
		try {
			rs.close();
		} catch (SQLException e) {
		}
	if (stmt != null)
		try {
			stmt.close();
		} catch (SQLException e) {
		}
	if (conn != null)
		try {
			conn.close();
		} catch (SQLException e) {
		}
	%>
</body>
</html>
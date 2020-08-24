<%@page import="com.study.code.vo.CodeVO"%>
<%@page import="com.study.code.service.CommonCodeServiceImpl"%>
<%@page import="com.study.code.service.ICommonCodeService"%>
<%@page import="com.study.member.vo.MemberVO"%>
<%@page import="com.study.member.service.MemberServiceImpl"%>
<%@page import="com.study.member.service.IMemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	request.setCharacterEncoding("utf-8");
%>   
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/header.jsp"%>
<title>memberList.jsp</title>
</head>
<body>
	<%@ include file="/WEB-INF/inc/top.jsp"%>
	<jsp:useBean id="searchVO" class="com.study.member.vo.MemberSearchVO" />
	<jsp:setProperty property="*" name="searchVO" />
	<%
		IMemberService memberService = new MemberServiceImpl();
		List<MemberVO> members = memberService.getMemberList(searchVO);
		request.setAttribute("members", members); /* members란 속성에 members를 대입시키는 것*/
		
		ICommonCodeService codeService = new CommonCodeServiceImpl();
		List<CodeVO> jobCateList = codeService.getCodeListByParent("JB00");
		request.setAttribute("jobCateList", jobCateList);
		
		List<CodeVO> hobbyCateList = codeService.getCodeListByParent("HB00");
		request.setAttribute("hobbyCateList", hobbyCateList);
	%>
	<%-- <c:set var="members" value = "<%=members %>" scope = "request"> --%>
	<div class="container">
		<h3>회원목록 - <small>목록조회</small></h3>
		<%-- ${ searchVO} --%>
		
		<!-- START : 검색 폼  -->
		<div class="collapse.in panel panel-default" id="id_search_area">
			<div class="panel-body">
				<form name="frm_search" action="memberList.jsp" method="post"
					class="form-horizontal">
					<input type="hidden" name="curPage" value="${searchVO.curPage}">
					<input type="hidden" name="rowSizePerPage"
						value="${searchVO.rowSizePerPage}">
					<div class="form-group">
						<label for="id_searchType" class="col-sm-2 control-label">검색</label>
						<div class="col-sm-2">
							<select id="id_searchType" name="searchType"
								class="form-control input-sm">
								<option value="IM"
									${searchVO.searchType eq "IM" ? "selected='selected'": "" }>회원명</option>
								<option value="ID"
									${searchVO.searchType eq "ID" ? "selected='selected'": "" }>아이디</option>
								<option value="HP"
									${searchVO.searchType eq "HP" ? "selected='selected'": "" }>핸드폰</option>
								<option value="ADDR"
									${searchVO.searchType eq "ADDR" ? "selected='selected'": "" }>주소</option>
							</select>
						</div>
						<div class="col-sm-2">
							<input type="text" name="searchWord"
								class="form-control input-sm" value="${searchVO.searchWord}"
								placeholder="검색어">
						</div>
						<label for="id_searchJob"
							class="col-sm-2 col-sm-offset-2 control-label">직업분류</label>
						<div class="col-sm-2">
							<select id="id_searchJob" name="searchJob"
								class="form-control input-sm">
								<option value="">-- 전체 --</option>
								<c:forEach items="${jobCateList}" var="code">
									<option value="${code.commCd}"
										${code.commCd eq searchVO.searchJob ? "selected='selected'": "" }>${code.commNm}</option>
									<!-- 이 위에 바꾸기 -->
								</c:forEach>
							</select>
							</div>
						<label for="id_searchJob"
							class="col-sm-2 col-sm-offset-2 control-label">취미분류</label>
						<div class="col-sm-2">
							<select id="id_searchLike" name="searchLike"
								class="form-control input-sm">
								<option value="">-- 전체 --</option>
								<c:forEach items="${hobbyCateList}" var="code">
									<option value="${code.commCd}"
										${code.commCd eq searchVO.searchLike ? "selected='selected'": "" }>${code.commNm}</option>
									<!-- 이 위에 바꾸기 -->
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-2 col-sm-offset-9 text-right">
							<button type="button" id="id_btn_reset"
								class="btn btn-sm btn-default">
								<i class="fa fa-sync"></i> &nbsp;&nbsp;초기화
							</button>
						</div>
						<div class="col-sm-1 text-right">
							<button type="submit" class="btn btn-sm btn-primary ">
								<i class="fa fa-search"></i> &nbsp;&nbsp;검 색
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- END : 검색 폼  -->
		
		<!-- START : 목록건수 및 새글쓰기 버튼  -->
		<div class="row" style="margin-bottom: 10px;">
			<div class="col-sm-3 form-inline">
				전체 ${searchVO.totalRowCount} 건 조회 <select id="id_rowSizePerPage"
					name="rowSizePerPage" class="form-control input-sm">
					<option value="10" ${searchVO.rowSizePerPage eq "10" ? "selected='selected'": "" }>10</option>
					<option value="20" ${searchVO.rowSizePerPage eq "20" ? "selected='selected'": "" }>20</option>
					<option value="30" ${searchVO.rowSizePerPage eq "30" ? "selected='selected'": "" }>30</option>
					<option value="50" ${searchVO.rowSizePerPage eq "50" ? "selected='selected'": "" }>50</option>
				</select>
			</div>
			
			<div class="col-sm-2 col-sm-offset-6 text-right">
				<a href="#" class="btn btn-info btn-sm" id="id_search_show" data-toggle="collapse" data-target="#id_search_area" > 
					<i class="fas fa-chevron-up"></i>
					<span>&nbsp;검색닫기</span>
				</a>
			</div>
			
			<div>
			<a href="memberForm.jsp" class="btn btn-primary btn-sm pull-right">회원등록</a>
			</div>
		</div>
		<!-- END : 목록건수 및 회원등록 버튼  -->
		
		<table class="table table-striped table-bordered">
			<caption class="hidden">회원목록 조회</caption>
			<colgroup>
				<col style="width: 15%;" />
				<col />
				<col style="width: 15%;" />
				<col style="width: 15%;" />
				<col style="width: 15%;" />
				<col style="width: 15%;" />
			</colgroup>
			<thead>
				<tr>
					<th>ID</th>
					<th>회원명</th>
					<th>HP</th>
					<th>생일</th>
					<th>직업</th>
					<th>마일리지</th>
				</tr>
			</thead>
			<tbody>
			<!-- 아래의 스크립트 코드를 EL/JSTL 사용해서 변경 -->
			<c:forEach var="members" items="${members}">
				<tr>
					<td>${members.memId }</td>
					<td><a href="memberView.jsp?memId=${members.memId}">${members.memName}</a></td>
					<td>${members.memHp}</td>
					<td>${members.memBir}</td>
					<td>${members.memJobNm}</td>
					<td>${members.memMileage}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		
		
		<!-- START : 페이지네이션  -->
		<nav class="text-center">
			<ul class="pagination">
				<!-- 이전 페이지 -->
				<c:if test="${searchVO.firstPage > 1 }">
					<li><a href="memberList.jsp?curPage=${searchVO.firstPage-1}"
						data-page="${searchVO.firstPage-1}"><span aria-hidden="true">&laquo;</span></a></li>
				</c:if>
				<!-- 페이지 넘버링  -->
				<c:forEach var="i" begin="${searchVO.firstPage}"
					end="${searchVO.lastPage}">
					<c:if test="${searchVO.curPage == i}">
						<li class="active"><a href="#">${i}</a></li>
					</c:if>
					<c:if test="${searchVO.curPage != i}">
						<li><a href="memberList.jsp?curPage=${i}" data-page="${i}">${i}</a></li>
					</c:if>
				</c:forEach>

				<!-- 다음  페이지  -->
				<c:if test="${searchVO.lastPage < searchVO.totalPageCount}">
					<li><a href="memberList.jsp?curPage=${searchVO.lastPage+1}"
						data-page="${searchVO.lastPage+1}"><span aria-hidden="true">&raquo;</span></a></li>
				</c:if>
			</ul>
		</nav>
		<!-- END : 페이지네이션  -->
		
	</div>
	<script type="text/javascript">
		// 변수 정의
		var f = document.forms['frm_search'];

		// 함수 정의

		// 각 이벤트 등록

		// 페이지 링크 클릭
		$('ul.pagination > li > a[data-page]').click(function(e) {
			e.preventDefault(); // 이벤트 전파 방지. a의 href로 이동 금지
			// data-page 에 있는 값을 f.curPage.value에 설정, 서브밋
			f.curPage.value = $(this).data('page');
			f.submit();
			// alert(this.innerHTML);
		});

		// 검색버튼 클릭
		$(f).submit(function(e) {
			e.preventDefault();
			f.curPage.value = 1;
			f.submit();
		});
		
		// 목록갯수 변경
		// id_rowSizePerPage 변경되었을 때
		$('#id_rowSizePerPage').change(function(e){
		// 페이지 1, 목록수 = 현재 값으로 변경후 서브밋
			/* alert($(this).val()); */
			f.curPage.value = 1;
			f.rowSizePerPage.value = this.value;
			f.submit();
		}); // #id_rowSizePerPage . change
		
		
		$('#id_btn_reset').click(function(){
			f.curPage.value = 1;
			f.searchWord.value = "";
			f.searchType.options[0].selected = true;
			f.searchJob.options[0].selected = true;
			f.searchLike.options[0].selected = true;
		});// #id_btn_reset . click
		
			
		$('#id_search_area').on('hidden.bs.collapse', function() {
			$('#id_search_show i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
			$('#id_search_show span').html("&nbsp; 검색열기")
		})
		$('#id_search_area').on('shown.bs.collapse', function() {
			$('#id_search_show i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
			$('#id_search_show span').html("&nbsp; 검색닫기")
		})
		
		
	</script>
</body>
</html>
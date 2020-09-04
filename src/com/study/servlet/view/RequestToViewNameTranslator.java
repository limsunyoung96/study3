package com.study.servlet.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestToViewNameTranslator extends View {
	public RequestToViewNameTranslator(StudyViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	public static void main(String[] args) {
		String uri = "/study31/free/freeList;JSESSIONID=MILKIS1004";
		String uriViewName = uri;
		String ct = "/study31";
		// 단계적으로 컨텍스트 경로, 확장자, 세미콜론이 있다면 제거
		// "free/freeList.wow;JSESSIONID=MILKIS1004";
		if(ct.length() > 0) {
			uriViewName = uriViewName.substring(ct.length() + 1);
		}
		System.out.println(uriViewName);
		System.out.println(uriViewName.indexOf("."));
		if(uriViewName.indexOf(".") > 0) {
			// 마지막에 있는 "/" 다음의 "." 입니다.
			uriViewName = uriViewName.substring(0, uriViewName.indexOf("."));
		}
		System.out.println(uriViewName);
		// "free/freeList"
		if(uriViewName.indexOf(";") > 0) {
			// 마지막에 있는 "/" 다음의 ";" 입니다.
			uriViewName = uriViewName.substring(0, uriViewName.indexOf(";"));
		}
		System.out.println(uriViewName);
		
	}
	
	@Override
	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 뷰이름이 지정되지 않은 경우 현재 요청 URI 에서 뷰이름을 생성한다.
		String uri = req.getRequestURI();
		String uriViewName = uri;
		String ct = req.getContextPath();
		// 과제 : 아래의 조건을 만족하도록 변수 uriViewName 을 변경하시오.
		// uriViewName에서 컨텍스트 경로, 확장자, 세미콜론이 있다면 제거
		// 예 : "/study31/free/freeList.wow;JSESSIONID=MILKIS1004" -> "free/freeList"
		if(ct.length() > 0) {
			uriViewName = uriViewName.substring(ct.length() + 1);
		}
		if(uriViewName.indexOf(".") > 0) {
			uriViewName = uriViewName.substring(0, uriViewName.indexOf("."));
		}
		if(uriViewName.indexOf(";") > 0) {
			uriViewName = uriViewName.substring(0, uriViewName.indexOf(";"));
		}
		
		String jspPath = viewResolver.getPrefix() + uriViewName + viewResolver.getSuffix();
		logger.debug("URI=" + uri + ", RequestToViewNameTranslator=" + jspPath);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(jspPath);
		dispatcher.forward(req, resp);
	}
}

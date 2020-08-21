package com.study.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.exception.BizNotFoundException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

public class SimpleController extends HttpServlet {
	public static void main(String[] args) {
		String cp = "/study3";
		String t = "/study3/free/freeList.wow";
		String u = t.substring(cp.length());
		System.out.println(u);
	}

	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	// 1. 클라이언트의 요청을 받는다
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공통기능 실행
		req.setCharacterEncoding("utf-8");

		// 2. 요청 분석 (uri)
		String uri = req.getRequestURI(); // /study3/free/freeList.wow
		uri = uri.substring(req.getContextPath().length()); // /free/freeList.wow
		String viewPage = "";

		System.out.println("uri: " + uri);

		// 3. 모델을 사용하여 처리

		// 4. 결과를 속성에 저장
		if ("/free/freeList.wow".equals(uri)) {
			FreeBoardSearchVO searchVO = new FreeBoardSearchVO();
			List<FreeBoardVO> boards = freeBoardService.getBoardList(searchVO);
			req.setAttribute("boards", boards);

			List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
			req.setAttribute("cateList", cateList);
			viewPage = "/free/freeList.jsp";

		} else if ("/free/freeView.wow".equals(uri)) {
			try {
				int boNo = Integer.parseInt(req.getParameter("boNo"));

				FreeBoardVO free = freeBoardService.getBoard(boNo);
				if (free != null) {
					freeBoardService.increaseHit(boNo);
				}
				req.setAttribute("boardVo", free);
			} catch (BizNotFoundException ex) {
				req.setAttribute("ex", ex);
			}
			viewPage = "/free/freeView.jsp";

		} else if ("/free/freeForm.wow".equals(uri)) {
			List<CodeVO> zzz = codeService.getCodeListByParent("BC00");
			req.setAttribute("categoryList", zzz);
			viewPage = "/free/freeForm.jsp";
		}
		System.out.println("viewPage: " + viewPage);
		// 5.뷰 페이지로 이동
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, resp);
	}
}

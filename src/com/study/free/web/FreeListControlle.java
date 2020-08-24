package com.study.free.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeListControlle implements IController {
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		FreeBoardSearchVO searchVO = new FreeBoardSearchVO();
		List<FreeBoardVO> boards = freeBoardService.getBoardList(searchVO);
		req.setAttribute("boards", boards);

		List<CodeVO> cateList = codeService.getCodeListByParent("BC00");
		req.setAttribute("cateList", cateList);
		return "/WEB-INF/views/free/freeList.jsp";
	}

}
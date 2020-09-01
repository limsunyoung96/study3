package com.study.free.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.code.vo.CodeVO;
import com.study.exception.BizNotFoundException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeEditController implements IController {
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try{
			String boNo = req.getParameter("boNo");
			FreeBoardVO boardVO = freeBoardService.getBoard(Integer.parseInt(boNo));
			req.setAttribute("boardVO", boardVO);
			
			List<CodeVO> categorylist = codeService.getCodeListByParent("BC00");
			req.setAttribute("categorylist", categorylist);
		}catch(BizNotFoundException ex){
			req.setAttribute("ex", ex);
		}
		return "free/freeEdit";
	}

}

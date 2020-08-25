package com.study.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeViewController implements IController {
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		try {
			int boNo = Integer.parseInt(req.getParameter("boNo"));

			FreeBoardVO free = freeBoardService.getBoard(boNo);
			if (free != null) {
				freeBoardService.increaseHit(boNo);
			}
			req.setAttribute("boardVo", free);
			return "/WEB-INF/views/free/freeView.jsp";

		} catch (BizNotFoundException ex) {
			ex.printStackTrace();
			ResultMessageVO messageVO = new ResultMessageVO();
			messageVO.setResult(false).setTitle("조회 실패").setMessage("해달 글이 존재하지 않습니다.").setUrl("/free/freeList.wow")
					.setUrlTitle("목록으로");
			req.setAttribute("messgaveVO", messageVO);
			return "/WEB-INF/views/common/message.jsp";
		}
		
	}

}

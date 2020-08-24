package com.study.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.exception.DaoDuplicateKeyException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeRegistController implements IController {
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		<jsp:useBean id="board" class="com.study.free.vo.FreeBoardVO" />
//		<jsp:setProperty property="*" name="board" />

		FreeBoardVO board = new FreeBoardVO();
		BeanUtils.populate(board, req.getParameterMap());
//		board.setBoTitle(req.getParameter("boTitle"));
		try {
			board.setBoIp(req.getRemoteAddr());
			freeBoardService.registBoard(board);
		} catch (DaoDuplicateKeyException e) {
			e.printStackTrace();
			req.setAttribute("ex", e);
		}
		return "/WEB-INF/views/free/freeRegist.jsp";
	}

}

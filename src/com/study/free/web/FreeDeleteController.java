package com.study.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeDeleteController implements IController {
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		<jsp:useBean id="board" class="com.study.free.vo.FreeBoardVO" />
//		<jsp:setProperty property="*" name="board" />
		FreeBoardVO board = new FreeBoardVO();
		BeanUtils.populate(board, req.getParameterMap());
		try { // 성공
			board.setBoIp(req.getRemoteAddr());
			freeBoardService.removeBoard(board);

		} catch (BizNotFoundException exNotFound) {
			exNotFound.printStackTrace();
			req.setAttribute("exNotFound", exNotFound);
		} catch (BizPasswordNotMatchedException exPassword) {
			exPassword.printStackTrace();
			req.setAttribute("exPassword", exPassword);
		}
		return "/WEB-INF/views/free/freeDelete.jsp";
	}

}

package com.study.free.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.service.FreeBoardServiceImpl;
import com.study.free.service.IFreeBoardService;
import com.study.free.vo.FreeBoardVO;
import com.study.servlet.IController;

public class FreeModifyController implements IController {
	IFreeBoardService freeBoardService = new FreeBoardServiceImpl();
	ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		<jsp:useBean id="board" class="com.study.free.vo.FreeBoardVO" />
//		<jsp:setProperty property="*" name="board" />

		FreeBoardVO board = new FreeBoardVO();
		BeanUtils.populate(board, req.getParameterMap());
		ResultMessageVO messageVO = new ResultMessageVO();
		
		try { // 성공
			board.setBoIp(req.getRemoteAddr());
			freeBoardService.modifyBoard(board);
			return "redirect:/free/freeView.wow?boNo="+req.getParameter("boNo");
			
		} catch (BizNotFoundException exNotFound) {
			exNotFound.printStackTrace();
			messageVO.setResult(false).setTitle("글 수정 실패")
					  .setMessage("해당 회원이 존재하지 않습니다.")
					  .setUrl("/free/freeList.wow")
					  .setUrlTitle("목록으로");
		} catch (BizPasswordNotMatchedException exPassword) {
			exPassword.printStackTrace();
			messageVO.setResult(false).setTitle("글 수정 실패")
			  .setMessage("비밀번호가 일치하지 않습니다.")
			  .setUrl("/free/freeList.wow")
			  .setUrlTitle("목록으로");
		}
		req.setAttribute("messageVO", messageVO);
		
		return "common/message";
	}

}

package com.study.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.IController;

public class MemberDeleteController implements IController {

	private IMemberService memberService = new MemberServiceImpl();
	private ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		MemberVO member = new MemberVO();
		BeanUtils.populate(member, req.getParameterMap());
		ResultMessageVO messageVO = new ResultMessageVO();
		try { // 성공
			memberService.removeMember(member);
			return "redirect:/member/memberList.wow";
				
		} catch (BizNotFoundException exNotFound) {
			exNotFound.printStackTrace();
			messageVO.setResult(false).setTitle("회원 삭제 실패")
			  .setMessage("회원이 존재하지 않습니다.")
			  .setUrl("/member/memberList.wow")
			  .setUrlTitle("목록으로");
			
		} 
		catch (BizPasswordNotMatchedException exPassword) {
			exPassword.printStackTrace();
			messageVO.setResult(false).setTitle("회원 삭제 실패")
			  .setMessage("비밀번호가 일치하지 않습니다.")
			  .setUrl("/member/memberList.wow")
			  .setUrlTitle("목록으로");
		}
		req.setAttribute("messageVO", messageVO);
		return "common/message";
	}

}

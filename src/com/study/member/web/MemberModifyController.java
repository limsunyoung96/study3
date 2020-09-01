package com.study.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.IController;

public class MemberModifyController implements IController {

	private IMemberService memberService = new MemberServiceImpl();
	private ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// <jsp:useBean id="member" class="com.study.member.vo.MemberVO" />
		// <jsp:setProperty property="*" name="member" />

		MemberVO member = new MemberVO();
		BeanUtils.populate(member, req.getParameterMap());
		ResultMessageVO messageVO = new ResultMessageVO();

		try { // 성공
//			member.set

			memberService.modifyMember(member);
			return "redirect:/member/memberView.wow?memId=" + req.getParameter("memId");

			/*
			 * <div class="alert alert-warning"> <h4>수정이 완료되었습니다.</h4> 정상적으로 회원 정보를 수정했습니다.
			 * </div>
			 */
		} catch (BizNotFoundException ex) {
			ex.printStackTrace();
			messageVO.setResult(false)
					  .setTitle("회원 수정 실패")
					  .setMessage("회원이 존재하지 않습니다. 올바르게 접근해 주세요.")
					  .setUrl("/member/memberList.wow")
					  .setUrlTitle("목록으로");
		} catch (BizPasswordNotMatchedException ex) {
			ex.printStackTrace();
			messageVO.setResult(false).setTitle("회원 수정 실패")
			  .setMessage("비밀번호가 일치하지 않습니다.")
			  .setUrl("/member/memberList.wow")
			  .setUrlTitle("목록으로");
		} catch (BizNotEffectedException ex) {
			ex.printStackTrace();
			messageVO.setResult(false)
					  .setTitle("회원 수정 실패")
					  .setMessage("아이디를 확인해주세요.")
					  .setUrl("/member/memberList.wow")
					  .setUrlTitle("목록으로");
		} 
		req.setAttribute("messageVO", messageVO);

		return "common/message";
	}

}

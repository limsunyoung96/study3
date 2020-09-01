package com.study.member.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.study.code.service.CommonCodeServiceImpl;
import com.study.code.service.ICommonCodeService;
import com.study.common.vo.ResultMessageVO;
import com.study.exception.BizDuplicateKeyException;
import com.study.member.service.IMemberService;
import com.study.member.service.MemberServiceImpl;
import com.study.member.vo.MemberVO;
import com.study.servlet.IController;

public class MemberRegistController implements IController {

	private IMemberService memberService = new MemberServiceImpl();
	private ICommonCodeService codeService = new CommonCodeServiceImpl();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// <jsp:useBean id="member" class="com.study.member.vo.MemberVO" />
		// <jsp:setProperty property="*" name="member" />

		MemberVO member = new MemberVO();
		BeanUtils.populate(member, req.getParameterMap());
		ResultMessageVO messageVO = new ResultMessageVO();

		try {

			memberService.registMember(member);
			return "redirect:/member/memberList.wow";

//			
//			<div class="alert alert-warning">
//				<h4> 회원이 등록되었습니다. </h4>
//				정상적으로 회원이 가입되었습니다.
//			</div>	
		} catch (BizDuplicateKeyException ex) {
			ex.printStackTrace();
			messageVO.setResult(false)
					  .setTitle("회원 등록 실패")
					  .setMessage("이미 사용중인 아이디입니다.")
					  .setUrl("/member/memberList.wow")
					  .setUrlTitle("목록으로");
		}
		// 속성에 messageVO 로 저장
		req.setAttribute("messageVO", messageVO);

		return "common/message";
	}

}

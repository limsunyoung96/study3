package com.study.member.service;

import java.util.List;

import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

public interface IMemberService {
	
	public void registMember(MemberVO member) throws BizDuplicateKeyException ;
	public void modifyMember(MemberVO member) throws BizNotEffectedException, BizPasswordNotMatchedException, BizNotFoundException ;
	public void removeMember(MemberVO member) throws BizNotEffectedException, BizPasswordNotMatchedException, BizNotFoundException ;
	
	public MemberVO getMember(String memId) throws BizNotFoundException  ;
	public List<MemberVO> getMemberList(MemberSearchVO searchVO);
	
}

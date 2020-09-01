package com.study.member.dao;

import java.util.List;

import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

public interface IMemberDao {

	/**
	 * <b>목록건수 리턴</b>
	 * @param searchVO
	 * @return int
	 */
	public int getBoardCount(MemberSearchVO searchVO);
	
	public int insertMember(MemberVO member);
	public int updateMember(MemberVO member);
	public int deleteMember(MemberVO member);
	public MemberVO getMember(String memId);
	public List<MemberVO> getMemberList(MemberSearchVO searchVO);
	MemberVO getMemberList(String memId);
}

package com.study.member.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.study.common.util.MybatisSqlSessionFactory;
import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.member.dao.IMemberDao;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {

	SqlSessionFactory Factory = MybatisSqlSessionFactory.getSqlSessionFactory();

	@Override
	public void registMember(MemberVO member) throws BizDuplicateKeyException {
		try (SqlSession sqlSession = Factory.openSession()) {
			IMemberDao memberDao = sqlSession.getMapper(IMemberDao.class);
			memberDao.insertMember(member);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifyMember(MemberVO member)
			throws BizNotEffectedException, BizNotFoundException, BizPasswordNotMatchedException {
		try (SqlSession sqlSession = Factory.openSession()) {
			IMemberDao memberDao = sqlSession.getMapper(IMemberDao.class);
			MemberVO vo = memberDao.getMember(member.getMemId());
			if (vo == null) {
				throw new BizNotFoundException("[" + member.getMemId() + "] 조회 실패");
			}
			int cnt = memberDao.updateMember(member);
			if (cnt < 1) {
				throw new BizNotEffectedException("[" + member.getMemId() + "] 수정 실패");
			}
			if (!(vo.getMemPass().equals(member.getMemPass()))) {
				throw new BizPasswordNotMatchedException();
			}
			sqlSession.commit();
		}
	}

	@Override
	public void removeMember(MemberVO member)
			throws BizNotEffectedException, BizNotFoundException, BizPasswordNotMatchedException {
		try (SqlSession sqlSession = Factory.openSession()) {
			IMemberDao memberDao = sqlSession.getMapper(IMemberDao.class);
			MemberVO vo = memberDao.getMember(member.getMemId());
			if (vo == null) {
				throw new BizNotFoundException();
			}
			if (!(vo.getMemPass().equals(member.getMemPass()))) {
				throw new BizPasswordNotMatchedException();
			}
			int cnt = memberDao.deleteMember(member);
			if (cnt < 1) {
				throw new BizNotEffectedException();
			}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
			sqlSession.commit();
		}
	}

	@Override
	public MemberVO getMember(String memId) throws BizNotFoundException {
		try (SqlSession sqlSession = Factory.openSession()) {
			IMemberDao memberDao = sqlSession.getMapper(IMemberDao.class);
			MemberVO vo = memberDao.getMember(memId);
			if (vo == null) {
				throw new BizNotFoundException("[" + memId + "] 조회 실패");
			}
//			System.out.println(vo);
			return vo;
		}
	}

	@Override
	public List<MemberVO> getMemberList(MemberSearchVO searchVO) {
		try (SqlSession sqlSession = Factory.openSession()) {
			IMemberDao memberDao = sqlSession.getMapper(IMemberDao.class);
			// 건수를 구해서 searchVO 설정 -> searchVO.pageSetting() -> list 호출
			int cnt = memberDao.getBoardCount(searchVO);
			searchVO.setTotalRowCount(cnt);
			searchVO.pageSetting();
			List<MemberVO> list = memberDao.getMemberList(searchVO);
			return list;
		}
	}

}

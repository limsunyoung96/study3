package com.study.member.service;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.study.exception.BizDuplicateKeyException;
import com.study.exception.BizException;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.DaoDuplicateKeyException;
import com.study.exception.DaoException;
import com.study.member.dao.IMemberDao;
import com.study.member.dao.MemberDaoOracle;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

public class MemberServiceImpl implements IMemberService {

	private IMemberDao memberDao = new MemberDaoOracle();

	@Override
	public void registMember(MemberVO member) throws BizDuplicateKeyException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
//			MemberVO vo = memberDao.getMember(conn, member.getMemId());
//			if (vo != null) {
//				throw new BizDuplicateKeyException("[" + member.getMemId() + "] 이미 존재합니다.");
//			}
			try {
				memberDao.insertMember(conn, member);
			}catch(DaoDuplicateKeyException e) {
				throw new BizDuplicateKeyException(e.getMessage(), e);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			// 자원 종료
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

	}

	@Override
	public void modifyMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			MemberVO vo = memberDao.getMember(conn, member.getMemId());
			if (vo == null) {
				throw new BizNotFoundException("[" + member.getMemId() + "] 조회 실패");
			}
			int cnt = memberDao.updateMember(conn, member);
			if(cnt < 1) {
				throw new BizNotEffectedException("[" + member.getMemId() + "] 수정 실패");
			}
		} catch (SQLException e) {
			throw new DaoException("조회시", e);
		} finally {
			// 자원 종료
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

	}

	@Override
	public void removeMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public MemberVO getMember(String memId) throws BizNotFoundException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			MemberVO vo = memberDao.getMember(conn, memId);
			if (vo == null) {
				throw new BizNotFoundException("[" + memId + "] 조회 실패");
			}
//			System.out.println(vo);
			return vo;
		} catch (SQLException e) {
			throw new DaoException("조회시", e);
		} finally {
			// 자원 종료
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
	}

	@Override
	public List<MemberVO> getMemberList(MemberSearchVO searchVO) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			// 건수를 구해서 searchVO 설정 -> searchVO.pageSetting() -> list 호출 
			int cnt = memberDao.getBoardCount(conn, searchVO);
			searchVO.setTotalRowCount(cnt);
			searchVO.pageSetting();
			List<MemberVO> list = memberDao.getMemberList(conn, searchVO);
			return list;
		} catch (SQLException e) {
			throw new DaoException("조회시", e);
		} finally {
			// 자원 종료
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}
	}

}

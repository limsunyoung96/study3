package com.study.login.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.exception.DaoException;
import com.study.free.vo.FreeBoardVO;
import com.study.login.vo.UserVO;
import com.study.member.dao.IMemberDao;
import com.study.member.dao.MemberDaoOracle;
import com.study.member.vo.MemberVO;

public class LoginServiceImpl implements ILoginService {

	private IMemberDao memberDao = new MemberDaoOracle();

	@Override
	public UserVO loginCheck(UserVO user) throws BizNotFoundException, BizPasswordNotMatchedException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			MemberVO vo = memberDao.getMember(conn, user.getUserId());
			if (vo == null) {
				throw new BizNotFoundException(user.getUserId() + "회원이 존재하지 않습니다.");
			}
			if (!vo.getMemPass().equals(user.getUserPass())) {
				throw new BizPasswordNotMatchedException();
			}
			// 성공
			UserVO userVO = new UserVO();
			userVO.setUserId(vo.getMemId());
			userVO.setUserPass(vo.getMemPass());
			userVO.setUserName(vo.getMemName());
			userVO.setUserRole("MEMBER"); // 현재 권한 테이블이 없어서 그냥 MEMBER로 설정
			return userVO;
			
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
	public void logout(UserVO user) {
		//TODO 로그인 이력테이블이 완성되면 처리
	}

}

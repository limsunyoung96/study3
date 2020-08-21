package com.study.free.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.exception.DaoException;
import com.study.free.dao.FreeBoardDaoOracle;
import com.study.free.dao.IFreeBoardDao;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

public class FreeBoardServiceImpl implements IFreeBoardService  {
	
	private IFreeBoardDao freeBoardDao = new FreeBoardDaoOracle();
	
	@Override
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			// 건수를 구해서 searchVO 설정 -> searchVO.pageSetting() -> list 호출 
			int cnt = freeBoardDao.getBoardCount(conn, searchVO);
			searchVO.setTotalRowCount(cnt);
			searchVO.pageSetting();			
			List<FreeBoardVO> list = freeBoardDao.getBoardList(conn, searchVO);
			return list;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if (conn != null)try {conn.close();	} catch (SQLException e) {}
		}
	}

	@Override
	public FreeBoardVO getBoard(int boNo) throws BizNotFoundException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			FreeBoardVO vo = freeBoardDao.getBoard(conn, boNo);
			if (vo == null) {
				throw new BizNotFoundException("[" + boNo + "] 조회 실패");
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
	public void registBoard(FreeBoardVO board) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			freeBoardDao.insertBoard(conn, board);
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
	public void modifyBoard(FreeBoardVO board) throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			FreeBoardVO vo = freeBoardDao.getBoard(conn, board.getBoNo());
			if (vo == null) {
				throw new  BizNotFoundException();
			}
			if ( !(vo.getBoPass().equals(board.getBoPass()))) {
				throw new BizPasswordNotMatchedException();
			}
			int cnt = freeBoardDao.updateBoard(conn, board);
			if (cnt < 1) {  
				throw new BizNotEffectedException();
				}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
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
	public void removeBoard(FreeBoardVO board) throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			FreeBoardVO vo = freeBoardDao.getBoard(conn, board.getBoNo());
			if (vo == null) {
				throw new  BizNotFoundException();
			}
			if ( !(vo.getBoPass().equals(board.getBoPass()))) {
				throw new BizPasswordNotMatchedException();
			}
			int cnt = freeBoardDao.deleteBoard(conn, board);
			if (cnt < 1) {  
				throw new BizNotEffectedException();
				}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
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
	public void increaseHit(int boNo) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			freeBoardDao.increaseHit(conn, boNo);
			
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

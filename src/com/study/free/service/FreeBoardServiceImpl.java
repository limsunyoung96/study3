package com.study.free.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.study.common.util.MybatisSqlSessionFactory;
import com.study.exception.BizNotEffectedException;
import com.study.exception.BizNotFoundException;
import com.study.exception.BizPasswordNotMatchedException;
import com.study.free.dao.IFreeBoardDao;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

public class FreeBoardServiceImpl implements IFreeBoardService {

	@Override
	public List<FreeBoardVO> getBoardList(FreeBoardSearchVO searchVO) {
		try (SqlSession sqlSession = Factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			// 건수를 구해서 searchVO 설정 -> searchVO.pageSetting() -> list 호출
			int cnt = freeBoardDao.getBoardCount(searchVO);
			searchVO.setTotalRowCount(cnt);
			searchVO.pageSetting();
			List<FreeBoardVO> list = freeBoardDao.getBoardList(searchVO);
			return list;
		}
	}

	@Override
	public FreeBoardVO getBoard(int boNo) throws BizNotFoundException {
		try (SqlSession sqlSession = Factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			FreeBoardVO vo = freeBoardDao.getBoard(boNo);
			if (vo == null) {
				throw new BizNotFoundException("[" + boNo + "] 조회 실패");
			}
//			System.out.println(vo);
			return vo;
		}
	}

	SqlSessionFactory Factory = MybatisSqlSessionFactory.getSqlSessionFactory();

	@Override
	public void registBoard(FreeBoardVO board) {
		try (SqlSession sqlSession = Factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			System.out.println("이거 되닝");
			freeBoardDao.insertBoard(board);
			sqlSession.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifyBoard(FreeBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {

		try (SqlSession sqlSession = Factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			FreeBoardVO vo = freeBoardDao.getBoard(board.getBoNo());
			if (vo == null) {
				throw new BizNotFoundException();
			}
			if (!(vo.getBoPass().equals(board.getBoPass()))) {
				throw new BizPasswordNotMatchedException();
			}
			int cnt = freeBoardDao.updateBoard(board);
			if (cnt < 1) {
				throw new BizNotEffectedException();
			}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
			sqlSession.commit();
		}
	}

	@Override
	public void removeBoard(FreeBoardVO board)
			throws BizNotFoundException, BizPasswordNotMatchedException, BizNotEffectedException {
		try (SqlSession sqlSession = Factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);

			FreeBoardVO vo = freeBoardDao.getBoard(board.getBoNo());
			if (vo == null) {
				throw new BizNotFoundException();
			}
			if (!(vo.getBoPass().equals(board.getBoPass()))) {
				throw new BizPasswordNotMatchedException();
			}
			int cnt = freeBoardDao.deleteBoard(board);
			if (cnt < 1) {
				throw new BizNotEffectedException();
			}
//			BizPasswordNotMatchedException 이거 던지려면 !=대신 !( .equals)
			sqlSession.commit();
		}
	}

	@Override
	public void increaseHit(int boNo) {
		try (SqlSession sqlSession = Factory.openSession()) {
			IFreeBoardDao freeBoardDao = sqlSession.getMapper(IFreeBoardDao.class);
			freeBoardDao.increaseHit(boNo);
		}
	}

}

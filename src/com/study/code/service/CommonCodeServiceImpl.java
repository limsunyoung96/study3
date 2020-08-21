package com.study.code.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.study.code.dao.CommonCodeDaoOracle;
import com.study.code.dao.ICommonCodeDao;
import com.study.code.vo.CodeVO;
import com.study.exception.DaoException;
import com.study.member.vo.MemberVO;

public class CommonCodeServiceImpl implements ICommonCodeService {
	private ICommonCodeDao codeDao = new CommonCodeDaoOracle();
	
	@Override
	public List<CodeVO> getCodeListByParent(String parentCode) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:study");
			List<CodeVO> list = codeDao.getCodeListByParent(conn, parentCode);
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

package com.study.free.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.study.exception.DaoDuplicateKeyException;
import com.study.exception.DaoException;
import com.study.free.vo.FreeBoardSearchVO;
import com.study.free.vo.FreeBoardVO;

public class FreeBoardDaoOracle implements IFreeBoardDao {

	@Override
	public int getBoardCount(Connection conn, FreeBoardSearchVO searchVO) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT count(*)         ");
			sb.append("  FROM free_board       ");
			sb.append(" WHERE bo_del_yn = 'N'  ");
			// 검색 처리
			if(StringUtils.isNotBlank(searchVO.getSearchCategory())) {				
				sb.append(" AND bo_category = ?      ");
			}
			if(StringUtils.isNotBlank(searchVO.getSearchWord())) {
				switch (searchVO.getSearchType()) {
				case "T": sb.append(" AND bo_title LIKE  '%' || ? || '%'   ");	break;
				case "W": sb.append(" AND bo_writer LIKE  '%' || ? || '%'   ");	break;
				case "C": sb.append(" AND bo_content LIKE  '%' || ? || '%'   ");	break;				
				}
			}
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 처리 
			int i = 1;
			if(StringUtils.isNotBlank(searchVO.getSearchCategory())) {				
				pstmt.setString(i++, searchVO.getSearchCategory());
			}
			if(StringUtils.isNotBlank(searchVO.getSearchWord())) {
				pstmt.setString(i++, searchVO.getSearchWord()); // searchType 이 설정되었다는 가정 
			}
			
			rs = pstmt.executeQuery();
			int cnt = 0;
			if (rs.next()) {
				cnt = rs.getInt(1);
			}
			return cnt;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}finally {
			if(rs != null)try{ rs.close();}catch(SQLException e){}
			if(pstmt != null)try{ pstmt.close();}catch(SQLException e){}
		}
	}
	
	@Override
	public List<FreeBoardVO> getBoardList(Connection conn, FreeBoardSearchVO searchVO) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		List<FreeBoardVO> list = new ArrayList<FreeBoardVO>();
		try {
			sb.append("SELECT *                           ");
			sb.append("  FROM ( SELECT rownum rnum , a.*  ");
			sb.append("          FROM (			          ");
			sb.append("SELECT bo_no        ");
			sb.append("     , bo_title     ");
			sb.append("     , bo_category  ");
			sb.append("     , b.comm_nm AS bo_category_nm ");
			sb.append("     , bo_writer    ");
			sb.append("     , bo_pass      ");
			sb.append("     , bo_ip        ");
			sb.append("     , bo_hit       ");
			sb.append("     , TO_CHAR(bo_reg_date,'YYYY.MM.DD') AS bo_reg_date  ");
			sb.append("     , TO_CHAR(bo_mod_date,'YYYY.MM.DD') AS bo_mod_date  ");
			sb.append("     , bo_del_yn    ");
			sb.append("  FROM free_board a, comm_code b  ");
			sb.append(" WHERE a.bo_category = b.comm_cd  ");
			sb.append("   AND a.bo_del_yn   = 'N'        ");
			// 검색 처리
//			if(searchVO.getSearchCategory() != null && !searchVO.getSearchCategory().equals("") 
//					&& !searchVO.getSearchCategory().trim().equals(""));
			
			if(StringUtils.isNotBlank(searchVO.getSearchCategory())) {				
				sb.append(" AND bo_category = ?      ");
			}
			if(StringUtils.isNotBlank(searchVO.getSearchWord())) {
				switch (searchVO.getSearchType()) {
				case "T": sb.append(" AND bo_title LIKE  '%' || ? || '%'   ");	break;
				case "W": sb.append(" AND bo_writer LIKE  '%' || ? || '%'   ");	break;
				case "C": sb.append(" AND bo_content LIKE  '%' || ? || '%'   ");	break;				
				}
			}			
			sb.append("  ORDER BY bo_no DESC         ");
			
			sb.append("        		  ) a            ");
			sb.append("          WHERE rownum <= ?   ");
			sb.append("        ) b                   ");
			sb.append(" WHERE rnum BETWEEN ? AND ?   ");
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", " "));
			pstmt = conn.prepareStatement(sb.toString());
			// bind 변수 
			int i = 1;	
			// 검색 값 설정 
			if(StringUtils.isNotBlank(searchVO.getSearchCategory())) {				
				pstmt.setString(i++, searchVO.getSearchCategory());
			}
			if(StringUtils.isNotBlank(searchVO.getSearchWord())) {
				pstmt.setString(i++, searchVO.getSearchWord()); // searchType 이 설정되었다는 가정 
			}
			
			pstmt.setInt(i++, searchVO.getLastRow());
			pstmt.setInt(i++, searchVO.getFirstRow());
			pstmt.setInt(i++, searchVO.getLastRow());
			
			rs = pstmt.executeQuery();
			FreeBoardVO freeBoard = null;
			while (rs.next()) {				
				freeBoard = new FreeBoardVO();
				freeBoard.setBoNo(rs.getInt("bo_no"));
				freeBoard.setBoTitle(rs.getString("bo_title"));
				freeBoard.setBoWriter(rs.getString("bo_writer"));
				freeBoard.setBoCategory(rs.getString("bo_category"));
				freeBoard.setBoCategoryNm(rs.getString("bo_category_nm"));
				freeBoard.setBoIp(rs.getString("bo_ip"));
				freeBoard.setBoHit(rs.getInt("bo_hit"));
				freeBoard.setBoRegDate(rs.getString("bo_reg_date"));
				freeBoard.setBoModDate(rs.getString("bo_mod_date"));
				list.add(freeBoard);
			} // while			
			return list;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}finally {
			if(rs != null)try{ rs.close();}catch(SQLException e){}
			if(pstmt != null)try{ pstmt.close();}catch(SQLException e){}
		}
	}

	@Override
	public FreeBoardVO getBoard(Connection conn, int boNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		/* List<MemberVO> list = new ArrayList<MemberVO>(); */

		try {
			sb.append("SELECT    bo_no    , bo_title    , bo_category, (select comm_nm FROM comm_code WHERE comm_cd = bo_category) AS bo_category_nm   ");
			sb.append("        , bo_writer    , bo_pass    , bo_content                                                                                ");
		 	sb.append("        , bo_ip    , bo_hit    , to_char(bo_reg_date, 'YYYY-MM-DD HH24:MI') as bo_reg_date                                      ");
			sb.append("        , bo_mod_date    , bo_del_yn                                                                                            ");
			sb.append("  FROM    free_board                                                                                                            ");
			sb.append(" WHERE   bo_no = ?                                                                                                              ");
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", ""));
			System.out.println("boNo : "+boNo);
			pstmt = conn.prepareStatement(sb.toString());
			
			// 바인드 변수 설정
			pstmt.setInt(1, boNo);
			rs = pstmt.executeQuery();
			FreeBoardVO freeboard = null;
			if (rs.next()) {
				freeboard = new FreeBoardVO(); // 여기에 new MemberVO를 해야 여러사람이 나옴
				freeboard.setBoNo(rs.getInt("bo_no"));
				freeboard.setBoTitle(rs.getString("bo_title"));
				freeboard.setBoCategory(rs.getString("bo_category"));
				freeboard.setBoCategoryNm(rs.getString("bo_category_nm"));
				freeboard.setBoWriter(rs.getString("bo_writer"));
				freeboard.setBoPass(rs.getString("bo_pass"));
				freeboard.setBoContent(rs.getString("bo_content"));
				freeboard.setBoIp(rs.getString("bo_ip"));
				freeboard.setBoHit(rs.getInt("bo_hit"));
				freeboard.setBoRegDate(rs.getString("bo_reg_date"));
				freeboard.setBoModDate(rs.getString("bo_mod_date"));
				freeboard.setBoDelYn(rs.getString("bo_del_yn"));
			} // if
			return freeboard;
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				} // catch
		} // funally
	}

	@Override
	public int insertBoard(Connection conn, FreeBoardVO board) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("INSERT INTO free_board (    bo_no      , bo_title  , bo_category     ");
			sb.append("                          , bo_writer  , bo_pass   , bo_content      ");
			sb.append("                          , bo_ip      , bo_hit    , bo_reg_date     ");
			sb.append("                          , bo_del_yn                                ");
			sb.append(") VALUES (    seq_free_board.nextval   ,?          ,?                ");
			sb.append("                          , ?          ,?          ,?                ");
			sb.append("                          , ?          ,0          ,sysdate          ");
			sb.append("                          , 'N' )                                    ");


			pstmt = conn.prepareStatement(sb.toString());
			// 구문 실행 전에 파라미터 설정
			int i = 1;
			pstmt.setString(i++, board.getBoTitle());
			pstmt.setString(i++, board.getBoCategory());
			pstmt.setString(i++, board.getBoWriter());
			pstmt.setString(i++, board.getBoPass());
			pstmt.setString(i++, board.getBoContent());
			pstmt.setString(i++, board.getBoIp());
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			if(e.getErrorCode()==1) { //unique 에러일 경우
				throw new DaoDuplicateKeyException("등록된 코드 발생 = ["+board.getBoNo()+"]");
			}
			throw new DaoException(e.getMessage(), e);
			
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				} // catch
		} // funally
	}

	@Override
	public int updateBoard(Connection conn, FreeBoardVO board) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("UPDATE free_board                 ");
			sb.append("SET    bo_title = ?               ");
			sb.append("     , bo_category = ?            ");
			sb.append("     , bo_writer = ?              ");
			sb.append("     , bo_content = ?             ");
			sb.append("     , bo_ip = ?                  ");
			sb.append("     , bo_hit = ?                 ");
			sb.append("     , bo_mod_date = sysdate      ");
			sb.append("WHERE  bo_no = ?                  ");
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", ""));
			
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			int i = 1;
			pstmt.setString(i++, board.getBoTitle());
			pstmt.setString(i++, board.getBoCategory());
			pstmt.setString(i++, board.getBoWriter());
			pstmt.setString(i++, board.getBoContent());
			pstmt.setString(i++, board.getBoIp());
			pstmt.setInt(i++, board.getBoHit());
			pstmt.setInt(i++, board.getBoNo());
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				} // catch
		} // funally
	}

	@Override
	public int deleteBoard(Connection conn, FreeBoardVO board) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("DELETE FROM free_board    ");
			sb.append("WHERE   bo_no = ?         ");
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", ""));
			
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			int i = 1;
			pstmt.setInt(i++, board.getBoNo());
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				} // catch
		} // funally
				
	}

	@Override
	public int increaseHit(Connection conn, int boNo) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			sb.append("UPDATE free_board             ");
			sb.append("SET    bo_hit = bo_hit + 1    ");
			sb.append("WHERE  bo_no = ?              ");
			
			System.out.println(sb.toString().replaceAll("\\s{2,}", ""));
			pstmt = conn.prepareStatement(sb.toString());
			// 바인드 변수 설정
			pstmt.setInt(1, boNo);
			
			int cnt = pstmt.executeUpdate();
			return cnt;
			
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				} // catch
		} // funally
	}

}// class

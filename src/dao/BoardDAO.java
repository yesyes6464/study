package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import static db.JdbcUtil.*;
import javax.sql.DataSource;


import vo.BoardBean;
import vo.PageInfo;

public class BoardDAO {

	Connection con;
	private static BoardDAO boardDAO;
	
	private BoardDAO(){}
	
	public static BoardDAO getInstance(){
		if(boardDAO == null){
			boardDAO = new BoardDAO();
		}
		return boardDAO;
	}
	
	public void setConnection(Connection con){
		this.con = con;
	}
	
	PreparedStatement pstmt;
	ResultSet rs;
	String sql;
	
	//글의 개수 구하기
	public int selectListCount(){
		int listCount = 0;
		
		try {
			pstmt = con.prepareStatement("SELECT count(*) FROM board");
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				listCount = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("selectListCount() error"+e);
		} 
		return listCount;
	}

	//글 등록
	public int insertArticle(BoardBean boardBean) {
		
		int num = 0;
		int insertCount = 0;
		
		try {
			pstmt = con.prepareStatement("SELECT max(board_num) FROM board");
			rs = pstmt.executeQuery();
			
			if(rs.next()) num = rs.getInt(1)+1;
			else num = 1;
			
			sql = "INSERT INTO board(BOARD_NUM, "
								  + "BOARD_NAME, "
								  + "BOARD_SUBJECT, "
								  + "BOARD_CONTENT, "
								  + "BOARD_PASS, "
								  + "RE_REF, RE_LEV, RE_STEP, "
								  + "BOARD_READCOUNT, BOARD_DATE) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,now())";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num); //BOARD_NUM
			pstmt.setString(2, boardBean.getBoard_name()); //BOARD_NAME
			pstmt.setString(3, boardBean.getBoard_subject()); //BOARD_SUBJECT
			pstmt.setString(4, boardBean.getBoard_content()); //BOARD_CONTENT
			pstmt.setString(5, boardBean.getBoard_pass()); //BOARD_PASS
			pstmt.setInt(6, num); //RE_REF
			pstmt.setInt(7, 0); //RE_LEV
			pstmt.setInt(8, 0); //RE_STEP
			pstmt.setInt(9, 0); //BOARD_READCOUNT
			
			insertCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("insertArticle(BoardBean boardBean) err : "+e);
		} finally {
			close(pstmt);
			close(rs);
		}	
		return insertCount;
	}
	
	//답글등록
	public int insertReplyArticle(BoardBean boardBean) {
		
		int num = 0;
		int insertCount = 0;
		
		int re_ref = boardBean.getRe_ref();
		int re_lev = boardBean.getRe_lev();
		int re_step = boardBean.getRe_step();
		
		try {
			pstmt = con.prepareStatement("SELECT max(board_num) FROM board");
			rs = pstmt.executeQuery();
			
			if(rs.next()) num = rs.getInt(1)+1;
			else num = 1;
			
			sql = "UPDATE board SET re_step=re_step+1 WHERE re_ref=? and re_step>?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_step);
			pstmt.executeUpdate();
			
			re_step += 1;
			re_lev += 1;
			
			sql = "INSERT INTO board(BOARD_NUM, "
					  + "BOARD_NAME, "
					  + "BOARD_SUBJECT, "
					  + "BOARD_CONTENT, "
					  + "BOARD_PASS, "
					  + "RE_REF, RE_LEV, RE_STEP, "
					  + "BOARD_READCOUNT, BOARD_DATE) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,now())";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num); //BOARD_NUM
			pstmt.setString(2, boardBean.getBoard_name()); //BOARD_NAME
			pstmt.setString(3, boardBean.getBoard_subject()); //BOARD_SUBJECT
			pstmt.setString(4, boardBean.getBoard_content()); //BOARD_CONTENT
			pstmt.setString(5, boardBean.getBoard_pass()); //BOARD_PASS
			pstmt.setInt(6, re_ref); //RE_REF
			pstmt.setInt(7, re_lev); //RE_LEV
			pstmt.setInt(8, re_step); //RE_STEP
			pstmt.setInt(9, 0); //BOARD_READCOUNT
			insertCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("insertReplyArticle(BoardBean boardBean) err : "+e);
		} finally {
			close(pstmt);
			close(rs);
		}
		return insertCount;
	}
	
	//글 목록 보기
	public ArrayList<BoardBean> selectArticleList(int page, int limit){
		
		sql = "SELECT * "
		    + "FROM board "
		    + "ORDER BY re_ref desc, re_step asc "
		    + "LIMIT ?,10";
		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		BoardBean board = null;
		int startrow = (page-1)*10; //읽기 시작할 row번호
				
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startrow);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()){
				board = new BoardBean();
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_content(rs.getString("board_content"));
				board.setRe_ref(rs.getInt("re_ref"));
				board.setRe_lev(rs.getInt("re_lev"));
				board.setRe_step(rs.getInt("re_step"));
				board.setBoard_readcount(rs.getInt("board_readcount"));
				board.setBoard_date(rs.getDate("board_date"));
				
				articleList.add(board);
			}
			
			if(articleList.isEmpty()) articleList = null;
			
		} catch (Exception e) {
			System.out.println("selectArticleList(int page, int limit) error"+e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return articleList;
	}
	
	//조회수 증가
	public void countAdd(int num){ 
		System.out.println(num);
		try {
			sql = "UPDATE board SET board_readcount = board_readcount+1 where board_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("countAdd() err : ");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
	}
	
	//게시글 상세보기
	public BoardBean contentLookup(int num){
		BoardBean board = new BoardBean();
		try {
			sql = "SELECT * FROM board WHERE board_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_name(rs.getString("board_name"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_date(rs.getDate("board_date"));
				board.setBoard_pass(rs.getString("board_pass"));
				board.setRe_ref(rs.getInt("re_ref"));
				board.setRe_lev(rs.getInt("re_lev"));
				board.setRe_step(rs.getInt("re_step"));
			}	
		} catch (Exception e) {
			System.out.println("contentLookup() err");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		return board;
	}
	
	//게시글 비밀번호
	public String getPass(int num){
		String password = null;
		try {
			sql = "SELECT board_pass FROM board WHERE board_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) password = rs.getString("board_pass");
				
		} catch (Exception e) {
			System.out.println("getPass(int num) err"+e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return password;
	}

	//게시글 삭제
	public void boardDelete(int num) {
		try {
			sql = "DELETE FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("boardDelete(int num) err"+e);
		} finally {
			close(pstmt);
		}
	}

	public int updateModifyArticle(BoardBean boardBean) {
		int insertCount = 0;
		
		try {
			sql = "UPDATE board SET board_name=?, board_subject=?, board_content=?, board_pass=? WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, boardBean.getBoard_name()); //BOARD_NAME
			pstmt.setString(2, boardBean.getBoard_subject()); //BOARD_SUBJECT
			pstmt.setString(3, boardBean.getBoard_content()); //BOARD_CONTENT
			pstmt.setString(4, boardBean.getBoard_pass()); //BOARD_PASS
			pstmt.setInt(5, boardBean.getBoard_num()); //BOARD_NUM
			insertCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("updateModifyArticle(BoardBean boardBean) err : "+e);
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}

	//제목기준으로 게시글 검색
	public ArrayList<BoardBean> subjectSearchList(String input) {

		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		BoardBean article = null;
		try {
			sql = "SELECT board_num, board_subject, board_name, board_date, board_readcount, re_ref, re_lev, re_step "
				+ "FROM board "
				+ "WHERE board_subject like ? "
				+ "ORDER BY board_num desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+input+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				article = new BoardBean();
				article.setBoard_num(rs.getInt("board_num"));
				article.setBoard_name(rs.getString("board_name"));
				article.setBoard_subject(rs.getString("board_subject"));
				article.setBoard_date(rs.getDate("board_date"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setRe_ref(rs.getInt("re_ref"));
				article.setRe_lev(rs.getInt("re_lev"));
				article.setRe_step(rs.getInt("re_step"));
				
				articleList.add(article);
			}
			
			
		} catch (Exception e) {
			System.out.println("subjectSearchList(String input) err : "+e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return articleList;
	}

	//이름기준으로 게시글 검색
	public ArrayList<BoardBean> nameSearchList(String input) {

		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		BoardBean article = null;
		try {
			sql = "SELECT board_num, board_subject, board_name, board_date, board_readcount, re_ref, re_lev, re_step "
				+ "FROM board "
				+ "WHERE board_name like ? "
				+ "ORDER BY board_num desc";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+input+"%");
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				article = new BoardBean();
				article.setBoard_num(rs.getInt("board_num"));
				System.out.println(article.getBoard_num());
				article.setBoard_name(rs.getString("board_name"));
				article.setBoard_subject(rs.getString("board_subject"));
				article.setBoard_date(rs.getDate("board_date"));
				article.setBoard_readcount(rs.getInt("board_readcount"));
				article.setRe_ref(rs.getInt("re_ref"));
				article.setRe_lev(rs.getInt("re_lev"));
				article.setRe_step(rs.getInt("re_step"));
						
				articleList.add(article);
			}
			
			
		} catch (Exception e) {
			System.out.println("nameSearchList(String input) err : "+e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return articleList;
	}

	public PageInfo subjectSearchCount(String input) {
		
		PageInfo pageInfo = new PageInfo();
		try {
			pstmt = con.prepareStatement("SELECT count(*) FROM board WHERE board_subject like ? ");
			pstmt.setString(1, "%"+input+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) pageInfo.setListCount(rs.getInt(1));
			
		} catch (Exception e) {
			System.out.println("subjectSearchCount(String input) err : "+e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return pageInfo;
	}

	public PageInfo nameSearchCount(String input) {
		
		PageInfo pageInfo = new PageInfo();
		try {
			pstmt = con.prepareStatement("SELECT count(*) FROM board WHERE board_name like ? ");
			pstmt.setString(1, "%"+input+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) pageInfo.setListCount(rs.getInt(1));
			
		} catch (Exception e) {
			System.out.println("nameSearchCount(String input) err : "+e);
		} finally {
			close(rs);
			close(pstmt);
		}
		return pageInfo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

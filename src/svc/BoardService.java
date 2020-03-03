package svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;
import vo.PageInfo;

import static db.JdbcUtil.*;

public class BoardService {
	
	//총 게시글 수
	public int getListCount() throws Exception{
		
		int listCount = 0;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		listCount = boardDAO.selectListCount();
		close(con);
		
		return listCount;
	}
	
	//게시글 리스트
	public ArrayList<BoardBean> getArticleList(int page, int limit) throws Exception {
		
		ArrayList<BoardBean> articleList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		articleList = boardDAO.selectArticleList(page, limit);
		close(con);
		
		return articleList;
	}
	
	//게시글 저장
	public boolean registArticle(BoardBean boardBean) {
		
		boolean isWriteSuccess = false;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		int insertCount = boardDAO.insertArticle(boardBean);
		
		if(insertCount > 0) isWriteSuccess = true;
		
		close(con);
		
		return isWriteSuccess;
	}
	
	//답글 저장
	public boolean replyArticle(BoardBean boardBean) {
		
		boolean isReplySuccess = false;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		int insertCount = boardDAO.insertReplyArticle(boardBean);
		
		if(insertCount > 0) isReplySuccess = true;
		
		close(con);
		
		return isReplySuccess;
	}

	//게시글 상세보기
	public BoardBean getArticle(int num) {
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		BoardBean bean = boardDAO.contentLookup(num);
		boardDAO.countAdd(num); //조회수 증가
		close(con);
		
		return bean;
	}
	
	//수정할 게시글 상세보기
	public BoardBean getModifyArticle(int num) {
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		BoardBean bean = boardDAO.contentLookup(num);
		close(con);
		
		return bean;
	}
	
	//게시글 비밀번호
	public String getPass(int num) {
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		String password = boardDAO.getPass(num);
		
		close(con);
		
		return password;
	}

	//게시글 삭제
	public void boardDelete(int num) {
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		boardDAO.boardDelete(num);
		
		close(con);
	}

	public boolean modifyArticle(BoardBean boardBean) {
		
		boolean isModifySuccess = false;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		int insertCount = boardDAO.updateModifyArticle(boardBean);
		
		if(insertCount > 0) isModifySuccess = true;
		
		close(con);
		
		return isModifySuccess;
	}

	public ArrayList<BoardBean> boardSearch(String choice, String input) {

		ArrayList<BoardBean> articleList = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);

		if("subject".equals(choice)) articleList = boardDAO.subjectSearchList(input);
		else if("board_name".equals(choice)) articleList = boardDAO.nameSearchList(input);
		
		close(con);
		
		return articleList;
		
	}

	public PageInfo searchPageInfo(String choice, String input) {
		
		PageInfo pageInfo = null;
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		if("subject".equals(choice)) pageInfo = boardDAO.subjectSearchCount(input);
		else if("board_name".equals(choice)) pageInfo = boardDAO.nameSearchCount(input);
		
		return pageInfo;
	}
	
	
}

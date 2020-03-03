package action.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.BoardService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
								throws Exception {
		ActionForward forward = null;

		String choice = request.getParameter("choice"); //검색 기준
		String input = request.getParameter("input"); //검색 내용
		
		BoardService boardservice = new BoardService();
		ArrayList<BoardBean> articleList = boardservice.boardSearch(choice, input);
		
		PageInfo pageInfo = boardservice.searchPageInfo(choice, input); //페이지정보 객체
		int listCount = pageInfo.getListCount(); //검색 리스트 수
		int page = 1; //첫페이지
		int limit = 10; //페이지 제한 수
		int maxPage = (int)( (double)listCount/limit + 0.95 ); //페이지 계산
		
		pageInfo.setMaxPage(maxPage); //총 페이지 수 저장
		pageInfo.setPage(page);
 		
		AjaxAction ajax = new AjaxAction();
		String jsonInfo = ajax.jsonList(articleList, pageInfo);
		request.setAttribute("jsonInfo", jsonInfo);
		
		//ajax
		return forward;
	}

}

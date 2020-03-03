package action.board;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import action.Action;
import svc.BoardService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
								throws Exception {
		AjaxAction ajax = null;
		ActionForward forward = null;
		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		int page = 1;
		int limit = 10;
		
		if(request.getParameter("page") != null){
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		BoardService boardService = new BoardService();
		articleList = boardService.getArticleList(page, limit);
		int listCount = boardService.getListCount();
		
		//총 페이지 수
		int maxPage = (int)( (double)listCount/limit + 0.95 );
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setPage(page);
		pageInfo.setMaxPage(maxPage);
		pageInfo.setListCount(listCount);
		
		if( "ajax".equals( request.getParameter("Handler") ) ){ //비동기 요청일때
			
			ajax = new AjaxAction();
			String jsonInfo = ajax.jsonList(articleList, pageInfo);
			request.setAttribute("jsonInfo", jsonInfo);
			
			return forward;
		}
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("articleList", articleList);
		
		forward = new ActionForward();
		forward.setPath("/notice_board/notice_board.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
	
	

	
	
}

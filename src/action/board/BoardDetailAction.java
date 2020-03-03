package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.BoardService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		int num = Integer.parseInt(request.getParameter("num")); 
		
		BoardService boardDetailService = new BoardService();
		BoardBean article = boardDetailService.getArticle(num);
		
		request.setAttribute("article", article);
		
		forward.setPath("/notice_board/post.jsp");
		forward.setRedirect(false);
		
		return forward;
	}
	
	
	
}

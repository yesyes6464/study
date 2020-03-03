package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.BoardService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
								throws Exception {
		ActionForward forward = null;
		
		int num = Integer.parseInt(request.getParameter("board_num")); //수정할 게시글 번호
		
		BoardService boardservice = new BoardService();
		BoardBean article = boardservice.getModifyArticle(num); //수정할 게시글 정보
		
		request.setAttribute("article", article);
		
		forward = new ActionForward();
		forward.setPath("/notice_board/modify.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}

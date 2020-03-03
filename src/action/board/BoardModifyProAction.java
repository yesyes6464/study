package action.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.BoardService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
								throws Exception {
		ActionForward forward = null;
		BoardBean boardBean = null;

		int num = Integer.parseInt(request.getParameter("board_num"));
		String writer = request.getParameter("writer");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String password = request.getParameter("password");
		
		boardBean = new BoardBean();
		boardBean.setBoard_num(num);
		boardBean.setBoard_name(writer);
		boardBean.setBoard_subject(subject);
		boardBean.setBoard_content(content);
		boardBean.setBoard_pass(password);
	
		BoardService boardService = new BoardService();
		boolean isModifySuccess = boardService.modifyArticle(boardBean);
		
		if(!isModifySuccess){
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('수정실패');");
			out.println("history.back();");
			out.println("</script>");
			
		}else{
			
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("boardList.bo");
			
		}
		
		return forward;
	}

}

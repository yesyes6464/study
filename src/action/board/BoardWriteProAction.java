package action.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.BoardService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardWriteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
								throws Exception {
		
		ActionForward forward = null;
		BoardBean boardBean = null;
		
		String writer = request.getParameter("writer");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String password = request.getParameter("password");
		
		boardBean = new BoardBean();
		boardBean.setBoard_name(writer);
		boardBean.setBoard_subject(subject);
		boardBean.setBoard_content(content);
		boardBean.setBoard_pass(password);
		
		BoardService boardService = new BoardService();
		boolean isWriteSuccess = boardService.registArticle(boardBean);
		
		if(!isWriteSuccess){
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('등록실패');");
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

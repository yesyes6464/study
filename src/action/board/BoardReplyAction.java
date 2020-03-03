package action.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.BoardService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
								throws Exception {
		ActionForward forward = null;
		BoardBean boardBean = null;
		
		int board_num = Integer.parseInt(request.getParameter("board_num")); //답글 게시글 번호
		int re_ref = Integer.parseInt(request.getParameter("re_ref"));
		int re_lev = Integer.parseInt(request.getParameter("re_lev"));
		int re_step = Integer.parseInt(request.getParameter("re_step"));
		
		String writer = request.getParameter("writer"); //답글 작성자
		String subject = request.getParameter("subject"); //답글 제목
		String content = request.getParameter("content"); //답글 내용
		String password = request.getParameter("password"); //답글 비밀번호
		
		
		
		boardBean = new BoardBean();
		//답글 게시글 정보
		boardBean.setBoard_num(board_num);
		boardBean.setRe_ref(re_ref);
		boardBean.setRe_lev(re_lev);
		boardBean.setRe_step(re_step);
		
		//답글 정보
		boardBean.setBoard_name(writer);
		boardBean.setBoard_subject(subject);
		boardBean.setBoard_content(content);
		boardBean.setBoard_pass(password);
		
		BoardService boardService = new BoardService();
		boolean isWriteSuccess = boardService.replyArticle(boardBean);
		
		if(!isWriteSuccess){
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('답장실패');");
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

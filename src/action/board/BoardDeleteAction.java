package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import action.Action;
import svc.BoardService;
import vo.ActionForward;
import vo.PageInfo;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) 
								throws Exception {
		
		ActionForward forward = null;
		int board_num = Integer.parseInt(request.getParameter("board_num")); //게시글 번호
		System.out.println(board_num);
		String input = request.getParameter("input"); //입력한 비밀번호
		System.out.println(input);
		
		BoardService boardservice = new BoardService();
		String password = boardservice.getPass(board_num); //게시글 비밀번호
		System.out.println(password);
		
		if( "ajax".equals( request.getParameter("Handler") ) ){ //비동기 요청일때
			
		}
		
		//ajax로 요청받음
		if(password.equals(input)){
			
			boardservice.boardDelete(board_num);
			JSONObject result = new JSONObject();
			result.put("result", "success");
			String jsonInfo = result.toJSONString();
			request.setAttribute("jsonInfo", jsonInfo);
			
		}else{
			
			JSONObject result = new JSONObject();
			result.put("result", "fail");
			String jsonInfo = result.toJSONString();
			request.setAttribute("jsonInfo", jsonInfo);
			
		}
		
		return forward;
	}

}

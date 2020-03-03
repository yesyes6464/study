package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.board.BoardDeleteAction;
import action.board.BoardDetailAction;
import action.board.BoardListAction;
import action.board.BoardModifyAction;
import action.board.BoardModifyProAction;
import action.board.BoardReplyAction;
import action.board.BoardSearchAction;
import action.board.BoardWriteProAction;
import vo.ActionForward;

@WebServlet("*.bo")
public class BoardFrontController extends javax.servlet.http.HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=utf-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;
		
		if(command.equals("/boardWritePro.bo")){
			
			action = new BoardWriteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				System.out.println("/boardWritePro.bo err : "+e);
			}
			
		}else if(command.equals("/boardList.bo")){
			action = new BoardListAction();
			try { 
				forward = action.execute(request, response);
				System.out.println("forward");
			} catch (Exception e) {
				System.out.println("/boardList.bo err : "+e);
			}
			
		}else if(command.equals("/boardDetail.bo")){

			action = new BoardDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				System.out.println("/boardDetail.bo err : "+e);
			}
			
		}else if(command.equals("/boardDelete.bo")){

			action = new BoardDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				System.out.println("/boardDelete.bo err : "+e);
			}
			
		}else if(command.equals("/boardReply.bo")){
			
			action = new BoardReplyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				System.out.println("/boardReply.bo err : "+e);
			}
			
		}else if(command.equals("/boardModify.bo")){
			
			action = new BoardModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				System.out.println("/boardModify.bo err : "+e);
			}
			
		}else if(command.equals("/boardModifyPro.bo")){
			
			action = new BoardModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				System.out.println("/boardModifyPro.bo err : "+e);
			}
			
		}else if(command.equals("/boardSearch.bo")){
			
			action = new BoardSearchAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				System.out.println("/boardSearch.bo err : "+e);
			}
			
		}
		
		if(forward != null){
			
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());
			}else{
				System.out.println("디스패치");
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
			
		}else{
			
			String jsonInfo = (String)request.getAttribute("jsonInfo");
			response.getWriter().print(jsonInfo);
			System.out.println(jsonInfo);
		}
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		doProcess(request, response);
	}
	
	
}

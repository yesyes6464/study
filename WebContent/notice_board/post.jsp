<%@page import="vo.BoardBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% BoardBean article = (BoardBean)request.getAttribute("article"); %>
<% String user_id = (String)session.getAttribute("user_id"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>post</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/notice_board/css/post.css">
</head>
<body>

<article>
	<div class="post_block">
	<%if(article.getRe_lev() == 0){ %>
		<h1 align="center">게시글</h1>
	<%} else{%>
		<h1 align="center">답글</h1>
	<%} %>
		<input id="board_num" type="hidden" value="<%=article.getBoard_num() %>">
		<input id="re_ref" type="hidden" value="<%=article.getRe_ref() %>">
		<input id="re_lev" type="hidden" value="<%=article.getRe_lev() %>">
		<input id="re_step" type="hidden" value="<%=article.getRe_step() %>">
		<table>
			<tr>
				<th>작성자</th>
				<td id="writer"><%=article.getBoard_name() %></td>
				<th>작성일</th>
				<td id="date"><%=article.getBoard_date() %></td>
			</tr>
			<tr>
				<th>제목</th>
				<td colspan="3"><%=article.getBoard_subject() %></td>
			</tr>
			<tr style="height: 340px;">
				<th>내용</th>
				<td valign="top" style="padding-top: 8px; padding-bottom: 8px;" colspan="3"><%=article.getBoard_content() %></td>
			</tr>							
		</table>
		<div class="post_btn_block">
			<input class="post_btn" type="button" value="목록" />
			<input class="post_btn" type="button" value="답글" />
			<input class="post_btn" type="button" value="수정" />
			<input class="post_btn" type="button" value="삭제" />
		</div>
	</div>
</article>

<script src="${pageContext.request.contextPath }/notice_board/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath }/notice_board/js/post.js"></script>

</body>
</html>
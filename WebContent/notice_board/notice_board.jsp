<%@page import="vo.PageInfo"%>
<%@page import="vo.BoardBean"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% ArrayList<BoardBean> articleList = (ArrayList<BoardBean>)request.getAttribute("articleList"); %>
<% PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>notice_board</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/notice_board/css/notice_board.css">
</head>
<body>

<article>
	<h1 align="center" style="font-size: 45px;">게시판</h1>
	<div class="count_search_block">
		<div class="count">
		<%if(pageInfo != null){ %>
			<input type="hidden" value="<%=pageInfo.getMaxPage() %>">
			<span style="font-size: 18px;">[ 총 게시글 수 : <b><%=pageInfo.getListCount() %></b> ]</span>
		<%} %>
		</div>
	</div>
	<table>
		<thead>
			<tr>
				<th class="num">번호</th>
				<th class="subject">제목</th>
				<th class="writer">작성자</th>
				<th class="date">작성일</th>
				<th class="views">조회수</th>
			</tr>
		</thead>
		<tbody>
	<%if(articleList != null){%>
		<%for(int i=0; i<articleList.size(); i++){%>
			<tr class="row">
				<td class="num"><%=articleList.get(i).getBoard_num() %></td>
				<td class="subject">
			<%if(articleList.get(i).getRe_lev() > 0){ %>
				<%for(int j=1; j<articleList.get(i).getRe_lev(); j++){ %>
					<img src="${pageContext.request.contextPath}/images/reply/blank.gif" alt="화살표이미지">
				<%} %>
					<img src="${pageContext.request.contextPath}/images/reply/arrow.png" alt="화살표이미지">
			<%} %>
				<%=articleList.get(i).getBoard_subject() %></td>
				<td class="writer"><%=articleList.get(i).getBoard_name() %></td>
				<td class="date"><%=articleList.get(i).getBoard_date() %></td>
				<td class="views"><%=articleList.get(i).getBoard_readcount() %></td>
			</tr>
		<%}%>
	<%}else{%>
			<tr>
				<td align="center" colspan="5">등록된 게시글이 없습니다.</td>
			</tr>
	<%}%>
		</tbody>
	</table>
	<div class="page">
		<div class="pagebtn">
	<%if(articleList != null){%>
		<%-- <%if(pageInfo.getPage() > 1){ %> --%>
			<input name="<%=pageInfo.getPage() %>" type="button" value="<">
		<%-- <%}%> --%>
		<%for(int i=0; i<pageInfo.getMaxPage(); i++){%>	
			<input name="<%=pageInfo.getPage() %>" type="button" value="<%=i+1 %>">
		<%}%>
		<%-- <%if(pageInfo.getPage() != pageInfo.getMaxPage()){ %> --%>
			<input name="<%=pageInfo.getPage() %>" type="button" value=">">
		<%-- <%}%> --%>
	<%}%>
		</div>
	</div>
	<div class="writing">
		<input type="button" value="글쓰기">
	</div>
</article>

<script src="${pageContext.request.contextPath }/notice_board/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath }/notice_board/js/notice_board.js"></script>

</body>
</html>
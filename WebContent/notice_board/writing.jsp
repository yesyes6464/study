<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>writing</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/notice_board/css/writing.css">
</head>
<body>

<article>
	<div class="writing_wrap">
		<div class="writing_input">
			<h1 align="center">게시글 작성</h1>
			<form action="${pageContext.request.contextPath}/boardWritePro.bo" method="post" id="writer_form">
				<table>
					<tr>
						<th>작성자</th>
						<td>
							<input type="text" id="writer" name="writer">
						</td> 
					</tr>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" id="subject" name="subject">
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<textarea rows="30" cols="10" id="content" name="content"></textarea>
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>
							<input type="text" id="password" name="password">
						</td>
					</tr>
				</table>
				<div class="writing_button">
					<input class="button" id="writing_submit" type="button" value="submit">
					<input class="button" type="reset" value="reset">
				</div>
			</form>
		</div>
	</div>
</article>

<script src="${pageContext.request.contextPath}/notice_board/js/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/notice_board/js/writing.js"></script>
</body>
</html>
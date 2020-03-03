$(function() {
	
	//게시글 작성[유효성 체크]
	$('#writing_submit').click(function() {
		
		var subject = $('#subject').val();
		var content = $('#content').val();
		var password = $('#password').val();
		
		if(subject == "" || subject.length == 0){
			alert('제목을 입력해주세요.');
		}else if(content == "" || content.length == 0){
			alert('내용을 입력해주세요.');
		}else if(password == "" || password.length == 0){
			alert('비밀번호를 입력해주세요.');
		}else{
			$('#writer_form').submit();
		}
		
	});
	
	//게시글 수정[유효성 체크]
	$('#modify_submit').click(function() {
		
		var subject = $('#subject').val();
		var content = $('#content').val();
		var password = $('#password').val();
		
		if(subject == "" || subject.length == 0){
			alert('제목을 입력해주세요.');
		}else if(content == "" || content.length == 0){
			alert('내용을 입력해주세요.');
		}else if(password == "" || password.length == 0){
			alert('비밀번호를 입력해주세요.');
		}else{
			$('#modify_form').submit();
		}
		
	});
	
});
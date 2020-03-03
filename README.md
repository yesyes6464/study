# project : MVC 패턴 게시판

국비지원교육 개인프로젝트 <br>
기능 : 게시판 목록, 상세내용, 글쓰기, 글수정, 글삭제, 답글기능 <br>
언어 : JAVA, HTML/CSS, JavaScript, ajax <br>
개발도구 : Tomcat 8.5, Eclipse, my-SQL <br>
<br>
# 소개

MVC 패턴이해를 위해 수업진행 내용을 참고로 게시판DB만을 이용하여 사용할 수 있는 MVC 모델2 기반의 익명게시판입니다.<br>
게시판 기능으로 게시판 목록, 상세내용, 글쓰기, 글수정, 글삭제, 답글 기능이 있습니다. <br>
JDBC와 연동하기 위해서 Connection pool(커넥션 풀)을 사용하였습니다. <br>
<br>

# 실행 방법

### 1. 이클립스에 프로젝트를 import 하세요.

### 2. connection pool이 작동하는지 connect 여부를 확인해주세요.
- META-INF 폴더에 context.xml 파일에서 설정 값을 확인해주세요.

### 3. 아래와 같이 board 테이블을 만들어 주세요. 
<br>
<pre><code>
CREATE TABLE `board` (
  `board_num` int(11),
  `board_name` varchar(45),
  `board_subject` varchar(45),
  `board_content` varchar(200),
  `board_pass` varchar(45),
  `re_ref` int(11),
  `re_lev` int(11),
  `re_step` int(11),
  `board_readcount`,
  `board_date` date,
  PRIMARY KEY (`board_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
</code></pre>
<br>
- column은 `board_num`, `board_name`, `board_subject`, `board_content`
  `board_pass`, `re_ref`, `re_lev`, `re_step`, `board_readcount`, `board_date` 입니다.

### 4. 메인 test는  WebContent 폴더에 'index.jsp'를 run하시면 됩니다.
- 메인 페이지의 상담 메뉴 중 '게시판' 기능망 구현되어있습니다.
- 제목을 클릭하시면 내용보기 창으로 넘어가고, 그 외 수정, 삭제 등의 기능을 테스트해보실 수 있습니다.

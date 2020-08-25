<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>청소&인테리어</title>
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>

<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.1/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.1/js/bootstrap.min.js"></script>
<link
	href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css"
	rel="stylesheet">

<link href="resources/style/StoreNoticeRegView.css" rel="stylesheet">
<link href="resources/summernote/dist/summernote.css" rel="stylesheet">
<script src="resources/summernote/dist/summernote.min.js"></script>

<link rel="stylesheet" href="resources/style/MyRecipeNoticeUpdateView.css" type="text/css">
	
<!-- include summernote-ko-KR -->
<script src="resources/summernote/dist/lang/summernote-ko-KR.js"></script>
<script>
$(function() {
	$('#n_Content').summernote({
		height : 330,
		width : 810,
		minHeight : null,
		maxHeight : null,
		focus : true,
		lang : 'ko-KR' // default: 'en-US'
	});
	
	function readURL(input) {
        if (input.files && input.files[0]) {
           var reader = new FileReader(); // 파일읽기위한 객체 생성
           reader.onload = function(e) { // 파일읽어들이기 성공했을 때 호출되는 이벤트
              $('#img').attr('src', e.target.result);
              // 이미지 태그의 읽어들인 SRC속성 file 내용 지정  
           }
           reader.readAsDataURL(input.files[0]);
           // file내용을 읽어 dataURL형식의 문자열로 저장
        }

     }
     // file 양식으로 이비지를 선택(변경된 값)되었을 때 처리
     $('#n_File').change(function() {
        // alert(this.value); // 선택한 이미지 경로 표시
        readURL(this);
     });
	
	
	$('#write').click(function() {
		var n_Title = $('#n_Title').val();
		var n_Content = $('#n_Content').val();
		if(!n_Title){
			alert("제목을 입력하세요.");
			return;
		}
		if(!n_Content){
			alert("내용을 입력하세요.");
			return;
		}
		var aHTML = $('#n_Content').code();
		//$('#summernote').destroy();

		$('#n_Content').html(aHTML);
		var summernote = $('#n_Content').val();
		
		$('#noticeInput').submit();
	});
	
	$('#login_result').hide();
	$('#login_Id, #login_Pwd').keydown(function(key){
		 if (key.keyCode == 13) {
  		login();   		
		 }
	});
	$('#login_btn').click(function(){
		login();
   		
    });
	function login(){
		var m_Id = $("#login_Id").val();
   		var m_Pwd = $("#login_Pwd").val();
   		
   		var param = "m_Id="+m_Id+"&m_Pwd="+m_Pwd;
   		
   		$.ajax({
			type : "post",
			url  : "loginCheck",	
			data : param,
			success : function(data){
				if(data=="success"){
					$('#login_result').hide();
					location.reload();
				}else{
					$("#login_result").html(data).css("color","red");
					$('#login_result').show();
				}							
			}
		});
	};
    
    
    $('#login_a').click(function(){
    	$('#login_box ul').toggle();
    });
	
});
</script>

</head>
<body>
	<div id="wrapper">
		<div id="header">
            <a href="/threemeals/"><img src="resources/images/logo.png"></a>
         </div>		
		<form id="loginAtion" action="loginProcess" method="post">		
			<div id="menu">
			<ul>
				<li class="active sub"><a href="#"><span>냉장고</span></a>
					<ul>
						<li class="sub"><a href="#"><span>냉장고등록</span></a>
						<li class="sub"><a href="#"><span>냉장고보기</span></a>
					</ul></li>
				<li class="active sub"><a href="#"><span>오늘뭐먹지?</span></a>
					<ul>
						<li class="sub"><a href="#"><span>오늘뭐먹지?</span></a>
					</ul></li>
				<li class="active sub"><a href="#"><span>꿀팁</span></a>
					<ul>
						<li class="sub"><a href="necessityNoticeList"><span>생활필수품</span></a>
						<li class="sub"><a href="myRoomNoticeList"><span>청소&인테리어</span></a>
						<li class="sub"><a href="honeyTipNoticeList"><span>기타 꿀팁</span></a>
					</ul></li>
				<li class="active sub"><a href="#"><span>레시피</span></a>
					<ul>
						<li class="sub"><a href="cookList"><span>레시피</span></a>
						<li class="sub"><a href="#"><span>인기메뉴</span></a>
						<li class="sub"><a href="myRecipeNoticeList"><span>나만의레시피</span></a>
					</ul></li>
				<li><a href="storeNoticeList"><span>편의점</span></a></li>
				<li class="last"><a href="anabadaNoticeList"><span>아나바다</span></a></li>
				<li class="active sub" id="login_box"><a href="#" id="login_a">
						<c:if test="${empty m_Id}">
							<span>Login</span>
						</c:if> <c:if test="${not empty m_Id}">
							<span>${m_Id}님</span>
						</c:if>
				</a>
					<ul>
						<li class="sub"><c:if test="${empty m_Id}">
								<div class="loginframe">
									<!-- 아이디/패스워드 입력 -->
									<div class="input_box">
										<input type="text" id="login_Id" name="login_Id"
											placeholder="아이디" />
									</div>
									<div class="input_box">
										<input type="password" id="login_Pwd" name="login_Pwd"
											placeholder="패스워드" />
									</div>
									<div>
										<p id="login_result"></p>
									</div>
									<!-- 로그인 버튼 -->
									<div class="login_btn">
										<input type="button" id="login_btn" value="로그인" />
									</div>
									<!-- 기타 링크 -->
									<div class="link_btn">
										<a href="memberRegView">회원가입</a> <a href="memberSearch">아이디/비밀번호
											찾기</a>
									</div>
								</div>
							</c:if> <c:if test="${not empty m_Id}">
								<div class="loginframe2">
									<!-- 아이디/패스워드 입력 -->
									<div class="input_box2">
										<div id="login">${m_Id}님환영합니다.</div>
									</div>
									<!-- 기타 링크 -->
									<div class="link_btn">
										<a href="logoutProcess">로그아웃</a> <a href="memberInfo">내정보</a>
									</div>
								</div>
							</c:if>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		</form>
		<form id="noticeInput" action="myRoomNoticeUpdateProcess" method="post">
			<div id="content">
         		<div id="place1">
            		<img src="resources/images/place9_9.png" />
         		</div>
         		<div id="top">
					<div id="title">
						<br /><br />
						<input type="hidden" id="n_Seq" name="n_Seq" value="${myRoom.n_Seq}" />
						제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목 <input type="text" id="n_Title" name="n_Title" value="${myRoom.n_Title}"/><br /><br />
						작&nbsp;&nbsp;&nbsp;&nbsp;성&nbsp;&nbsp;&nbsp;&nbsp;자 <input type="text" id="n_Writer" name="n_Writer" value="${myRoom.n_Writer}" readonly="readonly"/><br /><br />
						<br /><br />
					</div>
					<div id="bottom">
						<textarea rows="20" cols="50" id="n_Content" name="n_Content">${myRoom.n_Content}</textarea>
					</div>
					<div>
						<input type="button" id="write" value="글쓰기" />
					</div>
				</div>
				<div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
			</div>
		</form>
	</div>
</body>
</html>
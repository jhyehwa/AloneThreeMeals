<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료</title>
<link rel="stylesheet" href="resources/style/RegComplete.css" type="text/css">
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script>

$(function(){
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
										<input type="text" id="login_Id" name="login_Id" placeholder="아이디" />
									</div>
									<div class="input_box">
										<input type="password" id="login_Pwd" name="login_Pwd" placeholder="패스워드" />
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
										<a href="memberRegView">회원가입</a> <a href="memberSearch">아이디/비밀번호 찾기</a>
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
		<form action="/threemeals/" method="post">
			<div class="complete1">
			
				<h1>자취왕 되세요!</h1>
				<div class="complete2">
					<p>${joinName }님 회원가입을 축하드려요.</p>
					<p>자취왕에 한발짝 다가오셨군요!</p>
					<p>나혼자삼끼의 아이디는 ${joinId } 입니다.</p>
					<input type="submit" id="home" value="Home"/>
				</div>
				<div id="footer">
					copyright ⓒ 2015 by 나 혼자 3끼 
				</div>
			</div>
		</form>
	</div>
</body>
</html>
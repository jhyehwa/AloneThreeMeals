<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset= "UTF-8">
<title>나 혼자 3끼</title>
</head>
<link rel="stylesheet" href="resources/style/Main.css" type="text/css">
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script>
	$(function() {
		$('#login_result').hide();
		$('#login_Id, #login_Pwd').keydown(function(key) {
			if (key.keyCode == 13) {
				login();
			}
		});
		$('#login_btn').click(function() {
			login();
		});
		function login() {
			var m_Id = $("#login_Id").val();
			var m_Pwd = $("#login_Pwd").val();

			var param = "m_Id=" + m_Id + "&m_Pwd=" + m_Pwd;

			$.ajax({
				type : "post",
				url : "loginCheck",
				data : param,
				success : function(data) {
					if (data == "success") {
						$('#login_result').hide();
						/* $("#loginAtion").submit(); */
						location.reload();
					} else {
						$("#login_result").html(data).css("color", "red");
						$('#login_result').show();
					}
				}
			});
		}
		$('#login_a').click(function() {
			$('#login_box ul').toggle();
		});
		
		$('.two_1').click(function(){
			if($('#idck').val()==""){
				alert('로그인 후 이용이 가능합니다');
				return false;
			}
		})
	});
</script>
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
				<li class="active sub"><a href="todayChoice"><span>오늘뭐먹지?</span></a>
					<ul>
						<li class="sub"><a href=""><span>오늘뭐먹지?</span></a>
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
		</form>
			<div id="content">
				<ul class="place">
					<li class="one"><img src="resources/images/place1.png" /></li>	
					<li class="two"><img src="resources/images/place2.png" /></li>
					<li class="three"><img src="resources/images/place3.png" /></li>
					<li class="four"><img src="resources/images/place4.png" /></li>				
					<li class="five"><img src="resources/images/chef.png" /></li>
				</ul>
				<input type="hidden" id="idck" value="${m_Id}" />
				<ul class="button">
					<li class="one_1">
					</li>
					<li class="two_1">
						<a href="refrigeratorRegView"><input type="button" id="reg" value="냉장고 등록" /></a><br /><br />
						<a href="myRefrigeratorView?m_Id=${m_Id}"><input type="button" id="reg" value="냉장고 보기" /></a>
					</li>
					<li class="three_1">
						<a href="todayChoice"><input type="button" id="today" value="오늘 뭐 먹지?" /></a>
					</li>
					<li class="four_1">
						<a href="bestMenuRegView"><input type="button" id="star" value="인기 메뉴" /></a>
					</li>
				</ul>				
			</div>
			<div id="footer">
				copyright ⓒ 2015 by 나 혼자 3끼 
			</div>
		</div>
	
</body>
</html>
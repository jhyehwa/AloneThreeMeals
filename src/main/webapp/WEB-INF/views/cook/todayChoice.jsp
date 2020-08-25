<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script src="resources/script/popupwindow.js"></script>
<script src="resources/jquery.bxslider/jquery.bxslider.min.js"></script>
<link href="resources/style/TodayChoice.css" rel="stylesheet" />
<script>
$(function(){
	var slider1 = $('.bx-wrapper1').bxSlider({
		auto : true,
		autoContols: true,
		speed : 1,
		pause : 200
	});
	
	$('#go1').click(function(){
		slider1.startAuto();
	});
	
	$('#stop1').click(function(){
		slider1.stopAuto();
	}); 
	
	$('.recipeImg').on('click',function(){
    	var c_Id=$(this).prev().val();
        var popUrl = "cookDetail?c_Id="+c_Id; //팝업창에 출력될 페이지 URL
        var popOption = "width=600, height=600, location=no";    //팝업창 옵션(optoin)
        $.popupWindow(popUrl,{});
    });
	
	var slider2 = $('.bx-wrapper2').bxSlider({
		auto : true,
		autoContols: true,
		speed : 1,
		pause : 200
	});
	
	$('#go2').click(function(){
		slider2.startAuto();
	});
	
	$('#stop2').click(function(){
		slider2.stopAuto();
	}); 
	
	
	
});
</script>
<style>
	div.wrapper_cook{
		float:left;
	}
</style>
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
		
		<h2 class="one">오늘 뭐해 먹지?</h2>
		<div class="wrapper_cook">
			<ul class="bx-wrapper1">
				<c:forEach var="i" begin="0" end="${cooklist.size()-1}">
				<li>
					<input type="hidden" value="${cooklist[i].c_Id}"/>
					<img class="recipeImg" src="upload/${cooklist[i].c_Picture}" width="230px" height="230px"/>
				</li>
				</c:forEach>
			</ul>
			<input type="button" id="go1" value="go" />
			<input type="button" id="stop1" value="stop" />
		</div>
		
		<h2 class="two">오늘 뭐 먹지?</h2>
		<div class="wrapper_delivery">
			<ul class="bx-wrapper2">
				<li><img src="resources/images/delivery/도시락.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;도시락은 어떠세요?</li>
				<li><img src="resources/images/delivery/돈까스.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;돈까스은 어떠세요?</li>
				<li><img src="resources/images/delivery/보쌈.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;보쌈은 어떠세요?</li>
				<li><img src="resources/images/delivery/분식.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;분식은 어떠세요?</li>
				<li><img src="resources/images/delivery/족발.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;족발은 어떠세요?</li>
				<li><img src="resources/images/delivery/짜장면.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;짜장면은 어떠세요?</li>
				<li><img src="resources/images/delivery/짬뽕.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;짬뽕은 어떠세요?</li>
				<li><img src="resources/images/delivery/초밥.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;초밥은 어떠세요?</li>
				<li><img src="resources/images/delivery/치즈 불닭.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;치즈 불닭은 어떠세요?</li>
				<li><img src="resources/images/delivery/햄버거.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;햄버거은 어떠세요?</li>
				<li><img src="resources/images/delivery/치킨.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;치킨은 어떠세요?</li>
				<li><img src="resources/images/delivery/피자.jpg" width="230px" height="230px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;피자는 어떠세요?</li>
			</ul>
			<input type="button" id="go2" value="go" />
			<input type="button" id="stop2" value="stop" />
		</div>
		<div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
	</form>
</div>
</body>
</html>
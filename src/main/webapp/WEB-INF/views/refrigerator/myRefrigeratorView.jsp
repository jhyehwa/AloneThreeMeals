<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/style/MyRefrigeratorView.css"
	type="text/css">
</head>
<body>
	<div class="wrapper">
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
							<li class="sub"><a href="honeyTipNoticeList"><span>기타
										꿀팁</span></a>
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
								</c:if></li>
						</ul></li>
				</ul>
			</div>
		</form>
		<form action="refrigeratorFindComplete" method="post">
			<c:forEach var="i" begin="0" end="${dataSize-1}">
				<div id="div${i}">
					<img src="resources/images/${r_Ingredient[i]}.png" />
					<input type="checkbox" name="r_Ingredient" value="${r_Ingredient[i]}" /> ${r_Ingredient[i]}
				</div>
			</c:forEach>
			<div id="left">
				<input type="image" src="resources/images/Refrigerator1.png" />
			</div>
			<div id="right">
				<input type="image" src="resources/images/Refrigerator2.png" />
			</div>
			<input type="submit" id="sub" value="검색" />
		</form>
		<div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
	</div>
</body>
</html>
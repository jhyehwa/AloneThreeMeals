<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/style/BestMenuRegView.css" type="text/css">
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script src="resources/script/popupwindow.js"></script>
<script>
$(function(){
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
	
	$('.recipeImg').on('click',function(){
        location.reload();
        var n_Seq=$(this).prev().val();
        var popUrl = "cookDetail?c_Id="+n_Seq; //팝업창에 출력될 페이지 URL
        var popOption = "width=600, height=600, location=no";    //팝업창 옵션(optoin)
        $.popupWindow(popUrl,{});
     });
	$('.myRecipeMenu').hide();
	   $('.storeMenu').hide();
	   
	   $('input[name=select]').change(function(){
	      var value = $(this).val();
	      if(value=="레시피"){
	         $('.myRecipeMenu').hide();
	         $('.storeMenu').hide();
	         $('.cookMenu').show();
	      }else if(value=="나만의 레시피"){
	         $('.myRecipeMenu').show();
	         $('.storeMenu').hide();
	         $('.cookMenu').hide();
	      }else if(value=="편의점 음식"){
	         $('.myRecipeMenu').hide();         
	         $('.cookMenu').hide();
	         $('.storeMenu').show();
	      }
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
      
      <div id="info_radio">
            <label>
            	<input type="radio" class="radio" id="cook" name="select" value="레시피" checked="checked"><span>레시피 TOP3</span></label>
            <label>
            	<input type="radio" class="radio" id="myRecipe" name="select" value="나만의 레시피"><span>나만의 레시피 TOP3</span></label>
      		<label>
            	<input type="radio" class="radio" id="store" name="select" value="편의점 음식"><span>편의점 음식 TOP3</span></label>
      </div>
      <div class="cookMenu">
      	<c:forEach var="i" begin="0" end="2">
      		<ul>      			
      			<li class="c_Picture"><input type="hidden" value="${cookList[i].c_Id}"/><img class="recipeImg" src="upload/${cookList[i].c_Picture}" width="230px" height="230px"/></li>
                <li class="c_Name"><input type="hidden" value="${cookList[i].c_Id}"/><a class="recipeImg">${cookList[i].c_Name}</a></li>
                <li class="c_Like">${cookList[i].c_Like}</li>		
      		</ul> 
      	</c:forEach> 
      </div>
      
      <div class="myRecipeMenu">
      	<c:forEach var="i" begin="0" end="2">
      		<ul>      			
      			<li class="n_Picture"><a href="myRecipeNoticeDetail?n_Seq=${myRecipeList[i].n_Seq}"><img src="upload/${myRecipeList[i].n_Picture}" width="230px" height="230px"/></a></li>
                <li class="n_Title"><a href="myRecipeNoticeDetail?n_Seq=${myRecipeList[i].n_Seq}">${myRecipeList[i].n_Title}</a></li>
                <li class="n_Grok">${myRecipeList[i].n_Grok}</li>		
      		</ul> 
      	</c:forEach>
      </div>
      
      <div class="storeMenu">
      	<c:forEach var="i" begin="0" end="2">
      		<ul>
      			<li class="n_Picture"><a href="storeNoticeDetail?n_Seq=${storeList[i].n_Seq}"><img src="upload/${storeList[i].n_Picture}" width="230px" height="230px"/></a></li>
                <li class="n_Title"><a href="storeNoticeDetail?n_Seq=${storeList[i].n_Seq}">${storeList[i].n_Title}</a></li>	
                <li class="n_Grok">${storeList[i].n_Grok}</li>	
      		</ul> 
      	</c:forEach>
      </div>
		<div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
	</div>
</body>
</html>
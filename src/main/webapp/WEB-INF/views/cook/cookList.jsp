<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko" class="album">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script src="resources/script/popupwindow.js"></script>
<link rel="stylesheet" href="resources/style/CookList.css"
   type="text/css">
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
      
      $('.page').click(function(){
         var find=$('#find').val();
         var qtext=$('#qtext').val();
         var currPage=$(this).prev().val();
         $(this).attr('href','cookList?find='+find+'&qtext='+qtext+'&currPage='+currPage);
         
      });
      
    $('.recipeImg').on('click',function(){
         location.reload();
         var n_Seq=$(this).prev().val();
         var popUrl = "cookDetail?c_Id="+n_Seq; //팝업창에 출력될 페이지 URL
         var popOption = "width=600, height=600, location=no";    //팝업창 옵션(optoin)
         $.popupWindow(popUrl,{});
      });
      
    $("#regnotice").click(function() {
        $('#regProcess').submit();
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
      
      <form action="cookList" method="get">
      <div class="place">
      	<img src="resources/images/5.png">
      </div>
      <div class="content">
         <select id="find" name="find">
            <option value="c_Name" ${find=="c_Name" ? "selected":" "}>제목</option>
            <option value="main_Ingredient" ${find=="main_Ingredient" ? "selected":" "}>작성자</option>
         </select>
         	<span class="text"><input type="text" id="qtext" name="qtext" value="${qtext}" /></span>
        	<span class="btn"><input type="submit" value="검색" /></span>
      </div>
      </form>
      <div class="notice">
        <c:if test="${cooklist.size() > 0}">
            <c:forEach var="i" begin="0" end="${cooklist.size()-1}">
               <ul>
                  <%-- <li class="n_Seq" id="n_seq${i}">${storelist[i].n_Seq}</li> --%>
                  <%-- <li><input type="hidden" id="n_seq${i}" value="${storelist[i].n_Seq}"/></li> --%>
                  <li class="n_Picture"><input type="hidden" value="${cooklist[i].c_Id}"/><img class="recipeImg" src="upload/${cooklist[i].c_Picture}" width="230px" height="230px"/></li>
                  <li class="n_Title"><input type="hidden" value="${cooklist[i].c_Id}"/><a class="recipeImg">${cooklist[i].c_Name}</a></li>
                  <%-- <li class="n_Writer">${storelist[i].n_Writer}</li>
                  <li class="n_Regdate">${storelist[i].n_RegDate}</li> --%>
                  <li class="n_Hit">${cooklist[i].c_Like}</li>
               </ul>
            </c:forEach>
         </c:if>
     </div>
     <c:if test="${m_Id eq 'admin'}">
      <form id="regProcess" action="cookRegView" method="post">
     <div id="reg">
       	<input type="button" id="regnotice" value="글쓰기" />
     </div>
     </form>
     </c:if>
     <div id="paging">
         <c:forEach var="i" begin="1" end="${totalPages}">
            <input type="hidden" class="currPage" name="currPage" value="${i}" />
            <a class="page" href = "#">${i}</a>
      </c:forEach>
     </div>
		<div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
   </div>
</body>
</html>
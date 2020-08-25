<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/script/jquery-1.11.3.min.js"></script>
<link rel="stylesheet" href="resources/style/CookUpdateComplete.css" type="text/css">
<script>
   $(function() {
      $('#update').click(function() {
         var c_Id = $('#c_Id').val();
         opener.document.location.href = 'cookUpdateView?c_Id='+c_Id;
         window.close();
      })

   });
</script>
<body>
   <div id="wrapper">
      <div id="header">
         <input type="image" src="resources/images/logo.png" disabled="disabled">
      </div>
      <form id="cookUpdate" action="cookUpdateView" method="get">
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
                     <li class="sub"><a href="honeyTipNoticeList"><span>기타꿀팁</span></a>
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
         <div id="top">
            <input type="hidden" id="m_Id" name="m_Id" value="${m_Id}" /> 
            <input type="hidden" id="c_Id" name="c_Id" value="${cook.c_Id}" />
            <ul>
               <li class="name"><span>요&nbsp;리&nbsp;명&nbsp;&nbsp;</span>${cook.c_Name}</li>
               <li class="ingredient"><span>주&nbsp;재&nbsp;료&nbsp;&nbsp;</span>${cook.main_Ingredient}</li>
               <li class="like"><span>추&nbsp;천&nbsp;수&nbsp;&nbsp;</span>${cook.c_Like}</li>
               <li class="picture"><img src="upload/${cook.c_Picture}" width="125px" height="125px"/></li>
               <li class="recipe"><span>${cook.c_Recipe}</span></li>
            </ul>
            <div>
               <input type="button" id="update" value="수정" />
            </div>
         </div>
      </form>
      <div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
   </div>
</body>
</html>
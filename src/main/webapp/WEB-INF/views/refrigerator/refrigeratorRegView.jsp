<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/style/RefrigeratorRegView.css" type="text/css">
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script>
   $(function(){
      $('#add').click(function(){
    	 var length = $('input:checkbox:checked').length;
    	 if(length == 0){
    		 alert("재료는 최소 1개 이상 선택하셔야 합니다.");
    		 return; 
    	 }
    	 if(length>=16){
    		 alert("재료는 최대 15개까지 선택할 수 있습니다.");
    		 return;
    	 }
         $('#refrigeratorInput').submit();
      });
      
   })
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
      <form id="refrigeratorInput" action="refrigeratorRegProcess" method="POST">
      <div class="table">
         <h1>&nbsp;당신의 냉장고에 넣을 재료를 선택하세요!</h1>
         <div class="recipe">
            <div class="vegetable">
               <p class="vegetable">채소</p>
               <span class="potato"><img src="resources/images/감자.png">
               <input type="checkbox" name="r_Ingredient" id="potato" value="감자" /> 감자</span>
   
            <span class="pepper"><img src="resources/images/고추.png">
               <input type="checkbox" name="r_Ingredient" id="pepper" value="고추" /> 고추</span>
   
            <span class="wasabi"><img src="resources/images/냉이.png">
               <input type="checkbox" name="r_Ingredient" id="wasabi" value="냉이" /> 냉이</span>
   
            <span class="spinach"><img src="resources/images/시금치.png">
               <input type="checkbox" name="r_Ingredient" id="spinach" value="시금치" /> 시금치</span>
               
               <span class="sweet"><img src="resources/images/고구마.png">
               <input type="checkbox" name="r_Ingredient" id="sweet" value="고구마" /> 고구마</span>
            
               <span class="garlic"><img src="resources/images/마늘.png">
               <input type="checkbox" name="r_Ingredient" id="garlic" value="마늘" /> 마늘</span>
            
               <span class="onion"><img src="resources/images/양파.png">
               <input type="checkbox" name="r_Ingredient" id="onion" value="양파" /> 양파</span>
         
               <span class="shallots"><img src="resources/images/쪽파.png">
               <input type="checkbox" name="r_Ingredient" id="shallots" value="쪽파" /> 쪽파</span>
        
              <span class="chives"><img src="resources/images/부추.png">
               <input type="checkbox" name="r_Ingredient" id="chives" value="부추" /> 부추</span>
   
               <span class="lotus"><img src="resources/images/연근.png">
               <input type="checkbox" name="r_Ingredient" id="lotus" value="연근" /> 연근</span>
   
               <span class="broccoli"><img src="resources/images/브로콜리.png">
               <input type="checkbox" name="r_Ingredient" id="broccoli" value="브로콜리" /> 브로콜리</span>
   
               <span class="cabbage"><img src="resources/images/양배추.png">
               <input type="checkbox" name="r_Ingredient" id="cabbage" value="양배추" /> 양배추</span>
   
             <span class="bean"><img src="resources/images/콩나물.png">
               <input type="checkbox" name="r_Ingredient"  id="bean" value="콩나물" /> 콩나물</span> 
            </div>
   
         <div class="meat">
            <p class="meat">육류</p>
            <span class="meat">
               <img src="resources/images/소.png" /></span>
            <ul class="check1">
               <li><input type="checkbox" name="r_Ingredient" id="bulgogi" value="쇠고기 불고기용"> 쇠고기 불고기용</li>
            <li><input type="checkbox" name="r_Ingredient" id="soegalbi" value="쇠갈비"> 쇠갈비</li>
            <li><input type="checkbox" name="r_Ingredient" id="jangjorim" value="장조림 고기" /> 장조림 고기</li>
            </ul>
            <span class="pig">
               <img src="resources/images/돼지.png" /></span>
            <ul class="check2">
               <li><input type="checkbox" name="r_Ingredient" id="pig1" value="돼지고기앞다리살"> 돼지고기앞다리살</li>
            <li><input type="checkbox" name="r_Ingredient" id="pig2" value="돼지고기목등심"> 돼지고기목등심</li>
            <li><input type="checkbox" name="r_Ingredient" id="pig3" value="다진돼지고기" /> 다진돼지고기</li>
            <li><input type="checkbox" name="r_Ingredient" id="pig4" value="대패삼겹살" /> 대패삼겹살</li>
            <li><input type="checkbox" name="r_Ingredient" id="pig5" value="장조림고기" /> 장조림고기</li>
            <li><input type="checkbox" name="r_Ingredient" id="pig6" value="차돌박이" /> 차돌박이</li>
            <li><input type="checkbox" name="r_Ingredient" id="pig7" value="베이컨" /> 베이컨</li>
            <li><input type="checkbox" name="r_Ingredient" id="pig8" value="삼겹살" /> 삼겹살</li>
            </ul>
            <span class="chicken">
               <img src="resources/images/닭.png" /></span>
            <ul class="check3">
               <li><input type="checkbox" name="r_Ingredient" id="chicken1" value="닭다리살"> 닭다리살</li>
            <li><input type="checkbox" name="r_Ingredient" id="chicken2" value="닭안심"> 닭안심</li>
            <li><input type="checkbox" name="r_Ingredient" id="chicken3" value="닭가슴살" /> 닭가슴살</li>
            </ul>
            <span class="don">
               <img src="resources/images/돈가스.png" /></span>
            <ul class="check4">
               <li><input type="checkbox" name="r_Ingredient" id="tonkatsu" value="돈가스"> 돈가스</li>
            </ul>
         </div>
         <div class="mushroom">
            <p class="mushroom">버섯</p>
            <span class="mushroom">
               <img src="resources/images/버섯.png"></span>
            <ul class="check5">
               <li><input type="checkbox" name="r_Ingredient" id="mushroom1" value="새송이버섯" /> 새송이버섯</li>
               <li><input type="checkbox" name="r_Ingredient" id="mushroom2" value="느타리버섯" /> 느타리버섯</li>
               <li><input type="checkbox" name="r_Ingredient" id="mushroom3" value="팽이버섯" /> 팽이버섯</li>
               <li><input type="checkbox" name="r_Ingredient" id="mushroom4" value="표고버섯" /> 표고버섯</li>
          </ul>
         </div>
         <div class="fisheries">
            <p class="fisheries">수산물</p>
               <span class="mussel"><img src="resources/images/홍합.png">
               <input type="checkbox" name="r_Ingredient" id="mussel" value="홍합"> 홍합</span>
               
               <span class="clam"><img src="resources/images/조개.png">
               <input type="checkbox" name="r_Ingredient" id="clam" value="조개"> 조개</span>
               
               <span class="squid"><img src="resources/images/오징어.png">
               <input type="checkbox" name="r_Ingredient" id="squid" value="오징어"> 오징어</span>
                  
               <span class="jjukkumi"><img src="resources/images/쭈꾸미.png">
               <input type="checkbox" name="r_Ingredient" id="jjukkumi" value="쭈꾸미"> 쭈꾸미</span>
                  
               <span class="geonsaewoo"><img src="resources/images/새우.png">
               <input type="checkbox" name="r_Ingredient" id="geonsaewoo" value="새우"> 새우</span>
       </div>
         <div class="noodle">
            <p class="noodle">면류</p>
            <span class="noodle"><img src="resources/images/면.png"></span>
            <ul class="check6">
               <li><input type="checkbox" name="r_Ingredient" id="noodle1" value="쫄면"> 쫄면</li>
               <li><input type="checkbox" name="r_Ingredient" id="noodle2" value="스파게티면"> 스파게티면</li>
               <li><input type="checkbox" name="r_Ingredient" id="noodle3" value="우동면"> 우동면</li>
               <li><input type="checkbox" name="r_Ingredient" id="noodle4" value="소면"> 소면</li>
               <li><input type="checkbox" name="r_Ingredient" id="noodle5" value="중면"> 중면</li>
               <li><input type="checkbox" name="r_Ingredient" id="noodle6" value="당면"> 당면</li>
               <li><input type="checkbox" name="r_Ingredient" id="noodle7" value="사리면"> 사리면</li>
            </ul>
         </div>
         <div class="other">
            <p class="other">기타</p>
            
            <span class="other1"><img src="resources/images/된장.png">
            <input type="checkbox" name="r_Ingredient" id="other1" value="된장"> 된장</span>
            
            <span class="other2"><img src="resources/images/만두.png">
            <input type="checkbox" name="r_Ingredient" id="other2" value="만두"> 만두</span>
            
            <span class="other3"><img src="resources/images/카레.png">
            <input type="checkbox" name="r_Ingredient" id="other3" value="카레"> 카레</span>
            
            <span class="other4"><img src="resources/images/북어포.png">
            <input type="checkbox" name="r_Ingredient" id="other4" value="북어포"> 북어포</span>
            
            <span class="other5"><img src="resources/images/사골곰탕.png">
            <input type="checkbox" name="r_Ingredient" id="other5" value="사골곰탕"> 사골곰탕</span>
            
            <span class="other6"><img src="resources/images/김치.png">
            <input type="checkbox" name="r_Ingredient" id="other6" value="김치"> 김치</span>
            
            <span class="other7"><img src="resources/images/가래떡.png">
            <input type="checkbox" name="r_Ingredient" id="other7" value="가래떡"> 가래떡</span>
            
            <span class="other8"><img src="resources/images/견과류.png">
            <input type="checkbox" name="r_Ingredient" id="other8" value="견과류"> 견과류</span>
            
            <span class="other9"><img src="resources/images/만두피.png">
            <input type="checkbox" name="r_Ingredient" id="other9" value="만두피"> 만두피</span>
         
            <span class="other10"><img src="resources/images/달걀.png">
            <input type="checkbox" name="r_Ingredient" id="other10" value="달걀"> 달걀</span>
            
            <span class="other11"><img src="resources/images/김.png">
            <input type="checkbox" name="r_Ingredient" id="other11" value="김"> 김</span>
            
            <span class="other12"><img src="resources/images/떡볶이떡.png">
            <input type="checkbox" name="r_Ingredient" id="other12" value="떡볶이떡"> 떡볶이떡</span>
            
            <span class="other13"><img src="resources/images/비엔나.png">
            <input type="checkbox" name="r_Ingredient" id="other13" value="비엔나"> 비엔나</span>
            
            <span class="bread"><img src="resources/images/식빵.png">
            <input type="checkbox" name="r_Ingredient" id="other14" value="식빵"> 식빵<br />
            <input type="checkbox" name="r_Ingredient" id="other15" value="잡곡빵"> 잡곡빵</span>
   
           <span class="cheese"><img src="resources/images/치즈.png">
            <input type="checkbox" name="r_Ingredient" id="other16" value="슈레드치즈">슈레드치즈<br />
            <input type="checkbox" name="r_Ingredient" id="other17" value="모차렐라치즈">모차렐라<br />치즈<br />
            <input type="checkbox" name="r_Ingredient" id="other18" value="체다치즈">체다치즈</span>
       
           <span class="pt"><img src="resources/images/통조림.png">
           <input type="checkbox" name="r_Ingredient" id="other19" value="고추참치통조림"> 고추참치<br />통조림<br />
           <input type="checkbox" name="r_Ingredient" id="other20" value="옥수수통조림"> 옥수수<br />통조림<br />
           <input type="checkbox" name="r_Ingredient" id="other21" value="참치통조림"> 참치<br />통조림</span>
         </div>
         <div class="btn">
            <input type="button" id="add" value="등록하기">
         </div>  
         </div>
         <div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
         </div>
     </form>
   </div>
</body>
</html>
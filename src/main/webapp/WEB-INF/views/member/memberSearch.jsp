<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나혼자3끼 : 아이디/비밀번호 찾기</title>
<link rel="stylesheet" href="resources/style/MemberSearch.css" type="text/css">
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script>
   $(function() {
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
	   
      $('.pwd_Information').hide();
      $('#findresult').hide();
      
      $("input[name=search_radio]").change(function() {
         var radioValue = $(this).val();
         if (radioValue == "search_pwd") {
            $('.id_Information').hide();
            $('.pwd_Information').show();
            $('#findresult').hide();
         } else if (radioValue == "search_id") {
            $('.pwd_Information').hide();
            $('.id_Information').show();
         }
      });

      
      
      $("#id_Find").click(function() {

         var m_Name = $("#M_Name").val();
         var m_BirthYear = $("#M_BirthYear").val();
         var m_BirthMonth = $("#M_BirthMonth").val();
         var m_BirthDay = $("#M_BirthDay").val();
         var m_Phone = $("#M_Phone").val();

         var m_Birth = m_BirthYear+"년"+m_BirthMonth+"월"+m_BirthDay+"일";
            
         var param = "m_Name="+m_Name+"&m_Birth="+m_Birth+"&m_Phone="+m_Phone;
         $.ajax({
            type : "post",
            url  : "idSearch",
            data : param,            
            success : function(data){
               if(data=="no"){
                  $("#idresult").html("정보와 일치하는 아이디가 존재하지 않습니다.").css("color","red");
               }else{            
                  $("#idresult").html(m_Name+"님의 아이디는 '"+data+"' 입니다.").css("color","blue");
                  
               }                     
            }
         });
         
      
      });

      
      $("#pwd_find").click(function() {
         var m_Id = $('#M_Id').val();
         var pwd_Question = $('#Pwd_Question').val();
         var pwd_Answer = $('#Pwd_Answer').val();
         var param = "m_Id="+m_Id+"&pwd_Question="+pwd_Question+"&pwd_Answer="+pwd_Answer;
         $.ajax({
            type : "post",
            url  : "pwdSearch",
            data : param,            
            success : function(data){
               if(data=="no"){
                  $("#pwdResult").html("정보와 일치하는 정보가 존재하지 않습니다.").css("color","red");
               }else{            
                  $('#Pwd_Find').submit();
               }                     
            }
         });
         
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
      <div class="search">
         <div id="search_radio">
            <label><input type="radio" name="search_radio" value="search_id" checked="checked" /><span>아이디찾기</span></label>
            <label><input type="radio" name="search_radio" value="search_pwd" /><span>비밀번호찾기</span></label>
         </div>
         <form id="Id_Find" action="idSearch" method="post">
            <div class="id_Information">
               <p id="idresult"></p>
               <p>회원정보에 등록한 생년월일과 휴대전화 번호, 이메일을 입력하세요.</p>
               <div id="m_Name">
				 이&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;름 :
                <input type="text" id="M_Name" maxlength="20"/>
               </div>
               <div id="m_BirthYear">
               	   생 년 월 일 :
               	 <input type="text" id="M_BirthYear" name="M_BirthYear" placeholder="년(4자)" maxlength="4"/> 년 
               	 <input type="text" id="M_BirthMonth" name="M_BirthMonth" placeholder="월" maxlength="2"/> 월
               	 <input type="text" id="M_BirthDay" name="M_BirthDay" placeholder="일"  maxlength="2"/>일
               </div>
               <div id="m_Phone">
              	    휴 대 전 화 :
              	  <input type="text" id="M_Phone" name="M_Phone" placeholder="연락처(-없이 입력하세요)" />
               </div>
               <div id="id_find">
                  <input type="button" id="id_Find" value="찾기" />
               </div>
            </div>
         </form>
         <br />
         <form id="Pwd_Find" action="pwdSearchMailForm" method="post">
            <div class="pwd_Information">
               <input type="hidden" id="pwdSearchCk" value="${pwdSearchCk}" />
               <p id="pwdResult"></p>
               <p>아이디와 회원정보에 등록한 질문을 선택하고 답을 입력하세요.</p>
               <div id="m_Id">
					 아&nbsp;&nbsp;&nbsp;이&nbsp;&nbsp;&nbsp;디 :
					<input type="text" id="M_Id" name="M_Id" />
               </div>
               <div id="pwd_Question">
                  	비밀번호 찾기 질문 :
                  	<select id="Pwd_Question" name="Pwd_Question">
	                     <option value="비밀번호 찾기 질문을 선택하세요.">비밀번호 찾기 질문을 선택하세요.</option>
	                     <option value="나의 생일은?">나의 생일은?</option>
	                     <option value="기억하고 싶은 기념일은?">기억하고 싶은 기념일은?</option>
	                     <option value="어머니의 성함은?">어머니의 성함은?</option>
	                     <option value="아버지의 성함은?">아버지의 성함은?</option>
	                     <option value="추억하고 싶은 장소는?">추억하고 싶은 장소는?</option>
	                     <option value="내 보물 1호는?">내 보물 1호는?</option>
	                </select>
               </div>
               <div id="pwd_Answer">
                	  비밀번호 찾기 답 :
                	  <input type="text" id="Pwd_Answer" name="Pwd_Answer" />
               </div>
               <div id="pwd_find">
                  <input type="button" id="pwd_find" value="찾기" />
               </div>
            </div>
         </form>
      </div>
      <div id="footer">
		copyright ⓒ 2015 by 나 혼자 3끼 
	</div>
   </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정하기</title>
</head>
<link rel="stylesheet" href="resources/style/MemberInfoUpdate.css" type="text/css">
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
					location.reload();;
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
   

   /*3. 비밀번호 형식 */
   var pwdType = 0;   
   $("#m_Pwd").keyup(function () {
      var pw = $("#m_Pwd").val();
      var num = pw.search(/[0-9]/g);
      var eng = pw.search(/[a-z]/ig);
      var spe = pw.search(/[`~!@@#$%^&*|\\\'\";:\/?]/gi);
      var spa = checkSpace(pw);
      
      
      if (spa) {
         $("#pwd_result").html("비밀번호는 공백없이 입력해주세요.").css("color","red");
         pwdType = 0;
         return false;
      }
      if(pw.length==0){
         $("#pwd_result").html("필수 입력 정보입니다.").css("color","red");
         pwdType = 0;
         return false;
      }
      if (pw.length < 6 || pw.length > 12) {
         $("#pwd_result").html("6자리 ~ 12자리로 입력해주세요.").css("color","red");
         pwdType = 0;
         return false;
      }
      if (num < 0 || eng < 0 || spe < 0) {
         $("#pwd_result").html("영문, 숫자, 특수문자를 혼합해주세요.").css("color","red");
         pwdType = 0;
         return false;
      }else{
         $("#pwd_result").html("사용 가능한 비밀번호입니다.").css("color","blue");
         pwdType=1;
      }
      
      return true;
   });

   
   /*4. 비밀번호 재확인 */
   var pwdCheck = 0;
   $('#m_PwdCk').keyup(function(){
         var m_Pwd = $('#m_Pwd').val();
         var m_PwdCk = $('#m_PwdCk').val();
         if(m_Pwd != m_PwdCk){
            $('#pwdCk_result').html("입력하신 비밀번호와 같지 않습니다.").css("color","red");
            pwdCheck = 0;
            return;
         }else{
            $('#pwdCk_result').html("입력하신 비밀번호와 일치합니다.").css("color","blue");
            pwdCheck=1;  
         }
   });
   /*7. 비밀번호 찾기 답변*/
   var pwdACheck = 0;
   $('#pwd_Answer').keyup(function(){
         var pwd_Answer = $('#pwd_Answer').val();
         
         if(pwd_Answer.length==0){
             $("#pwdA_result").html("필수 입력 정보입니다.").css("color","red");
             pwdACheck = 0;
             return false;         
         }else{
            $("#pwdA_result").html("");
            pwdACheck = 1;  
         }
   });

   /*8. 연락처 유효성 검사 */
   var phoneCheck = 0;
   $('#m_Phone').keyup(function(){
      var m_Phone = $('#m_Phone').val();
       var spa = checkSpace(m_Phone);
        
       if(spa) {
          $("#phone_result").html("연락처는 공백없이 입력해주세요.").css("color","red");
          phoneCheck = 0;
           return false;
       }
       if(m_Phone.length == 0){
           $("#phone_result").html("필수 입력 정보 입니다.").css("color","red");
           phoneCheck = 0;
           return false;
       }
         
       if (isNaN(m_Phone)){
           $('#phone_result').html("연락처는 숫자만 입력하세요.").css("color","red"); 
           phoneCheck = 0;
           return false;
       }
       if (m_Phone.length < 9 || m_Phone.length > 11) {
           $("#phone_result").html("9자리 ~ 11자리로 입력해주세요.").css("color","red");
           phoneCheck = 0;
           return false;
       }else{
           $('#phone_result').html("올바른 전화번호 입력입니다").css("color","blue");
           phoneCheck = 1;
       }
   });
      
      
      /*9. email 유효성 검사 */
      var emailCheck=0;
      $('#email').keyup(function(){
         var email = $('#email').val();
         var format  = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
         var spa = checkSpace(email);
         
         if (spa) {
              $("#email_result").html("이메일은 공백없이 입력해주세요.").css("color","red");
              emailCheck=0;   
              return false;
         }
         if(email.length == 0){
            $("#email_result").html("필수 입력 정보 입니다").css("color","red");
            emailCheck=0;
            return false;
         }
         if(email.search(format) == -1){
            $('#email_result').html("올바른 email 입력하세요.").css("color","red");
            emailCheck=0;
            return false;
         }else{
            $('#email_result').html("올바른 email 입력입니다.").css("color","blue");
            emailCheck=1;
         }
      });
   
   
   /* 가입시 경고창 */
   $("#memberReg").click(function() {
      var pwd_Question = $("select[name=pwd_Question]").val();   

      if(pwdType == 0){
         $("#pwd_result").html("필수 입력 정보 입니다.").css("color","red");
      }
      if(pwdCheck == 0){
         $("#pwdCk_result").html("필수 입력 정보 입니다.").css("color","red");
      }
      if(pwd_Question == "비밀번호 찾기 질문을 선택하세요."){
         $("#pwdQ_result").html("필수 입력 정보 입니다.").css("color","red");
      }
      if(pwdACheck==0){
         $("#pwdA_result").html("필수 입력 정보 입니다.").css("color","red");
      }   
      if(phoneCheck==0){
         $("#phone_result").html("필수 입력 정보 입니다.").css("color","red");
      }      
      if(emailCheck==0){
         $("#email_result").html("필수 입력 정보 입니다.").css("color","red");
         return;
      }
      
      $("#update").submit();
   });
});
//공백 체크
function checkSpace(str) {
   if (str.search(/\s/) != -1) {
      return true;
   } else {
      return false;
   }
}
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
         <form id="update" action="memberInfoUpdateProcess" method="POST">
         <div id="content">
            <div id="place1">
               <img src="resources/images/place3_3.png" />
            </div>
            <div id="join">
               <div id="join1">
                  <ul>
                     <li>
                     	<input type="text" id="m_Id" name="m_Id" maxlength="20" value="${myMember.m_Id}" disabled="disabled"/>
                     </li>
                  </ul>
                  <ul>
                     <li>
                     	<input type="password" id="m_Pwd" name="m_Pwd" maxlength="12" placeholder="비밀번호(6~12자리)" />
                        <p class="result" id="pwd_result"></p></li>
                  </ul>
                  <ul>
                     <li>
                     	<input type="password" id="m_PwdCk" name="m_PwdCk" maxlength="20" placeholder="비밀번호 재확인" />
                        <p class="result" id="pwdCk_result"></p>
                     </li>
                  </ul>
               </div>
               <div id="join2">
                  <ul>
                     <li>
                     	<input type="text" id="m_Name" name="m_Name" value="${myMember.m_Name}" disabled="disabled"/>
                        <p class="result" id="name_result"></p>
                     </li>
                  </ul>
               </div>
               <div id="join3">
                  <c:if test="${myMember.m_Gender eq '남자'}">
                  <label><input type="radio" name="m_Gender" value="남자" checked="checked" ><span>남자</span></label>
                  <label><input type="radio" name="m_Gender" value="여자" disabled="disabled" ><span>여자</span></label>
                  </c:if>
                  <c:if test="${myMember.m_Gender eq '여자'}">
                  <label><input type="radio" name="m_Gender" value="남자" disabled="disabled"><span>남자</span></label>
                  <label><input type="radio" name="m_Gender" value="여자"checked="checked" ><span>여자</span></label>
                  </c:if>   
               </div>
               <div id="join4">
                  <ul>
                     <li>
                     	<input type="text" id="m_Birth" name="m_Birth" maxlength="4" value="${myMember.m_Birth}" disabled="disabled"/>
                     </li>
                  </ul>
               </div>
               <div id="join5">
                  <ul>
                     <li><select name="pwd_Question">
                           <option value="비밀번호 찾기 질문을 선택하세요.">비밀번호 찾기 질문을 선택하세요.</option>
                           <option value="나의 생일은?">나의 생일은?</option>
                           <option value="기억하고 싶은 기념일은?">기억하고 싶은 기념일은?</option>
                           <option value="어머니의 성함은?">어머니의 성함은?</option>
                           <option value="아버지의 성함은?">아버지의 성함은?</option>
                           <option value="추억하고 싶은 장소는?">추억하고 싶은 장소는?</option>
                           <option value="내 보물 1호는?">내 보물 1호는?</option>
                     </select>
                        <p class="result" id="pwdQ_result"></p></li>
                  </ul>
               </div>
               <div id="join6">
                  <ul>
                     <li><input type="text" id="pwd_Answer" name="pwd_Answer" placeholder="비밀번호 찾기 답변" />
                        <p class="result" id="pwdA_result"></p></li>
                  </ul>
                  <ul>
                     <li>
                     	<input type="text" id="m_Phone" name="m_Phone" placeholder="연락처(-없이 입력하세요)" />
                        <p class="result" id="phone_result"></p>
                     </li>
                  </ul>
                  <ul>
                     <li>
                     	<input type="text" id="email" name="email" placeholder="e-mail을 입력하세요" />
                        <p class="result" id="email_result">
                     </li>
                  </ul>
               </div>
               <p>
                  <input type="button" id="memberReg" value="수정할래" />
               </p>
            </div>
         </div>
         <div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
         </form>
      </div>
</body>
</html>
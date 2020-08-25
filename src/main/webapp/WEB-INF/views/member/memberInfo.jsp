<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내정보</title>
</head>
<link rel="stylesheet" href="resources/style/MemberInfo.css" type="text/css">
<script src="resources/script/jquery-1.11.3.min.js"></script>
<script>
$(function(){
	$('#delete_Information').hide();
    
    $("input[name=info_radio]").change(function() {
        var radioValue = $(this).val();
        if (radioValue == "m_Info") {
           $('#delete_Information').hide();
           $('#content').show();
        } else if (radioValue == "m_Delete") {
        	$('#delete_Information').show();
            $('#content').hide();
        }
	});
	
	
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
   
   
    $("#infoUpdate").click(function(){
        $('#info').submit();      
     });

    
    $("#delete_result").hide();
    $('#delete_btn').click(function(){
    	var delete_Pwd = $("#delete_Pwd").val();
    	var check_pwd = $("#check_pwd").val();
    	if(delete_Pwd !=check_pwd){
    		$("#delete_result").html("비밀번호가 일치하지 않습니다.").css("color","red");
    		$("#delete_result").show();
    		return;
    	}
    	
		var message = confirm("정말로 탈퇴하시겠습니까?");
		if(message == true){
			location.replace("memberOutProcess");
	   	}	  
	});
    
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
         <div id="place1">
               <img src="resources/images/place2_2.png" />
         </div>
         <div id="info_radio">
            <label>
            	<input type="radio" name="info_radio" value="m_Info" checked="checked" /><span>내정보</span></label>
            <label>
            	<input type="radio" name="info_radio" value="m_Delete" /><span>회원탈퇴</span></label>
         </div>
         <form id="info" action="memberInfoUpdateView" method="POST">
         <div id="content">         	      
            <div id="join">
               <div id="join1">
                  <ul>
                     <li>
                     	<input type="text" id="m_Id" name="m_Id" maxlength="20" value="${myMember.m_Id}" disabled="disabled"/>
                     </li>
                  </ul>
                  <ul>
                     <li>
                     	<input type="password" id="m_Pwd" name="m_Pwd" maxlength="12" value="${myMember.m_Pwd}" disabled="disabled"/>
                        <p class="result" id="pwd_result"></p>
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
                     <li>
                     	<input type="text" id="pwd_Question" name="pwd_Question" maxlength="4" value="${myMember.pwd_Question}" disabled="disabled"/>
                     </li>
                  </ul>
               </div>
               <div id="join6">
                  <ul>
                     <li>
                     	<input type="text" id="pwd_Answer" name="pwd_Answer" value="${myMember.pwd_Answer}" disabled="disabled"/>
                        <p class="result" id="pwdA_result"></p>
                    </li>
                  </ul>
                  <ul>
                     <li>
                     	<input type="text" id="m_Phone" name="m_Phone" value="${myMember.m_Phone}" disabled="disabled" />
                        <p class="result" id="phone_result"></p>
                     </li>
                  </ul>
                  <ul>
                     <li>
                     	<input type="text" id="email" name="email" value="${myMember.email}" disabled="disabled"/>
                        <p class="result" id="email_result">
                     </li>
                  </ul>
               </div>
               <p>
                  <input type="button" id="infoUpdate" value="수정할래" />
               </p>
            </div>
         </div>
         </form>
              
         <form id="delete" action="memberDelete" method="POST"> 
         <div id="delete_Information">     
            <div id="join">
               <div id="join1">               
                  <ul>
                     <li>
                     	<input type="password" id="delete_Pwd" name="delete_Pwd" placeholder="비밀번호 확인"/>
                     	<input type="hidden" id="check_pwd" value="${myMember.m_Pwd}"/>
                        <p class="result" id="delete_result"></p>
                    </li>
                  </ul>            
               </div>
               <p>
                  <input type="button" id="delete_btn" value="탈퇴할래" />
               </p>
            </div>        
         </div>
         </form>
         <div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>  
      </div>
</body>
</html>
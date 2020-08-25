<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/script/jquery-1.11.3.min.js"></script>
<link rel="stylesheet" href="resources/style/StoreNoticeDetail.css"
   type="text/css">
<script>

	$(function() {

		$('#update').click(function() {
			$('#storeUpdate').submit();
		});
		
		$('#back').click(function(){
			location.replace("storeNoticeList");
		});
		
		$('#delete').click(function(){
			var message = confirm("정말로 삭제하시겠습니까?");
			var n_Seq = $('#n_Seq').val();
			if(message == true){
				location.replace("storeNoticeDelete?n_Seq="+n_Seq);				
			}
		});
		
		$('#update_btn').hide();
		$('#cancle_btn').hide();
		$('#c_seq').hide();

		$('#commentIn').click(
				function() {
					var n_Seq = $('#n_Seq').val();
					var c_Writer = $('#userId').val();
					var c_Content = $('#contents').val();
					var c_Grok = $('input[name=grok]:checked').val();

					var param = "n_Seq=" + n_Seq + "&c_Writer=" + c_Writer
							+ "&c_Content=" + c_Content + "&c_Grok=" + c_Grok;

					if(!c_Writer){
						alert("로그인 후 이용하실 수 있습니다.");
						return;
					}
					if(!c_Content){
						alert("내용을 입력하세요.")
						return;
					}
					$.ajax({
						type : "post",
						url : "storeCommentRegProcess",
						data : param,
						success : function(data) {
							location.reload();
						}
					});
				});

		$('.commentDelete').click(function() {
			var n_Seq = $('#n_Seq').val();
			var c_Seq = $(this).prev().val();
			var param = "n_Seq=" + n_Seq + "&c_Seq=" + c_Seq;

			$.ajax({
				type : "post",
				url : "storeCommentDelete",
				data : param,
				success : function(data) {
					location.reload();
				}
			});
		});
		
		$('.commentUpdate').click(function() {
			var c_Seq = $(this).next().val();
			var c_Content = $(this).parent().siblings('.c_Content').text();
			var c_Grok = $(this).parent().siblings('.grok').text();
			
			$('#contents').html("");
			$('#contents').html(c_Content);
			if(c_Grok=="공감"){
				$('#no').prop("checked", false);
				$('#yes').prop("checked", true);
			}else if(c_Grok=="비공감"){
				$('#yes').prop("checked", false);
				$('#no').prop("checked", true);
			}

			$('#c_seq').html(c_Seq);			
			
			$('#update_btn').show();
			$('#cancle_btn').show();
			$('#commentIn').hide();
		});
		
		
		
		$('#update_btn').click(function() {
			var n_Seq = $('#n_Seq').val();
			var c_Seq = $('#c_seq').text();
			var c_Content = $('#contents').val();
			var c_Grok = $('input[name=grok]:checked').val();
			
			var param = "n_Seq=" + n_Seq + "&c_Seq=" + c_Seq + "&c_Content=" + c_Content + "&c_Grok=" + c_Grok;
			
			if(!c_Content){
				alert("내용을 입력하세요.")
				return;
			}
			$.ajax({
				type : "post",
				url : "storeCommentUpdate",
				data : param,
				success : function(data) {
					location.reload();
				}
			});
		});
		
		$('#cancle_btn').click(function(){
			$('#contents').html("");
			$('#no').prop("checked", false);
			$('#yes').prop("checked", true);
			$('#update_btn').hide();
			$('#cancle_btn').hide();
			$('#commentIn').show();
		});

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
	});
</script>
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
											<input type="text" id="login_Id" name="login_Id"
												placeholder="아이디" />
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
			<form id="storeUpdate" action="storeNoticeUpdateView" method="get">
			<div id="top">
				<input type="hidden" id="n_Seq" name="n_Seq" value="${store.n_Seq}" />
				<ul>
					<li class="title"><span>제&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;목&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>${store.n_Title}</li>
					<li class="writer"><span>작&nbsp;성&nbsp;자&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>${store.n_Writer}</li>
					<li class="day"><span>작성일자&nbsp;&nbsp;&nbsp;</span>${dateview}</li>
					<li class="hit"><span>조&nbsp;회&nbsp;수&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>${store.n_Hit}</li>
					<li class="grok"><span>공&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;감&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>${grok}</li>
					<li class="nongrok"><span>비&nbsp;공&nbsp;감&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>${nongrok}</li>
					<li class="star"><span>별&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;점&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>${store.n_Star}</li>
					<li class="img"><img src="upload/${store.n_Picture}" width="150px" height="150px"></li>
					<li class="content">${store.n_Content}</li>
				</ul>
			</div>
			<div>				
				<c:if test="${m_Id eq store.n_Writer}">
				<input type="button" id="update" value="수정" />
				<input type="button" id="delete" value="삭제" />
				</c:if>
				<input type="button" id="back" value="목록" />
			</div>
		</form>

		<form action="" method="get">
			<div class="commentList">
				<c:if test="${storeCommentList.size() > 0}">
					<c:forEach var="i" begin="0" end="${storeCommentList.size()-1}">
						<ul>
						<c:if test="${storeCommentList[i].c_Grok eq 'yes'}">
							<li class="grok yes">공감</li>
						</c:if>
						<c:if test="${storeCommentList[i].c_Grok eq 'no'}">
							<li class="grok no">비공감</li>
						</c:if>
						<li><span class="writer">ID : </span>${storeCommentList[i].c_Writer}</li>
						<li class="c_Content"><span class="content">내용 : </span>${storeCommentList[i].c_Content}</li>
						<c:if test="${dateList_ymd[i] ne today}">
							<li class="n_Regdate"><span class="date">날짜 : </span>${dateList_ymd[i]}</li>
							</c:if>
							<c:if test="${dateList_ymd[i] eq today}">
							<li class="n_Regdate"><span class="date">날짜 : </span>${dateList_hm[i]}</li>
							</c:if>
																
						<c:if test="${storeCommentList[i].c_Writer eq m_Id}">
						<li>
							<input type="button" class="commentUpdate" value="수정"/>
							<input type="hidden" class="c_Seq" value="${storeCommentList[i].c_Seq}"/>
							<img src="resources/images/x-icon.png" class="commentDelete" width="15px" height="15px">
						</li>
						</c:if>
						</ul>						
					</c:forEach>
				</c:if>
				</div>
				<div id="paging">
					<c:forEach var="i" begin="1" end="${totalPages}">
					<a class="page" href="storeNoticeDetail?n_Seq=${store.n_Seq}&currPage=${i}">${i}</a>
					</c:forEach>
				</div>
				<div class="comment">
				<input type="hidden" id="userId" value="${m_Id}"/>
				<div class="commnetWrite">
					<ul>
					<li>댓글</li>
					<li>
						<p id="c_seq"></p>
						<textarea rows="4" cols="66" id="contents"></textarea>
					</li>
					<li>
						<input type="radio" name="grok" id="yes" value="yes" checked="checked">공감 	
						<input type="radio" name="grok" id="no" value="no" >비공감
					</li>		
					<li>
						<input type="button" id="update_btn" value="수정" />
						<input type="button" id="cancle_btn" value="취소" />
						<input type="button" id="commentIn" value="입력" />
					</li>	
					</ul>
				</div>
				<div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
			</div>
		</form>
	</div>
</body>
</html>
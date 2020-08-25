<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resources/script/jquery-1.11.3.min.js"></script>
<link rel="stylesheet" href="resources/style/CookDetail.css"
	type="text/css">
<script>
   $(function() {
      $('#update').click(function() {
         var c_Id = $('#c_Id').val();
         opener.document.location.href = 'cookUpdateView?c_Id='+c_Id;
         window.close();
      })
      $('#likeBtn').click(function(){
         var m_Id = $('#m_Id').val();
         var c_Id = $('#c_Id').val();
         var param = "m_Id="+m_Id+"&c_Id="+c_Id;
         $.ajax({
            type : "post",
            url : "likeCheck",
            data : param,
            success : function(data){
               opener.document.location.reload();
               location.reload('/cookList');
            }
         });
      });
      $('#delete').click(function(){
          var message = confirm("정말로 삭제하시겠습니까?");
          var c_Id = $('#c_Id').val();
          if(message == true){
             opener.document.location.href = "cookDeleteProcess?c_Id="+c_Id;
             window.close();
          }
       });
      
   });
</script>
</head>
<body>
   <div id="wrapper">
   		<form id="cookUpdate" action="cookUpdateView" method="get">
   <div id="header">
		<img src="resources/images/logo.png">
	</div>
	<div id="top">
	    <input type="hidden" id="m_Id" name="m_Id" value="${m_Id}" />
	    <input type="hidden" id="c_Id" name="c_Id" value="${cook.c_Id}" />
	        <ul>
	          <li class="name"><span>요&nbsp;리&nbsp;명&nbsp;&nbsp;</span>${cook.c_Name}</li>
	          <li class="writer"><span>작&nbsp;성&nbsp;자&nbsp;&nbsp;</span>admin</li>
	          <li class="ingredient"><span>주&nbsp;재&nbsp;료&nbsp;&nbsp;</span>${cook.main_Ingredient}</li>
	          <li class="like"><input type="button" id="likeBtn" name="likeBtn" /><span class="one">&nbsp;추&nbsp;&nbsp;&nbsp;&nbsp;천</span><span class="two">${cook.c_Like}</span></li>
	          <li class="picture"><img src="upload/${cook.c_Picture}" width="150px" height="150px"/></li>
	          <li class="recipe"><span>${cook.c_Recipe}</span></li>
	        </ul>
		<div>		
		   <input type="button" id="update" value="수정" />
		   <input type="button" id="delete" value="삭제" />
		</div>
	</div>
		</form>   
  		<div id="footer">copyright ⓒ 2015 by 나 혼자 3끼</div>
   </div>
</body>
</html>
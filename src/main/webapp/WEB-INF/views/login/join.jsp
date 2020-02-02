<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//가입 동의버튼을 눌렀을 시 원페이지로 화면 전환
		$('#joinSubmit').click(function(){
			var str = "";
			str+="<br>";
			str+="<div>";
			str+="<div>";
			str+='<div class="col-sm-offset-4 col-sm-3">';
			str+='<input type="text" id="userId" class="form-control" placeholder="아이디" onblur="idCheck()" >';
			str+='</div>';
			str+='<div class="col-sm-1">';
			str+='<label id="idCheckRes"></label>';
			str+='</div>';
			str+='<div class="col-sm-offset-4 col-sm-3">';
			str+='<br>';
			str+='<input id="pw1" type="password" class="form-control" placeholder="비밀번호" onblur="pwCheck()" >';
			str+='</div>';
			str+='<div class="col-sm-1">';
			str+='<br>';
			str+='<label id="pwCheckRes"></label>';
			str+='</div>';
			str+='<div class="col-sm-offset-4 col-sm-3">';
			str+='<br>';
			str+='<input id="pw2" type="password" class="form-control" placeholder="비밀번호 확인" onblur="pwCheck()" >';
			str+='</div>';
			str+='<div class="col-sm-offset-4 col-sm-3">';
			str+='<br>';
			str+='<input id="userName" type="text" class="form-control" placeholder="이름" >';
			str+='</div>';
			str+='<div class="col-sm-offset-4 col-sm-3">';
			str+='<br>';
			str+='<input id="userEmail" type="email" class="form-control" placeholder="email@example.com" >';
			str+='</div>';
			str+='<div class="col-sm-12" style="text-align: center;">';
			str+='<br>';
			str+='<a id="join" class="btn btn-primary" href="#" onclick="joinUser()">가입</a> ';
			str+='<a class="btn btn-danger" href="/wcc">취소</a>';
			str+='</div>';
			str+='</div>';
			str+='</div>';
			$('#userJoin').html(str);
		});

	});

	//아이디 중복검사
	function idCheck(){
		$('#idCheckResult').remove();
		var id = $('#userId').val();
		if(id.length>3&&id.length<10){
			$.ajax({
				type:'post',
				url:'user/check',
				data:'id='+id,
				dataType:'text',
				success:function(res){
					if(res=='success'){
						$('#idCheckRes').html("<p id='idCheckResult' style='color:blue;'>사용가능</p>");
					}else{
						$('#idCheckRes').html("<p id='idCheckResult' style='color:red;'>계정중복</p>");
					}
				}
			})
		}else{
			$('#idCheckRes').html("<p id='idCheckResult' style='color:red;'>4~9글자</p>");
		}
	}

	//비밀번호 검사
	function pwCheck(){
		var pw1 = $('#pw1').val();
		var pw2 = $('#pw2').val();
		$('#pwCheckResult').remove();
		if(pw1.length>5&&pw1.length<16){			
			if(pw1==pw2){
				$('#pwCheckRes').html("<p id='pwCheckResult' style='color:blue;'>일치</p>");
			}else{
				$('#pwCheckRes').html("<p id='pwCheckResult' style='color:red;'>불일치</p>");
			}
		}else{
			$('#pwCheckRes').html("<p id='pwCheckResult' style='color:red;'>6~15글자</p>");
		}
	}

	//회원가입
	function joinUser(){
		var data = {};
		data['id'] = $('#userId').val(); 
		data['pw'] = $('#pw1').val(); 
		data['name'] = $('#userName').val(); 
		data['email'] = $('#userEmail').val(); 
		var jsonData = JSON.stringify(data);
		var result = joinUserCheck();
		alert(result);
		$.ajax({
			type:'post',
			url:'user/join',
			dataType:'text',
			contentType:'application/json;charset=UTF-8',
			data:jsonData,
			success:function(res){
				window.location.href='/wcc/';
			}
		});
	}

	//회원가입 시 체크사항
	function joinUserCheck(){
		var id = $('#userId').val(); 
		var pw = $('#pw1').val(); 
		var name = $('#userName').val(); 
		var email = $('#userEmail').val(); 
		var result = false;

		//아이디 체크
		if($('#idCheckResult').text()=='사용가능'){
			//비밀번호 체크
			if($('#pwCheckResult').text()=='일치'){
				result = true;
				
			}else{
				result = false;
			}
		}else{
			result = false;
		}
		
		
		
		return result;
	}
	
</script>
</head>
<body>
	<%@ include file="../template/header.jspf" %>
	
	<div id="content">
		<div class="row">
			<div class="col-sm-12">
				<div class="col-sm-offset-1 col-sm-10">
					<h2>회원가입 페이지</h2>
					<div id="userJoin">
						<div class="col-sm-offset-1 col-sm-10">
<pre style="height: 300px;">
<%@ include file="join.txt" %>
</pre>
						</div>
						<div class="row">
							<div class="col-sm-12" style="text-align: center;">
								<a id="joinSubmit" class="btn btn-default" href="#">동의</a>
								<a class="btn btn-danger" href="/wcc">취소</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@ include file="../template/footer.jspf" %>
</body>
</html>
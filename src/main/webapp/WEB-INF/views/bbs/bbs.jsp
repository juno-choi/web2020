<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.css" />
<script type="text/javascript" src="../js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.js"></script>
<script type="text/javascript">
	window.onload=function(){
		

		document.querySelector('#bbsAddBtn').addEventListener('click',function(){
			
			document.querySelector('#content').innerHTML = bbsAddBtnClick();

			document.querySelector('#bbsAdd').addEventListener('click',function(){
				bbsAdd();
			});
		});

	};
	function bbsReset(){
		window.location.reload();
	}
	function bbsAdd(){
		var ajax = new XMLHttpRequest();
		var url = 'add';
		var sub = document.querySelector('#sub').value;
		var cont = document.querySelector('#cont').value;
		var writer = "${sessionScope.user.name}"+'';
		var data = {};
		data['sub'] = sub;
		data['content'] = cont;
		data['writer'] = writer;
		
		ajax.open('POST',url,true);
		ajax.setRequestHeader('Content-type','application/json');
		ajax.onreadystatechange = function(e){
			if(ajax.readyState === 4){
				if(ajax.status === 200) {
					if(ajax.responseText=='success'){
						window.location.reload();
					}else{
						alert('글 등록에 실패했습니다.');
					}
				}else{
					alert('실패');
				}
			}
		}
		ajax.send(JSON.stringify(data));
		alert('성공');
	}

	function bbsAddBtnClick(){
		var str = '';
		str+='<div id="write" class="row">';
		str+='<div class="col-sm-12">';
		str+='<div class="form-horizontal">';
		str+='<div class="row col-sm-offset-3">';
		str+='<label class="control-label col-sm-2">제목</label>';
		str+='<div class="col-sm-4">';
		str+='<input id="sub" type="text" class="form-control" placeholder="제목을 적어주세요">';
		str+='</div>';
		str+='</div>';
		str+='<div class="row">&nbsp;</div>';
		str+='<div class="row col-sm-offset-3">';
		str+='<label class="control-label col-sm-2">내용</label>';
		str+='<div class="col-sm-4">';
		str+='<textarea id="cont" class="form-control" rows="20" cols=""></textarea>';
		str+='</div>';
		str+='</div>';
		str+='</div>';
		str+='<div class="row">&nbsp;</div>';
		str+='<div class="row">';
		str+='<div class="col-sm-12" style="text-align: center;">';
		str+='<button id="bbsAdd" class="btn btn-default">등록</button>';
		str+='<button id="bbsReset" class="btn btn-danger" onclick="bbsReset();">취소</button>';
		str+='</div>';
		str+='</div>';
		str+='</div>';
		str+='</div>';
		return str;
	}

</script>
</head>
<body>

<%@ include file="../template/header.jspf" %>
<div class="row">
	<div class="col-sm-12" id="content">
		<div class="col-sm-offset-1 col-sm-10">
			<h2>게시판</h2>
			<table class="table table-striped">
		 		<tr>
		 			<th style="width: 10%; text-align: center;">#</th>
		 			<th style="width: 50%; text-align: center;">제목</th>
		 			<th style="width: 15%; text-align: center;">글쓴이</th>
		 			<th style="width: 25%; text-align: center;">날짜</th>
		 		</tr>
		 			<c:forEach items="${bbsList}" var="bean">
		 				<tr>
		 					<td style="text-align: center;">${bean.num}</td>
		 					<td style="text-align: center;">${bean.sub}</td>
		 					<td style="text-align: center;">${bean.writer}</td>
		 					<td style="text-align: center; overflow: hidden;">${bean.date}</td>
		 				</tr>
		 			</c:forEach>
			</table>	
		</div>
		<c:choose>
			<c:when test="${empty sessionScope.user.id}">
			
			</c:when>
			<c:otherwise>
				<div class="row">
					<div class="col-sm-12" style="text-align: center;">
						<button id="bbsAddBtn" class="btn btn-default">글쓰기</button>
					</div>
				</div>	
			</c:otherwise>
		</c:choose>
	</div>
</div>


<!-- 여기서부터 -->

<!-- 여기까지 -->
<%@ include file="../template/footer.jspf" %>

</body>
</html>
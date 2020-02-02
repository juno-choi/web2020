<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<%@ include file="../template/header.jspf" %>
	<div class="row">
		<div class="col-sm-12">
			<p>로그인페이지</p>
			<p>id2 = </p> ${sessionScope.id }
			<p>pw = </p>
		</div>
	</div>
	<%@ include file="../template/footer.jspf" %>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>WCC</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		getLocation();
	});

	//지역정보를 받아 날씨정보를 가져옴 어떠케
	function getLocation() {
		  if (navigator.geolocation) { // GPS를 지원하면
		    navigator.geolocation.getCurrentPosition(function(position) {
		    	
				var ajax = new XMLHttpRequest();
				var url = 'getReg';
				var x = position.coords.longitude;
		    	var y = position.coords.latitude;
		    	var data = {};
		    	data['x']=x;
		    	data['y']=y;

				ajax.open('POST',url,true);
				ajax.setRequestHeader('Content-type','application/json');
				ajax.onreadystatechange = function(e){
					if(ajax.readyState === 4){
						if(ajax.status === 200) {
							if(ajax.responseText!=''){
								var regionInfo = ajax.responseText;
								document.querySelector('#reg').innerHTML = '<p>'+regionInfo+'</p>';
								var regionInfoList = regionInfo.split("시");
								console.log('실행됨 : '+regionInfoList[0]);
							}else{
								alert('지역정보를 가져오는데 실패했습니다..');
							}
						}else{
							alert('실패');
						}
					}
				}	    	
				ajax.send(JSON.stringify(data));


				
		    }, function(error) {
		      console.error(error);
		    }, {
		      enableHighAccuracy: false,
		      maximumAge: 0,
		      timeout: Infinity
		    });
		  } else {
		    alert('GPS를 지원하지 않아 서울 값으로 위치를 지정합니다.');
		  }
		}

	function parcelSubmit(){
		var company = document.querySelector('.parcelCompany').value;
		var num = document.querySelector('.parcel').value;
		var http = new XMLHttpRequest();
		var url = 'getParcel';
		var data = {};
		data['company'] = company;
		data['num'] = num;
		
		http.open('POST',url,true);
		http.setRequestHeader('Content-type','application/json');
		http.onreadystatechange = function(e){
			if(http.readyState === 4){
				if(http.status === 200) {
					if(http.responseText!=''){
						var parcelInfo = JSON.parse(http.responseText);
						document.querySelector('#parcelInfo').innerHTML = '<p>배송출발 : '+parcelInfo.fromTime+'</p><p>배송상태 : '+parcelInfo.state+'</p>';
					}else{
						alert('배송정보를 가져오는데 실패했습니다..');
					}
				}else{
					alert('실패');
				}
			}
		}	    	
		http.send(JSON.stringify(data));
	}

// 	function parcel(){
		
// 	}
	
		
</script>
</head>
<body>
	<%@ include file="template/header.jspf" %>
	
	<div id="content">
		<div class="row">
			<div class="col-sm-12">
				<div class="col-sm-offset-1 col-sm-10">
					<p>메인 홈</p>
					<p>날씨 정보 뿌릴거임</p>
					<p>${weather }</p>
					<p>지역 정보 뿌릴거임</p>
					<p id="reg">...</p>
				</div>
				
				<div class="col-sm-offset-1 col-sm-10">
					<p>여기는 택배 뿌릴거야</p>
					<p>택배에 대한 정보를 입력받아야함</p>
					<label>택배사</label>
					<select class="parcelCompany">
						<optgroup label="택배선택">
							<option value="kr.cjlogistics">cj배송</option>
							<option value="kr.epost">우체국</option>
							<option>쿠팡</option>
						</optgroup>
					</select>
					<label>송장번호</label><input type="text" class="parcel" />
					<button class="submitParcel" onclick="parcelSubmit()">택배조회</button>
					<p id="parcelInfo">배송정보가 표시되어집니다.</p>
					
					<!-- 예시 -->
					<a href="https://tracker.delivery/#/kr.cjlogistics/353563075655" target="_blank">배송조회</a>
				</div>
			</div>
		</div>
	</div>	
	
	<%@ include file="template/footer.jspf" %>
</body>
</html>

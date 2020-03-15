package com.project.wcc.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MainServiceImpl implements MainService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void weather(Model model) {
		logger.debug("날씨 정보를 받아옵니다...");
		
		String data="";
		String apiKey = "";
		String regId = "11B20605";
		String numOfRows = "10";
		String pageNo= "1";
		String urlStr = "http://apis.data.go.kr/1360000/VilageFcstMsgService/getLandFcst?";
		
		try{
            //파일 객체 생성
            File file = new File("D:\\junho\\JAVA\\java\\study\\WCC\\src\\main\\webapp\\txt\\weatherAppKey.txt");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                logger.debug("결과 : "+line);
                apiKey = line;
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
        }catch (FileNotFoundException e) {
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }

		
		//apiKey 들어가는 부분
		urlStr += "ServiceKey="+apiKey;
		urlStr += "&numOfRows="+numOfRows;
		urlStr += "&pageNo="+pageNo;
		//검색할 지역 ID들어갈 부분
		urlStr += "&regId="+regId;
		//반환되는 정보의 타입은 json으로
		urlStr += "&dataType=json";
		BufferedReader br =null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			String line;
			while((line = br.readLine())!=null) {
				data += line +"\n";
			}
			logger.debug("날씨 정보를 받아오는데 성공했습니다.");
		} catch (Exception e) {
			logger.debug("날씨 정보를 받아오는데 실패했습니다...");
		}
		model.addAttribute("weather",data);
	}

	@Override
	public String getRegId(Map<String, Object> map) {
		logger.debug("지역정보를 받아옵니다.");
		try {
			logger.debug("지역정보받는 중...");
			//key는 개인정보를 위해 삭제
			String key = "";
			
			try{
	            //파일 객체 생성
	            File file = new File("D:\\junho\\JAVA\\java\\study\\WCC\\src\\main\\webapp\\txt\\regAppKey.txt");
	            //입력 스트림 생성
	            FileReader filereader = new FileReader(file);
	            //입력 버퍼 생성
	            BufferedReader bufReader = new BufferedReader(filereader);
	            String line = "";
	            while((line = bufReader.readLine()) != null){
	                logger.debug("결과 : "+line);
	                key = line;
	            }
	            //.readLine()은 끝에 개행문자를 읽지 않는다.            
	            bufReader.close();
	        }catch (FileNotFoundException e) {
	            // TODO: handle exception
	        }catch(IOException e){
	            System.out.println(e);
	        }

			String x="127.0016985";
			String y="37.5942135";
			if(!(map.get("x").toString()).equals("")) {
				x=map.get("x").toString();
				y=map.get("y").toString();	
				logger.debug("지역정보받아 오기 성공 x = "+x + " y = "+y);
			}
			
			String apiUrl = "https://dapi.kakao.com/v2/local/geo/coord2address.json?"
					+"x="+x
					+"&y="+y
					+"&input_coord=WGS84";
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "KakaoAK " + key);
	
			MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	        parameters.add("x", x);
	        parameters.add("y", y);
	        parameters.add("input_coord", "WGS84");
	 
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> result = restTemplate.exchange(apiUrl ,HttpMethod.GET, new HttpEntity(headers), String.class);
	
	        //System.out.println(result.getBody());
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) jsonParser.parse(result.getBody());
	        JSONArray jsonArray = (JSONArray)jsonObject.get("documents");
	        
	    	JSONObject local = (JSONObject)jsonArray.get(0);
	    	JSONObject jsonArray1 = (JSONObject)local.get("address");
	    	String localAddress = (String)jsonArray1.get("address_name");
			
	    	logger.debug("지역정보를 성공적으로 받아왔습니다. "+localAddress);
	        return localAddress;
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("지역정보를 받아오는 것에 실패했습니다..");
			return "error";
		}
	}

	@Override
	public Map<String,Object> getParcel(Map<String, Object> map) throws Exception {
		logger.debug("배송 정보를 받아옵니다...");
		
		String data="";
		String company = map.get("company").toString();		//map에서 배송사 코드를 가져옴
		String num = map.get("num").toString().trim();		//map에서 송장번호를 가져옴
		String urlStr = "https://apis.tracker.delivery/carriers/"+company+"/tracks/"+num;	//url을 다음과 같이 요청
		Map<String, Object> rmap = new HashMap<String, Object>();
		BufferedReader br =null;
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");		//GET방식으로 요청
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));		//가져올 데이터를 UTF-8로 받아줌
			String line;
			while((line = br.readLine())!=null) {
				data += line +"\n";		//data에 가져오는 내용을 모두 넣어줌
			}

			JSONParser parser = new JSONParser();		//jsonParser를 통해 가져온 String data를 json으로 변환해줌
			JSONObject json = (JSONObject) parser.parse(data);		//JsonObject로 해당 데이터를 json 객체에 담아줌
			
			JSONObject obj = (JSONObject) json.get("from");		//json Object객체에 접근하는 방법
			String fromTime = obj.get("time").toString();
			
			obj = (JSONObject) json.get("to");
			String toTime = obj.get("time").toString();
			
			obj = (JSONObject) json.get("state");
			String state = obj.get("text").toString();
			
			rmap.put("fromTime", fromTime);
			rmap.put("toTime", toTime);
			rmap.put("state", state);
			
			logger.debug("배송 시작 : "+fromTime + "배송 도착 : "+toTime + "배송 상태 : "+state);
			
			logger.debug("배송 정보를 받아오는데 성공했습니다.");
		} catch (Exception e) {
			logger.debug("배송 정보를 받아오는데 실패했습니다...");
		}
		return rmap;
	}

}

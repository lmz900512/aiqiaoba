package com.aiqiaoba.facehealth.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class HttpXmlUtils {

	public static String post(String url, Map<String, String> params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		HttpPost post = postForm(url, params);
		body = invoke(httpclient, post);
		return body;
	}

	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		HttpGet get = new HttpGet(url);
		body = invoke(httpclient, get);
		return body;
	}

	private static String invoke(CloseableHttpClient httpclient, HttpUriRequest httpost) {
		HttpResponse response = sendRequest(httpclient, httpost);
		String body = paseResponse(response);
		return body;
	}

	private static String paseResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String body = null;
		try {
			body = EntityUtils.toString(entity);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	private static HttpResponse sendRequest(CloseableHttpClient httpclient, HttpUriRequest httpost) {
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private static HttpPost postForm(String url, Map<String, String> params) {

		HttpPost httpost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		Set<String> keySet = params.keySet();
		for (String key : keySet) {
			nvps.add(new BasicNameValuePair(key, params.get(key)));
		}

		try {
			httpost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return httpost;
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		// 鑾峰彇access_token
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> params = new HashMap<String, String>();
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
		url += "&appid=wxb2f02d0f34a7e450";
		url += "&secret=83b599f69e1953babe576abc4db4343b";
		String xml = HttpXmlUtils.get(url);
		System.out.println(xml);

		Map<String, Object> jsonMap = mapper.readValue(xml, new TypeReference<Map<String, Object>>() {
		});
		Map<String, String> map = new HashMap<String, String>();
		Iterator<String> it = jsonMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String u = jsonMap.get(key).toString();
			map.put(key, u);
		}
		String access_token = map.get("access_token");
		System.out.println("access_token=" + access_token);

		// 鑾峰彇ticket
		params.put("access_token", access_token);
		
		xml = HttpXmlUtils.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + access_token);
		jsonMap = mapper.readValue(xml, new TypeReference<Map<String, Object>>() {
		});
		System.out.println(xml);
		map = new HashMap<String, String>();
		it = jsonMap.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			String u = jsonMap.get(key).toString();
			map.put(key, u);
		}
		String jsapi_ticket = map.get("ticket");
		System.out.println("jsapi_ticket=" + jsapi_ticket);

		// 鑾峰彇绛惧悕signature
		String noncestr = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		url = "http://mp.weixin.qq.com";
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ url;
		// sha1鍔犲瘑
		String signature = SHA1(str);
		System.out.println("noncestr=" + noncestr);
		System.out.println("timestamp=" + timestamp);
		System.out.println("signature=" + signature);
		// 鏈�粓鑾峰緱璋冪敤寰俊js鎺ュ彛楠岃瘉闇�鐨勪笁涓弬鏁皀oncestr銆乼imestamp銆乻ignature
	}

	/**
	 * @author锛氱綏鍥借緣
	 * @date锛�2015骞�2鏈�7鏃�涓婂崍9:24:43
	 * @description锛�SHA銆丼HA1鍔犲瘑 @parameter锛� str锛氬緟鍔犲瘑瀛楃涓� @return锛� 鍔犲瘑涓�
	 **/
	public static String SHA1(String str) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1"); // 濡傛灉鏄疭HA鍔犲瘑鍙渶瑕佸皢"SHA-1"鏀规垚"SHA"鍗冲彲
			digest.update(str.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexStr = new StringBuffer();
			// 瀛楄妭鏁扮粍杞崲涓�鍗佸叚杩涘埗 鏁�
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexStr.append(0);
				}
				hexStr.append(shaHex);
			}
			return hexStr.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}

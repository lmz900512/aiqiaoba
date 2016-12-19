package com.aiqiaoba.facehealth.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aiqiaoba.facehealth.utils.HttpXmlUtils;

@Controller
public class WeixinController {

	@RequestMapping("/weixin")
	public ModelAndView getIndex(HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException {

		ModelAndView mav = new ModelAndView("index");
		// 获取access_token
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
		url += "&appid=wxb2f02d0f34a7e450";
		url += "&secret=83b599f69e1953babe576abc4db4343b";
		String xml = HttpXmlUtils.get(url);
		System.out.println(xml);
		ObjectMapper mapper = new ObjectMapper();
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

		// 获取ticket
		xml = HttpXmlUtils
				.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + access_token);
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

		// 获取签名signature
		String noncestr = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		// 获取请求url
		String path = request.getContextPath();
		// 以为我配置的菜单是http://yo.bbdfun.com/first_maven_project/，最后是有"/"的，所以url也加上了"/"
		url = request.getScheme() + "://" + request.getServerName() +":"+request.getServerPort() + path + "/weixin";
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ url;
		// sha1加密
		String signature = HttpXmlUtils.SHA1(str);
		mav.addObject("signature", signature);
		mav.addObject("timestamp", timestamp);
		mav.addObject("noncestr", noncestr);
		mav.addObject("appId", "wxb2f02d0f34a7e450");
		System.out.println("jsapi_ticket=" + jsapi_ticket);
		System.out.println("noncestr=" + noncestr);
		System.out.println("timestamp=" + timestamp);
		System.out.println("url=" + url);
		System.out.println("str=" + str);
		System.out.println("signature=" + signature);
		return mav;

	}
}
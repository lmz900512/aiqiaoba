package com.aiqiaoba.facehealth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aiqiaoba.facehealth.model.response.Response;
import com.aiqiaoba.facehealth.service.Face_area_service;

@Controller
@RequestMapping(value="/api/face_area")
public class FaceAreaController {
	@Autowired
	private Face_area_service face_area_service;
	@ResponseBody
	@RequestMapping(value = "/getAll", produces = "application/json;charset=UTF-8")
	public Response getAll(){
		Response response = new Response();
		response.setCode(200);
		response.setData(face_area_service.getAll());
		return response;
	}
}

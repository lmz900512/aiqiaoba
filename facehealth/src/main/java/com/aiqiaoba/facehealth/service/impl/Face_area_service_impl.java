package com.aiqiaoba.facehealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aiqiaoba.facehealth.dao.Face_area_dao;
import com.aiqiaoba.facehealth.model.po.Face_area;
import com.aiqiaoba.facehealth.service.Face_area_service;

@Service
public class Face_area_service_impl implements Face_area_service {
	
	@Autowired
	private Face_area_dao face_area_dao;
	@Override
	public List<Face_area> getAll() {
		return face_area_dao.getAll();
	}
	@Override
	public Face_area getOneById() {
		return face_area_dao.getOneById();
	}
	

}

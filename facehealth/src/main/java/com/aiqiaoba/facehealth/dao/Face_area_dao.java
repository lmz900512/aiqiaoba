package com.aiqiaoba.facehealth.dao;

import java.util.List;

import com.aiqiaoba.facehealth.model.po.Face_area;

public interface Face_area_dao {
	
	public List<Face_area> getAll();
	public Face_area  getOneById();

}

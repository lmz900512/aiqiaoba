package com.aiqiaoba.facehealth.model.po;

import java.util.Date;

public class Face_area {
	
	private String id;
	private String face_area_name;
	private Integer status;
	private String face_area_desc;
	private Date create_time;
	private Date update_time;
	private String backup1;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFace_area_name() {
		return face_area_name;
	}

	public void setFace_area_name(String face_area_name) {
		this.face_area_name = face_area_name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFace_area_desc() {
		return face_area_desc;
	}

	public void setFace_area_desc(String face_area_desc) {
		this.face_area_desc = face_area_desc;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getBackup1() {
		return backup1;
	}

	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}

}

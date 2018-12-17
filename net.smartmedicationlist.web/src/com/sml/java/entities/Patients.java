package com.sml.java.entities;

import java.util.List;

public class Patients {

	public Patients(){}
	
	public Patients(List<String> ids){
		this.iDs = ids;
	}
	
	List<String> iDs;

	public List<String> getiDs() {
		return iDs;
	}

	public void setiDs(List<String> iDs) {
		this.iDs = iDs;
	}

}

package com.sml.java.entities;

import java.util.List;

public class Team {

	public Team(){}
	
	public Team(List<String> teammates){
		this.teamMates = teammates;
	}
	
	List<String> teamMates;

	public List<String> getTeamMates() {
		return teamMates;
	}

	public void setTeamMates(List<String> teamMates) {
		this.teamMates = teamMates;
	}
	
}

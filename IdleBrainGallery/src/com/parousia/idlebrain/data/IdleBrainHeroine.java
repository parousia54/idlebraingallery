package com.parousia.idlebrain.data;

import java.util.ArrayList;

public class IdleBrainHeroine {
	
	
	int id;
	String name;
	ArrayList<IdleBrainHeroineLink> links;
	public IdleBrainHeroine() {
		super();
	}
	public IdleBrainHeroine(int id, String name, ArrayList<IdleBrainHeroineLink> links) {
		super();
		this.id = id;
		this.name = name;
		this.links = links;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<IdleBrainHeroineLink> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<IdleBrainHeroineLink> links) {
		this.links = links;
	}
	

}

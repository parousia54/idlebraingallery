package com.parousia.idlebrain.data;

public class IdleBrainHeroineLink {
	
	
	int id;
	String name;
	String url;
	public IdleBrainHeroineLink() {
		super();
	}
	public IdleBrainHeroineLink(int id, String name, String url) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}

package com.clei.entity;

public class Dog {
	private Integer id;
	private String name;
	private String color;
	public Dog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Dog(Integer id, String name, String color) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
	}
	@Override
	public String toString() {
		return "Dog [id=" + id + ", name=" + name + ", color=" + color + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}

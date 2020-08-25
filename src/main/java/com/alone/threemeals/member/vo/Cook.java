package com.alone.threemeals.member.vo;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Cook {
	private int c_Id;
	private String c_Picture;
	private CommonsMultipartFile c_File;
	private String c_Name;
	private String main_Ingredient;
	private String c_Recipe;
	private int c_Like;
	
	public Cook() {
		// TODO Auto-generated constructor stub
	}

	public Cook(int c_Id, String c_Picture, CommonsMultipartFile c_File, String c_Name, String main_Ingredient,
			String c_Recipe, int c_Like) {
		super();
		this.c_Id = c_Id;
		this.c_Picture = c_Picture;
		this.c_File = c_File;
		this.c_Name = c_Name;
		this.main_Ingredient = main_Ingredient;
		this.c_Recipe = c_Recipe;
		this.c_Like = c_Like;
	}

	public int getC_Id() {
		return c_Id;
	}

	public void setC_Id(int c_Id) {
		this.c_Id = c_Id;
	}

	public String getC_Picture() {
		return c_Picture;
	}

	public void setC_Picture(String c_Picture) {
		this.c_Picture = c_Picture;
	}

	public CommonsMultipartFile getC_File() {
		return c_File;
	}

	public void setC_File(CommonsMultipartFile c_File) {
		this.c_File = c_File;
	}

	public String getC_Name() {
		return c_Name;
	}

	public void setC_Name(String c_Name) {
		this.c_Name = c_Name;
	}

	public String getMain_Ingredient() {
		return main_Ingredient;
	}

	public void setMain_Ingredient(String main_Ingredient) {
		this.main_Ingredient = main_Ingredient;
	}

	public String getC_Recipe() {
		return c_Recipe;
	}

	public void setC_Recipe(String c_Recipe) {
		this.c_Recipe = c_Recipe;
	}

	public int getC_Like() {
		return c_Like;
	}

	public void setC_Like(int c_Like) {
		this.c_Like = c_Like;
	}

	@Override
	public String toString() {
		return "Cook [c_Id=" + c_Id + ", c_Picture=" + c_Picture + ", c_File=" + c_File + ", c_Name=" + c_Name
				+ ", main_Ingredient=" + main_Ingredient + ", c_Recipe=" + c_Recipe + ", c_Like=" + c_Like + "]";
	}


	
	
	
}

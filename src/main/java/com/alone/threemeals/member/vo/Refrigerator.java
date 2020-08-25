package com.alone.threemeals.member.vo;

public class Refrigerator {
	private String m_Id;
	private String r_Ingredient;
	public Refrigerator() {
		// TODO Auto-generated constructor stub
	}
	public Refrigerator(String m_Id, String r_Ingredient) {
		super();
		this.m_Id = m_Id;
		this.r_Ingredient = r_Ingredient;
	}
	public String getM_Id() {
		return m_Id;
	}
	public void setM_Id(String m_Id) {
		this.m_Id = m_Id;
	}
	public String getR_Ingredient() {
		return r_Ingredient;
	}
	public void setR_Ingredient(String r_Ingredient) {
		this.r_Ingredient = r_Ingredient;
	}
	@Override
	public String toString() {
		return "Refrigerator [m_Id=" + m_Id + ", r_Ingredient=" + r_Ingredient + "]";
	}
}

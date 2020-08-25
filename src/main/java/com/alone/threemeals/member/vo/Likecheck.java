package com.alone.threemeals.member.vo;

public class Likecheck {
	private String m_Id;
	private int c_Id;
	private String l_Ck;
	public Likecheck() {
		// TODO Auto-generated constructor stub
	}
	public Likecheck(String m_Id, int c_Id, String l_Ck) {
		super();
		this.m_Id = m_Id;
		this.c_Id = c_Id;
		this.l_Ck = l_Ck;
	}
	public String getM_Id() {
		return m_Id;
	}
	public void setM_Id(String m_Id) {
		this.m_Id = m_Id;
	}
	public int getC_Id() {
		return c_Id;
	}
	public void setC_Id(int c_Id) {
		this.c_Id = c_Id;
	}
	public String getL_Ck() {
		return l_Ck;
	}
	public void setL_Ck(String l_Ck) {
		this.l_Ck = l_Ck;
	}
	@Override
	public String toString() {
		return "Likecheck [m_Id=" + m_Id + ", c_Id=" + c_Id + ", l_Ck=" + l_Ck + "]";
	}

}

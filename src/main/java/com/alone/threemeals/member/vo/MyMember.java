package com.alone.threemeals.member.vo;

public class MyMember {
	private String m_Id;
	private String m_Pwd;
	private String m_Name;
	private String m_Birth;
	private String m_Phone;
	private String pwd_Question;
	private String pwd_Answer;
	private String m_Gender;
	private String email;

	public MyMember() {
		// TODO Auto-generated constructor stub
	}

	public MyMember(String m_Id, String m_Pwd, String m_Name, String m_Birth, String m_Phone, String pwd_Question,
			String pwd_Answer, String m_Gender, String email) {
		super();
		this.m_Id = m_Id;
		this.m_Pwd = m_Pwd;
		this.m_Name = m_Name;
		this.m_Birth = m_Birth;
		this.m_Phone = m_Phone;
		this.pwd_Question = pwd_Question;
		this.pwd_Answer = pwd_Answer;
		this.m_Gender = m_Gender;
		this.email = email;
	}

	public String getM_Id() {
		return m_Id;
	}

	public void setM_Id(String m_Id) {
		this.m_Id = m_Id;
	}

	public String getM_Pwd() {
		return m_Pwd;
	}

	public void setM_Pwd(String m_Pwd) {
		this.m_Pwd = m_Pwd;
	}

	public String getM_Name() {
		return m_Name;
	}

	public void setM_Name(String m_Name) {
		this.m_Name = m_Name;
	}

	public String getM_Birth() {
		return m_Birth;
	}

	public void setM_Birth(String m_Birth) {
		this.m_Birth = m_Birth;
	}

	public String getM_Phone() {
		return m_Phone;
	}

	public void setM_Phone(String m_Phone) {
		this.m_Phone = m_Phone;
	}

	public String getPwd_Question() {
		return pwd_Question;
	}

	public void setPwd_Question(String pwd_Question) {
		this.pwd_Question = pwd_Question;
	}

	public String getPwd_Answer() {
		return pwd_Answer;
	}

	public void setPwd_Answer(String pwd_Answer) {
		this.pwd_Answer = pwd_Answer;
	}

	public String getM_Gender() {
		return m_Gender;
	}

	public void setM_Gender(String m_Gender) {
		this.m_Gender = m_Gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MyMember [m_Id=" + m_Id + ", m_Pwd=" + m_Pwd + ", m_Name=" + m_Name + ", m_Birth=" + m_Birth
				+ ", m_Phone=" + m_Phone + ", pwd_Question=" + pwd_Question + ", pwd_Answer=" + pwd_Answer
				+ ", m_Gender=" + m_Gender + ", email=" + email + "]";
	}

	
	

	
}

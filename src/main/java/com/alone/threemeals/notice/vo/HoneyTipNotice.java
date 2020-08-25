package com.alone.threemeals.notice.vo;

import java.util.Date;

public class HoneyTipNotice {
	private int n_Seq;
	private String n_Title;
	private Date n_RegDate;
	private String n_Writer;
	private String n_Content;
	private int n_Hit;
	private int n_Grok;
	private int n_NonGrok;
	public HoneyTipNotice() {
		// TODO Auto-generated constructor stub
	}
	public HoneyTipNotice(int n_Seq, String n_Title, Date n_RegDate, String n_Writer, String n_Content, int n_Hit,
			int n_Grok, int n_NonGrok) {
		super();
		this.n_Seq = n_Seq;
		this.n_Title = n_Title;
		this.n_RegDate = n_RegDate;
		this.n_Writer = n_Writer;
		this.n_Content = n_Content;
		this.n_Hit = n_Hit;
		this.n_Grok = n_Grok;
		this.n_NonGrok = n_NonGrok;
	}
	public int getN_Seq() {
		return n_Seq;
	}
	public void setN_Seq(int n_Seq) {
		this.n_Seq = n_Seq;
	}
	public String getN_Title() {
		return n_Title;
	}
	public void setN_Title(String n_Title) {
		this.n_Title = n_Title;
	}
	public Date getN_RegDate() {
		return n_RegDate;
	}
	public void setN_RegDate(Date n_RegDate) {
		this.n_RegDate = n_RegDate;
	}
	public String getN_Writer() {
		return n_Writer;
	}
	public void setN_Writer(String n_Writer) {
		this.n_Writer = n_Writer;
	}
	public String getN_Content() {
		return n_Content;
	}
	public void setN_Content(String n_Content) {
		this.n_Content = n_Content;
	}
	public int getN_Hit() {
		return n_Hit;
	}
	public void setN_Hit(int n_Hit) {
		this.n_Hit = n_Hit;
	}
	public int getN_Grok() {
		return n_Grok;
	}
	public void setN_Grok(int n_Grok) {
		this.n_Grok = n_Grok;
	}
	public int getN_NonGrok() {
		return n_NonGrok;
	}
	public void setN_NonGrok(int n_NonGrok) {
		this.n_NonGrok = n_NonGrok;
	}
	@Override
	public String toString() {
		return "HoneyTipNotice [n_Seq=" + n_Seq + ", n_Title=" + n_Title + ", n_RegDate=" + n_RegDate + ", n_Writer="
				+ n_Writer + ", n_Content=" + n_Content + ", n_Hit=" + n_Hit + ", n_Grok=" + n_Grok + ", n_NonGrok="
				+ n_NonGrok + "]";
	}
	
	
	

}

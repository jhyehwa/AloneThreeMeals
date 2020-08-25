package com.alone.threemeals.notice.vo;

import java.util.Date;

public class StoreNoticeComment {
	private int c_Seq;
	private int n_Seq;
	private Date c_RegDate;
	private String c_Writer;
	private String c_Content;
	private String c_Grok;
	public StoreNoticeComment() {
		// TODO Auto-generated constructor stub
	}
	public StoreNoticeComment(int c_Seq, int n_Seq, Date c_RegDate, String c_Writer, String c_Content, String c_Grok) {
		super();
		this.c_Seq = c_Seq;
		this.n_Seq = n_Seq;
		this.c_RegDate = c_RegDate;
		this.c_Writer = c_Writer;
		this.c_Content = c_Content;
		this.c_Grok = c_Grok;
	}
	public int getC_Seq() {
		return c_Seq;
	}
	public void setC_Seq(int c_Seq) {
		this.c_Seq = c_Seq;
	}
	public int getN_Seq() {
		return n_Seq;
	}
	public void setN_Seq(int n_Seq) {
		this.n_Seq = n_Seq;
	}
	public Date getC_RegDate() {
		return c_RegDate;
	}
	public void setC_RegDate(Date c_RegDate) {
		this.c_RegDate = c_RegDate;
	}
	public String getC_Writer() {
		return c_Writer;
	}
	public void setC_Writer(String c_Writer) {
		this.c_Writer = c_Writer;
	}
	public String getC_Content() {
		return c_Content;
	}
	public void setC_Content(String c_Content) {
		this.c_Content = c_Content;
	}
	public String getC_Grok() {
		return c_Grok;
	}
	public void setC_Grok(String c_Grok) {
		this.c_Grok = c_Grok;
	}
	@Override
	public String toString() {
		return "StoreNoticeComment [c_Seq=" + c_Seq + ", n_Seq=" + n_Seq + ", c_RegDate=" + c_RegDate + ", c_Writer="
				+ c_Writer + ", c_Content=" + c_Content + ", c_Grok=" + c_Grok + "]";
	}
	
	
}

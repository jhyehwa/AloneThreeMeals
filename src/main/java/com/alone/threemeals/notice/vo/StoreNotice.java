package com.alone.threemeals.notice.vo;

import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class StoreNotice {
	private int n_Seq;
	private String n_Title;
	private String n_Writer;
	private Date n_RegDate;
	private int n_Star;
	private String n_Picture;
	private CommonsMultipartFile n_File;
	private String n_Content;
	private int n_Hit;
	private int n_Grok;
	private int n_NonGrok;
	
	public StoreNotice() {
		// TODO Auto-generated constructor stub
	}

	public StoreNotice(int n_Seq, String n_Title, String n_Writer, Date n_RegDate, int n_Star, String n_Picture,
			CommonsMultipartFile n_File, String n_Content, int n_Hit, int n_Grok, int n_NonGrok) {
		super();
		this.n_Seq = n_Seq;
		this.n_Title = n_Title;
		this.n_Writer = n_Writer;
		this.n_RegDate = n_RegDate;
		this.n_Star = n_Star;
		this.n_Picture = n_Picture;
		this.n_File = n_File;
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

	public String getN_Writer() {
		return n_Writer;
	}

	public void setN_Writer(String n_Writer) {
		this.n_Writer = n_Writer;
	}

	public Date getN_RegDate() {
		return n_RegDate;
	}

	public void setN_RegDate(Date n_RegDate) {
		this.n_RegDate = n_RegDate;
	}

	public int getN_Star() {
		return n_Star;
	}

	public void setN_Star(int n_Star) {
		this.n_Star = n_Star;
	}

	public String getN_Picture() {
		return n_Picture;
	}

	public void setN_Picture(String n_Picture) {
		this.n_Picture = n_Picture;
	}

	public CommonsMultipartFile getN_File() {
		return n_File;
	}

	public void setN_File(CommonsMultipartFile n_File) {
		this.n_File = n_File;
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
		return "StoreNotice [n_Seq=" + n_Seq + ", n_Title=" + n_Title + ", n_Writer=" + n_Writer + ", n_RegDate="
				+ n_RegDate + ", n_Star=" + n_Star + ", n_Picture=" + n_Picture + ", n_File=" + n_File + ", n_Content="
				+ n_Content + ", n_Hit=" + n_Hit + ", n_Grok=" + n_Grok + ", n_NonGrok=" + n_NonGrok + "]";
	}
	

}

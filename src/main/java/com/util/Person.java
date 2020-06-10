package com.util;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.util.Apptest.Column;
import com.util.Apptest.Table;

@Table("person")
public class Person {

	@DateTimeFormat
	private Date time;
	
	@Column("name")
	private String naem;
	@Column("user_name")
	private String userName;
	
	public String getNaem() {
		return naem;
	}
	public void setNaem(String naem) {
		this.naem = naem;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}

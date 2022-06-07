package com.model;

public class User {
	private int uacno;
	private String uacname;
	private String uactype;
	private String uacpin;
	@Override
	public String toString() {
		return "User [uacno=" + uacno + ", uacname=" + uacname + ", uactype=" + uactype + ", uacpin=" + uacpin
				+ ", uacbalance=" + uacbalance + "]";
	}
	private double uacbalance;
	public int getUacno() {
		return uacno;
	}
	public void setUacno(int uacno) {
		this.uacno = uacno;
	}
	public String getUactype() {
		return uactype;
	}
	public void setUactype(String uactype) {
		this.uactype = uactype;
	}
	public String getUacpin() {
		return uacpin;
	}
	public void setUacpin(String uacpin) {
		this.uacpin = uacpin;
	}
	public double getUacbalance() {
		return uacbalance;
	}
	public void setUacbalance(double uacbalance) {
		this.uacbalance = uacbalance;
	}
	public String getUacname() {
		return uacname;
	}
	public void setUacname(String uacname) {
		this.uacname = uacname;
	}
}

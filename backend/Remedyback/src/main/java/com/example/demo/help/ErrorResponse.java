package com.example.demo.help;

public class ErrorResponse {
private String msg;
private String status;
public String getMsg() {
	return msg;
}
public ErrorResponse(String msg,String status)
{
	this.msg=msg;
	this.status=status;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
}

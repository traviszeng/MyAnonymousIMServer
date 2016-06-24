package com.example.im.domain;

public class QQUser extends ProtocalObj {
	public long account;// 账号 QQ号
	public String password = "";// 密码
	public String nick = "";// 昵称
	public int avatar;// 头像
	public boolean allowed_back_online = true;  //设置初始每个账号都是允许上线的

}

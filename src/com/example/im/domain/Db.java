package com.example.im.domain;

import java.util.HashMap;

public class Db {
	public static HashMap<Long, QQUser> map = new HashMap<Long, QQUser>();
	static {
		for (int i = 0; i < 50; i++) {
			QQUser user = new QQUser();
			user.account = 100 + i;
			
			//将密码用MD5加密算法加密
			user.password = "098F6BCD4621D373CADE4E832627B4F6";
			user.nick = "Stranger" + i;
			user.avatar = 0;
			map.put(user.account, user);
		}
		for (int i = 50; i < 100; i++) {
			QQUser user = new QQUser();
			user.account = 100 + i;
			
			//将密码用MD5加密算法加密
			user.password = "098F6BCD4621D373CADE4E832627B4F6";
			user.nick = "stranger" + i;
			user.avatar = 0;
			map.put(user.account, user);
		}
	}

	public static QQUser getByAccount(long account) {
		if (map.containsKey(account)) {
			return map.get(account);
		}
		return null;

	}

}

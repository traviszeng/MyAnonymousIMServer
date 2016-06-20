package com.example.im.domain;

import java.util.HashMap;

public class Db {
	public static HashMap<Long, QQUser> map = new HashMap<Long, QQUser>();
	static {
		for (int i = 0; i < 50; i++) {
			QQUser user = new QQUser();
			user.account = 100 + i;
			user.password = "test";
			user.nick = "¶þ¹·" + i;
			user.avatar = 0;
			map.put(user.account, user);
		}
		for (int i = 50; i < 100; i++) {
			QQUser user = new QQUser();
			user.account = 100 + i;
			user.password = "test";
			user.nick = "ÀÏËÄ" + i;
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

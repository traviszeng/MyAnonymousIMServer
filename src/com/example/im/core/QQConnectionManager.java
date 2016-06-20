package com.example.im.core;

import java.util.HashMap;

import com.example.im.domain.Db;
import com.example.im.domain.QQBuddy;
import com.example.im.domain.QQBuddyList;
import com.example.im.domain.QQUser;


public class QQConnectionManager {
	public static HashMap<Long, QQConnection> conns = new HashMap<Long, QQConnection>();
	public static QQBuddyList list = new QQBuddyList();

	public static void put(long account, QQConnection conn) {
		System.out.println("====账号" + account + "上线了");
		remove(account);
		conns.put(account, conn);
		QQUser u = Db.getByAccount(account);
		QQBuddy item = new QQBuddy();
		item.account = u.account;
		item.avatar = u.avatar;
		item.nick = u.nick;
		list.buddyList.add(item);
	}

	public static void remove(Long account) {
		if (conns.containsKey(account)) {
//			System.out.println("====账号" + account + "下线了");
			conns.remove(account);
			QQBuddy delete = null;
			for (QQBuddy item : list.buddyList) {
				if (account == item.account) {
					delete = item;
					break;
				}
			}
			if (delete != null) {
//				System.out.println("====从在线名单上移除 0000" + account);
				list.buddyList.remove(delete);

			}
		}
	}

	public static QQConnection get(long account) {
		if (conns.containsKey(account)) {
			return conns.get(account);
		}
		return null;
	}
}

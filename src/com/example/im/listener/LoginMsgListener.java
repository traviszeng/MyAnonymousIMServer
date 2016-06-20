package com.example.im.listener;

import com.example.im.core.QQConnection;
import com.example.im.core.QQConnectionManager;
import com.example.im.core.QQConnection.OnRecevieMsgListener;
import com.example.im.domain.Db;
import com.example.im.domain.QQBuddyList;
import com.example.im.domain.QQMessage;
import com.example.im.domain.QQMessageType;
import com.example.im.domain.QQUser;


public class LoginMsgListener extends MessageSender implements OnRecevieMsgListener {
	private QQConnection conn = null;

	public LoginMsgListener(QQConnection conn) {
		super();
		this.conn = conn;
	}

	public void onReceive(QQMessage fromCient) {
		if (QQMessageType.MSG_TYPE_LOGIN.equals(fromCient.type)) {
			try {
				QQMessage toClient = new QQMessage();
				if (QQMessageType.MSG_TYPE_LOGIN.equals(fromCient.type)) {
					String[] params = fromCient.content.split("#");
					String account = params[0];
					String pwd = params[1];
					QQUser user = Db.getByAccount(Long.parseLong(account));
					if (user == null) {
						// 不存在
						toClient.type = QQMessageType.MSG_TYPE_FAILURE;
						toClient.content = "不存在";
						toClient(toClient, conn);
					} else {
						// 存在
						if (user.password.equals(pwd)) {
							// 登录 成功
							toClient.type = QQMessageType.MSG_TYPE_BUDDY_LIST;
							// 返回在线名单
							// 创建带身份的连接对象
							conn.who = user;
							QQConnectionManager.put(user.account, conn);
							QQBuddyList list = QQConnectionManager.list;
							toClient.content = list.toJson();
							toEveryClient(toClient);
						} else {
							toClient.type = QQMessageType.MSG_TYPE_FAILURE;
							toClient.content = "失败";
							toClient(toClient, conn);
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
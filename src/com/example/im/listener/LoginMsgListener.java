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
							//并且没有同一账号登陆的用户
							
							toClient.type = QQMessageType.MSG_TYPE_BUDDY_LIST;
							toClient.newAddAccount=user.account;
							// 返回在线名单
							// 创建带身份的连接对象
							conn.who = user;
							QQConnectionManager.put(user.account, conn);
							QQBuddyList list = QQConnectionManager.list;
							toClient.content = list.toJson();
							toEveryClient(toClient);
						}
					/*	else if(user.password.equals(pwd)&&user.allowed_back_online==false){
							//账号登陆成功，但是有其他使用同一账号的用户在使用
							toClient.existAnother = true;  //存在使用同一账户登陆的用户
							toClient.type=QQMessageType.MSG_TYPE_BUDDY_LIST;
							// 返回在线名单
							// 创建带身份的连接对象
							conn.who = user;
							QQConnectionManager.put(user.account, conn);
							QQBuddyList list = QQConnectionManager.list;
							toClient.content = list.toJson();
							toOtherClient(toClient);
							
							//现在登陆的账户的信息另外发
							toClient.existAnother=false;
							toClient.type=QQMessageType.MSG_TYPE_BUDDY_LIST;
							// 返回在线名单
							// 创建带身份的连接对象
							conn.who = user;
							QQConnectionManager.put(user.account, conn);
							list = QQConnectionManager.list;
							toClient.content = list.toJson();
							
							toClient(toClient,conn);
							
						}*/
						
						 else {
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
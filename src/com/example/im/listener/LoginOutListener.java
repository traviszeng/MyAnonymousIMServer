package com.example.im.listener;

import com.example.im.core.QQConnection;
import com.example.im.core.QQConnectionManager;
import com.example.im.core.QQConnection.OnRecevieMsgListener;
import com.example.im.domain.QQMessage;
import com.example.im.domain.QQMessageType;


public class LoginOutListener extends MessageSender implements OnRecevieMsgListener {
	public LoginOutListener() {
		super();
	}
	public void onReceive(QQMessage fromCient) {
		if (QQMessageType.MSG_TYPE_LOGIN_OUT.equals(fromCient.type)) {
			try {
				QQMessage toClient = new QQMessage();
				toClient.type = QQMessageType.MSG_TYPE_SUCCESS;
				toClient.content = "ÍË³ö³É¹¦";
				QQConnection conn = QQConnectionManager.get(fromCient.from );
				QQConnectionManager.remove(fromCient.from );
				toClient(toClient, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
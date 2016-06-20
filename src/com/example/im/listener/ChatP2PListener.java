package com.example.im.listener;

import java.io.IOException;

import com.example.im.core.QQConnection;
import com.example.im.core.QQConnectionManager;
import com.example.im.core.QQConnection.OnRecevieMsgListener;
import com.example.im.domain.QQMessage;
import com.example.im.domain.QQMessageType;


public class ChatP2PListener extends MessageSender implements OnRecevieMsgListener {
	public void onReceive(QQMessage fromOneClient) {
		if (QQMessageType.MSG_TYPE_CHAT_P2P.equals(fromOneClient.type)) {
			QQConnection anotherOne = QQConnectionManager.get(fromOneClient.to);
			try {
				toClient(fromOneClient, anotherOne);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

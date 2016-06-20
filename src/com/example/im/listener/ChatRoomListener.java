package com.example.im.listener;

import java.io.IOException;

import com.example.im.core.QQConnection.OnRecevieMsgListener;
import com.example.im.domain.QQMessage;
import com.example.im.domain.QQMessageType;


public class ChatRoomListener extends MessageSender implements OnRecevieMsgListener {
	public void onReceive(QQMessage fromOneClient) {
		if (QQMessageType.MSG_TYPE_CHAT_ROOM.equals(fromOneClient.type)) {
			try {
				toOtherClient(fromOneClient);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

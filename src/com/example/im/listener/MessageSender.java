package com.example.im.listener;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.example.im.core.QQConnection;
import com.example.im.core.QQConnectionManager;
import com.example.im.domain.QQMessage;

public class MessageSender {
	/**
	 * 给一个客户端发送消息，点对点聊天
	 * 
	 * @param msg
	 * @param conn
	 * @throws IOException
	 */
	public void toClient(QQMessage msg, QQConnection conn) throws IOException {
		System.out.println("单发当前客户端to Client \n" + msg.toXml());
		if (conn != null) {
			conn.writer.writeUTF(msg.toXml());
		}
	}

	/**
	 * 给连接进来的所有的客户端发送消息
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void toEveryClient(QQMessage msg) throws IOException {
		System.out.println("群发所有客户端  to toEveryClient Client \n" + msg.toXml());
		// conn.writer.writeUTF(toClient.toXml());
		Set<Map.Entry<Long, QQConnection>> allOnLines = QQConnectionManager.conns
				.entrySet();
		for (Map.Entry<Long, QQConnection> entry : allOnLines) {
			entry.getValue().writer.writeUTF(msg.toXml());
		}
	}

	public void toOtherClient(QQMessage msg) throws IOException {
		System.out.println("群发所有其他客户端  to toEveryClient Client \n"
				+ msg.toXml());
		// conn.writer.writeUTF(toClient.toXml());
		Set<Map.Entry<Long, QQConnection>> allOnLines = QQConnectionManager.conns
				.entrySet();
		for (Map.Entry<Long, QQConnection> entry : allOnLines) {
			if (entry.getValue().who.account != msg.from) {
				entry.getValue().writer.writeUTF(msg.toXml());
			}
		}
	}
}

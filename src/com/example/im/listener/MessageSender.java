package com.example.im.listener;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.example.im.core.QQConnection;
import com.example.im.core.QQConnectionManager;
import com.example.im.domain.QQMessage;

public class MessageSender {
	/**
	 * ��һ���ͻ��˷�����Ϣ����Ե�����
	 * 
	 * @param msg
	 * @param conn
	 * @throws IOException
	 */
	public void toClient(QQMessage msg, QQConnection conn) throws IOException {
		System.out.println("������ǰ�ͻ���to Client \n" + msg.toXml());
		if (conn != null) {
			conn.writer.writeUTF(msg.toXml());
		}
	}

	/**
	 * �����ӽ��������еĿͻ��˷�����Ϣ
	 * 
	 * @param msg
	 * @throws IOException
	 */
	public void toEveryClient(QQMessage msg) throws IOException {
		System.out.println("Ⱥ�����пͻ���  to toEveryClient Client \n" + msg.toXml());
		// conn.writer.writeUTF(toClient.toXml());
		Set<Map.Entry<Long, QQConnection>> allOnLines = QQConnectionManager.conns
				.entrySet();
		for (Map.Entry<Long, QQConnection> entry : allOnLines) {
			entry.getValue().writer.writeUTF(msg.toXml());
		}
	}

	public void toOtherClient(QQMessage msg) throws IOException {
		System.out.println("Ⱥ�����������ͻ���  to toEveryClient Client \n"
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

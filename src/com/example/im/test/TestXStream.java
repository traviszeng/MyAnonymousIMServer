package com.example.im.test;

import org.junit.Test;


import com.example.im.domain.QQMessage;
import com.example.im.domain.QQMessageType;
import com.example.im.domain.QQUser;
import com.thoughtworks.xstream.XStream;

public class TestXStream {
	@Test
	public void testToXMLObject() {
		XStream stream = new XStream();
		// stream.alias(标签名, 字节类);
		stream.alias("QQMessage", QQMessage.class);
		QQMessage msg = new QQMessage();
		msg.type = QQMessageType.MSG_TYPE_CHAT_P2P;
		msg.from = 1000L;
		msg.fromNick = "大侠";
		msg.fromAvatar = 1;
		msg.content = "约不？";
		msg.to = 2000L;
		String xml = stream.toXML(msg);
		System.out.println(xml);
		QQMessage msg2 = (QQMessage) stream.fromXML(xml);
		System.out.println(msg2.content);
	}

	@Test
	public void testFromXMLString() {
		XStream stream = new XStream();
		// stream.alias(标签名, 字节类);
		stream.alias("QQMessage", QQMessage.class);
		stream.alias("User", QQUser.class);
		QQUser user = new QQUser();
		user.account = 1000L;
		user.password = "2122";

		String xml = stream.toXML(user);
		System.out.println(xml);
		//
		QQMessage msg = new QQMessage();
		msg.type = QQMessageType.MSG_TYPE_LOGIN;
		msg.content = xml;

		String dataXml = stream.toXML(msg);
		System.out.println(dataXml);
		// -----服务端-----
		QQMessage data = (QQMessage) stream.fromXML(dataXml);
		QQUser user2 = (QQUser) stream.fromXML(data.content);
		System.out.println(user2.account);
	}

	// -------------登录------------------
	@Test
	public void testLogin() {
		// XStream stream = new XStream();
		// // stream.alias(标签名, 字节类);
		// stream.alias("QQMessage", QQMessage.class);
		// QQMessage msg = new QQMessage();
		// msg.type = MessageType.MSG_TYPE_LOGIN;
		// msg.content = "1001#123";
		// String xml = stream.toXML(msg);
		// System.out.println(xml);
		QQUser user = new QQUser();
		System.out.println(user.toJson());
		// String xml = user.toXml();
		// System.out.println(xml);
		//
		// QQUser u = (QQUser) user.fromXml(xml);
		// System.out.println(u.account);
	}

}

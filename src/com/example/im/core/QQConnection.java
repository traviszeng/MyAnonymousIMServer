package com.example.im.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.im.domain.QQMessage;
import com.example.im.domain.QQUser;


public class QQConnection extends Thread {
	private Socket scoket = null;
	// readUTF
	public DataOutputStream writer = null;
	public DataInputStream reader = null;
	public QQUser who = null;

	public String ip;
	public int port;

	public QQConnection(Socket scoket) {
		super();
		try {
			this.scoket = scoket;
			writer = new DataOutputStream(this.scoket.getOutputStream());
			reader = new DataInputStream(this.scoket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QQConnection(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
		init(ip, port);
	}

	private void init(String ip, int port) {
		try {
			this.scoket = new Socket(ip, port);
			writer = new DataOutputStream(this.scoket.getOutputStream());
			reader = new DataInputStream(this.scoket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 连接
	public void connect() {
		if (this.scoket == null) {
			init(ip, port);
		}
		flag = true;
		start();
	}

	// 断开连接
	public void disconnect() {
		try {
			flag = false;
			writer.close();
			reader.close();
			// stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----------监听器------
	// 1.申明监听器与响应方法
	public static interface OnRecevieMsgListener {
		public void onReceive(QQMessage msg);
	}

	// 2.支持多个监听器
	private List<OnRecevieMsgListener> listeners = new ArrayList<OnRecevieMsgListener>();

	// 3.添加监听器
	public void addOnRecevieMsgListener(OnRecevieMsgListener listener) {
		listeners.add(listener);
	}

	// 4.删除监听器
	public void removeOnRecevieMsgListener(OnRecevieMsgListener listener) {
		listeners.remove(listener);
	}

	private boolean flag = true;

	@Override
	public void run() {
		super.run();
		// 等待 数据
		while (flag) {
			try {
				String xml = reader.readUTF();
				System.out.println(xml);
				if (xml != null && !"".equals(xml)) {
					QQMessage msg = new QQMessage();
					msg = (QQMessage) msg.fromXml(xml);
					for (OnRecevieMsgListener l : listeners) {
						l.onReceive(msg);
					}
				}
			} catch (EOFException e) {
//				e.printStackTrace();
				System.out.println("=-=EOFException---");
				if (who != null) {
					QQConnectionManager.remove(who.account);
				}
				disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				if (who != null) {
					QQConnectionManager.remove(who.account);
				}
				disconnect();
			}
		}

	}
}

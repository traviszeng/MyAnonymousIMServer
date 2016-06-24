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
						// ������
						toClient.type = QQMessageType.MSG_TYPE_FAILURE;
						toClient.content = "������";
						toClient(toClient, conn);
					} else {
						// ����
						if (user.password.equals(pwd)) {
							// ��¼ �ɹ�
							//����û��ͬһ�˺ŵ�½���û�
							
							toClient.type = QQMessageType.MSG_TYPE_BUDDY_LIST;
							toClient.newAddAccount=user.account;
							// ������������
							// ��������ݵ����Ӷ���
							conn.who = user;
							QQConnectionManager.put(user.account, conn);
							QQBuddyList list = QQConnectionManager.list;
							toClient.content = list.toJson();
							toEveryClient(toClient);
						}
					/*	else if(user.password.equals(pwd)&&user.allowed_back_online==false){
							//�˺ŵ�½�ɹ�������������ʹ��ͬһ�˺ŵ��û���ʹ��
							toClient.existAnother = true;  //����ʹ��ͬһ�˻���½���û�
							toClient.type=QQMessageType.MSG_TYPE_BUDDY_LIST;
							// ������������
							// ��������ݵ����Ӷ���
							conn.who = user;
							QQConnectionManager.put(user.account, conn);
							QQBuddyList list = QQConnectionManager.list;
							toClient.content = list.toJson();
							toOtherClient(toClient);
							
							//���ڵ�½���˻�����Ϣ���ⷢ
							toClient.existAnother=false;
							toClient.type=QQMessageType.MSG_TYPE_BUDDY_LIST;
							// ������������
							// ��������ݵ����Ӷ���
							conn.who = user;
							QQConnectionManager.put(user.account, conn);
							list = QQConnectionManager.list;
							toClient.content = list.toJson();
							
							toClient(toClient,conn);
							
						}*/
						
						 else {
							toClient.type = QQMessageType.MSG_TYPE_FAILURE;
							toClient.content = "ʧ��";
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
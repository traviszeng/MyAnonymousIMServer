package com.example.im.domain;

public class QQMessage extends ProtocalObj {
	public String type = QQMessageType.MSG_TYPE_CHAT_P2P;// ���͵����� chat login
	public long from = 0L;// ������ account
	public String fromNick = "";// �ǳ�
	public int fromAvatar = 1;// ͷ��
	public long to = 0L; // ������ account
	public String content = ""; // ��Ϣ������
	public String sendTime = MyTime.geTime(); // ����ʱ��
}

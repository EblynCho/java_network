package ch14;

public class ChatProtocol2 {

//	protocol�� ����
//	(Client���� Server��) ID:aaa
//	(Server -> Client) CHALLIST:aaa;bbb;ccc;��ȣ��;
	public static final String ID = "ID";  // ����� �����ؾ� ��Ÿ���� �پ��
	
//	(Client -> Server) CHAT:�޴¾��̵�;�޼��� ex)CHAT:bbb;�����
//	(S -> C) CHAT:�����¾��̵�;�޼��� ex)CHAT:aaa;�����
	public static final String CHAT = "CHAT";
	
//	(C -> S) CHATALL:�޼���
//	(S -> C) CHATALL:[�������̵�]�޼���
	public static final String CHATALL = "CHATALL";
	
//	(C -> S) CHAT:�޴¾��̵�;�������� ex)CHAT:bbb;���� ���?
//	(S -> C) CHAT:�����¾��̵�;�������� ex)CHAT:aaa;���� ���?
	public static final String MESSAGE = "MESSAGE";
	
//	(S -> C) CHATLIST:aaa;bbb;ccc;��ȣ��;
	public static final String CHATLIST = "CHATLIST";
	
//	������ -> ��������:data (delimiter)
	public static final String DM = ":";
}

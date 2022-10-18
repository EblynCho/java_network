package ch14;

public class ChatProtocol2 {

//	protocol을 붙임
//	(Client에서 Server로) ID:aaa
//	(Server -> Client) CHALLIST:aaa;bbb;ccc;강호동;
	public static final String ID = "ID";  // 상수로 선언해야 오타율이 줄어듬
	
//	(Client -> Server) CHAT:받는아이디;메세지 ex)CHAT:bbb;밥먹자
//	(S -> C) CHAT:보내는아이디;메세지 ex)CHAT:aaa;밥먹자
	public static final String CHAT = "CHAT";
	
//	(C -> S) CHATALL:메세지
//	(S -> C) CHATALL:[보낸아이디]메세지
	public static final String CHATALL = "CHATALL";
	
//	(C -> S) CHAT:받는아이디;쪽지내용 ex)CHAT:bbb;지금 어디?
//	(S -> C) CHAT:보내는아이디;쪽지내용 ex)CHAT:aaa;지금 어디?
	public static final String MESSAGE = "MESSAGE";
	
//	(S -> C) CHATLIST:aaa;bbb;ccc;강호동;
	public static final String CHATLIST = "CHATLIST";
	
//	구분자 -> 프로토콜:data (delimiter)
	public static final String DM = ":";
}

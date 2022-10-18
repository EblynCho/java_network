package ch14;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;



public class ChatClient2 extends MFrame 
implements ActionListener, Runnable {

	Button bt1, bt2, bt3, bt4;
	TextField tf1, tf2, tf3;
	TextArea area;
	List list;
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	String listTitle = "*******대화자명단*******";
	boolean flag = false;
	String filterList[] = {"바보","개새끼","새끼","자바","java"};  // 메소드 밖에 작성

	public ChatClient2() {
		super(450, 500);
		setTitle("MyChat 2.0");
		Panel p1 = new Panel();
		p1.add(new Label("Host", Label.RIGHT));
		p1.add(tf1 = new TextField("127.0.0.1"));
		p1.add(new Label("Port", Label.RIGHT));
		p1.add(tf2 = new TextField("8003"));
		bt1 = new Button("connect");
		bt1.addActionListener(this);
		p1.add(bt1);
		add(BorderLayout.NORTH, p1);
		// //////////////////////////////////////////////////////////////////////////////////////////
		area = new TextArea("ChatClient 2.0");
		area.setBackground(Color.DARK_GRAY);
		area.setForeground(Color.PINK);
		area.setEditable(false);
		add(BorderLayout.CENTER, area);
		// /////////////////////////////////////////////////////////////////////////////////////////
		Panel p2 = new Panel();
		p2.setLayout(new BorderLayout());
		list = new List();
		list.add(listTitle);
		p2.add(BorderLayout.CENTER, list);
		Panel p3 = new Panel();
		p3.setLayout(new GridLayout(1, 2));
		bt2 = new Button("Save");
		bt2.addActionListener(this);
		bt3 = new Button("Message");
		bt3.addActionListener(this);
		p3.add(bt2);
		p3.add(bt3);
		p2.add(BorderLayout.SOUTH, p3);
		add(BorderLayout.EAST, p2);
		// ///////////////////////////////////////////////////////////////////////////////////////////
		Panel p4 = new Panel();
		tf3 = new TextField("", 50);
		tf3.addActionListener(this);
		bt4 = new Button("send");
		bt4.addActionListener(this);
		p4.add(tf3);
		p4.add(bt4);
		add(BorderLayout.SOUTH, p4);
		validate();
	}
	
	public void run() {  // 서버로부터 들어옴 (multiThread)
		try {
			String host = tf1.getText().trim();
			int port = Integer.parseInt(tf2.getText().trim());  // 정수처럼 보이지만 문자열이므로 parseInt 필요
			connect(host, port);
			area.append(in.readLine() + "\n");
			while (true) {
				String line = in.readLine();
				if (line == null) 
					break;
				else
					routine(line);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//--run
	
	public void routine(String line) {  // 서버로부터 들어옴 (multiThread)
		int idx = line.indexOf(ChatProtocol2.DM);
		String cmd = line.substring(0, idx);
		String data = line.substring(idx+1);
		if (cmd.equals(ChatProtocol2.CHATLIST)) {
			// data = aaa;bbb;홍길동;ccc;
			list.removeAll();
			list.add(listTitle);
			StringTokenizer st = new StringTokenizer(data, ";");
			while (st.hasMoreElements()) {
				list.add(st.nextToken());
			}
		} else if (cmd.equals(ChatProtocol2.CHAT) || 
				cmd.equals(ChatProtocol2.CHATALL)) {
			area.append(data + "\n");
		} else if (cmd.equals(ChatProtocol2.MESSAGE)) {
			// data = bbb;밥먹자..
			idx = data.indexOf(';');
			cmd = data.substring(0, idx); // bbb
			data = data.substring(idx+1); // 밥먹자
			new Message("FROM:", cmd, data);
			
		}
	}//--routine
	
	public void actionPerformed(ActionEvent e) {  // 이벤트 발생 (동작) (to server)
		Object obj = e.getSource();
		if (obj == bt1) {
			new Thread(this).start();  // run 메소드 호출 결과
			bt1.setEnabled(false);
			tf1.setEnabled(flag);
			tf2.setEnabled(flag);
			area.setText("");
		} else if (obj == bt2) {  // save button
			try {
				// 파일 생성
				long file = System.currentTimeMillis();
				FileWriter fw = new FileWriter("ch14/" + file + ".txt");
				fw.write(area.getText());
				fw.flush();
				fw.close();
				area.setText("");
				new MDialog(this, "Save", "대화 내용을 저장하였습니다.");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} else if (obj == bt3) {  // Message 보내기
			int idx = list.getSelectedIndex();
			if (idx == -1 || idx == 0) {  // 선택안한경우 : -1, ***대화자명단*** 선택 : 0
				new MDialog(this, "경고", "아이디를 선택하세요.");
			} else {
				new Message("TO:");
			}
		} else if (obj == bt4 || obj == tf3) { // press enter || send button click
			String str = tf3.getText().trim();
			if (str.length()==0) {
				return;
			}
			if (filterMgr(str)) {
				new MDialog(this, "경고", "입력하신 글자는 금지어입니다.");  // this ; MDialog는 자기를 띄운 frame이 누군지 알아야함 (경고창과 프레임/ 기존창 비활성화 - default:비활성화)
				return;
			}
			// id 입력 경우 또는 채팅
			if (!flag) {  // id 입력 -> (한번만 발생)
				// 서버로 id 값을 보낸다.
				sendMessage(ChatProtocol2.ID + ChatProtocol2.DM + str);
				setTitle(getTitle() + " - " + str +"님 반갑습니다");
				area.setText("");
				tf3.setText("");
				tf3.requestFocus();
				flag = true;
			} else {  // 채팅
				int idx = list.getSelectedIndex();
//				System.out.println("idx : " + idx);
				if (idx == 0 || idx == -1) {  // 전체 채팅
					sendMessage(ChatProtocol2.CHATALL + ChatProtocol2.DM + str);
				} else {  // 귓속말 채팅 (단톡방 내에서)
					String id = list.getSelectedItem();
					sendMessage(ChatProtocol2.CHAT + ChatProtocol2.DM + id + ";" + str);
				}
				tf3.setText("");
				tf3.requestFocus();
			}
		}
	}//--actionPerformed
	
	public void connect(String host, int port) {
		try {
			sock = new Socket(host, port);
			in = new BufferedReader(
					new InputStreamReader(
							sock.getInputStream()));
			out = new PrintWriter(
					sock.getOutputStream(),true/*auto flush*/);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//--connect
	
	public void sendMessage(String msg) {
		out.println(msg);
	}

	public boolean filterMgr(String msg){
		for (int i = 0; i < filterList.length; i++) {
			if(msg.contains(filterList[i])) {
				return true;
			}
		}
		return false;
	}
	
	class Message extends Frame implements ActionListener {

		Button send, close;
		TextField name;
		TextArea ta;
		String mode;// to/from
		String id;

		public Message(String mode) {
			setTitle("쪽지보내기");
			this.mode = mode;
			id = list.getSelectedItem();
			layset("");
			validate();
		}
		public Message(String mode, String id, String msg) {
			setTitle("쪽지읽기");
			this.mode = mode;
			this.id = id;
			layset(msg);
			validate();
		}
		public void layset(String msg) {
			 addWindowListener(new WindowAdapter() {
				   public void windowClosing(WindowEvent e) {
				    dispose();
				   }
			});
			Panel p1 = new Panel();
			p1.add(new Label(mode, Label.CENTER));
			name = new TextField(id, 20);
			p1.add(name);
			add(BorderLayout.NORTH, p1);
			
			ta = new TextArea("");
			add(BorderLayout.CENTER, ta);
			ta.setText(msg);
			Panel p2 = new Panel();
			if (mode.equals("TO:")) {
				p2.add(send = new Button("send"));
				send.addActionListener(this);
			}
			p2.add(close = new Button("close"));
			close.addActionListener(this);
			add(BorderLayout.SOUTH, p2);
			
			setBounds(200, 200, 250, 250);
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==send){
				sendMessage(ChatProtocol2.MESSAGE+
						ChatProtocol2.DM+id+";"+ ta.getText());
			}
			setVisible(false);
			dispose();
		}
	}

	class MDialog extends Dialog implements ActionListener{
		
		Button ok;
		ChatClient2 ct2;
		
		public MDialog(ChatClient2 ct2,String title, String msg) {
			super(ct2, title, true);
			this.ct2 = ct2;
			 //////////////////////////////////////////////////////////////////////////////////////////
			   addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent e) {
			     dispose();
			    }
			   });
			   /////////////////////////////////////////////////////////////////////////////////////////
			   setLayout(new GridLayout(2,1));
			   Label label = new Label(msg, Label.CENTER);
			   add(label);
			   add(ok = new Button("확인"));
			   ok.addActionListener(this);
			   layset();
			   setVisible(true);
			   validate();
		}
		
		public void layset() {
			int x = ct2.getX();
			int y = ct2.getY();
			int w = ct2.getWidth();
			int h = ct2.getHeight();
			int w1 = 150;
			int h1 = 100;
			setBounds(x + w / 2 - w1 / 2, 
					y + h / 2 - h1 / 2, 200, 100);
		}

		public void actionPerformed(ActionEvent e) {
			tf3.setText("");
			dispose();
		}
	}
	
	public static void main(String[] args) {
		new ChatClient2();
	}
}

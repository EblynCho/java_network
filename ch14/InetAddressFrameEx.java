package ch14;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressFrameEx extends MFrame 
implements ActionListener{
	
	TextField tf;
	TextArea ta;
	Button lookup;
	InetAddress intAddr;
	
	public InetAddressFrameEx() {
		setTitle("InetAddress Example");
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		p.add("North",new Label("ȣ��Ʈ�̸�"));
		p.add(tf = new TextField("",40));
		p.add("South",lookup = new Button("ȣ��Ʈ ���� ���"));
		tf.addActionListener(this);
		lookup.addActionListener(this);
		add("North",p);
		ta = new TextArea("���ͳ��ּ�\n");
		ta.setFont(new Font("Dialog",Font.BOLD,15));
		ta.setForeground(Color.BLUE);
		ta.setEditable(false);
		add(ta);
		validate();
	}
	
	
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (obj == lookup || obj == tf) {
			String host = tf.getText().trim();
			try {
				intAddr = InetAddress.getByName(host);
				String add = intAddr.getHostName();
				String ip = intAddr.getHostAddress();
				ta.append("" + add + "\n");
				ta.append("" + ip + "\n");				
			} catch (Exception e2) {
//				e2.printStackTrace();
				ta.append("[" + host + "]\n");
				ta.append("�ش� ȣ��Ʈ�� �����ϴ�.\n");
			}
			tf.setText("");
			tf.requestFocus();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		new InetAddressFrameEx();
	}
}
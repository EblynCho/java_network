package ch13;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.Iterator;

public class FileWriterEx2 extends MFrame implements ActionListener {

	
	TextArea ta;
	TextField tf;
	Button save;
	
	public FileWriterEx2() {
		super(320, 400);
		setTitle("FileWriter");
		add(ta=new TextArea());
		Panel p = new Panel();
		p.add(tf = new TextField(30));
		p.add(save = new Button("SAVE"));
		ta.setEditable(false);
		tf.addActionListener(this);
		save.addActionListener(this);
		add(p,BorderLayout.SOUTH);
		validate();
	
	}
	
//	override �ϰ����ϴ� ��ġ���� ctrl+space
//	enter or button click -> action event ��ü �������
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();  // �̺�Ʈ�� �߻���Ų ��ü�� ����
		if (obj == tf) {
			//System.out.println("tf");
			ta.append(tf.getText() + "\n");
			tf.setText("");
			tf.requestFocus();
		} else if (obj == save) {  // save ������ ���Ϸ� ����
			//System.out.println("save");
			saveFile(ta.getText());
			ta.setText("");
			try {
				for (int i = 5; i > 0; i--) {
					ta.setText("���� �Ͽ����ϴ�.-" + i + "�� �Ŀ� ������ϴ�.");
					Thread.sleep(1000);  //1�ʵ��� 
				}
		
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			ta.setText("");
		}
	}
	
//	��ɺ��� �л��ؾ� ����
	public void saveFile(String str) {
		try {
			long name = System.currentTimeMillis();
			FileWriter fw = new FileWriter("ch13/" + name + ".txt");
			fw.write(str);
			fw.flush();
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		new FileWriterEx2();
	}

}

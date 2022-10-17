package ch13;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;

public class FileCopyEx2 extends MFrame implements ActionListener{
	
	Button open, save;
	TextArea ta;
	FileDialog openDialog, saveDialog;
	String sourceDir;
	String sourceFile;
	
	public FileCopyEx2() {
		super(400, 500);
		setTitle("FileCopyEx2");
		add(ta = new TextArea());
		Panel p = new Panel();
		p.add(open = new Button("OPEN"));
		p.add(save = new Button("SAVE"));
		//ta.setEditable(false);
		open.addActionListener(this);  //����
		save.addActionListener(this);
		add(p,BorderLayout.SOUTH);
		validate();
	}

	@Override

	public void actionPerformed(ActionEvent e) { // �̺�Ʈ ���忡�� ��ư�� source
		Object obj = e.getSource();
		System.out.println(open.hashCode());
		System.out.println(save.hashCode());
		if(obj == open) { // �������� ��ü�� �ּҰ��� ���Ѵ�. 
			//��� �񱳴� ���� ==����ϸ� �ȵȴ�. db����� ����ڰ� �Է��� ����� ���� �ٸ� String ��ü
			//dbPass == inputPass �׾��ٱ��� false���´�.
			// dbPass.equals(inputPass) �̷��� �ؾ� �Ѵ�.
			// equals�� ������ �������� ��. ==�� �ּҰ� ��
			
			openDialog = new FileDialog(this,"���Ͽ���",FileDialog.LOAD);
			openDialog.setVisible(true);
			String dir, file;
			dir = openDialog.getDirectory();
			file = openDialog.getFile();
			fileReader(dir + file);
			
			
		}else if(obj == save) {
			saveDialog = new FileDialog(this,"��������",FileDialog.SAVE);
			saveDialog.setVisible(true);
			String dir, file;
			dir = openDialog.getDirectory();
			file = openDialog.getFile();
			fileWriter(dir + file);
		}
		
//		int i = 10;
//		int j =12;
		//System.out.println(i == j); //java �⺻���� ���� ��
	}
	
	public void fileReader(String file){
		try {
			FileReader fr = new FileReader(file);
			int c;
			String s="";  
			while((c=fr.read())!=-1) {
				s+=(char)c;
			}
			ta.setText(s);
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fileWriter(String file){
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(ta.getText());
			for (int i = 5; i > 0; i--) {
				ta.setText("���� �Ͽ����ϴ�. - " + i
						+"���Ŀ� ������ϴ�.");
				Thread.sleep(1000);
			}
			ta.setText("");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new FileCopyEx2();
	}
}




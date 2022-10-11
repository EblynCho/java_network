package ch13;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class BufferedWriterEx {

	public static void main(String[] args) {
	
		String s = "������ ��ſ� ȭ�����Դϴ�.";
		
		BufferedWriter bw = 
				new BufferedWriter(
						new OutputStreamWriter(System.out));
			try {
				bw.write(s, 0, s.length());
				bw.newLine();
				bw.write(s);
				bw.flush();
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}

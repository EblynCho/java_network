package ch13;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ReaderEx {

	public static void main(String[] args) {
		InputStream is = System.in;
		// 1����Ʈ ������ ���� ���� ���� ���� ó��
		Reader reader = new InputStreamReader(is);	// �ѱ�
		while (true) {
			try {
				int i = reader.read();
				if(i==-1) break;
				System.out.print((char)i);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

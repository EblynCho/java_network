package ch13;

import java.io.InputStream;

public class InputStreamEx {

	public static void main(String[] args) {
		InputStream in = System.in;	// Ű���� // in : static ����(��ü�������� ���)
		try {
			// throws �޼ҵ�� �ݵ�� ����ó�� �ؾ���
			while(true) {
				int i = in.read();	// Ű���� �Է� ������ ��� ���� -> Ű���� �Է� ����
				if(i==-1) break;	// ctrl+z
				System.out.print((char)i);
			}
		} catch (Exception e) {
			e.printStackTrace();	// ���ܰ� �Ͼ ��� �� �޽���
		}
	}

}

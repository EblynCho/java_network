package ch13;

import java.io.InputStream;

public class InputStreamEx { 

	public static void main(String[] args) {
	InputStream in =System.in; 
	//Ű����. ���Ÿ�ü�� ��¦ �����ִ� �� static ����. ��ȣ�ִ� �� �޼ҵ�. ��ȣ���°� �ʵ�.
	// InputStream in => in�� Ű����� �ڹٸ� ��������ش�. in�� �Ϲ������� ������ ������ �� ����.
	
	try {
		//throws �޼ҵ�� �ݵ�� ����ó�� �ؾ� ��.
		while(true) {
		int i = in.read(); //Ű���� �Է� ������ ��� ���� -> Ű���� �Է� ����
		if(i==-1) break; //ctrl + z
		System.out.print((char)i);
		//try �Ͼ ���ɼ��� �ִ� �͵��� �־���´�. ����ó���س�����.
		//���ܸ� ����� catch�� ����.
		}
	} catch (Exception e) {
		//e�� ������ ��ü. rapper(?)����.
		e.printStackTrace();//���ܰ� �Ͼ ��� �� �޽���
	}
  }
}

package ch13;

import java.io.FileReader;

// java ��Ű����.Ŭ������
// java ch13.FileReaderEx

public class FileReaderEx {

	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader("ch13/aaa.txt");
			int i;	// ������ �������� �ִ� ���� -1
			while ((i=fr.read())!=-1) {
				System.out.print((char)i);
			}
			fr.close();
			System.out.println("\nEnd~~");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

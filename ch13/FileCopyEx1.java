package ch13;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class FileCopyEx1 {

	public static void main(String[] args) {

		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("�������� : ");
			String sFile = sc.nextLine();
			System.out.print("���� ���� : ");
			String cFile = sc.nextLine();
			FileReader fr = new FileReader("ch13/" + sFile);
			FileWriter fw = new FileWriter("ch13/" + cFile);  // ���� ����
			int c;
			while((c = fr.read()) != -1) {
				fw.write(c);
			}
			fw.close();
			fr.close();
			System.out.println("Copy End~~~");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

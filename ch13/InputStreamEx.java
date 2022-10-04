package ch13;

import java.io.InputStream;

public class InputStreamEx {

	public static void main(String[] args) {
		InputStream in = System.in;	// 키보드 // in : static 변수(객체생성없이 사용)
		try {
			// throws 메소드는 반드시 예외처리 해야함
			while(true) {
				int i = in.read();	// 키보드 입력 전까지 대기 상태 -> 키보드 입력 실행
				if(i==-1) break;	// ctrl+z
				System.out.print((char)i);
			}
		} catch (Exception e) {
			e.printStackTrace();	// 예외가 일어난 경로 및 메시지
		}
	}

}

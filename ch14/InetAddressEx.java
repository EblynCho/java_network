package ch14;

import java.net.InetAddress;
import java.util.Iterator;

public class InetAddressEx {

	public static void main(String[] args) {
		try {
			InetAddress add = InetAddress.getLocalHost();
			System.out.println("Host Name : " + add.getHostName());
			System.out.println("Host Address : " + add.getHostAddress());	
			add = InetAddress.getByName("auction.co.kr");
			System.out.println("옥션 Address : " + add.getHostAddress());
			
//			서버 여러개인 naver 사이트 확인
			InetAddress adds[] = InetAddress.getAllByName("naver.com");
			System.out.println("-----------------------");
			for (int i = 0; i < adds.length; i++) {
				System.out.println("naver : " + adds[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
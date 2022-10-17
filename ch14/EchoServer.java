package ch14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	int port = 8001;
	int cnt = 0;
	
	public EchoServer() {
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("ServerSocket Start......");
			while (true) {
				Socket sock = server.accept();  // Client가 접속할때까지 대기 상태(Thread)
				EchoThread et = new EchoThread(sock);
				et.start();  // run 메소드 호출
				cnt++;
				System.out.println("Client " + cnt + " Socket");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 내부 클래스 -> 외부 클래스와 아주 밀접한 관계가 있는 클래스, 장점 : 상속 가능
	class EchoThread extends Thread {  // 내부 클래스 -> EchoServer$EchoThread.class
		
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		
		public EchoThread(Socket sock) {
		
			try {
				this.sock = sock;
				in = new BufferedReader(
						new InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(sock.getOutputStream(), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			try {
				// Client 가 접속을 하면 가장 먼저 받는 메세지
				out.println("Hello Enter BYE to exit");  // print(X), println(O)
				while (true) {
					// Client 가 메세지 보내면 실행
					String line = in.readLine();
					if (line == null) break;  // Client 가 접속 끊으면 break
					else {
						out.println("Echo : " + line);
						if (line.equals("BYE")) break;
					}
				}
				in.close();
				out.close();
				sock.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new EchoServer();
	}

}
package ch14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer1 {
   
   ServerSocket server;
   int port = 8002;
   // Vector<�����ų ������Ÿ��> : ���׸�
   Vector<ClientThread1> vc;
   
   
   public ChatServer1() {
      try { // ���� ����
         server = new ServerSocket(port);
         vc = new Vector<ChatServer1.ClientThread1>(); 
      
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println("Error in Server");
         // 1�� ���������� ���Ḧ �ǹ� (�Ű������� 0�̸� �������� ����) : �Ϲ������λ��
         System.exit(1); 
      }
      System.out.println("*ChatServer1*********************");
      System.out.println("*client ������ ��ٸ����ֽ��ϴ�.*");
      System.out.println("*********************************");
      
      try {
         while(true) { // ���ѹݺ���
            Socket sock = server.accept();
            ClientThread1 ct = new ClientThread1(sock);
            ct.start(); // ������ �����췯�� ��� -> run �޼ҵ� ȣ��
            vc.addElement(ct); // ClientThread ��ü�� ���Ϳ� ����
         }
         
      } catch (Exception e) {
         System.err.println("Error in sock");
         e.printStackTrace();
      }
   }
   
   // ��� ���ӵ� Client���� �޼��� �����ϴ� �޼��� sendAllMessage()
   public void sendAllMessage(String msg) {
      // A�� Vector�� ���� ������ "�ȳ�" ������ A, B, C ��ο��� �޼����� ������
      for (int i = 0; i < vc.size(); i++) {
         // Vector�� ����� ClientThread�� ���������� ������
         ClientThread1 ct = vc.get(i);
         // ClientThread ������ �ִ� ������ �޼��� ������ �޼ҵ� ȣ��
         //          -> ��� ���ӵǾ��ִ� Ŭ���̾�Ʈ���� ������ �޼����� ������
         ct.sendMessage(msg);
      }
   }
   
   // ������ ������ ClientThread�� Vector���� �����������
   public void removeClient(ClientThread1 ct) {
      vc.remove(ct);
   }
   
   
   
   class ClientThread1 extends Thread{ // ����Ŭ����
      
      Socket sock;
      BufferedReader in;
      PrintWriter out;
      String id; // ����� id�� ������ ���� id ����
      
      public ClientThread1(Socket sock) { // ������
         // �ݺ���� try - catch��
         try {
            this.sock = sock;
            in = new BufferedReader(
                  new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);
            System.out.println(sock + "���ӵ�...");
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      @Override
      public void run() {
         try {
            // Client�� ó������ �޴� �޼���
            out.println("����Ͻ� ���̵� �Է��ϼ���.");
            id = in.readLine();
            // ��� ����ڵ鿡�� ������ ����� welcome �޼��� ����
            sendAllMessage("[" + id + "]���� �����Ͽ����ϴ�.");
            String line = "";
            while(true) {
               line = in.readLine(); // �޼��� ���ö����� ������
               if(line == null)
                  break;
               sendAllMessage("[" + id + "]" + line);
               } // --while
               in.close();
               out.close();
               removeClient(this);            
         } catch (Exception e) { // ä�� ����(run)�� ������ ��������
            removeClient(this); // ���� ��ü �ڽ�(Vector ������)
            System.err.println(sock + "������...");
         }
      }
      
      // Client ���� �޼��� ���� �޼ҵ�
      public void sendMessage(String msg) {
         out.println(msg);
      }
   }
   
   public static void main(String[] args) {
         new ChatServer1();

   }
}


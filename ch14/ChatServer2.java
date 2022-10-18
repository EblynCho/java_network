package ch14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ChatServer2 {

		   
   ServerSocket server;
   int port = 8003;
   // Vector<�����ų ������Ÿ��> : ���׸�
   Vector<ClientThread2> vc;
   
   
   public ChatServer2() {
      try { // ���� ����
         server = new ServerSocket(port);
         vc = new Vector<ChatServer2.ClientThread2>(); 	      
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println("Error in Server");
         // 1�� ���������� ���Ḧ �ǹ� (�Ű������� 0�̸� �������� ����) : �Ϲ������λ��
         System.exit(1); 
      }
      System.out.println("*ChatServer2.0*******************");
      System.out.println("*client ������ ��ٸ����ֽ��ϴ�.*");
      System.out.println("*********************************");
      
      try {
         while(true) { // ���ѹݺ���
            Socket sock = server.accept();
            ClientThread2 ct = new ClientThread2(sock);
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
         ClientThread2 ct = vc.get(i);
         // ClientThread ������ �ִ� ������ �޼��� ������ �޼ҵ� ȣ��
         //          -> ��� ���ӵǾ��ִ� Ŭ���̾�Ʈ���� ������ �޼����� ������
         ct.sendMessage(msg);
      }
   }
   
   // ������ ������ ClientThread�� Vector���� �����������
   public void removeClient(ClientThread2 ct) {
      vc.remove(ct);
   }
   
   // ���ӵ� ��� id ����Ʈ ex)aaa;bbb;ccc;��ȣ��;
   public String getIds() {
	   String ids = "";
	   for (int i = 0; i < vc.size(); i++) {
		   ClientThread2 ct = vc.get(i);
		   ids += ct.id + ";";
	   }
	   return ids;
   }
   
   // ������ ClientThread2 �˻�
   public ClientThread2 findClient(String id) {
	   ClientThread2 ct = null;
	   for (int i = 0; i < vc.size(); i++) {
		   ct = vc.get(i);
		   if(id.equals(ct.id)) {
			   break;
		   }//--if
	   }//--for
	   return ct;
   }
   
   class ClientThread2 extends Thread{ // ����Ŭ����
      
      Socket sock;
      BufferedReader in;
      PrintWriter out;
      String id; // ����� id�� ������ ���� id ����
      
      public ClientThread2(Socket sock) { // ������
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
            while(true) {
            	String line = in.readLine();
            	if(line == null)
            		break;
            	else
            		routine(line);  // routine �޼ҵ� ���ؼ� protocol �м�
            }               
            in.close();
            out.close();
            removeClient(this);            
         } catch (Exception e) { // ä�� ����(run)�� ������ ��������
            removeClient(this); // ���� ��ü �ڽ�(Vector ������)
            System.err.println(sock + "������...");
         }
      }
      
      public void routine(String line) {
    	  // CHATALL:[aaa]������ �������Դϴ�. �׸� �ұ��?
    	  System.out.println(line);
    	  int idx = line.indexOf(ChatProtocol2.DM);  // :
    	  String cmd = line.substring(0, idx); // CHATALL
    	  String data = line.substring(idx+1); // [aaa]������ �������Դϴ�.
    	  if(cmd.equals(ChatProtocol2.ID)) {
    		  id = data; // aaa
    		  // ���ο� �����ڰ� �߰��Ǿ��� ������ ����Ʈ ������ (���� ����Ʈ remove)
    		  sendAllMessage(ChatProtocol2.CHATLIST+ChatProtocol2.DM+getIds());
    		  // welcome �޼��� ����
    		  sendAllMessage(ChatProtocol2.CHATALL+ChatProtocol2.DM+
    				  "["+id+"]���� �����Ͽ����ϴ�.");
    	  } else if(cmd.equals(ChatProtocol2.CHAT)) {
    		  // data:bbb;�����
    		  idx = data.indexOf(';');
    		  cmd = data.substring(0, idx); // bbb
    		  data = data.substring(idx+1); // data
    		  ClientThread2 ct = findClient(cmd);
    		  if(ct!=null) {  // ���� ������
    			  // ct�� bbb
    			  ct.sendMessage(ChatProtocol2.CHAT+ChatProtocol2.DM+
    					  "["+id+"(S)]" + data); // CHAT:[aaa(S)]�����
    			  sendMessage(ChatProtocol2.CHAT+ChatProtocol2.DM+
    					  "["+id+"(S)]" + data); // CHAT:[aaa(S)]�����  // �ϴ� ������ ���ٰ� �����Ե� ����
    		  } else {  // bbb�� ������ �ȵ� ���
    			  sendMessage(ChatProtocol2.CHAT+ChatProtocol2.DM+
    					  "["+cmd+"]�� �����ڰ� �ƴմϴ�.");
    		  }
    	  } else if(cmd.equals(ChatProtocol2.MESSAGE)) {
    		  idx = data.indexOf(';');
    		  cmd = data.substring(0, idx); // bbb
    		  data = data.substring(idx+1); // data
    		  ClientThread2 ct = findClient(cmd);
    		  if(ct!=null) {  // ���� ������
    			  ct.sendMessage(ChatProtocol2.MESSAGE+ChatProtocol2.DM+
    					  id+";]" + data);
    		  } else {  // bbb�� ������ �ȵ� ���
    			  sendMessage(ChatProtocol2.CHAT+ChatProtocol2.DM+
    					  "["+cmd+"]�� �����ڰ� �ƴմϴ�.");
    		  }
    	  } else if(cmd.equals(ChatProtocol2.CHATALL)) {
    		  sendAllMessage(ChatProtocol2.CHATALL+ChatProtocol2.DM+
    				  "["+id+"]"+data);
    	  }
      }
      
	// Client ���� �޼��� ���� �޼ҵ�
      public void sendMessage(String msg) {
         out.println(msg);
      }
   }
   
   public static void main(String[] args) {
         new ChatServer2();

   }
}




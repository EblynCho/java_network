package ch14;

class MyThread extends Thread {

	String name;
	public MyThread(String name) {
		this.name = name;
	}
	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println(name + " : " + i);
				Thread.sleep(500);  // 0.5�� ������
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
public class ThreadEx {

	public static void main(String[] args) {

		MyThread m1 = new MyThread("ù��°");
		MyThread m2 = new MyThread("�ι�°");
		// Thread �����ٷ����� ���� �غ� �� �̶�� ���
		m1.start();  // ��������� run ȣ�� (multi Thread)
		m2.start();
//		m1.run();  // ���� Thread
//		m2.run();
	
	}

}
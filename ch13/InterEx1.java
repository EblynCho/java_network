package ch13;

interface A {
	int i = 10;  // �ڵ� ���; static final
	void prn();  // �޼ҵ�� ����θ�, �����δ� ����
}

interface Calcu {
	void plus(int i, int j);
}

class Function extends Object implements Calcu {
	@Override
	public void plus(int i, int j) {
	}
}

class Graphic implements Calcu {
	@Override
	public void plus(int i, int j) {
	}
}

public class InterEx1 {

	public static void main(String[] args) {

	}

}

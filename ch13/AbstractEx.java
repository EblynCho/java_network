package ch13;

import java.awt.Button;
import java.awt.Color;
import java.awt.Label;

// �߻�Ŭ���� �� �޼ҵ� ����

abstract class Shape {
	abstract void draw();	// �߻�޼ҵ� : ��ü�� ���� ���� ����
}


class Rectangle extends Shape{
	@Override
	void draw() {
			
	}
}

class Circle extends Shape{
	@Override
	void draw() {
		
	}
}

public class AbstractEx extends Object {
	public AbstractEx() {
		super();	// ����Ŭ���� ������ ; Object
	}
	public static void main(String[] args) {
		AbstractEx ae = new AbstractEx();
		// java.awt;
		Button btn = new Button("��ư");
		btn.setBackground(Color.RED);
		Label label = new Label("��");
		
		try {
			int arr[] = new int[3];
			arr[3] = 22;
		} catch (Exception e) {
			System.out.println("�迭����");
		}
		
	}
}

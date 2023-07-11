package org.sp.app0711.thread1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
★★★★★ 
1) [ Thread(쓰레드) ]란?
- 하나의 "프로세스(=실행중인 프로그램) 내"에서 "독립적"으로 실행될 수 있는 세부실행단위

*쓰레드와 프로세스의 차이점
							[OS] 						[jvm]
멀티태스킹대상				프로세스 					쓰레드
병행처리원리				시분할 						시분할

2) 쓰레드의 생명주기 (LifeCycle)
- 작성: 로직은 run() 메서드를 오버라이드해야함
- 생성: 개발자가 new
- Runnable 상태로 진입시킴 : start() 호출
  jvm의 실행 대상이됨, 즉 실행 후보가됨
- jvm의 내부 알고리즘에 의해 랜덤하게 특정 쓰레드가 선택되고, 
  이 선택된 쓰레드에 run()를 실행하는 단계를 Running이라고 함
- 소멸: run()메서드의 닫는 }(브레이스)를 만나면 쓰레드는 소멸됨
		이때의 생명주기 상태를 dead라고 함
		
3) 대부분의 프로그래밍 언어에서는 쓰레드를 지원함

 */

public class ThreadTest extends JFrame{
	JButton bt; //시작 버튼
	JLabel la1; //+1씩 증가하는 숫자를 보여줄 라벨
	JLabel la2; //-5씩 감소하는 숫자를 보여줄 라벨
	
	int n;
	
	public ThreadTest() {
		bt=new JButton("START");
		la1=new JLabel("0");
		la2=new JLabel("0");
		
		la1.setBackground(Color.YELLOW);
		la2.setBackground(Color.YELLOW);
		la1.setFont(new Font("Verdana", Font.BOLD, 100));
		la2.setFont(new Font("Verdana", Font.BOLD, 100));
		la1.setPreferredSize(new Dimension(270, 170));
		la2.setPreferredSize(new Dimension(270, 170));
		
		setLayout(new FlowLayout());
		add(bt);
		add(la1);
		add(la2);
		
		setSize(300, 400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//funal int x=9;
		
		//이벤트 연결코드는 재사용성이 없기때문에 되도록이면 코드가 간소화 될수록 좋음
		//따라서 이벤트 구현시 내부익명클래스를 지원함
		//java8 람다(Lambda) : 굳이 이벤트 연결코드를 객체로까지 갈 필요가 있는지?
		//그냥 함수로 표현하는 방법을 지원함  ex) js에서의 화살표함수와 흡사함
		bt.addActionListener(new ActionListener() {
			//내부익명클래스 내의 메서드에서는 바깥쪽 외부 클래스(ThreadTest)의 멤버변수를 내것처럼 사용할 수 있음
			public void actionPerformed(ActionEvent e) {
				System.out.println("버튼뿅");
				//x=9; //내부익명클래스에선 지역변수로 접근하려면 해당 지역변수는 final로 선언되어 있어야함
				
				createThread();
				
			}
		});
	}
	
	//버튼을 누르면 2개의 쓰레드를 생성해 jvm에게 맡기자
	//왜? 시분할로 멀티쓰레드 구현을 위함
	//!!메인 실행부는 절대로, 대기상태나 무한루프에 빠뜨리면 안됨!!
	//왜? 
	//메인실행부의 역할 1) gui에서 사용자의 이벤트 처리, 그래픽 처리
	public void createThread() {
		/*
		while(true) {
			System.out.println("뿅");
		}
		*/
		AddThread t1=new AddThread(this);
		t1.start(); //Runnable상태로 진입
		
		//MinusThread t2=new MinusThread();
		//t2.start();
		//내부익명클래스로 해보자
		Thread t2=new Thread() {
			public void run() {
				while(true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					
					n-=5;
					la2.setText(Integer.toString(n));
				}
			}
		};
		t2.start();
		
		
		//동기 방식 : 순서가有. 안정적. but 대기상태에 빠짐.
		//비동기 방식 (Asynchronous) : 순서가無
		//쓰레드를 이용하는 코드는 비동기방식으로 동작되므로 메인쓰레드는 개발자가 정의한 쓰레드의 실행완료를 기다리지 않음.
		//비동기방식이란? 특정 코드가 끝날때까지 기다리지않고 다른 코드를 실행하는 방식
		System.out.println("메인쓰레드에 의해 실행");
	}
	
	//"메인 쓰레드". 자바는 쓰레드 기반의 언어임
	public static void main(String[] args) {
		//아래의 코드를 실행하면, 에러메세지에 main thread에서 에러가 발생했다는 메세지가 출력됨
		//결론: 자바의 실행부라고 불렸던 것은 사실 메인 쓰레드였음
		//메인 쓰레드는 프로그램을 운영하는 실행부이므로, 대기상태나 루프에 빠뜨리지 말 것.
		/*int[] arr=new int[2];
		arr[0]=5;
		arr[1]=7;
		arr[2]=9;*/
		
		new ThreadTest();
	}
}

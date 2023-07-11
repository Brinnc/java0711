package org.sp.app0711.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//1) 실제로 모든 게임의 그래픽이 표현될 컨테이너
public class GamePanel extends JPanel implements KeyListener{
	public static final int WIDTH=1200;
	public static final int HEIGHT=700;
	
	//메인쓰레드를 루프에 빠뜨리지않고 별도의 개발자정의 쓰레드를 만들어 루프를 실행함
	//메인쓰레드를 루프나 대기 상태에 빠뜨리지 않아야 하는  이유?
	//메인쓰레드는 프로그램의 실행과 운영을 담당하는 메인 실행부이므로 대기나 루프에 빠지는 순간
	//프로그램 운영이 멈추고 이벤트 감지조차 불가하게 됨
	Thread loopThread;
	
	Image bgImg; //배경이미지
	
	Hero hero; 
	//Bullet bullet;
	List<Bullet> BulletList=new ArrayList<Bullet>();
	
	public GamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		createBg(); //배경그리기
		createHero(); //주인공등장
		
		//게임 루프 생성하기
		loopThread=new Thread() {
			
			public void run() {
				while(true) {
					try {
						Thread.sleep(1); // 1/1000초 동안 non-runnable 진입
						//하지만 지정한 시간이 지나면 스스로 runnable 복귀
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				
					loop();
					
				}
			}
		};
		loopThread.start(); //runnable 진입
		
		//패널에 키보드와 관련된 이벤트 연결
		this.addKeyListener(this);
		
		
	}
	
	//개발자가 주도하여 그림을 뺏어서 그려야 게임을 처리할 수 있음
	//결론) 컴포넌트가 보유한 페인트paint() 메서드를 재정의 하자
	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(bgImg, 0, 0, WIDTH, HEIGHT, this);
		
		hero.render(g);
		
		for(int i=0; i<BulletList.size(); i++) {
			BulletList.get(i).render(g);
			//bullet.tick();
		}
		
	}
	
	//배경이미지 만들기
	public void createBg() {
		URL url=ClassLoader.getSystemResource("res/hero/gamebg00.jpg");
		BufferedImage buffImg=null; //기존 이미지 객체보다 크기, 편집이 용이한 이미지 객체
		try {
			buffImg=ImageIO.read(url);
			bgImg=buffImg;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//주인공 생성
	public void createHero() {
		hero=new Hero(0, 200, 100, 65, 0, 0);
	}
	
	//주인공의 좌우움직임
	public void moveX(int velX) {
		hero.velX=velX;
	}
	//주인공의 상하움직임
	public void moveY(int velY) {
		hero.velY=velY;
	}
	
	//주인공 총알 발사
	public void fire() {
		Bullet bullet=new Bullet(hero.x+hero.width, hero.y+(hero.height/2), 25, 25, 5, 0);
		BulletList.add(bullet);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("키보드뿅");
		int key=e.getKeyCode(); //아스키코드
		 switch(key) {
		 	case KeyEvent.VK_LEFT : moveX(-2);break;
		 	case KeyEvent.VK_RIGHT : moveX(+2);break;
		 	case KeyEvent.VK_UP : moveY(-2);break;
		 	case KeyEvent.VK_DOWN : moveY(+2);break;
		 	case KeyEvent.VK_SPACE : fire();break;
		 	
		 }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("키보드뿅");
				int key=e.getKeyCode(); //아스키코드
				 switch(key) {
				 	case KeyEvent.VK_LEFT : moveX(0);break;
				 	case KeyEvent.VK_RIGHT : moveX(0);break;
				 	case KeyEvent.VK_UP : moveY(0);break;
				 	case KeyEvent.VK_DOWN : moveY(0);break;
				 	
				 }
	}
	
	//게임의 심장인 루프를 수행
	public void loop() {
		//System.out.println("loop() 수행중..");
		
		//주인공의 움직임
		hero.tick();
		//총알의 움직임
		for(int i=0; i<BulletList.size(); i++) {
			BulletList.get(i).tick();
			//bullet.tick();
		}
		
		repaint(); //paint()메서드는 개발자가 호출하지 못하므로, 간접적으로 repaint() 호출
		
	}
	
	
}

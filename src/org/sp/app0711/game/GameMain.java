package org.sp.app0711.game;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

//0) 게임메인프레임
public class GameMain extends JFrame{
	GamePanel gamePanel;
	
	public GameMain() {
		gamePanel=new GamePanel();
		add(gamePanel);
		
		pack();
		setVisible(true);
		setLocationRelativeTo(null); //js) margin:auto
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		//윈도우가 취득한 포커스를 패널에 전달하기
		gamePanel.requestFocus();
		
		//this.addKeyListener(this);
		//gamePanel.loop();
	}


	
	public static void main(String[] args) {
		new GameMain();
	}
}

package org.sp.app0711.game;

import java.awt.Graphics;

//3) 게임에 등장하는 모든 요소들의 최상위 객체
public abstract class GameObject {
	int x;
	int y;
	int width;
	int height;
	int velX;
	int velY;
	
	public GameObject(int x, int y, int width, int height, int velX, int velY) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.velX=velX;
		this.velY=velY;
	}
	
	//하위 모든 객체들의 움직임을 알수 없으므로, 아래의 메서드는 추상메서드로 선언해야하고
	//이 메서드의 완성은 자식에게 맡김 -> 구현강제
	public abstract void tick();
	
	//모든 게임에 등장하는 요소들은 결국 게임패널에 그려지기 때문에
	//아래의 메서드의 g객체는 게임패널의 g를 얻어와야함
	public abstract void render(Graphics g);
		
	
	
}

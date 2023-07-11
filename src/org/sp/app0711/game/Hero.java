package org.sp.app0711.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

//4) 
public class Hero extends GameObject{
	Image image; //주인공이미지

	public Hero(int x, int y, int width, int height, int velX, int velY) {
		super(x, y, width, height, velX, velY);
		
		URL url=ClassLoader.getSystemResource("res/hero/Biplane_HD.png"); //.java가 아니기때문에 슬래쉬
		BufferedImage buffImg=null;
		try {
			buffImg=ImageIO.read(url);
			image=buffImg;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void tick() {
		x+=velX;
		y+=velY;
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, x, y, width, height, null); 
		//hero가 메모리에 올라갈 때 결국 부모인 GameObject와 함께 올라감
		//따라서 x, y, width, height 값은 hero의 것이기도 하지만 부모인 GameObject의 것이기도 하므로
		//부모 생성자의 x, y, ...로도 접근 가능하다고 할 수 있음
		
	}

}

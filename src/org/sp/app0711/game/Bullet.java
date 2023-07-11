package org.sp.app0711.game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet extends GameObject{
	Image image;

	public Bullet(int x, int y, int width, int height, int velX, int velY) {
		super(x, y, width, height, velX, velY);
		
		try {
			image=ImageIO.read(ClassLoader.getSystemResource("res/hero/ball.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		x+=velX;
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, x, y, width, height, null);
		
	}

}

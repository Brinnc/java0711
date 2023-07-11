package test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ClassLoaderApp {
	
	public ClassLoaderApp() {
		//자바의 여러 클래스 중 클래스에 대한 정보를 가진 Class(객체명)
		//url 자바에선 자원의 위치	
		URL url=ClassLoader.getSystemResource("res/hero/gamebg00.jpg"); 
		//기존 이미지 객체보다 크기나, 사이즈 조절이 가능한 이미지를 말함
		//따라서 Image보다 활용도가 높음
		try {
			BufferedImage buffimg=ImageIO.read(url);
			System.out.println(buffimg);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	
	}
	
	public static void main(String[] args) {
		new ClassLoaderApp();
	}

}

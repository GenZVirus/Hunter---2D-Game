package com.GenZVirus.Level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.GenZVirus.Audio.PlayMusic;
import com.GenZVirus.Entity.Mob.Shooter;
import com.GenZVirus.Entity.Mob.Star;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {

		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load SpawnLevel file");
		}		
		PlayMusic BackgroundMusic = new PlayMusic("res/Sounds/LightAndShadow.wav");
		BackgroundMusic.play();
		// add(new Chaser(20, 6));
		//add(new Star(20, 20));
		add(new Shooter(20, 6));
		for (int i = 0; i < 5; i++) {
			// add(new Dummy(20, 6));
		}
	}

	protected void generateLevel() {

	}

}

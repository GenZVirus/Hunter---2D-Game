package com.GenZVirus.Audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlayMusic {

	private File music;

	public PlayMusic(String filePath) {
		try {
			music = new File(filePath);
			if (!music.exists()) {
				System.out.println("File not found at " + filePath);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		try {
		AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
		Clip clip = AudioSystem.getClip();
		clip.open(audioInput);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

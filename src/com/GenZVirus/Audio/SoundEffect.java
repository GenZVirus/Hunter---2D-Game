package com.GenZVirus.Audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffect {

	private File sound;
	public Clip clip;

	private boolean isPlaying = false;

	public SoundEffect(String string) {
		sound = new File(string);
		if (!sound.exists()) System.out.println("Sound effect file can't be found!");
		else try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(sound);
			clip = AudioSystem.getClip();
			clip.open(audioInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update() {

	}

	public void play(Boolean loop) {
		if (isPlaying) return;
		clip.setFramePosition(0);
		isPlaying = true;
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		if (isPlaying) {
			clip.stop();
			isPlaying = false;
			clip.setFramePosition(0);
		}
	}

}

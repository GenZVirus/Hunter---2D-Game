package com.GenZVirus.Level.Tile;

import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;

public class GrassTile extends Tile {

	public GrassTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, sprite);
	}

}

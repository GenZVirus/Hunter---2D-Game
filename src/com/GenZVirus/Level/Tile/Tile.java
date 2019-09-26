package com.GenZVirus.Level.Tile;

import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;

public class Tile {

	public Sprite sprite;

	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile wall = new WallTile(Sprite.wall);
	public static Tile gravelFloor = new GravelFloorTile(Sprite.gravelFloor);
	public static Tile woodFloor = new WoodFloorTile(Sprite.woodFloor);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);

	public static final int col_grass = 0xff00ff00;
	public static final int col_flower = 0xffffff00;
	public static final int col_rock = 0xff808080;
	public static final int col_wall = 0xff303030;
	public static final int col_gravelFloor = 0xff404040;
	public static final int col_woodFloor = 0xff2D1106;

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {

	}

	public boolean solid() {
		return false;
	}

}

package com.GenZVirus.Graphics;

public class Sprite {

	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;

	// Terrain Sprites here:
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.spawnTiles);
	public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.spawnTiles);
	public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.spawnTiles);
	public static Sprite wall = new Sprite(16, 0, 1, SpriteSheet.spawnTiles);
	public static Sprite gravelFloor = new Sprite(16, 3, 0, SpriteSheet.spawnTiles);
	public static Sprite woodFloor = new Sprite(16, 1, 1, SpriteSheet.spawnTiles);
	public static Sprite voidSprite = new Sprite(16, 0x00009f);

	// Projectile Sprites:
	public static Sprite fire_ball = new Sprite(16, 1, 1, SpriteSheet.tiles);

	// Particles
	public static Sprite particle_normal = new Sprite(3, 0xffffff);

	// Player Sprites:
	public static Sprite player_forward = new Sprite(32, 0, 5, SpriteSheet.tiles);
	public static Sprite player_forward_1 = new Sprite(32, 0, 6, SpriteSheet.tiles);
	public static Sprite player_forward_2 = new Sprite(32, 0, 7, SpriteSheet.tiles);

	public static Sprite player_backward = new Sprite(32, 2, 5, SpriteSheet.tiles);
	public static Sprite player_backward_1 = new Sprite(32, 2, 6, SpriteSheet.tiles);
	public static Sprite player_backward_2 = new Sprite(32, 2, 7, SpriteSheet.tiles);

	public static Sprite player_side = new Sprite(32, 1, 5, SpriteSheet.tiles);
	public static Sprite player_side_1 = new Sprite(32, 1, 6, SpriteSheet.tiles);
	public static Sprite player_side_2 = new Sprite(32, 1, 7, SpriteSheet.tiles);

	public static Sprite dummy = new Sprite(32, 0, 0, SpriteSheet.dummy_down);

	protected Sprite(SpriteSheet sheet, int width, int height) {
		if (width == height) SIZE = width;
		else SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[SIZE * SIZE];
		this.sheet = sheet;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite(int width, int height, int colour) {
		this.width = width;
		this.height = height;
		this.SIZE = width;
		pixels = new int[width * height];
		setColourWH(colour);
	}

	public Sprite(int size, int colour) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColourSIZE(colour);
	}

	public Sprite(int[] pixels, int width, int height) {
		if (width == height) SIZE = width;
		else SIZE = -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}

	public static Sprite Rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}

	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];

		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 1.0);

		double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xffff00ff;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}

		return result;
	}

	private static double rot_x(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * cos + y * -sin;
	}

	private static double rot_y(double angle, double x, double y) {
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		return x * sin + y * cos;
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}
				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}

		return sprites;
	}

	public Sprite(int[] pixels, int size) {
		SIZE = width = height = size;
		this.pixels = pixels;
	}

	private void setColourSIZE(int colour) {
		for (int i = 0; i < SIZE * SIZE; i++) {
			pixels[i] = colour;
		}
	}

	private void setColourWH(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}

}

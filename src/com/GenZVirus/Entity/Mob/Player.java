package com.GenZVirus.Entity.Mob;

import com.GenZVirus.Audio.SoundEffect;
import com.GenZVirus.Entity.Projectile.FireBall;
import com.GenZVirus.Entity.Projectile.Projectile;
import com.GenZVirus.Graphics.AnimatedSprite;
import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;
import com.GenZVirus.Graphics.SpriteSheet;
import com.GenZVirus.Graphics.UI.UILabel;
import com.GenZVirus.Graphics.UI.UIManager;
import com.GenZVirus.Graphics.UI.UIPanel;
import com.GenZVirus.Input.Keyboard;
import com.GenZVirus.Input.Mouse;
import com.GenZVirus.Level.Tile.Tile;
import com.GenZVirus.Main.Game;
import com.GenZVirus.Util.Vector2i;

public class Player extends Mob {

	private Keyboard input;
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private SoundEffect sound = new SoundEffect("res/Sounds/LightAndShadow.wav");
	private SoundEffect woodSound = new SoundEffect("res/Sounds/WalkingOnWood.wav");
	private SoundEffect gravelSound = new SoundEffect("res/Sounds/WalkingOnGravel.wav");
	private SoundEffect stoneSound = new SoundEffect("res/Sounds/WalkingOnStone.wav");
	private SoundEffect grassSound = new SoundEffect("res/Sounds/WalkingOnGrass.wav");

	private UIManager ui;

	Projectile p;
	private int fireRate = 0;

	public Player(Keyboard input) {
		this.input = input;
		sprite = Sprite.player_forward;
		animSprite = down;
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_forward;
		fireRate = FireBall.FIRE_RATE;

		ui = Game.getUIManager();
		UIPanel panel = new UIPanel(new Vector2i(300 - 80, 0));
		ui.addPanel(panel);
		panel.addComponent(new UILabel(new Vector2i(-10, 2), "Hello"));
	}

	public void update() {
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (fireRate > 0) {
			fireRate--;
		}

		double xa = 0, ya = 0;
		double speed = 2.0;
		if (input.up) {
			ya -= speed;
			animSprite = up;
		} else if (input.down) {
			ya += speed;
			animSprite = down;
		}
		if (input.left) {
			xa -= speed;
			animSprite = left;
		} else if (input.right) {
			xa += speed;
			animSprite = right;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;

		} else walking = false;
		if (walking) {
			if (level.getTile((int) x / 16, (int) y / 16) == Tile.grass) {
				if (sound != grassSound) sound.stop();
				sound = grassSound;
			} else if (level.getTile((int) x / 16, (int) y / 16) == Tile.gravelFloor) {
				if (sound != gravelSound) sound.stop();
				sound = gravelSound;
			} else if (level.getTile((int) x / 16, (int) y / 16) == Tile.woodFloor) {
				if (sound != woodSound) sound.stop();
				sound = woodSound;
			}
			sound.play(true);
		} else sound.stop();
		clear();
		updateShooting();

	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}

	}

	private void updateShooting() {
		if (Mouse.getButton() == 1 && fireRate <= 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = (Mouse.getY() - Game.getWindowHeight() / 2);
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = FireBall.FIRE_RATE;
		}

	}

	public void render(Screen screen) {
		int flip = 0;
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite, flip);
	}

}

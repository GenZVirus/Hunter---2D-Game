package com.GenZVirus.Entity.Mob;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.GenZVirus.Audio.SoundEffect;
import com.GenZVirus.Entity.Projectile.FireBall;
import com.GenZVirus.Entity.Projectile.Projectile;
import com.GenZVirus.Graphics.AnimatedSprite;
import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;
import com.GenZVirus.Graphics.SpriteSheet;
import com.GenZVirus.Graphics.UI.UIActionListener;
import com.GenZVirus.Graphics.UI.UIButton;
import com.GenZVirus.Graphics.UI.UIButtonListenerIMG;
import com.GenZVirus.Graphics.UI.UILabel;
import com.GenZVirus.Graphics.UI.UIManager;
import com.GenZVirus.Graphics.UI.UIPanel;
import com.GenZVirus.Graphics.UI.UIProgressBar;
import com.GenZVirus.Input.Keyboard;
import com.GenZVirus.Input.Mouse;
import com.GenZVirus.Level.Tile.Tile;
import com.GenZVirus.Main.Game;
import com.GenZVirus.Util.ImageUtils;
import com.GenZVirus.Util.Vector2i;
import com.GenZVirus.events.Event;
import com.GenZVirus.events.EventDispatcher;
import com.GenZVirus.events.EventListener;
import com.GenZVirus.events.types.MousePressedEvent;
import com.GenZVirus.events.types.MouseReleasedEvent;

public class Player extends Mob implements EventListener {

	private String name;
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
	private UIProgressBar uiHealthBar;
	private UIButton button;
	private UIPanel panelHealth;
	private UIPanel panelButtons;

	private BufferedImage image = null, imageHover = null, imagePressed = null;

	Projectile p;
	private int fireRate = 0;

	private boolean shooting = false;

	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
		sprite = Sprite.player_forward;
		animSprite = down;
	}

	public Player(String name, int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_forward;
		fireRate = FireBall.FIRE_RATE;

		ui = Game.getUIManager();
		panelHealth = (UIPanel) new UIPanel(new Vector2i(0, 0), new Vector2i(80 * 3, 30 * 3)).setColor(0xff4f4f4f);
		ui.addPanel(panelHealth);
		panelHealth.addComponent(((UILabel) new UILabel(new Vector2i(10, 10 + 32), name).setColor(0xffcacaca)).setFont(new Font("helvetica", Font.BOLD, 24)));

		uiHealthBar = new UIProgressBar(new Vector2i(10, 50), new Vector2i(80 * 3 - 20, 15));
		uiHealthBar.setColor(0xff6a6a6a);
		uiHealthBar.setForegroundColor(0xffff3a3a);
		panelHealth.addComponent(uiHealthBar);

		// player default health
		currentHealth = maxHealth = 100;

		panelButtons = (UIPanel) new UIPanel(new Vector2i(260, 400), new Vector2i(130 * 3, 30 * 3)).setColor(0xff4f4f4f);
		ui.addPanel(panelButtons);

		button = new UIButton(new Vector2i(0, 0), new Vector2i(60, 30), new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});
		button.setText("Exit");
		// panelButtons.addComponent(button);

		try {
			image = ImageIO.read(new File("res/textures/Ability.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		imageHover = ImageUtils.changeBrightness(image, -50);
		imagePressed = ImageUtils.changeBrightness(image, 50);

		UIButton Ability1 = new UIButton(new Vector2i(10, 15), image, new UIActionListener() {
			public void perform() {
				for (double i = 0.0; i < 2 * Math.PI; i += Math.PI / 12) {
					shoot(level.getClientPlayer().getX(), level.getClientPlayer().getY(), i, level.getClientPlayer());
				}
			}
		});
		Ability1.setButtonListener(new UIButtonListenerIMG(image, imageHover, imagePressed));
		panelButtons.addComponent(Ability1);

		UIButton Ability2 = new UIButton(new Vector2i(84, 15), image, new UIActionListener() {
			public void perform() {
				for (double i = 0.0; i < 2 * Math.PI; i += Math.PI / 12) {
					shoot2(level.getClientPlayer().getX(), level.getClientPlayer().getY(), i, level.getClientPlayer(), i);
				}
			}
		});
		Ability2.setButtonListener(new UIButtonListenerIMG(image, imageHover, imagePressed));
		panelButtons.addComponent(Ability2);

		UIButton Ability3 = new UIButton(new Vector2i(158, 15), image, new UIActionListener() {
			public void perform() {
				for (double i = 0.0; i < 2 * Math.PI; i += Math.PI / 12) {
					shield(level.getClientPlayer().getX(), level.getClientPlayer().getY(), i, level.getClientPlayer(), i, 500);
				}
			}
		});
		Ability3.setButtonListener(new UIButtonListenerIMG(image, imageHover, imagePressed));
		panelButtons.addComponent(Ability3);

		UIButton Ability4 = new UIButton(new Vector2i(232, 15), image, new UIActionListener() {
			public void perform() {
				if (currentHealth <= 50) currentHealth += 50;
				else currentHealth = maxHealth;
			}
		});
		Ability4.setButtonListener(new UIButtonListenerIMG(image, imageHover, imagePressed));
		panelButtons.addComponent(Ability4);

		UIButton Ability5 = new UIButton(new Vector2i(306, 15), image, new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});
		Ability5.setButtonListener(new UIButtonListenerIMG(image, imageHover, imagePressed));
		panelButtons.addComponent(Ability5);
	}

	public String getName() {
		return name;
	}

	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));

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

		uiHealthBar.setProgress((double) (currentHealth) / maxHealth);

	}

	private void updateShooting() {
		if (!shooting || fireRate > 0) {
			if (Mouse.mouseB == MouseEvent.NOBUTTON) shooting = false;
			return;
		}
		double dx = Mouse.getX() - Game.getWindowWidth() / 2;
		double dy = (Mouse.getY() - Game.getWindowHeight() / 2);
		double dir = Math.atan2(dy, dx);
		shoot(x, y, dir, this);
		fireRate = FireBall.FIRE_RATE;
	}

	public boolean onMousePressed(MousePressedEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = true;
			return true;
		}
		return false;
	}

	public boolean onMouseReleased(MouseReleasedEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = false;
			return true;
		}
		return false;
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}

	}

	public void render(Screen screen) {
		int flip = 0;
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 16), (int) (y - 16), sprite, flip);
	}

}

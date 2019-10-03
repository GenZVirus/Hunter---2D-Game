package com.GenZVirus.Entity.Projectile;

import java.util.Random;

import com.GenZVirus.Entity.Entity;
import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;

public class ShieldBall extends Projectile {

	private double pos = 0;
	private int lifeTime;
	private int time = 0;

	public ShieldBall(double x, double y, double dir, Entity shooter, double pos, int lifeTime) {
		super(x, y, dir, shooter);
		this.pos = pos;
		this.lifeTime = lifeTime;
		sprite = Sprite.shield;
		speed = 3;
	}

	public void update() {
		time++;
		if (time >= 7000) time = 0;
		if (time > lifeTime) {
			remove();
		}
		Random random = new Random();
		pos += Math.PI / 12 / speed;
		x = shooter.getX() + Math.cos(pos) * (random.nextInt(1) + 25);
		y = shooter.getY() + Math.sin(pos) * (random.nextInt(1) + 25);
		if (pos >= 2 * Math.PI) pos = 0;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - 8, (int) y - 2, this);
	}

}

package com.GenZVirus.Entity.Projectile;

import com.GenZVirus.Entity.Spawner.ParticleSpawner;
import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;

public class FireBall extends Projectile {

	public static final int FIRE_RATE = 20; // Higher is slower!

	public FireBall(double x, double y, double dir) {
		super(x, y, dir);
		range = 200;
		speed = 4;
		damage = 20;
		sprite = sprite.Rotate(Sprite.fire_ball, angle);
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 8, 5, 5)) {
			level.add(new ParticleSpawner((int) x, (int) y, 100, 50, level));
			remove();
		}
		move();
	}

	protected void move() {

		x += nx;
		y += ny;
		if (distance() > range) remove();
	}

	private double distance() {
		double distance = 0;
		distance = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return distance;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - 8, (int) y - 2, this);
	}

}

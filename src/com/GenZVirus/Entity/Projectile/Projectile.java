package com.GenZVirus.Entity.Projectile;

import com.GenZVirus.Entity.Entity;
import com.GenZVirus.Graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double nx, ny;
	protected double speed, range, damage;
	protected Entity shooter;

	public Projectile(double x, double y, double dir, Entity shooter) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
		this.shooter = shooter;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getSpriteSize() {
		return sprite.SIZE;
	}

	protected void move() {

	}

	public Entity getShooter() {
		return shooter;
	}
}

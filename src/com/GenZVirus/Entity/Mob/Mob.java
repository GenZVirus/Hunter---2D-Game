package com.GenZVirus.Entity.Mob;

import com.GenZVirus.Entity.Entity;
import com.GenZVirus.Entity.Projectile.FireBall;
import com.GenZVirus.Entity.Projectile.Projectile;
import com.GenZVirus.Entity.Projectile.SeekerMissle;
import com.GenZVirus.Entity.Projectile.ShieldBall;
import com.GenZVirus.Graphics.Screen;

public abstract class Mob extends Entity {

	protected boolean moving = false;
	protected boolean walking = false;
	public double currentHealth;
	protected double maxHealth;

	protected enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	protected Direction dir;

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;

		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!collision(abs(xa), ya)) this.x += abs(xa);
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya)) this.x += xa;
				xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))) this.y += abs(ya);
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) this.y += ya;
				ya = 0;
			}
		}
	}

	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}

	public abstract void update();

	protected void shoot(double x, double y, double dir, Entity shooter) {
		Projectile p = new FireBall(x, y, dir, shooter);
		level.add(p);

	}

	protected void shoot2(double x, double y, double dir, Entity shooter, double pos) {
		Projectile p = new SeekerMissle(x, y, dir, shooter, pos);
		level.add(p);
	}

	protected void shield(double x, double y, double dir, Entity shooter, double pos, int lifeTime) {
		Projectile p = new ShieldBall(x, y, dir, shooter, pos, lifeTime);
		level.add(p);
	}

	private boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 15) / 16; // + (-(c / 2 * 8) + (c == 0 || c == 1 ? 2 : 1) / 2 * 7)
			double yt = ((y + ya) - c / 2 * 15) / 16;// + (c % 2 * 16)
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid = true;
		}

		return solid;
	}

	public abstract void render(Screen screen);

}

package com.GenZVirus.Entity.Particle;

import java.util.List;

import com.GenZVirus.Entity.Entity;
import com.GenZVirus.Entity.Mob.Mob;
import com.GenZVirus.Entity.Projectile.ShieldBall;
import com.GenZVirus.Entity.Spawner.ParticleSpawner;
import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;
import com.GenZVirus.Util.Vector2i;

public class Particle extends Entity {

	private Sprite sprite;

	private int life;
	private int time = 0;
	private ParticleSpawner spawner;

	protected double xx, yy, zz;
	protected double xa, ya, za;

	protected boolean doDamage = false;
	protected double damage = 0;

	protected Entity shooter;

	public Particle(int x, int y, int life, ParticleSpawner spawner, boolean doDamage, double damage, Entity shooter) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(20) - 10);
		sprite = Sprite.particle_normal;
		this.spawner = spawner;
		this.doDamage = doDamage;
		this.damage = damage;
		this.shooter = shooter;

		this.xa = random.nextGaussian() + 1.8;
		if (this.xa < 0) xa = 0.1;
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}

	public void update() {
		time++;
		if (time >= 7400) time = 0;
		if (time > life) {
			remove();
			spawner.remove();
		}
		za -= 0.1;
		if (zz < 0) {
			zz = 0;
			za *= -0.55;
			xa *= 0.4;
			ya *= 0.4;
		}
		move((xx + xa), (yy + ya) + (zz + za));
		if (doDamage) {
			List<Entity> entities = level.getEntities(this, 16);
			entities.addAll(level.getShieldBalls(this, 32));
			entities.add(level.getClientPlayer());
			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				double distance = Vector2i.getDistance(new Vector2i((int) xx, (int) yy), new Vector2i((int) e.getX(), (int) e.getY()));
				if (distance < 12 && e != shooter) {
					if (e instanceof ShieldBall && e != shooter) {
						remove();
					} else {
						Mob m = (Mob) e;
						if (m.currentHealth >= damage) m.currentHealth -= damage;
						else m.currentHealth = 0;
						remove();
					}
				}
			}
		}

	}

	private void move(double x, double y) {
		if (collision(x, y)) {
			this.xa *= -0.5;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;

	}

	public boolean collision(double x, double y) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = (x - c % 2 * 16) / 16;
			double yt = (y - c / 2 * 16) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid = true;
		}

		return solid;
	}

	public void render(Screen screen) {
		screen.renderSprite((int) xx - 4, (int) yy - (int) zz - 2, sprite, true);
		// System.out.println(sprite.getWidth());
	}

}

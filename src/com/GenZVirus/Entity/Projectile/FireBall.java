package com.GenZVirus.Entity.Projectile;

import java.util.List;

import com.GenZVirus.Entity.Entity;
import com.GenZVirus.Entity.Mob.Mob;
import com.GenZVirus.Entity.Spawner.ParticleSpawner;
import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;
import com.GenZVirus.Util.Vector2i;

public class FireBall extends Projectile {

	public static final int FIRE_RATE = 10; // Higher is slower!

	public FireBall(double x, double y, double dir, Entity shooter) {
		super(x, y, dir, shooter);
		range = 200;
		speed = 4;
		damage = 20;
		sprite = sprite.Rotate(Sprite.fire_ball, angle);
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 8, 5, 5)) {
			level.add(new ParticleSpawner((int) x, (int) y, 100, 20, level, true, 0.5, shooter));
			remove();
		}
		List<Entity> entities = level.getEntities(this, 32);
		entities.addAll(level.getShieldBalls(this, 32));
		entities.add(level.getClientPlayer());
		for (int i = 0; i < entities.size(); i++) {
			System.out.println(entities.size());
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i((int) x, (int) y), new Vector2i((int) e.getX(), (int) e.getY()));
			if (distance < 16 && e != shooter) {
				if (e instanceof ShieldBall) {
					if (((ShieldBall) e).getShooter() != shooter) {
						level.add(new ParticleSpawner((int) x, (int) y, 100, 20, level, true, 0.5, shooter));
						remove();
					}
				} else {
					Mob m = (Mob) e;
					if (m.currentHealth >= damage) m.currentHealth -= damage;
					else m.currentHealth = 0;
					level.add(new ParticleSpawner((int) x, (int) y, 100, 20, level, true, 0.5, shooter));
					remove();
				}
			}
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

package com.GenZVirus.Entity.Projectile;

import java.util.List;
import java.util.Random;

import com.GenZVirus.Entity.Entity;
import com.GenZVirus.Entity.Mob.Mob;
import com.GenZVirus.Entity.Spawner.ParticleSpawner;
import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;
import com.GenZVirus.Util.Vector2i;

public class SeekerMissle extends Projectile {

	private double pos = 0;

	public SeekerMissle(double x, double y, double dir, Entity shooter, double pos) {
		super(x, y, dir, shooter);
		this.pos = pos;
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
		double min = 200.0;
		List<Entity> entities = level.getEntities(this, 80);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i((int) x, (int) y), new Vector2i((int) e.getX(), (int) e.getY()));
			if (distance < min) min = distance;
			if (min >= 8 && e != shooter) {
				move((Mob) e);
			}
			if (min < 8 && e != shooter) {
				Mob m = (Mob) e;
				if (m.currentHealth >= damage) m.currentHealth -= damage;
				else m.currentHealth = 0;
				level.add(new ParticleSpawner((int) x, (int) y, 100, 20, level, true, 0.5, shooter));
				remove();
			}
		}

		Random random = new Random();
		if (entities.size() == 0) {
			pos += Math.PI / 12 / speed;
			x = shooter.getX() + Math.cos(pos) * (random.nextInt(1) + 25);
			y = shooter.getY() + Math.sin(pos) * (random.nextInt(1) + 25);
			if (pos >= 2 * Math.PI) pos = 0;
		}

	}

	protected void move(Mob m) {
		if ((int) x < (int) m.getX()) x += speed;
		if ((int) x > (int) m.getX()) x -= speed;
		if ((int) y < (int) m.getY()) y += speed;
		if ((int) y > (int) m.getY()) y -= speed;
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

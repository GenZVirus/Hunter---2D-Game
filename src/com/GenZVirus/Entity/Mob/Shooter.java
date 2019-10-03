package com.GenZVirus.Entity.Mob;

import java.util.List;

import com.GenZVirus.Entity.Entity;
import com.GenZVirus.Entity.Projectile.FireBall;
import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Util.Vector2i;

public class Shooter extends Mob {

	private int fireRate = 0;
	private int time = 0;
	private Entity rand = null;

	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = sprite.dummy;
		fireRate = FireBall.FIRE_RATE;
		currentHealth = maxHealth = 100;
	}

	public void update() {
		shootRandom();
		if (currentHealth == 0) removed = true;
	}

	private void shootRandom() {
		if (time % 60 == 0) {
			List<Entity> entities = level.getEntities(this, 100);
			Player player = level.getClientPlayer();
			if (Vector2i.getDistance(new Vector2i((int) x, (int) y), new Vector2i((int) player.getX(), (int) player.getY())) < 200) entities.add(level.getClientPlayer());
			if (entities.size() == 0) return;
			int index = random.nextInt(entities.size());
			rand = entities.get(index);
		}
		if (rand != null) {
			double dx = rand.getX() - x;
			double dy = rand.getY() - y;
			double dir = Math.atan2(dy, dx);
			if (fireRate > 0) {
				fireRate--;
			}
			if (fireRate <= 0) {
				shoot(x, y, dir, this);
				fireRate = FireBall.FIRE_RATE;
			}
		}

	}

	private void shootClosest() {
		List<Entity> entities = level.getEntities(this, 200);
		entities.add(level.getClientPlayer());
		double min = 0;
		Entity closest = null;
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i((int) x, (int) y), new Vector2i((int) e.getX(), (int) e.getY()));
			if (i == 0 || distance < min) {
				min = distance;
				closest = e;
			}
		}
		if (closest != null) {
			double dx = closest.getX() - x;
			double dy = closest.getY() - y;
			double dir = Math.atan2(dy, dx);
			if (fireRate > 0) {
				fireRate--;
			}
			if (fireRate <= 0) {
				shoot(x, y, dir, this);
				fireRate = FireBall.FIRE_RATE;
			}
		}
	}

	public void render(Screen screen) {
		screen.renderMob((int) x - 16, (int) y - 16, this);
	}

}

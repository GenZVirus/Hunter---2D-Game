package com.GenZVirus.Entity.Spawner;

import com.GenZVirus.Entity.Entity;
import com.GenZVirus.Level.Level;

public abstract class Spawner extends Entity {

	public enum Type {
		MOB, PARTICLE;
	}

	protected Type type;

	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
		
	}
}

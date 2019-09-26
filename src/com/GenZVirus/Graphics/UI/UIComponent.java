package com.GenZVirus.Graphics.UI;

import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Util.Vector2i;

public class UIComponent {

	private int backgroundColor;
	public Vector2i position, offset;

	public UIComponent(Vector2i position) {
		this.position = position;
		offset = new Vector2i();
	}

	public void update() {

	}

	public void render(Screen screen) {
	}

	void setOffset(Vector2i offset) {
		this.offset = offset;
	}
}

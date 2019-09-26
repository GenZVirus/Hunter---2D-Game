package com.GenZVirus.Graphics.UI;

import java.util.ArrayList;
import java.util.List;

import com.GenZVirus.Graphics.Screen;
import com.GenZVirus.Graphics.Sprite;
import com.GenZVirus.Util.Vector2i;

public class UIPanel {

	private List<UIComponent> components = new ArrayList<UIComponent>();
	private Vector2i position;
	private Sprite sprite;

	public UIPanel(Vector2i position) {
		this.position = position;
		sprite = new Sprite(80, 168, 0xcacaca);
		System.out.println(this.position.y);
	}

	public void addComponent(UIComponent component) {
		components.add(component);
	}

	public void update() {
		for (UIComponent component : components) {
			component.setOffset(position);
			component.update();
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(position.x, position.y, sprite, false);
		for (UIComponent component : components) {
			component.render(screen);
		}
	}

}

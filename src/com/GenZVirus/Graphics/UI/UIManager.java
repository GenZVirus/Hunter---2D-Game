package com.GenZVirus.Graphics.UI;

import java.util.ArrayList;
import java.util.List;

import com.GenZVirus.Graphics.Screen;

public class UIManager {

	private List<UIPanel> panels = new ArrayList<UIPanel>();

	public UIManager() {

	}

	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}

	public void update() {
		for (UIPanel panel : panels) {
			panel.update();
		}
	}

	public void render(Screen screen) {
		for (UIPanel panel : panels) {
			panel.render(screen);
		}
	}

}

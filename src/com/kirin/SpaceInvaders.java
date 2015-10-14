package com.kirin;

/*
 * This class tells the gamePanel how to work and what to do
 */

import java.awt.Color;
import java.awt.Container;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class SpaceInvaders extends JFrame {

	private static final long serialVersionUID = 1L;
	// Set up constants for width and height of frame
	static final int WIDTH = 900;
	static final int HEIGHT = 900;

	SpaceInvadersPanel basicPanel;

	// constructor
	public SpaceInvaders(String title) {
		// Set the title of the frame, must be before variable declarations
		super(title);

		Container container;

		// Instantiate and add the SimplePanel to the frame
		basicPanel = new SpaceInvadersPanel();
		basicPanel.setBackground(Color.BLACK);
		container = getContentPane();

		// container.setLayout(null);
		setLocationByPlatform(true);
		container.add(basicPanel);
		container.validate();

	}

	SpaceInvadersPanel getPanel() {
		return basicPanel;
	}

	static float red = 0, green = 0, blue = 0;

	public static void main(String[] args) {
		// Find the class path (because I was getting noClassDefFound errors)
		System.out.println(System.getProperty("java.class.path"));

		SpaceInvaders frame = new SpaceInvaders("Space Invaders");

		// Set up frame
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setLocation(0, 0);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		// Set up reference to Panel
		SpaceInvadersPanel gamePanel = frame.getPanel();

		while (true) {

			// Engine of the game
			Color c = new Color(red, green, blue);
			gamePanel.moveProjectile();
			gamePanel.shiftEnemies();
			gamePanel.movePlayer();
			gamePanel.enemyShoot();
			gamePanel.moveEnemyProjectile();
			gamePanel.setBackground(c);

			// Delay
			try {
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
				Thread.currentThread().interrupt();
			}

			// RAINBOWS
			
			if (gamePanel.getTicks() % 3 == 0) {
				if (red <= 0.9) {
					red += 0.01;
				} else {
					red = 0;
				}
			} else if (gamePanel.getTicks() % 3 == 1) {
				if (green <= 0.9) {
					green += 0.05;
				} else {
					green = 0;
				}
			} else if (gamePanel.getTicks() % 3 == 2) {
				if (blue <= 0.9) {
					blue += 0.1;
				} else {
					blue = 0;
				}
			}

			// Update labels/ticks and repaint
			gamePanel.increaseTicks();
			gamePanel.updateLabels();
			gamePanel.repaint();
		}

	}

}

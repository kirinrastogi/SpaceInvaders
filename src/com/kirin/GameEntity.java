package com.kirin;

/*
 * This is the superclass for ALL entities I made
 * It extends JLabel, so all entities can be placed on the panel/setVisible
 * The constructor grabs the already formatted images, and sets the JLabel's icons to them
 * Positionning is not included in this constructor, as Positionning depends on what subclass it is
 * Every entity passes it's filename so this constructor can use/deal with it
 * 
 */

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
abstract class GameEntity extends JLabel {

	public GameEntity(String imgName) {
		// Superclass constructor
		// This gets an img from the folder, sets it to an icon, then puts it in
		// the label.
		// And voilà, GRAPHICS!!!

		Image img = null;
		// If there's an error with the file
		try {
			// If you change imgName to another string eg "enemyProj.png" then
			// ALL entities will have that icon
			img = ImageIO.read(new File(imgName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ImageIcon icon = new ImageIcon(img);
		setIcon(icon);
		setSize(getIcon().getIconWidth(), getIcon().getIconHeight());

	}

	abstract int getPositionY();

	abstract int getPositionX();

	abstract Position getPosition();

	abstract void setPosition(int x, int y);

	abstract void setPositionX(int x);

	abstract void setPositionY(int y);

}
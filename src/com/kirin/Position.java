package com.kirin;

/*
 * Before I started I decided to use Position, not knowing there was a Point class
 */

public class Position {

	private int posX, posY;

	public Position(int posX2, int posY2) {
		posX = posX2;
		posY = posY2;

	}

	// Encapsulation
	
	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}

	public void setY(int y) {
		posY = y;
	}

	public void setX(int x) {
		posX = x;
	}
	
	public void setPosition (int x, int y) {
		posX = x;
		posY = y;
	}
	
	public void changeX(int x) {
		posX += x;
	}
	
	public void changeY(int y) {
		posY += y;
	}

}

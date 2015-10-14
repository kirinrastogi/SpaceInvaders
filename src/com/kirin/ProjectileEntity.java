package com.kirin;

/*
 * Superclass for projectiles made because I didn't want the images to be the same, and making a heirarchy was easier
 */

@SuppressWarnings("serial")
abstract class ProjectileEntity extends GameEntity {

	Position posOnScreen;

	public ProjectileEntity(int posX, int posY, String imgName) {
		super(imgName);
		posOnScreen = new Position(posX, posY);

	}

	// Encapsulation

	@Override
	int getPositionY() {
		// Auto-generated method stub
		return getPosition().getY();
	}

	@Override
	int getPositionX() {
		// Auto-generated method stub
		return posOnScreen.getX();
	}

	@Override
	Position getPosition() {
		// Auto-generated method stub
		return posOnScreen;
	}

	@Override
	void setPositionX(int x) {
		posOnScreen.setX(x);

	}

	@Override
	void setPositionY(int y) {
		posOnScreen.setY(y);

	}

	int getCenter() {
		// Auto-generated method stub
		return Math.abs(getIcon().getIconWidth() / 2);
	}

	void setPosition(int x, int y) {
		this.posOnScreen.setX(x);
		this.posOnScreen.setY(y);
	}

}

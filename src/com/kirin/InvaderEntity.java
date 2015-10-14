package com.kirin;

/*
 * Abstract superclass for Gunners and Tankers
 * This class is very helpful in the destroy(InvaderEntity) function in the panel
 * This is also very helpful in the Polymorphic array in the Panel also
 */

@SuppressWarnings("serial")
abstract class InvaderEntity extends GameEntity {

	private int START_X, START_Y;
	Position posOnScreen;

	public InvaderEntity(int posX, int posY, String imgName) {
		super(imgName);

		posOnScreen = new Position((posX * 40) + (posX * 10),
				((posY * 40) + 75) + (posY * 20));

		START_X = posOnScreen.getX();
		START_Y = posOnScreen.getY();

	}

	// Encapsulation

	public int getStartY() {
		return START_Y;
	}

	public int getStartX() {
		return START_X;
	}

	@Override
	public int getPositionY() {
		return posOnScreen.getY();
	}

	@Override
	public int getPositionX() {
		return posOnScreen.getX();
	}

	public void setPositionX(int x) {
		getPosition().setX(x);
	}

	public void setPositionY(int y) {
		getPosition().setY(y);
	}

	public Position getPosition() {
		return posOnScreen;
	}

	public void setPosition(int x, int y) {
		getPosition().setX(x);
		getPosition().setY(y);
	}

	final void setStartX(int x) {
		START_X = x;
	}

	final void setStartY(int y) {
		START_Y = y;
	}
	
	abstract int getHealth();
	
	abstract int getMaxHealth();
	
	abstract int damage();
	
	abstract void resetHealth();
	
	

}

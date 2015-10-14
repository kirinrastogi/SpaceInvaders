package com.kirin;

/*
 * The player!
 * Directly extends GameEntity, no polymorphism needed as there's nothing like it
 */

@SuppressWarnings("serial")
public class PlayerEntity extends GameEntity {

	private Position playerPosition;

	public PlayerEntity(int posX, int posY) {
		super("player.png");

		playerPosition = new Position(posX, posY);

	}
	
	// Encapsulation

	@Override
	int getPositionY() {
		// Auto-generated method stub
		return playerPosition.getY();
	}

	@Override
	int getPositionX() {
		// Auto-generated method stub
		return playerPosition.getX();
	}

	Position getPosition() {
		// Auto-generated method stub
		return playerPosition;
	}

	public void setPositionX(int x) {
		playerPosition.setX(x);
	}

	public void setPosition(int x, int y) {
		playerPosition.setX(x);
		playerPosition.setY(y);
	}

	@Override
	void setPositionY(int y) {
		playerPosition.setY(y);

	}

}

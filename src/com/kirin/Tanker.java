package com.kirin;

/*
 * Crablike alien
 */

@SuppressWarnings("serial")
public class Tanker extends InvaderEntity {

	private int health;
	private static final int maxHealth = 2;

	public Tanker(int posX, int posY) {
		super(posX, posY, "tanker.png");
		health = 2;
	}

	@Override
	public int getHealth() {
		return health;

	}

	@Override
	public int getMaxHealth() {
		return maxHealth;

	}

	@Override
	public int damage() {
		if (health > 0) {
			health--;
			if (health == 0) {
				setVisible(false);
				return 50;
			}
		} else {
			return 0;
		}
		return 0;
	}

	@Override
	void resetHealth() {
		health = maxHealth;

	}

}

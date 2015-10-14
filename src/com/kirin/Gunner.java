package com.kirin;

/*
 * Squidlike alien
 */

import com.kirin.InvaderEntity;

@SuppressWarnings("serial")
public class Gunner extends InvaderEntity {

	private int health;
	private static final int maxHealth = 1;

	public Gunner(int posX, int posY) {
		super(posX, posY, "gunner.png");
		health = 1;

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
				return 100;
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

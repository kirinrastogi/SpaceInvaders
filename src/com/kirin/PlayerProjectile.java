package com.kirin;

/*
 * Projectile that the Player shoots
 * Frequency is selected in SpaceInvadersPanel's askDifficulty
 * ALL METHODS STORED IN PROJECTILE ENTITY
 * ORIGINALLY I WAS GOING TO MAKE IPROJECTILE AND PPROJECTILE DIFFERENT
 * BUT NOW THE ONLY DIFFERENCE IS THEIR IMAGE
 */

@SuppressWarnings("serial")
public class PlayerProjectile extends ProjectileEntity {

	public PlayerProjectile(int posX, int posY) {
		super(posX, posY, "playerProj.png");
	}

}

package com.kirin;

/*
 * Panel for the game, this controls enemies, events, projectiles, basically everything that happens!
 * This has labels, buttons, and entities that I created that extend JLabel
 * This class moves them around, makes them invisible depending on the conditions etc.
 * This class has functions and stuff, basically the game components
 */

import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpaceInvadersPanel extends JPanel implements ActionListener,
		KeyListener {

	private static final long serialVersionUID = 1L;
	private JButton exitButton, resetButton;
	private JLabel playerLabel;
	private String cmd = null;
	private short speed = 20;

	private boolean playing;
	private int key;
	private int playerProjX;
	private int direction = 1;
	private int velocityX;

	// Assign these with difficulty
	// These change difficulty
	private static short shootDelay = 10;
	private final static short projectileAmount = 200;
	private short modVal;
	private short initialWait = 20;

	private int lives = 2;
	// Used for shooting
	private int enemyShotTracker;
	private long ticks;
	private long score;

	private Random rand;
	private int shootX, shootY;

	private final int PLAYER_START_X = 450;
	private final int PLAYER_START_Y = 720;

	private static final int bWidth = 100;
	private static final int bHeight = 50;

	private InvaderEntity[][] enemies; // Polymorphic array that will hold
										// Gunners and Tankers

	private InvaderProjectile[] enemyProj;

	private boolean won;
	private boolean lost;
	private boolean started = false;

	private int[] enemyProjX;

	private JLabel shield;
	private JLabel tickLabel;
	private JLabel scoreLabel;
	private JLabel outcomeLabel;
	private JLabel ceiling;
	private JLabel floor;
	private JLabel difficultyLabel;
	private JLabel instructionLabel;

	private PlayerProjectile playerProj;

	private JLabel[] livesImages;

	private PlayerEntity player;

	private JButton[] difficultyButton;

	// ************ Constructor start
	public SpaceInvadersPanel() {
		setLayout(null);

		// Used to evaluate if functions should be done, they stop null pointer
		// exceptions
		playing = true;
		won = false;
		lost = false;

		difficultyLabel = new JLabel("Choose difficulty:");
		difficultyLabel.setBounds(350, 350, 200, 50);
		difficultyLabel.setForeground(Color.WHITE);
		difficultyLabel.setVisible(true);
		add(difficultyLabel);

		instructionLabel = new JLabel(
				"Use arrow keys to move and press up to shoot");
		instructionLabel.setBounds(350, 300, 350, 50);
		instructionLabel.setForeground(Color.WHITE);
		instructionLabel.setVisible(true);
		add(instructionLabel);

		difficultyButton = new JButton[4];
		// Array of buttons that show at start and after restart
		for (int i = 0; i < 4; i++) {
			difficultyButton[i] = new JButton("" + (i + 1));
			difficultyButton[i].setBounds((i * bWidth) + 200, 450, bWidth,
					bHeight);
			difficultyButton[i].setVisible(true);
			difficultyButton[i].addActionListener(this);
			difficultyButton[i].addKeyListener(this);
			difficultyButton[i].setFocusable(false);
			add(difficultyButton[i]);
		}

		// Not really changed
		speed = 20;

		ticks = 0;
		velocityX = 0;
		score = 0;
		rand = new Random();

		// Your shootything
		playerProj = new PlayerProjectile(450, 700);
		playerProj.setVisible(false);
		add(playerProj);

		// This class implements listeners, so this class has this to add to
		// them
		addKeyListener(this);
		this.setFocusable(true);
		// So players can't use tab to switch focus
		this.setFocusTraversalKeysEnabled(false);

		// Arrays of FUN
		enemies = new InvaderEntity[10][10]; // Y < 4; X < 10
		livesImages = new JLabel[3];
		enemyProjX = new int[projectileAmount];
		enemyProj = new InvaderProjectile[projectileAmount];

		// Making projectiles...
		// Not all are used, but to avoid outofbounds, the projectileAmount MUST
		// be final
		for (int i = 0; i < projectileAmount; i++) {
			enemyProj[i] = new InvaderProjectile(200, 100);
			enemyProj[i].setVisible(false);
			add(enemyProj[i]);
		}

		// Labels for lives
		for (int i = 0; i < 3; i++) {
			livesImages[i] = new JLabel();
			Image img = null;
			try {
				img = ImageIO.read(new File("player.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			ImageIcon icon = new ImageIcon(img);
			livesImages[i].setIcon(icon);
			livesImages[i].setBounds((i * 40) + 70, 10, 40, 40);
			livesImages[i].setVisible(false);
			add(livesImages[i]);
		}

		tickLabel = new JLabel();
		tickLabel.setForeground(Color.WHITE);
		tickLabel.setBounds(450, 25, 200, 45);
		tickLabel.setVisible(false);
		add(tickLabel);

		outcomeLabel = new JLabel();
		outcomeLabel.setForeground(Color.WHITE);
		outcomeLabel.setBounds(450, 450, 200, 20);
		outcomeLabel.setVisible(false);
		add(outcomeLabel);

		scoreLabel = new JLabel();
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setBounds(900 - 200, 25, 200, 45);
		scoreLabel.setVisible(false);
		add(scoreLabel);

		shield = new JLabel(
				"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		shield.setForeground(Color.BLUE);
		shield.setBounds(0, 650, 900, 10);
		shield.setVisible(false);
		add(shield);

		ceiling = new JLabel(shield.getText());
		ceiling.setForeground(Color.WHITE);
		ceiling.setBounds(0, 50, 900, 10);
		ceiling.setVisible(false);
		add(ceiling);

		floor = new JLabel(shield.getText());
		floor.setForeground(ceiling.getForeground());
		floor.setSize(ceiling.getWidth(), ceiling.getHeight());
		floor.setLocation(0, 755);
		floor.setVisible(false);
		add(floor);

		playerLabel = new JLabel("Ships left:");
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setBounds(0, 10, 93, 45);
		playerLabel.setVisible(false);
		add(playerLabel);

		exitButton = new JButton("Exit");
		exitButton.setBounds((bWidth * 4) + 250, 450, bWidth, bHeight);
		exitButton.addActionListener(this);
		exitButton.addKeyListener(this);
		exitButton.setFocusable(false);
		exitButton.setVisible(true);
		add(exitButton);

		resetButton = new JButton("Restart");
		resetButton.setBounds(bWidth, 800, bWidth, bHeight);
		resetButton.addActionListener(this);
		resetButton.addKeyListener(this);
		resetButton.setFocusable(false);
		resetButton.setVisible(false);
		add(resetButton);

		// Make gunners and Tankers
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				if (y < 2) {
					enemies[y][x] = new Gunner(x, y);
				} else {
					enemies[y][x] = new Tanker(x, y);
				}
				enemies[y][x].setVisible(false);
				add(enemies[y][x]);
			}
		}

		// Instantiate PlayerEntity
		player = new PlayerEntity(PLAYER_START_X, PLAYER_START_Y);
		player.addKeyListener(this);
		player.setFocusable(false);
		player.setVisible(false);
		add(player);

		askDifficulty();

	}

	public void askDifficulty() {
		// hides everything but the things needed to be on the screen
		started = false;
		playing = false;

		instructionLabel.setVisible(true);
		difficultyLabel.setVisible(true);

		exitButton.setVisible(true);
		exitButton.setLocation((bWidth * 4) + 200, 450);

		for (int i = 0; i < 4; i++) {

			switch (i) {
			case (0):
				difficultyButton[i].setText("Easy");
				break;
			case (1):
				difficultyButton[i].setText("Medium");
				break;
			case (2):
				difficultyButton[i].setText("Hard");
				break;

			case (3):
				difficultyButton[i].setText("Insane");
				break;
			}

			difficultyButton[i].setVisible(true);
		}

		for (int i = 0; i < projectileAmount; i++) {
			enemyProj[i].setVisible(false);
		}

		for (int i = 0; i < livesImages.length; i++) {
			livesImages[i].setVisible(false);
		}

		playerLabel.setVisible(false);
		scoreLabel.setVisible(false);
		playerProj.setVisible(false);
		resetButton.setVisible(false);
		player.setVisible(false);
		outcomeLabel.setVisible(false);
		tickLabel.setVisible(false);
		shield.setVisible(false);
		ceiling.setVisible(false);
		floor.setVisible(false);

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				enemies[y][x].setVisible(false);
			}
		}

	}

	public void easy() {
		shootDelay = 500;
		start();
	}

	public void medium() {
		shootDelay = 25;
		start();
	}

	public void hard() {
		shootDelay = 5;
		start();
	}

	public void insane() {
		shootDelay = 1;
		start();
	}

	public void start() {
		// undoes all of askDifficulty()'s stuff, and starts the game

		direction = 1;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				enemies[y][x].setPosition(enemies[y][x].getStartX(),
						enemies[y][x].getStartY());
				enemies[y][x].setLocation(enemies[y][x].getStartX(),
						enemies[y][x].getStartY());
				enemies[y][x].setVisible(true);
			}
		}

		playing = true;
		started = true;

		instructionLabel.setVisible(false);

		difficultyLabel.setVisible(false);

		// Make everything visible!
		for (int i = 0; i < 4; i++) {
			difficultyButton[i].setVisible(false);
		}

		started = true;
		exitButton.setBounds(900 - 2 * bWidth, 800, bWidth, bHeight);
		resetButton.setVisible(true);
		exitButton.setVisible(true);

		for (int i = 0; i < projectileAmount; i++) {
			enemyProj[i].setVisible(false);
		}

		for (int i = 0; i < livesImages.length; i++) {
			livesImages[i].setVisible(true);
		}

		playerLabel.setVisible(true);
		scoreLabel.setVisible(true);
		playerProj.setVisible(false);
		resetButton.setVisible(true);
		player.setVisible(true);
		outcomeLabel.setVisible(false);
		tickLabel.setVisible(true);
		shield.setVisible(true);
		ceiling.setVisible(true);
		floor.setVisible(true);

		started = true;

	}

	public boolean hasStarted() {
		return started;
	}

	@Deprecated
	public int getSpeed() {
		return speed;
	}

	public void updateLabels() {
		// Update score and ticks (time)
		if (!hasWon() && !hasLost()) {
			tickLabel.setText("Ticks: " + getTicks());
			scoreLabel.setText("Score: " + getScore());
		}
	}

	// Encapsulation
	public long getScore() {
		return score;
	}

	public boolean isPlaying() {
		return playing;
	}

	public boolean hasWon() {
		return won;
	}

	public boolean hasLost() {
		return lost;
	}

	public void shiftEnemies() {
		// Set position of ALL elements of array getPosition +/- (depending on
		// direction) getPosition
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {

				enemies[y][x].setPositionX(enemies[y][x].getPosition().getX()
						+ direction);

				enemies[y][x].setLocation(enemies[y][x].getPosition().getX(),
						+enemies[y][x].getPosition().getY());

			}
		}

		// Avoids nullPointerException
		// I was getting weird behaviour by using thereAreInvaders() here
		if (isPlaying() && hasStarted() && thereAreInvaders()) {
			if ((getLeftMost().getPosition().getX() <= -1)
					|| getRightMost().getPosition().getX() > (900 - enemies[3][9]
							.getIcon().getIconWidth())) {

				// Move enemies down 1 row
				// Change their direction
				moveEnemies();
				changeDirection();
				checkForLoss();
				// Check if they hit the shield
			}
		}

	}

	public void checkForLoss() {
		// I made thereAreInvaders() after
		if (thereAreInvaders()) {
			if (getLowerMost().getPositionY()
					+ getLowerMost().getIcon().getIconHeight() > shield
					.getLocation().getY() + 5) {
				// gay
				// ^ Remi wrote that
				loss();
			}
		}
	}

	private void loss() {
		// Player loses, stops and makes things in/visible
		// Player can restart after, does not crash
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				enemies[y][x].setVisible(false);
				playing = false;
				lost = true;
				outcomeLabel.setText("You lose! gg no re");
				outcomeLabel.setVisible(true);
			}
		}

		for (int i = 0; i < projectileAmount; i++) {
			enemyProj[i].setVisible(false);
		}

		playerProj.setVisible(false);
	}

	public void enemyShoot() {
		// Where the math stuff happens
		// This is so nothing shoots right away
		// Not needed as it is very hard to start on the 0th tick
		if (getTicks() != 0) {
			modVal = initialWait;
			for (int i = 0; i < projectileAmount; i++) {

				if (getTicks() % modVal == 0) {
					// System.out.println(getTicks());
					// Tells which projectile to talk to
					enemyShotTracker = i;
					// stop augmenting
					break;
				}

				// add to the modulus value
				modVal += shootDelay;
			}

		}

		if ((getTicks() != 0) && !enemyProj[enemyShotTracker].isVisible()
				&& player.isVisible()) {
			// if the projectiles can be shot

			do {

				shootX = rand.nextInt(10);
				shootY = rand.nextInt(4);
				// Make sure they come from a visible invader!

			} while (!enemies[shootY][shootX].isVisible());

			// Make their x a variable, so it doesn't follow their x line

			enemyProjX[enemyShotTracker] = enemies[shootY][shootX]
					.getPositionX() + 17;

			enemyProj[enemyShotTracker].setPosition(
					enemyProjX[enemyShotTracker],
					enemies[shootY][shootX].getLocation().y + 30);

			enemyProj[enemyShotTracker].setLocation(
					enemyProjX[enemyShotTracker],
					enemies[shootY][shootX].getPositionY());

			enemyProj[enemyShotTracker].setVisible(true);

		}
	}

	public void moveEnemyProjectile() {
		// For ALL projectiles, increase their y value
		for (int i = 0; i < projectileAmount; i++) {
			if (enemyProj[i].isVisible()) {
				// if it has been shot

				enemyProj[i].setPositionY(enemyProj[i].getPositionY() + 5);

				enemyProj[i].setLocation(enemyProjX[i],
						enemyProj[i].getPositionY());

				checkForHit();
				// Check for hit after it has been moved

				// This is to prevent spawn camping, while the player is
				// respawning (not visible)
				// then the shots will break on the shield
				if (!player.isVisible()) {
					if (enemyProj[i].getPositionY() > shield.getLocation()
							.getY()) {
						enemyProj[i].setVisible(false);
					}

				} else {
					if (enemyProj[i].getPositionY() > 755) {
						enemyProj[i].setVisible(false);
					}
				}

			}
		}

	}

	public void shootProjectile() {
		// called when the player shoots (presses up)
		if (!playerProj.isVisible() && player.isVisible() && thereAreInvaders()) {
			// Cannot shoot during win time
			playerProjX = player.getPositionX() + 17;
			// Center

			playerProj.setPosition(playerProjX, PLAYER_START_Y);
			playerProj.setLocation(playerProjX, PLAYER_START_Y);
			playerProj.setVisible(true);
			// Start it from they player's x, and starting y (constant)
		}
		// The player cannot shoot while the projectile is visible (exists)
	}

	public void moveProjectile() {
		// if the player's shot exists
		if (playerProj.isVisible()) {
			playerProj.setPositionY(playerProj.getPositionY() - 5);
			// Move it down (higher edge of the screen)
			playerProj.setLocation(playerProjX, playerProj.getPositionY());
			// Check for hit
			checkForHit();
		}

		if (playerProj.getPositionY() <= ceiling.getLocation().getY() + 5) {
			playerProj.setVisible(false);
		}
		checkForHit();

	}

	public void changeDirection() {
		// Called when they hit a side
		// Player respawns when they hit a side
		direction *= -1;
		respawn();

	}

	public InvaderEntity getLowerMost() {

		for (int y = 3; y >= 0; y--) {
			for (int x = 0; x < 10; x++) {
				if (enemies[y][x].isVisible()) {
					return enemies[y][x];
				}
			}
		}

		return null;
	}

	public void checkForHit() {

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				if (enemies[y][x].getBounds().contains(playerProjX,
						playerProj.getPositionY())
						&& playerProj.isVisible() && enemies[y][x].isVisible()) {
					// loop through enemies, if they have the projectile in
					// them, destroy them and the shot
					playerProj.setVisible(false);
					score += enemies[y][x].damage();
					updateLabels();
				}
			}
		}

		checkForWin();

		for (int i = 0; i < projectileAmount; i++) {
			if (player.getBounds().contains(enemyProjX[i],
					enemyProj[i].getPositionY())
					&& player.isVisible() && enemyProj[i].isVisible()) {
				enemyProj[i].setVisible(false);
				player.setVisible(false);
				if (lives != -1) {
					livesImages[lives].setVisible(false);
				} else {
					// Player loses, lives = -1
					loss();
				}
			}
		}

	}

	public void respawn() {

		if (!player.isVisible() && (lives != -1)) {
			// Respawn the player if they are invisible

			player.setPosition(PLAYER_START_X, PLAYER_START_Y);
			player.setLocation(PLAYER_START_X, PLAYER_START_Y);
			player.setVisible(true);
			lives--;
		}
	}

	private void checkForWin() {

		if (score == 3000) {
			// Cannot use !thereAreInvaders()
			// or I get an error
			updateLabels();
			win();
		}

	}

	public boolean thereAreInvaders() {

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				if (enemies[y][x].isVisible()) {
					return true;
				}
			}
		}

		return false;
	}

	private void win() {
		// Player wins!
		// Make things invisible
		// set labels to say they won

		for (int i = 0; i < projectileAmount; i++) {
			enemyProj[i].setVisible(false);
		}

		playerProj.setVisible(false);

		won = true;
		lost = false;
		playing = false;
		outcomeLabel.setText("You win!");
		outcomeLabel.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// Event
		cmd = ae.getActionCommand();

		if (cmd.equals("Exit")) {
			playing = false;
			System.exit(0);

		} else if (cmd.equals("Easy")) {
			easy();
		} else if (cmd.equals("Medium")) {
			medium();
		} else if (cmd.equals("Hard")) {
			hard();
		} else if (cmd.equals("Insane")) {
			insane();
		} else if (cmd.equals("Restart")) {

			// Restart EVERYTHING

			speed = 20;
			playing = true;
			won = false;
			lost = false;

			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 10; x++) {
					enemies[y][x].setLocation(enemies[y][x].getStartX(),
							enemies[y][x].getStartY());
					enemies[y][x].setPosition(enemies[y][x].getStartX(),
							enemies[y][x].getStartY());
					enemies[y][x].resetHealth();
					enemies[y][x].setVisible(true);

				}
			}

			for (int i = 0; i < 3; i++) {
				livesImages[i].setVisible(true);
			}

			for (int i = 0; i < projectileAmount; i++) {
				enemyProjX[i] = 0;
				enemyProj[i].setPosition(0, 0);
				enemyProj[i].setLocation(0, 0);
				enemyProj[i].setVisible(false);
			}

			playerProj.setVisible(false);
			playerProj.setPosition(PLAYER_START_X, PLAYER_START_Y);
			playerProj.setLocation(PLAYER_START_X, PLAYER_START_Y);

			outcomeLabel.setVisible(false);
			velocityX = 0;
			player.setLocation(PLAYER_START_X, PLAYER_START_Y);
			player.getPosition().setPosition(PLAYER_START_X, PLAYER_START_Y);
			playerProj.setVisible(false);
			playerProjX = player.getPositionX();
			player.setVisible(true);
			lives = 2;
			score = 0;
			ticks = 0;
			direction = 1;
			// ask difficulty
			askDifficulty();
		}

	}

	public void movePlayer() {
		// Always move the player, the amount added is either 3, 0, or -3

		player.setLocation(player.getPositionX() + velocityX,
				player.getPositionY());
		player.getPosition().setX(player.getPositionX() + velocityX);

		if (player.getPositionX() <= 0) {
			player.setPositionX(0);
		}

		if (player.getPositionX() >= (900) - player.getIcon().getIconWidth()) {
			player.setPositionX(900 - player.getIcon().getIconWidth() - 2);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Auto-generated method stub
		key = e.getKeyCode();

		// Change velocity
		if (player.isVisible()) {
			if (key == KeyEvent.VK_LEFT) {
				velocityX = -3;
			}

			if (key == KeyEvent.VK_RIGHT) {
				velocityX = 3;
			}

			// shoot when they press up
			if (key == KeyEvent.VK_UP) {
				shootProjectile();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		key = e.getKeyCode();
		// Reset velocity when they release

		if (key == KeyEvent.VK_RIGHT)
			velocityX = 0;

		if (key == KeyEvent.VK_LEFT)
			velocityX = 0;

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void increaseTicks() {
		if (!hasLost() || !hasWon()) {
			ticks++;
		}
	}

	public long getTicks() {
		// return lyme's disease
		return ticks;
	}

	public InvaderEntity getLeftMost() {
		// Check for leftmost
		if (!hasLost() && !hasWon()) {
			for (int x = 0; x < 10; x++) {
				for (int y = 0; y < 4; y++) {
					if (enemies[y][x].isVisible()) {
						return enemies[y][x];
					}
				}
			}
		}
		return null;
	}

	public InvaderEntity getRightMost() {
		// Check for rightmost
		if (!hasLost() && !hasWon()) {
			for (int x = 9; x >= 0; x--) {
				for (int y = 3; y >= 0; y--) {
					if (enemies[y][x].isVisible()) {
						return enemies[y][x];
					}
				}
			}
		}
		return null;
	}

	public void moveEnemies() {
		// Shift all enemies down 1 row
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 10; x++) {
				enemies[y][x].setLocation(enemies[y][x].getPosition().getX(),
						enemies[y][x].getPosition().getY() + 40);
				enemies[y][x].getPosition().setX(
						enemies[y][x].getPosition().getX());
				enemies[y][x].getPosition().setY(
						enemies[y][x].getPosition().getY() + 40);
			}
		}

	}

}

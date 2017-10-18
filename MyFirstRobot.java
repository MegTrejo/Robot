import java.awt.Color;
import java.util.Random;

import robocode.AdvancedRobot;
import robocode.Condition;
import robocode.CustomEvent;
import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

/**
 * MyFirstRobot - a robot by Meagan Trejo and Samuel Foster
 */
public class MyFirstRobot extends AdvancedRobot {

	private double fieldW, fieldH;
	private double x, y;
	private byte direction = 1;
	private int turnSwitch;
	
	private int wallBuffer = 200;		// should keep this semi-small just to prevent the bot from getting
						// stuck in a location within the buffer and not executing the 
						// continuous loop in run()

	/**
	 * run: MyFirstRobot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		fieldW = this.getBattleFieldWidth();
		fieldH = this.getBattleFieldHeight(); // was width, same as above before. Just changed in case of
							// a scenario where width and height would be different

		addCustomEvent(new Condition("sf_wall_proximity") {
			public boolean test() {

				// Check top and bottom walls
				if (getY() <= wallBuffer || getY() >= fieldH - wallBuffer) {
					return true;
				}

				// Check left and right walls
				if (getX() <= wallBuffer || getX() >= fieldW - wallBuffer) {
					return true;
				}

				return false;
			}
		});

		setColors(Color.DARK_GRAY, Color.blue, Color.green); // body,gun,radar

		// Robot main loop
		turnSwitch = 1;
		
		while (true) {
			turnRadarRight(360);
			Random r = new Random();
			move(r.nextInt(50) + 50);
			turnSwitch *= -1;

		}
	}

	public void onCustomEvent(CustomEvent e) {
		if (e.getCondition().getName().equals("sf_wall_proximity")) {
			direction *= -1;
			turnRight(90);
			move(100);
		}
	}

	public void move(int amount) {
		ahead(amount * direction);	// also, I think it's a bit easier to keep the movement forward, from a 
						// standpoint of using the scanner and manipulating the reactive movement
						// from the robot relative the location of the scanner
		if (turnSwitch == 1)			// this is just to make the movement a bit more random
			turnRight(amount + 20);		// rather than just forward until another event is triggered
		else					// You feel me?
			turnLeft(amount + 20);
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		System.out.println(e.getBearing());
		turnRight(e.getBearing() + 90);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		move(50);
	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(100);
	}
}

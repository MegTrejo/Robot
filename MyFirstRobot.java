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

	private int wallBuffer = 200;

	/**
	 * run: MyFirstRobot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here
		fieldW = this.getBattleFieldWidth();
		fieldH = this.getBattleFieldWidth();

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
		while (true) {
			turnRadarRight(360);
			Random r = new Random();
			move(r.nextInt(50) + 50);

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
		ahead(amount * direction);
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

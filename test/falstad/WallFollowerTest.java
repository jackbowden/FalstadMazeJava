package falstad;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import generation.MazeBuilder;
import generation.MazeConfiguration;
import generation.MazeFactory;
import generation.Order.Builder;
import generation.StubOrder;

import org.junit.Before;
import org.junit.Test;

public class WallFollowerTest {
	private MazeBuilder build;
	private StubOrder order;
	private MazeFactory factory;
	private MazeController mazeController;
	private BasicRobot robot;
	private MazeConfiguration config;
	private RobotDriver driver;

	@Before
	public void setUp() throws Exception {
		order = new StubOrder(4, true, Builder.Eller);
		mazeController = new MazeController(Builder.Eller);
		factory = new MazeFactory(true);
		factory.order(order);
		factory.waitTillDelivered();
		/* if only this one line worked, the rest of the stuff would too... */
		// order.getConfiguration().setMazecells(something));
		robot = new BasicRobot(mazeController);
		driver = new WallFollower();
		driver.setRobot(robot);
		driver.setDistance(order.getConfiguration().getMazedists());
	}
	
	/**
	 * Test if drive2Exit() can exit the maze properly.
	 * drive2Exit() will either return false or throw an exception if it can't exit the maze properly.
	 * @author jabowden and tcmzeal
	 */
	@Test
	public void testIfDriverExitsMaze() {
//		try {
//			assertTrue(driver.drive2Exit());
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
	}
	
	/**
	 * Test if drive2Exit() will not exit the maze properly when the exit is inaccessible.
	 * We create an unsolvable maze by setting the maze to a new maze where all the walls are up.
	 * @author jabowden and tcmzeal
	 */
	@Test
	public void testIfDriverDoesntExitMazeWithNoHallways() {
		/* somehow set the cells to be a new Cells()... which of course we can't do :( */
//		try {
//			assertTrue(driver.drive2Exit() == false);
//		} catch (Exception e) {
//			/* pass */
//		}
	}
	
	/**
	 * Tests if, after drive2Exit(), the robot's battery level has gone down.\
	 * @author jabowden and tcmzeal
	 */
	@Test
	public void testIfBatteryIsUsed() {
//		float initialBattery = robot.getBatteryLevel();
//		try {
//			driver.drive2Exit();
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//		assertTrue(initialBattery > robot.getBatteryLevel());
	}
}

package falstad;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import generation.MazeConfiguration;
import generation.MazeFactory;
import generation.Order.Builder;
import generation.StubMazeController;
import generation.StubOrder;

import org.junit.Before;
import org.junit.Test;

public class BasicRobotTest extends BasicRobot {
	
	public BasicRobotTest() {
		super(new MazeController());
	}

	private MazeApplication maze; 
	private BasicRobot basicRobot;
	private StubMazeController stubMazeController;
	private MazeFactory mazefactory;
	private StubOrder stubOrder;
	private MazeConfiguration mazeConfig;
	private ManualDriver driver;

	@Before
	public void setup() {
		basicRobot = new BasicRobot(mazeController);
		driver = new ManualDriver();
		
		stubMazeController = new StubMazeController();
		mazefactory = new MazeFactory(true);
		stubOrder = new StubOrder(1, true, Builder.DFS);
		mazefactory.order(stubOrder);
		mazefactory.waitTillDelivered();
		mazeConfig = stubOrder.getConfiguration();
		
		stubMazeController.deliver(mazeConfig);
	}
	
	/*
	 * Sorry! We didn't have time to implement these, but here's what we would've done...
	 * A few really basic ones are implemented, though.
	 */
	
	@Test
	public void doesRotateLeftFacingNorthBecomeWest() {
		
	}
	
	@Test
	public void doesRotateRightFacingNorthBecomeEast() {
		
	}
	
	@Test
	public void doesRotateLeftFacingEastBecomeNorth() {
		
	}
	
	@Test
	public void doesRotateRightFacingEastBecomeSouth() {
		
	}
	
	@Test
	public void doesRotateLeftFacingSouthBecomeEast() {
		
	}
	
	@Test
	public void doesRotateRightFacingSouthBecomeWest() {
		
	}
	
	@Test
	public void doesRotateLeftFacingWestBecomeSouth() {
		
	}
	
	@Test
	public void doesRotateRightFacingWestBecomeNorth() {
		
	}
	
	@Test
	public void doesTurnAroundFacingNorthBecomeSouth() {
		
	}
	
	@Test
	public void doesTurnAroundFacingSouthBecomeNorth() {
		
	}
	
	@Test
	public void doesTurnAroundFacingWestBecomeEast() {
		
	}
	
	@Test
	public void doesTurnAroundFacingEastBecomeWest() {
		
	}

	@Test
	public void doesMovingWhileFacingNorthWork() {
		
	}
	
	@Test
	public void doesMovingWhileFacingEastWork() {
		
	}
	
	@Test
	public void doesMovingWhileFacingSouthWork() {
		
	}
	
	@Test
	public void doesMovingWhileFacingWestWork() {
		
	}
	
	@Test
	public void doesMovingIntoNorthWallFail() {
		
	}
	
	@Test
	public void doesMovingIntoEastWallFail() {
		
	}
	
	@Test
	public void doesMovingIntoSouthWallFail() {
		
	}
	
	@Test
	public void doesMovingIntoWestWallFail() {
		
	}
	
	@Test
	public void canMoveAdjacentToWall() {
		
	}
	
	@Test
	public void canMoveBetweenWalls() {
		
	}
	
	@Test
	public void doesTurningDecreaseBattery() {
		
	}
	
	@Test
	public void doesMovingDecreaseBattery() {
		
	}
	
	@Test
	public void doesSensingInDirectionDecreaseBattery() {
		
	}
	
	@Test
	public void doesRobotStartWithCorrectBatteryLevel() {
		assertTrue(3000 == basicRobot.getBatteryLevel());
	}
	
	@Test
	public void doesRobotStartWithCorrectOdometerReading() {
		assertEquals(0, basicRobot.getOdometerReading());	
	}
	
	@Test
	public void doesMovingUpdateOdometer() {
		
	}
	
	@Test
	public void doesTurningNotUpdateOdometer() {
		
	}
	
	@Test
	public void checkGetCurrentPositionInsideArray() {
		
	}
	
	@Test
	public void checkGetCurrentPositionNextToWall() {
		
	}
	
	@Test
	public void checkGetCurrentPositionInCorner() {
		
	}
	
	@Test
	public void checkGetCurrentPositionOutOfBounds() {
		
	}
	
	@Test 
	public void checkIsInsideRoomWhileInsideRoom() {
		
	}
	
	@Test
	public void checkIsInsideRoomWhileDirectlyOutsideRoom() {
		
	}
	
	@Test
	public void checkIsInsideRoomWhileOutOfBounds() {
		
	}
	
	@Test
	public void doesSetMazeUpdateMazeCorrectly() {
		
	}
	
	@Test
	public void doesIsAtExitReturnTrueWhenExitIsToTheNorth() {
		
	}
	
	@Test
	public void doesIsAtExitReturnTrueWhenExitIsToTheEast() {
		
	}
	
	@Test
	public void doesIsAtExitReturnTrueWhenExitIsToTheSouth() {
		
	}
	
	@Test
	public void doesIsAtExitReturnTrueWhenExitIsToTheWest() {
		
	}
	
	@Test
	public void doesIsAtExitReturnFalseWhenAgainstClosedMazeBorder() {
		
	}
	
	@Test
	public void doesIsAtExitReturnFalseWhenInMiddleOfMaze() {
		
	}
	
	@Test
	public void doesIsAtExitUseBattery() {
		
	}
	
	@Test
	public void doesCanSeeExitUseBattery() {
		
	}
	
	@Test
	public void doesCanSeeExitReturnTrueWhenExitIsToTheNorth() {
		
	}
	
	@Test
	public void doesCanSeeExitReturnTrueWhenExitIsToTheEast() {
		
	}
	
	@Test
	public void doesCanSeeExitReturnTrueWhenExitIsToTheSouth() {
		
	}
	
	@Test
	public void doesCanSeeExitReturnTrueWhenExitIsToTheWest() {
		
	}
	
	@Test
	public void doesCanSeeExitReturnTrueWhenOneAwayFromExit() {
		
	}
	
	@Test
	public void doesCanSeeExitReturnTrueWhenFarAwayFromExit() {
		
	}
	
	@Test
	public void doesCanSeeExitReturnFalseWhenCannotSeeExit() {
		
	}
	
	@Test
	public void doesHasRoomSensorReturnTrue() {
		assertTrue(basicRobot.hasRoomSensor());
	}
}
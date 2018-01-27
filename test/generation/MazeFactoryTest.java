package generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import falstad.Constants;
import generation.Order.Builder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MazeFactoryTest {

	private MazeFactory mazeFactory;
	private StubOrder stubOrder;
	private MazeConfiguration configuration;
	
	/**
	 * Sets up a maze factory and stub order to intialize the maze.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		//we want a random maze
		boolean determinate = false;
		//start the maze
		mazeFactory = new MazeFactory(determinate);
		stubOrder = new StubOrder(1, determinate, Builder.DFS);
		mazeFactory.order(stubOrder);
		mazeFactory.waitTillDelivered();
		configuration = stubOrder.getConfiguration();
		
	}

	/**
	 * Resets the maze.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Ensure that the setup initializes everything needed.
	 */
	@Test
	public void doesSetupWork(){
		assertNotNull(mazeFactory);
		assertNotNull(configuration);
		assertNotNull(stubOrder);
	}	
	
	/**
	 * Ensures that there is exactly one cell directly adjacent to an exit.
	 */
	@Test
	public void doesMazeHaveOneExit(){
		//start variables
		int numExits = 0; 
		Distance mazeDistance = configuration.getMazedists(); // gets distance from exit
		
		//for every column across
		for (int w = 0; w < configuration.getWidth(); w++) {
			//and every row down
			for (int h = 0; h < configuration.getHeight(); h++) {
				if (mazeDistance.getDistance(w, h) == 1){  
					//if we find a cell where the distance to the exit is 1, then increase amount of exits we've found
					numExits++;
				}
			}
		}
		//since there's only one exit, we will only have a problem if findExit is not 1. 
		assertEquals("The maze does not have exactly one exit.", 1, numExits); 
	}
	
	/**
	 * Ensures that every cell has a distance to a maze, i.e., the game can generate a path to the maze.
	 */
	@Test
	public void canEveryCellReachExit() {
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in the maze has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Ensures that the maze was generated with the correct size, as defined by the Constants object.
	 */
	@Test
	public void checkDimensions(){
		
		int skillLevel = stubOrder.getSkillLevel();
		int xDimension = Constants.SKILL_X[skillLevel]; 
		int yDimension = Constants.SKILL_Y[skillLevel];
		
		int width = configuration.getWidth();           
		int height = configuration.getHeight();
		
		//do we have the same as it needs to be?
		assertEquals(width, xDimension);
		assertEquals(height, yDimension);
		
	}
}
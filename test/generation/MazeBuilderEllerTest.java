package generation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import generation.Order.Builder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MazeBuilderEllerTest extends MazeBuilderEller {
	private MazeFactory mazeFactory;
	private StubOrder stubOrder;
	private MazeConfiguration configuration;
	
	//build the maze
	@Before
	public void setUp() throws Exception {
		/* false = random maze */
		mazeFactory = new MazeFactory(false);
		stubOrder = new StubOrder(1, false, Builder.Eller);
		mazeFactory.order(stubOrder);
		mazeFactory.waitTillDelivered();
		configuration = stubOrder.getConfiguration();
		width = 8;
		height = 8;
		cells = new Cells(width, height);
	}

	//end maze
	@After
	public void tearDown() throws Exception {
		resetLowestUnusedID();
	}
	
	/**
	 * Tests if set IDs are assigned correctly in the first row.
	 */
	@Test
	public void testIfSetIDsAssignedCorrectlyInFirstRow() {
		int[] resultArray = setUpSetIDs(0, new int[width]);
		assertArrayEquals("The IDs in the first row are not set up correctly.", resultArray,
				new int[]{0, 1, 2, 3, 4, 5, 6, 7});
	}
	
	/**
	 * Tests if set IDs can merge correctly in the middle rows, using presence of right walls as the metric.
	 */
	@Test
	public void testIfSetIDsMergeCorrectly() {
		int resultArray[] = removeRightWalls(4, new int[]{0, 1, 2, 3, 3, 4, 4, 4});
		/* some consecutive sets are 1 long. some are longer. */
		boolean isCorrect = true;
		for (int i = 0; i < width - 1; i++) {
			if (cells.hasWall(i, 4, CardinalDirection.East)) {
				/* the cell and its right neighbor should not be equal. */
				if (resultArray[i] == resultArray[i + 1]) {
					isCorrect = false;
				}
			}
		}
		assertTrue("The set IDs are not merged correctly in the middle rows.", isCorrect);
	}
	
	/**
	 * Tests if right walls are taken down correctly in the middle rows, using set IDs as the metric.
	 */
	@Test
	public void testIfRightWallsSetCorrectlyInMiddle() {
		int resultArray[] = removeRightWalls(4, new int[]{0, 1, 2, 3, 3, 4, 4, 4});
		/* some consecutive sets are 1 long. some are longer. */
		boolean isCorrect = true;
		for (int i = 0; i < width - 1; i++) {
			if (resultArray[i] == resultArray[i + 1]) {
				/* the wall should've come down.*/
				if (cells.hasWall(i, 4, CardinalDirection.East)) {
					isCorrect = false;
				}
			}
		}
		assertTrue("The right walls are not set correctly in the middle rows.", isCorrect);
	}
	
	/**
	 * Tests if right walls are taken down correctly in the last row.
	 */
	@Test
	public void testIfRightWallsSetCorrectlyInLastRow() {
		removeRightWalls(7, new int[]{0, 1, 2, 3, 4, 5, 6, 7});
		/* since no cells are equal to their neighbors, every wall should come down */
		int wallCount = 0;
		for (int i = 0; i < width; i++) {
			if (cells.hasWall(i, 7, CardinalDirection.East)) {
				wallCount++;
			}
		}
		assertEquals("The right walls are not set correctly in the last row.", wallCount, 0);
	}
	
	/**
	 * Tests if bottom rows are set correctly in the middle rows.
	 */
	@Test 
	public void testIfBottomWallsSetCorrectly() {
		removeBottomWalls(4, new int[]{0, 0, 1, 2, 2, 2, 2, 2});
		/* there should be at least three openings for three consecutive sets*/
		int openingCount = 0;
		for (int i = 0; i < width; i++) {
			if (!cells.hasWall(i, 4, CardinalDirection.South)) {
				openingCount++;
			}
		}
		assertTrue("The bottom walls are not set correctly in the middle rows.", openingCount > 3);
	}
	
	/**
	 * Tests if set IDs are set correctly in the top row.
	 */
	@Test
	public void testIfSetIDsSetCorrectlyInTopRow() {
		int[] resultArray = removeBottomWalls(7, new int[]{0, 1, 2, 3, 4, 5, 6, 7});
		assertArrayEquals("The bottom walls are not set correctly in the top row.", resultArray,
				new int[]{0, 1, 2, 3, 4, 5, 6, 7});
	}
	
	/**
	 * Tests if correct maze can generate if a room takes up the entire center, leaving only one-wide walkways on each side.
	 */
	@Test
	public void testIfMazeCorrectIfRoomTakesUpEntireCenter() {
		cells.markAreaAsRoom(6, 6, 1, 1, 6, 6);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in the maze with one-wide walkways on each side has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if rooms take up everything except two perpendicular one-wide walkways across the middle and one-wide walkways on each side.
	 */
	@Test
	public void testIfMazeCorrectIfRoomHasFourRoomsWithOneWidePathsBetween() {
		cells.markAreaAsRoom(2, 3, 1, 1, 2, 6);
		cells.markAreaAsRoom(3, 3, 4, 1, 6, 6);
		cells.markAreaAsRoom(2, 2, 1, 1, 2, 6);
		cells.markAreaAsRoom(3, 2, 4, 1, 6, 6);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in the maze with two perpendicular one-wide walkways has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if rooms take up everything except one horizontal one-wide walkway across the middle and one-wide walkways on each side.
	 */
	@Test
	public void testIfMazeCorrectIfRoomHasTwoTallRoomsWithOneWidePathsBetween() {
		cells.markAreaAsRoom(2, 6, 1, 1, 2, 6);
		cells.markAreaAsRoom(3, 6, 4, 1, 6, 6);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in the maze with one horizontal one-wide walkway has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if rooms take up everything except one vertical one-wide walkway across the middle and one-wide walkways on each side.
	 */
	@Test
	public void testIfMazeCorrectIfRoomHasTwoLongRoomsWithOneWidePaths() {
		cells.markAreaAsRoom(6, 2, 1, 1, 6, 2);
		cells.markAreaAsRoom(6, 3, 1, 4, 6, 6);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in the maze with one vertical one-wide walkway has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if the maze has one row.
	 */
	@Test
	public void doesMazeGenerateWithOneRow() {
		width = 8;
		height = 1;
		cells = new Cells(width, height);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in a maze with one row has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if the maze has two rows.
	 */
	@Test
	public void doesMazeGenerateWithTwoRows() {
		width = 8;
		height = 2;
		cells = new Cells(width, height);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in a maze with two rows has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if the maze has one column.
	 */
	@Test
	public void doesMazeGenerateWithOneColumn() {
		width = 1;
		height = 8;
		cells = new Cells(width, height);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in a maze with one column has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if the maze has two columns.
	 */
	@Test
	public void doesMazeGenerateWithTwoColumns() {
		width = 2;
		height = 8;
		cells = new Cells(width, height);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in a maze with two columns has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if the maze is 1x1.
	 */
	@Test
	public void does1x1MazeGenerate() {
		width = 1;
		height = 1;
		cells = new Cells(width, height);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in a 1x1 maze has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
	
	/**
	 * Tests if correct maze can generate if the maze is 2x2.
	 */
	@Test
	public void does2x2MazeGenerate() {
		width = 2;
		height = 2;
		cells = new Cells(width, height);
		generatePathways();
		Distance mazeDistance = configuration.getMazedists();
		for (int w = 0; w < configuration.getWidth(); w++) {
			for (int h = 0; h < configuration.getHeight(); h++) {
				int dist = mazeDistance.getDistance(w, h);
				assertTrue("Not every position in a 2x2 maze has a path to the exit.",
						dist > 0 && dist < Integer.MAX_VALUE);
			}
		}
	}
}

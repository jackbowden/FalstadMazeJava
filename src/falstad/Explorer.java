package falstad;

import generation.CardinalDirection;

import java.util.Iterator;
import java.util.LinkedList;


public class Explorer extends AutomaticDriver {
	/**
	 * Drives the robot out of the exit of the maze using an explorer algorithm.
	 * @author jabowden and tcmzeal
	 * @return whether the robot was able to exit the maze
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		int[][] timesVisited = new int[width][height];
		for (int i = 0; i < timesVisited.length; i++) {
			for (int j = 0; j < timesVisited[0].length; j++) {
				timesVisited[i][j] = 0;
			}
		}
		
		while (robot.isAtExit() == false) {
			/* start a list of potential positions to move to. */
			LinkedList<CardinalDirectionNumVisitsPair> possibleDirections = new LinkedList<CardinalDirectionNumVisitsPair>();
			
			/* get the directions and number of visits to all accessible adjacent spaces. */
			int robotX = robot.getCurrentPosition()[0];
			int robotY = robot.getCurrentPosition()[1];
			addCardinalDirectionNumVisitsPairIfValid(robotX + 1, robotY, CardinalDirection.East, possibleDirections, timesVisited);
			addCardinalDirectionNumVisitsPairIfValid(robotX - 1, robotY, CardinalDirection.West, possibleDirections, timesVisited);
			addCardinalDirectionNumVisitsPairIfValid(robotX, robotY + 1, CardinalDirection.North, possibleDirections, timesVisited);
			addCardinalDirectionNumVisitsPairIfValid(robotX, robotY - 1, CardinalDirection.South, possibleDirections, timesVisited);
			
			/* find the minimum number of visits. */
			int minVisits = Integer.MAX_VALUE;
			for (CardinalDirectionNumVisitsPair pair : possibleDirections) {
				if (pair.numVisits < minVisits) {
					minVisits = pair.numVisits;
				}
			}
			
			/* remove all pairs that do NOT have minVisits visits from the list. */
			for (Iterator<CardinalDirectionNumVisitsPair> iterator = possibleDirections.listIterator(); iterator.hasNext(); ) {
			    CardinalDirectionNumVisitsPair pair = iterator.next();
			    if (pair.numVisits != minVisits) {
			        iterator.remove();
			    }
			}
			
			/* the list now contains only spaces that have the minimum number of visits. */
			/* pick randomly from the list. */
			int randomIndex = (int) Math.floor(Math.random() * possibleDirections.size());
			CardinalDirectionNumVisitsPair randomPair = possibleDirections.get(randomIndex);
			
			/* turn to face that direction. */
			robot.turnToDirection(randomPair.cardinalDirection);
			
			/* move. */
			robot.move(1, false);
			
			/* update the numVisits array. */
			timesVisited[robot.getCurrentPosition()[0]][robot.getCurrentPosition()[1]]++;
		}
		
		return robot.stepOutOfExit();
	}
	
//	private void addDoorIfValid(int[] pos, LinkedList<PositionNumVisitsPair> list, int[][] timesVisited) {
//		if (!robot.hasWallInDirection(CardinalDirection.North) &&
//				!robot.hasWallInDirection(CardinalDirection.East) &&
//				!robot.hasWallInDirection(CardinalDirection.South) &&
//				!robot.hasWallInDirection(CardinalDirection.West)) {
//			int numVisits = timesVisited[pos[0]][pos[1]];
//			list.add(new PositionNumVisitsPair(pos, numVisits));
//		}
//	}

	/**
	 * Looks in a particular CardinalDirection. If the cell in that direction is accessible, a CardinalDirectionNumVisitsPair
	 * with the CardinalDirection and the number of visits of that cell will be added to the possibleDirections list.
	 * @author jabowden and tcmzeal
	 * @param x the x-coordinate of the robot
	 * @param y the y-coordinate of the robot
	 * @param cardinalDirection the cardinal direction being checked
	 * @param possibleDirections the list of possible directions the robot could go into
	 * @param timesVisited an array holding the number of times each cell has been visited
	 */
	private void addCardinalDirectionNumVisitsPairIfValid(int x, int y, CardinalDirection cardinalDirection,
			LinkedList<CardinalDirectionNumVisitsPair> possibleDirections, int[][] timesVisited) {
		if (x >= 0 && x < width && y >= 0 && y < height && !robot.hasWallInDirection(cardinalDirection)) {
			possibleDirections.add(new CardinalDirectionNumVisitsPair(cardinalDirection, timesVisited[x][y]));
		}
	}
	
	/**
	 * A helper class that holds a pair of a cardinal direction and the number of visits at the cell in the cardinal direction of the robot's current position.
	 * @author jabowden and tcmzeal
	 */
	class CardinalDirectionNumVisitsPair {
		CardinalDirection cardinalDirection;
		int numVisits;
		public CardinalDirectionNumVisitsPair(CardinalDirection cardinalDirection, int numVisits) {
			this.cardinalDirection = cardinalDirection;
			this.numVisits = numVisits;
		}
		
		public String toString() {
			return this.cardinalDirection.toString() + " " + numVisits;
		}
	}
	
//	class PositionNumVisitsPair {
//		int[] position;
//		int numVisits;
//		
//		public PositionNumVisitsPair(int[] position, int numVisits) {
//			this.position = position;
//			this.numVisits = numVisits;
//		}
//	}
}

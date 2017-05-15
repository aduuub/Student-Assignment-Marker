package swen221.assignment1;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import maze.*;

/**
 * An implementation of the left walker, which you need to complete according to
 * the specification given in the assignment handout.
 *
 */
public class LeftWalker extends Walker {
	public String dir = "n";
	@SuppressWarnings("unused")
	private Direction current = Direction.valueOf("WEST");
	ArrayList<int[]> locations = new ArrayList<int[]>();
	Deque<String> path = new ArrayDeque<String>();
	int x = 0;
	int y = 0;

	public LeftWalker() {
		super("Left Walker");
		locations.add(new int[] { 0, 0 });
	}

	@Override
	protected Direction move(View v) {
		// Use the pause() command to slow the simulation down so you can see
		// what's happening...
		pause(1000);

		// Currently, the left walker just follows a completely random
		// direction. This is not what the specification said it should do, so
		// tests will fail ... but you should find it helpful to look at how it
		// works!
		// return getRandomDirection(v);
		System.out.println("Direction: " + dir);
		return getDirection(v);
	}

	private Direction getDirection(View v) {
		@SuppressWarnings("unused")
		Direction check;
		List<Direction> possibleDirections = determinePossibleDirections(v);
		for (Direction d : possibleDirections){
			System.out.println(d.toString());
		}
		// if all sides are clear, continues the way it was going
		if (possibleDirections.size() == 4) {
			if (dir.equals("w")) {
				if (!checkLocation(dir)) {
					locations.add(new int[] { x-1, y });
					this.x -= 1;
					path.push(dir);
					return Direction.valueOf("WEST");
				}

			} else if (dir.equals("n")) {
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y+1 });
					this.y += 1;
					path.push(dir);
					return Direction.valueOf("NORTH");
				}
			} else if (dir.equals("e")) {
				if (!checkLocation(dir)) {
					locations.add(new int[] { x+1, y });
					this.x += 1;
					path.push(dir);
					return Direction.valueOf("EAST");
				}
			} else {
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y-1 });
					this.y -= 1;
					path.push(dir);
					return Direction.valueOf("SOUTH");
				}
			}
		}
		// first it checks if it a wall is in front of it
		// if there is, it turns so that the wall is on its left
		// otherwise, for each direction it checks if it can go left
		// if it can't it goes forward
		// if it can't go forward it goes right
		// and if it can't go right it turns around

		if (dir.equals("w")) {
			if (!possibleDirections.contains(Direction.valueOf("WEST"))){
				if (possibleDirections.contains(Direction.valueOf("NORTH"))){
					dir = "n";
					path.push(dir);
					return Direction.valueOf("NORTH");
				}
			}
			if (possibleDirections.contains(Direction.valueOf("SOUTH"))) {
				dir = "s";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y-1 });
					this.y -= 1;
					path.push(dir);
					return Direction.valueOf("SOUTH");
				}
			} else if (possibleDirections.contains(Direction.valueOf("WEST"))) {
				dir = "w";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x-1, y});
					this.x -= 1;
					path.push(dir);
					return Direction.valueOf("WEST");
				}
			} else if (possibleDirections.contains(Direction.valueOf("NORTH"))) {
				dir = "n";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y+1 });
					this.y += 1;
					path.push(dir);
					return Direction.valueOf("NORTH");
				}
			} else {
				dir = "e";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x + 1, y });
					this.x += 1;
					path.push(dir);
					return Direction.valueOf("EAST");
				}
			}
		}

		else if (dir.equals("n")) {
			System.out.println("I get here");
			if (!possibleDirections.contains(Direction.valueOf("NORTH"))){
				if (possibleDirections.contains(Direction.valueOf("EAST"))){
					dir = "e";
					path.push(dir);
					return Direction.valueOf("EAST");
				}
			}
			if (possibleDirections.contains(Direction.valueOf("WEST"))) {
				dir = "w";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x-1, y});
					this.x -= 1;
					path.push(dir);
					return Direction.valueOf("WEST");
				}
			} else if (possibleDirections.contains(Direction.valueOf("NORTH"))) {
				System.out.println("I get here 3");
				//for (Direction d : possibleDirections){
					//System.out.println(d.toString());
				//}
				dir = "n";
				if (!checkLocation(dir)) {
					System.out.println("Hi ben");
					locations.add(new int[] { x, y+1 });
					this.y += 1;
					path.push(dir);
					return Direction.valueOf("NORTH");
				}
			} else if (possibleDirections.contains(Direction.valueOf("EAST"))) {
				dir = "e";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x+1, y});
					this.x += 1;
					path.push(dir);
					return Direction.valueOf("EAST");
				}
			} else {
				System.out.println("Did it get here at least?\n");
				dir = "s";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y-1 });
					this.y -= 1;
					path.push(dir);
					return Direction.valueOf("SOUTH");
				}
			}
		}
		else if (dir.equals("e")) {
			if (!possibleDirections.contains(Direction.valueOf("EAST"))){
				if (possibleDirections.contains(Direction.valueOf("SOUTH"))){
					dir = "s";
					path.push(dir);
					return Direction.valueOf("SOUTH");
				}
			}
			if (possibleDirections.contains(Direction.valueOf("NORTH"))) {
				dir = "n";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y+1 });
					this.y += 1;
					path.push(dir);
					return Direction.valueOf("NORTH");
				}
			} else if (possibleDirections.contains(Direction.valueOf("EAST"))) {
				dir = "e";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x+1, y});
					this.x += 1;
					path.push(dir);
					return Direction.valueOf("EAST");
				}
			} else if (possibleDirections.contains(Direction.valueOf("SOUTH"))) {
				dir = "s";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y-1 });
					this.y -= 1;
					path.push(dir);
					return Direction.valueOf("SOUTH");
				}
			} else {
				dir = "w";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x-1, y });
					this.x -= 1;
					path.push(dir);
					return Direction.valueOf("WEST");
				}
			}
		}

		else if (dir.equals("s")) {
			if (!possibleDirections.contains(Direction.valueOf("SOUTH"))){
				if (possibleDirections.contains(Direction.valueOf("WEST"))){
					dir = "w";
					return Direction.valueOf("WEST");
				}
			}
			if (possibleDirections.contains(Direction.valueOf("EAST"))) {
				dir = "e";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x+1, y});
					this.x += 1;
					return Direction.valueOf("EAST");
				}
			} else if (possibleDirections.contains(Direction.valueOf("SOUTH"))) {
				dir = "s";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y-1 });
					this.y -= 1;
					return Direction.valueOf("SOUTH");
				}
			} else if (possibleDirections.contains(Direction.valueOf("WEST"))) {
				dir = "w";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x-1, y});
					this.x -= 1;
					return Direction.valueOf("WEST");
				}
			} else {
				dir = "n";
				if (!checkLocation(dir)) {
					locations.add(new int[] { x, y+1 });
					this.y += 1;
					return Direction.valueOf("NORTH");
				}
			}
		}
		if (path.isEmpty()){
			System.out.println("Right here ay");
			return getRandomDirection(v);
			}
		String s = path.pop();
		if (s.equals("w")){
			dir = s;
			return Direction.valueOf("EAST");
		}
		else if (s.equals("n")){
			dir = s;
			return Direction.valueOf("SOUTH");
		}
		else if (s.equals("e")){
			dir = s;
			return Direction.valueOf("WEST");
		}
		else {
			dir = s;
			return Direction.valueOf("NORTH");
		}
		
		

	}

	// based on the direction
	// it checks if the next square it can go to has been visited before
	public boolean checkLocation(String c) {
		
			if (c.equals("w")) {
				if (compareTo(this.x-1, this.y)) {
					return true;
				}
			} else if (c.equals("n")) {
				if (compareTo(this.x, this.y+1)) {
					return true;
				}
			} else if (c.equals("e")) {
				if (compareTo(this.x+1, this.y)) {
					return true;
				}
			} else if (c.equals("s")) {
				if (compareTo(this.x, this.y-1)) {
					return true;
				}
			}

		
		return false;

	}

	// compares the coordinates to all the locations currently visited
	public boolean compareTo(int hor, int ver) {
		for (int[] check : locations){
			if (check[0] == hor) {
				if (check[1] == ver) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * This simply returns a randomly chosen (valid) direction which the walker
	 * can move in.
	 *
	 * @param View
	 *            v
	 * @return
	 */

	private Direction getRandomDirection(View v) {
		// The random walker first decides what directions it can move in. The
		// walker cannot move in a direction which is blocked by a wall.
		List<Direction> possibleDirections = determinePossibleDirections(v);

		// Second, the walker chooses a random direction from the list of
		// possible directions
		return selectRandomDirection(possibleDirections);
	}

	/**
	 * Determine the list of possible directions. That is, the directions which
	 * are not blocked by a wall.
	 *
	 * @param v
	 *            The View object, with which we can determine which directions
	 *            are possible.
	 * @return
	 */
	private List<Direction> determinePossibleDirections(View v) {
		Direction[] allDirections = Direction.values();
		ArrayList<Direction> possibleDirections = new ArrayList<Direction>();

		for (Direction d : allDirections) {
			if (v.mayMove(d)) {
				// Yes, this is a valid direction
				possibleDirections.add(d);
			}
		}

		return possibleDirections;
	}

	/**
	 * Select a random direction from a list of possible directions.
	 *
	 * @param possibleDirections
	 * @return
	 */
	private Direction selectRandomDirection(List<Direction> possibleDirections) {
		int random = (int) (Math.random() * possibleDirections.size());
		return possibleDirections.get(random);
	}
}
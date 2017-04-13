package swen221.assignment1;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import maze.*;

/**
 * An implementation of the left walker, which you need to complete according to
 * the specification given in the assignment handout.
 * @param <toBeWalked>
 * 
 */
public class LeftWalker extends Walker {

	private Direction currentDirection; // current direction the walker is facing
	
	//  fields to check if there is a wall on each direction
    private boolean hasLeftWall;
    private boolean hasRightWall;
    private boolean hasFrontWall;
    private boolean hasBackWall;
    
    private boolean isSearching; //check if the walker looking for a wall
    
    
	private Coord currentCoord; //current coordinate location of the walker
	
	
	
	/**
	*hash map to store the walkers history
	*each key is a square the walker has visited
	*each value is a list of direction yet to be walked from this square
	*/
	private HashMap<Coord, List<Direction>> toBeWalked;
	
	/**
	*Constructor creating the walker,
	*Initialising the direction and starting coordinates of the walker
	*/
	public LeftWalker() {
		super("Left Walker");
		currentDirection = Direction.NORTH;
		currentCoord = new Coord(0, 0 );
		isSearching = true;
		toBeWalked = new HashMap<>();
	}
	
	/**

	@Override
	@param view
	@return The direction the walker should take
	
	*/
	@Override
	protected Direction move(View view) {
		
		//  you need to implement this method according to the
		// specification given for the left walker!!

		// Use the pause() command to slow the simulation down so you can see
		// what's happening...
		pause(10);
		
		//all possible directions for the walker to take
		if(!toBeWalked.containsKey(currentCoord)) {
			List<Direction> possibleMove = determinePossibleDirections(view);
			toBeWalked.put(currentCoord, possibleMove);
		}
		
		updateWalls(view);
		
		//check if the walker is searching for a wall
		 if (isSearching) {
	            // Starts to look fort a wall instead of trying to follow a wall.
	            searchForLeftWall();
	        } else {
	            // Wall-following mode.
	            followLeftWall();
	        }

		//uses the walkers history to determine un-walked path
		 memorise();
		
		return currentDirection;
		
	}

	/**
     * Update the four directions.
     * 
     * @param view
     * the view the walker has from the current position in the maze.
     */
	public void updateWalls(View v){
		
		hasLeftWall = hasLeftWall(v, currentDirection);
		hasRightWall = hasRightWall(v, currentDirection);
		hasFrontWall = hasFrontWall(v, currentDirection);
		hasBackWall = hasBackWall(v, currentDirection);
		
	}
	
	/**
	 * Method on how the walker selects finds a wall. if there is no wall, he goes forward
	 * if he finds a wall, he changes direction and walks along it to his left side 
	 */
	
	private void searchForLeftWall(){
		//if a wall is found
		isSearching = !hasLeftWall && !hasFrontWall && !hasRightWall && !hasBackWall;

		if(hasLeftWall && !hasFrontWall){} //don't change direction
			
		else if(hasLeftWall && hasFrontWall){ // corner of maze, turn right
			currentDirection = rightDirection(currentDirection);
		}
		else if(hasFrontWall && !hasRightWall){ // turn right
			currentDirection = rightDirection(currentDirection);
		}
		else if(hasRightWall && !hasLeftWall){ //turn left
			currentDirection = leftDirection(currentDirection);
		}
		else if(hasRightWall && !hasBackWall){ //turn back
			currentDirection = backDirection(currentDirection);
		}
		
		
	}
	
	/**
	 * Method on how the walker follows along a wall. 
	 * He tries to follow a left wall as often as possible
	 * 
	 */
	
	private void followLeftWall(){
		
		if(!hasLeftWall){ //turn left
			currentDirection = leftDirection(currentDirection);
		}else if(!hasFrontWall){ 
			//go straight, don't change direction
		}else if(!hasRightWall){ //turn right
			currentDirection = rightDirection(currentDirection);
		}else if(!hasBackWall){ //turn around
			currentDirection = backDirection(currentDirection);
		}else if(hasFrontWall && hasLeftWall && hasRightWall){
			currentDirection = backDirection(currentDirection);
		}
	}
	
	/**
	 * A list of directions the walker can use to store memory of walked paths
	 * Method called in the move(view) method to avoid previously walked paths
	 */
	
	 private void memorise() {
	        // what choices the walker has
	        List<Direction> directions = toBeWalked.get(currentCoord);
	        
	        // if the direction he just chose is not one of the remaining choices
	        if (!directions.contains(currentDirection) && !directions.isEmpty()) {
	        	
	            // he choose the first of the remaining choices.
	        	
	            currentDirection = toBeWalked.get(currentCoord).get(0);
	            
	            // and he needs to search for a wall again (even he has one on the other side)
	            isSearching = true;
	        }
	            
	        // tick off the direction he is going to take
	        toBeWalked.get(currentCoord).remove(currentDirection);

	        // update the current coordinate
	        currentCoord = this.currentCoord.go(currentDirection);
	 }
	        
	/**
	 * 
	 * @param currentDirection
	 * @return
	 * returns the walkers direction when a left move occurs
	 */
	 private Direction leftDirection(Direction currentDirection) {
	        Direction direction = null;
	        if (currentDirection == Direction.NORTH) {
	            direction = Direction.WEST;
	        } else if (currentDirection == Direction.EAST) {
	            direction = Direction.NORTH;
	        } else if (currentDirection == Direction.SOUTH) {
	            direction = Direction.EAST;
	        } else if (currentDirection == Direction.WEST) {
	            direction = Direction.SOUTH;
	        }
	        return direction;
	    }
	 /**
	  * 
	  * @param currentDirection
	  * @return
	  * returns the walkers direction when a right move occurs
	  */
	 private Direction rightDirection(Direction currentDirection) {
	        Direction direction = null;
	        if (currentDirection == Direction.NORTH) {
	            direction = Direction.EAST;
	        } else if (currentDirection == Direction.EAST) {
	            direction = Direction.SOUTH;
	        } else if (currentDirection == Direction.SOUTH) {
	            direction = Direction.WEST;
	        } else if (currentDirection == Direction.WEST) {
	            direction = Direction.NORTH;
	        }
	        return direction;
	    }
	 
	 /**
	  * 
	  * @param currentDirection
	  * @return
	  * returns the walkers direction when a turnaround/back move occurs
	  */
	 private Direction backDirection(Direction currentDirection) {
	        Direction direction = null;
	        if (currentDirection == Direction.NORTH) {
	            direction = Direction.SOUTH;
	        } else if (currentDirection == Direction.EAST) {
	            direction = Direction.WEST;
	        } else if (currentDirection == Direction.SOUTH) {
	            direction = Direction.NORTH;
	        } else if (currentDirection == Direction.WEST) {
	            direction = Direction.EAST; 
	        }
	        return direction;
	    }
	
	//prints a "Left" when the walker turns left
	private boolean hasLeftWall(View v, Direction currentDirection) {
		Direction left = leftDirection(currentDirection);
		System.out.println(currentDirection);
		return !v.mayMove(left);
		
	}
	//prints a "Right" when the walker turns right
	private boolean hasRightWall(View v, Direction currentDirection) {
		Direction right = rightDirection(currentDirection);
		System.out.println(currentDirection);
		return !v.mayMove(right);
	}
	//prints the current direction when the walker continues on hisw current path
	private boolean hasFrontWall(View v, Direction currentDirection) {
		System.out.println(currentDirection);
		return !v.mayMove(currentDirection);
    }
	
	//prints a "back" when the walker turns around
	private boolean hasBackWall(View v, Direction currentDirection) {
		Direction back = rightDirection(currentDirection);
		System.out.println(currentDirection);
		return !v.mayMove(back);
	}
	
	/**
	 * Determine a list of possible directions
	 * @param v
	 * @return
	 */
	private List<Direction> determinePossibleDirections (View v){
		Direction [] allDirections = Direction.values();
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
     * This is a coordinates class for the walker's location. 
     * The walker is initially set to (0, 0), the start position of maze. 
     * The x coordinate increases from left to right, and the y coordinate increases
     * from up to down.
     * 
     * @author Daniel Walker
     */
    private class Coord {
        // coordinates
        private int x;
        private int y;
        
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public Coord go(Direction currentDirection) {
            Coord coord = null;
            if (currentDirection == Direction.NORTH) {
                coord = new Coord(this.x, this.y - 1);
            } else if (currentDirection == Direction.EAST) {
                coord = new Coord(this.x + 1, this.y);
            } else if (currentDirection == Direction.SOUTH) {
                coord = new Coord(this.x, this.y + 1);
            } else if (currentDirection == Direction.WEST) {
                coord = new Coord(this.x - 1, this.y);
            }
            return coord;
        }
        
        @Override
        /**
         * Overridden method, in order to let the HashMap take two Coord objects
         * that have same x and y as the same key.
         */
        public int hashCode() {
            int primeX = 1;  
            int primeY = 1;
            return primeX * this.x + primeY * this.y;
        }

        @Override
        /**
         * Overrided method, in order to let the HashMap take two Coord objects
         * that have same x and y as the same key.
         */
        public boolean equals(Object obj) {

            if (this == obj) {
                return true;
            }

            if (obj instanceof Coord) {
                Coord coord = (Coord) obj;
                boolean b = (coord.x == this.x && coord.y == this.y);
                return b;
            }

            return false;
        }
    
   }


}
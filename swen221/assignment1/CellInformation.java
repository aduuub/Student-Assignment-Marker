package swen221.assignment1;

import java.awt.Point;

import maze.Direction;

/**
 * The purpose of this class is for the memorization part of the left walker. It
 * stores a Point object and a Direction value, which is supposed to represent
 * the direction the walker left the Point. Also has an equal method for comparisons
 * within the left walker class.
 * @author Kyle Claudio, 300385990
 *
 */

public class CellInformation {
	private Point point;
	private Direction exitDirection;
	
	public CellInformation(Point p, Direction d) {
		this.point = p;
		this.exitDirection = d;
	}

	public Point getPoint(){
		return this.point;
	}
	
	public Direction getExitDirection(){
		return this.exitDirection;
	}
	
	public boolean equals(int x, int y){
		return (x == this.point.x && y == this.point.y);
	}
}

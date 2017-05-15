package swen221.shapedrawer.shapes;

public class ShapeIntersection extends ShapeOperators implements Shape{
	

	public ShapeIntersection(Shape s1, Shape s2){
		super(s1,s2);//makes a reference to the constructor in the ShapeOperators abstract class
	}				// which also deals with the bounding box method.

	@Override
	public boolean contains(int x, int y) {
		if(this.shape1.contains(x, y) && this.shape2.contains(x, y)){
			return true;//for shape intersections, returns true for any aspect within both shapes at once
		}
		return false;
	}


}

package swen221.shapedrawer.shapes;

public abstract class ShapeOperators implements Shape{
	
	private int x;
	private int y;
	private int width;
	private int height;
	public Shape shape1;
	public Shape shape2;
	
	public ShapeOperators(Shape s1, Shape s2){
		this.shape1 = s1;
		this.shape2 = s2;
	}
	
	
	public Rectangle boundingBox() {
		Rectangle rect1 = shape1.boundingBox();
		Rectangle rect2 = shape2.boundingBox();
		
		if((rect1.x < rect2.x)){ //sets x and later y for the bounding box to the smallest of the two 
			this.x = rect1.x;   // as this will be the furthest top left corner of the box.
		}
		else{
			this.x = rect2.x;
		}
		if(rect1.y < rect2.y){
			this.y = rect1.y;
		}
		else{
			this.y = rect2.y;
		}
		if(rect1.x + rect1.width > rect2.x + rect2.width){ //sets the width and later height to be the largest
			this.width = rect1.x + rect1.width;           // of the combined x+width and y+height, as this will
		}												//be the furthest bottom right corner of the box
		else{
			this.width = rect2.x + rect2.width;	
		}
		if(rect1.y + rect1.height > rect2.y + rect2.height){
			this.height = rect1.y + rect1.height;
		}
		else{
			this.height = rect2.y + rect2.height;	
		}
		return new Rectangle(this.x,this.y, this.width,this.height);
	}

}

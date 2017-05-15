package swen221.shapedrawer.shapes;

public class Rectangle implements Shape{
	
public int x; //these are left public so the aspects of a bounding box can be accessed more easily in several other classess
public int y;
public int width;
public int height;

public Rectangle(int x, int y, int width, int height){
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
}

@Override
public boolean contains(int x, int y) {
	if(x >= this.x && x < this.x+this.width){
		if(y >= this.y && y< this.y+this.height){
			return true;
		}
		return false;
	}
	return false;
}

@Override
public Rectangle boundingBox() {
	return this; //as all bounding boxes are rectangles
}


}

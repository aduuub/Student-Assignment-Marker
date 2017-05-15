package swen221.shapedrawer.shapes;

public class Main {
	
public static void main(String[] args){
	Canvas canvas = new Interpreter("x = [2,2,50,50]\ny = [30,30,50,50]\ny = y + x\ndraw y #00ff00\n").run();
	canvas.show();
}

}

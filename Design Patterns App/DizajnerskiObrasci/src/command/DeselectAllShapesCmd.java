package command;

import java.util.ArrayList;

import geometry.Shape;
import mvc.DrawingModel;

public class DeselectAllShapesCmd implements Command {

	private DrawingModel model;
	private ArrayList<Shape> shapeList;

	public DeselectAllShapesCmd( ArrayList<Shape> shapeList) {
		this.shapeList = shapeList;
	}
	
	@Override
	public void execute() {
		shapeList.forEach(shape -> shape.setSelected(false));
		
	}

	@Override
	public void unexecute() {
		shapeList.forEach(shape -> shape.setSelected(true));
		
	}

}

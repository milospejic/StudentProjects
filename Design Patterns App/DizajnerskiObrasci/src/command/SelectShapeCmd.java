package command;

import geometry.Shape;
import mvc.DrawingModel;

public class SelectShapeCmd implements Command {

	private DrawingModel model;
	private Shape shape;

	public SelectShapeCmd(Shape shape) {
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		shape.setSelected(true);
		
	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
		
	}

}

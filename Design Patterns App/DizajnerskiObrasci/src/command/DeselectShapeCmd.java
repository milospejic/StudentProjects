package command;

import geometry.Shape;
import mvc.DrawingModel;

public class DeselectShapeCmd implements Command {

	private DrawingModel model;
	private Shape shape;

	public DeselectShapeCmd(Shape shape) {
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		shape.setSelected(false);
		
	}

	@Override
	public void unexecute() {
		shape.setSelected(true);
		
	}

}

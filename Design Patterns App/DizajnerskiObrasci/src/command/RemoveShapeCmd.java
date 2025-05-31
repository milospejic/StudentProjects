package command;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int index;

	public RemoveShapeCmd(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
		index=model.getShapes().indexOf(shape);
	}

	@Override
	public void execute() {
		model.remove(shape);

	}

	@Override
	public void unexecute() {
		model.getShapes().add(index,shape);

	}

}

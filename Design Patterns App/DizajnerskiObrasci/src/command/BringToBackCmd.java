package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command{
	DrawingModel model;
	Shape shape;
	int index;
	
	public BringToBackCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}
	@Override
	public void execute() {
		shape= model.getShape(index);
		model.remove(shape);
		model.insertShape(0, shape);
		
	}

	@Override
	public void unexecute() {
		
		model.remove(shape);
		model.insertShape(index,shape);
		
	}

}
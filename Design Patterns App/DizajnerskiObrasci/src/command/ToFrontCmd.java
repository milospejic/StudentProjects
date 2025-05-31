package command;

import geometry.Shape;
import mvc.DrawingModel;

public class ToFrontCmd implements Command{

	
	DrawingModel model;
	int index;
	
	
	public ToFrontCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		if(index != model.getShapes().size()-1) {
			Shape shape = model.getShape(index);
			model.remove(shape);
			model.insertShape(index+1, shape);
		}
		
	}

	@Override
	public void unexecute() {
			Shape shape = model.getShape(index+1);
			model.remove(shape);
			model.insertShape(index, shape);

	}

}

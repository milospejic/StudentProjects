package command;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command{
	
	DrawingModel model;
	int index;
	
	
	public ToBackCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}

	@Override
	public void execute() {
		if(index != 0) {
			Shape shape = model.getShape(index);
			model.remove(shape);
			model.insertShape(index-1, shape);
		}
		
	}

	@Override
	public void unexecute() {
			Shape shape = model.getShape(index-1);
			model.remove(shape);
			model.insertShape(index, shape);
		
	}

}
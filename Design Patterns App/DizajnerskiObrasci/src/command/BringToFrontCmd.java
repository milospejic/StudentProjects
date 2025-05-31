package command;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command{
	DrawingModel model;
	Shape shape;
	int index;
			
			
			
	public BringToFrontCmd(DrawingModel model, int index) {
		this.model = model;
		this.index = index;
	}
	@Override
	public void execute() {
		shape= model.getShape(index);
		model.remove(shape);
		model.add(shape);
	}

	@Override
	public void unexecute() {
		shape= model.getShape(model.size()-1);
		model.remove(shape);
		model.insertShape(index, shape);
		
	}

}
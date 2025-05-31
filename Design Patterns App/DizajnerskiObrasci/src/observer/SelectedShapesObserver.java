package observer;

import mvc.DrawingFrame;
import mvc.DrawingModel;

public class SelectedShapesObserver implements Observer {

	private int numberOfSelected;
	DrawingFrame frame;
	DrawingModel model;
	
	public SelectedShapesObserver(DrawingFrame frame, DrawingModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void update(int numberOfSelected) {
		this.numberOfSelected=numberOfSelected;
		change();
	}
	
	public void change() {
		if(numberOfSelected==0) {
			frame.getBtnDraw().setEnabled(true);
			frame.getBtnModify().setEnabled(false);
			frame.getBtnDelete().setEnabled(false);
			frame.getBtnToFront().setEnabled(false);
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);
		}else if(numberOfSelected == 1) {
			frame.getBtnDraw().setEnabled(false);
			frame.getBtnModify().setEnabled(true);
			frame.getBtnDelete().setEnabled(true);
			if(model.getShape(0).isSelected()) {
				frame.getBtnToBack().setEnabled(false);
				frame.getBtnBringToBack().setEnabled(false);
			}else {
				frame.getBtnToBack().setEnabled(true);
				frame.getBtnBringToBack().setEnabled(true);
			}
			if(model.getShape(model.size()-1).isSelected()){
				frame.getBtnToFront().setEnabled(false);		
				frame.getBtnBringToFront().setEnabled(false);
			}else {
				frame.getBtnToFront().setEnabled(true);		
				frame.getBtnBringToFront().setEnabled(true);
			}
			
	
		}else if(numberOfSelected >1) {
			frame.getBtnDraw().setEnabled(false);
			frame.getBtnModify().setEnabled(false);
			frame.getBtnDelete().setEnabled(true);
			frame.getBtnToFront().setEnabled(false);
			frame.getBtnToBack().setEnabled(false);
			frame.getBtnBringToFront().setEnabled(false);
			frame.getBtnBringToBack().setEnabled(false);
		}
		
	}

}

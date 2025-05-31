package mvc;

import java.awt.Graphics;
import java.util.Iterator;
import java.awt.Color;
import javax.swing.JPanel;

import geometry.Point;
import geometry.Shape;
import geometry.Shape2D;

public class DrawingView extends JPanel{
	DrawingModel model = new DrawingModel();
	
	public DrawingView() {
		setBackground(Color.WHITE);
	}

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getShapes().iterator();
		while (it.hasNext()) {
			it.next().draw(g);
			
		}
	}
	
	
	public boolean isOnView(Point point) {
		for (int i = model.getShapes().size()-1; i >= 0; i--) {
			if (model.getShape(i).contains(point.getX(), point.getY())) {
				return true;
			}
		}
		return false;
	}
	
	

}

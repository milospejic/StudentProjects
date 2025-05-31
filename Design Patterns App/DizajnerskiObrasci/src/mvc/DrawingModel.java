package mvc;

import java.util.ArrayList;
import java.util.List;

import geometry.Point;
import geometry.Shape;

public class DrawingModel {

	private List<Shape> shapes = new ArrayList<>();

	public void add(Shape p) {
		shapes.add(p);
	}

	public void remove(Shape p) {
		shapes.remove(p);
	}

	public List<Shape> getShapes() {
		return shapes;
	}
	
	public Shape getShape(int index) {
		return shapes.get(index);
	}
	
	public void insertShape(int index, Shape shape) {
		shapes.add(index, shape);
	}
	
	public void clear() {
		shapes.clear();
	}
	
	public boolean isEmpty() {
		return shapes.isEmpty();
	}
	
	public int size() {
		return shapes.size();
	}

}

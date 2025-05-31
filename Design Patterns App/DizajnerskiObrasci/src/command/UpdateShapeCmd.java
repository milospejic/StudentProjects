package command;

import geometry.Shape;
import adapter.HexagonAdapter;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
public class UpdateShapeCmd implements Command {

	private Shape oldShape;
	private Shape newShape;
	private Shape original;
	

	public UpdateShapeCmd(Shape oldShape, Shape newShape) {
		this.oldShape = oldShape;
		this.newShape = newShape;
	}

	@Override
	public void execute() {

		if(oldShape instanceof Point) {
			original = new Point();
			((Point)original).setX(((Point)oldShape).getX());
			((Point)original).setY(((Point)oldShape).getY());
			((Point)original).setColor(((Point)oldShape).getColor());
	
			((Point)oldShape).setX(((Point)newShape).getX());
			((Point)oldShape).setY(((Point)newShape).getY());
			((Point)oldShape).setColor(((Point)newShape).getColor());
		} else if((oldShape instanceof Line) ) {
			original = new Line();
			((Line)original).setStartPoint(((Line)oldShape).getStartPoint());
			((Line)original).setEndPoint(((Line)oldShape).getEndPoint());
			((Line)original).setColor(((Line)oldShape).getColor());
	
			((Line)oldShape).setStartPoint(((Line)newShape).getStartPoint());
			((Line)oldShape).setEndPoint(((Line)newShape).getEndPoint());
			((Line)oldShape).setColor(((Line)newShape).getColor());
		} else if((oldShape instanceof Rectangle) ) {
			original = new Rectangle();
			((Rectangle)original).setUpperLeftPoint(((Rectangle)oldShape).getUpperLeftPoint());
			((Rectangle)original).setHeight(((Rectangle)oldShape).getHeight());
			((Rectangle)original).setWidth(((Rectangle)oldShape).getWidth());
			((Rectangle)original).setColor(((Rectangle)oldShape).getColor());
			((Rectangle)original).setInnerColor(((Rectangle)oldShape).getInnerColor());
	
			((Rectangle)oldShape).setUpperLeftPoint(((Rectangle)newShape).getUpperLeftPoint());
			((Rectangle)oldShape).setHeight(((Rectangle)newShape).getHeight());
			((Rectangle)oldShape).setWidth(((Rectangle)newShape).getWidth());
			((Rectangle)oldShape).setColor(((Rectangle)newShape).getColor());
			((Rectangle)oldShape).setInnerColor(((Rectangle)newShape).getInnerColor());
			
		} else if((oldShape instanceof Donut) ) {
			original = new Donut();
			((Donut)original).setCenter(((Donut)oldShape).getCenter());
			try {
				((Donut)original).setRadius(((Donut)oldShape).getRadius());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((Donut)original).setInnerRadius(((Donut)oldShape).getInnerRadius());
			((Donut)original).setColor(((Donut)oldShape).getColor());
			((Donut)original).setInnerColor(((Donut)oldShape).getInnerColor());
	
			((Donut)oldShape).setCenter(((Donut)newShape).getCenter());
			try {
				((Donut)oldShape).setRadius(((Donut)newShape).getRadius());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((Donut)oldShape).setInnerRadius(((Donut)newShape).getInnerRadius());
			((Donut)oldShape).setColor(((Donut)newShape).getColor());
			((Donut)oldShape).setInnerColor(((Donut)newShape).getInnerColor());
		} else if((oldShape instanceof Circle) ) {
			original = new Circle();
			((Circle)original).setCenter(((Circle)oldShape).getCenter());
			try {
				((Circle)original).setRadius(((Circle)oldShape).getRadius());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((Circle)original).setColor(((Circle)oldShape).getColor());
			((Circle)original).setInnerColor(((Circle)oldShape).getInnerColor());
	
			((Circle)oldShape).setCenter(((Circle)newShape).getCenter());
			try {
				((Circle)oldShape).setRadius(((Circle)newShape).getRadius());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			((Circle)oldShape).setColor(((Circle)newShape).getColor());
			((Circle)oldShape).setInnerColor(((Circle)newShape).getInnerColor());
		} else if((oldShape instanceof HexagonAdapter) ) {
			original = new HexagonAdapter();
			((HexagonAdapter)original).setHexagon(((HexagonAdapter)oldShape).getHexagon());
	
			((HexagonAdapter)oldShape).setHexagon(((HexagonAdapter)newShape).getHexagon());
		}

	}

	@Override
	public void unexecute() {
		
		if(original != null) {
			if(original instanceof Point) {
				((Point)oldShape).setX(((Point)original).getX());
				((Point)oldShape).setY(((Point)original).getY());
				((Point)oldShape).setColor(((Point)original).getColor());
			} else if((oldShape instanceof Line) ) {
		
				((Line)oldShape).setStartPoint(((Line)original).getStartPoint());
				((Line)oldShape).setEndPoint(((Line)original).getEndPoint());
				((Line)oldShape).setColor(((Line)original).getColor());
			} else if((oldShape instanceof Rectangle) ) {
				
				((Rectangle)oldShape).setUpperLeftPoint(((Rectangle)original).getUpperLeftPoint());
				((Rectangle)oldShape).setHeight(((Rectangle)original).getHeight());
				((Rectangle)oldShape).setWidth(((Rectangle)original).getWidth());
				((Rectangle)oldShape).setColor(((Rectangle)original).getColor());
				((Rectangle)oldShape).setInnerColor(((Rectangle)original).getInnerColor());
				
			} else if((oldShape instanceof Circle) ) {
		
				((Circle)oldShape).setCenter(((Circle)original).getCenter());
				try {
					((Circle)oldShape).setRadius(((Circle)original).getRadius());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((Circle)oldShape).setColor(((Circle)original).getColor());
				((Circle)oldShape).setInnerColor(((Circle)original).getInnerColor());
			} else if((oldShape instanceof Donut) ) {
		
				((Donut)oldShape).setCenter(((Donut)original).getCenter());
				try {
					((Donut)oldShape).setRadius(((Donut)original).getRadius());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((Donut)oldShape).setInnerRadius(((Donut)original).getInnerRadius());
				((Donut)oldShape).setColor(((Donut)original).getColor());
				((Donut)oldShape).setInnerColor(((Donut)original).getInnerColor());
			} else if((oldShape instanceof HexagonAdapter) ) {
		
				((HexagonAdapter)oldShape).setHexagon(((HexagonAdapter)original).getHexagon());
			}
		}

	}

}

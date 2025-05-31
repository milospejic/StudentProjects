package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.Shape;


public class Donut extends Circle {

	private int innerRadius;

	public Donut() {

	}

	public Donut(Point center, int radius, int innerRadius) {
		
		super(center, radius);
		this.innerRadius = innerRadius;
	}
	public Donut(Point center, int radius, int innerRadius,Color color) {
		
		this(center, radius, innerRadius);
		setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius,Color color, Color innerColor) {
		
		this(center, radius, innerRadius, color);
		setInnerColor(innerColor);
	}


	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);

	}
	
	public Donut(Point center, int radius,int innerRadius,boolean selected,Color color) {
		
		this(center, radius, innerRadius,selected);
		setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius,boolean selected,Color color, Color innerColor) {
		
		this(center, radius, innerRadius, selected ,color);
		setInnerColor(innerColor);
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if (this.center.equals(pomocni.center) && this.getRadius() == pomocni.getRadius()
					&& innerRadius == pomocni.innerRadius) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean contains(int x, int y) {
		double dFromCenter = getCenter().distance(x, y);
		return dFromCenter > innerRadius && super.contains(x, y);
	}

	public boolean contains(Point p) {
		double dFromCenter = getCenter().distance(p.getX(), p.getY());
		return dFromCenter > innerRadius && super.contains(p.getX(), p.getY());
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
        super.draw(g2d);

        Shape outerCircle = new Ellipse2D.Double(center.getX() - getRadius(), center.getY() - getRadius(), getRadius() * 2, getRadius() * 2);
        Shape innerCircle = new Ellipse2D.Double(center.getX() - innerRadius, center.getY() - innerRadius, innerRadius * 2, innerRadius * 2);

        g2d.setColor(color);
        g2d.draw(outerCircle);
        g2d.draw(innerCircle);
		
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(center.getX() - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() - innerRadius - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() + innerRadius - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() - 2, center.getY() - innerRadius - 2, 4, 4);
			g.drawRect(center.getX() - 2, center.getY() + innerRadius - 2, 4, 4);
		}	
	}

	@Override
	public void fill(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

        Shape outerCircle = new Ellipse2D.Double(center.getX() - getRadius(), center.getY() - getRadius(), getRadius() * 2, getRadius() * 2);
        Shape innerCircle = new Ellipse2D.Double(center.getX() - innerRadius, center.getY() - innerRadius, innerRadius * 2, innerRadius * 2);

        Area donutArea = new Area(outerCircle);
        Area innerCircleArea = new Area(innerCircle);
        donutArea.subtract(innerCircleArea);

        g2d.setColor(getInnerColor());
        g2d.fill(donutArea);
	
	}

	@Override
	public int compareTo(Object o) {

		if(o instanceof Donut) {
			return (int)(this.area()-((Donut)o).area());
		}
		return 0;
	}

	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	public String toString() {
		return super.toString() + " innerRadius= "+innerRadius;
	}

}
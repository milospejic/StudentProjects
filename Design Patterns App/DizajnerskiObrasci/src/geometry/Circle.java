package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape2D{
	
	protected Point center;
	private int radius;
	
	
	public Circle() {
		
	}
	
	public Circle(Point center, int radius) {
		this.center= center;
		this.radius= radius;
	}
	public Circle(Point center, int radius, Color color) {
		this(center, radius);
		this.setColor(color);
	}
	
	public Circle(Point center, int radius, Color color, Color innerColor) {
		this(center, radius, color);
		setInnerColor(innerColor);
	}
	
	public Circle(Point center, int radius, boolean selected) {
		this(center,radius);
		setSelected(selected);
	}
	public Circle(Point center, int radius, boolean selected, Color color) {
		this(center,radius,selected);
		setColor(color);
	}
	
	public Circle(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center,radius,selected, color);
		setInnerColor(innerColor);
	}
	
	public double area() {
		return 2*this.radius*Math.PI;
	}
	
	public double circumference() {
		return 2 * this.radius * Math.PI;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Circle) {
			
			Circle pomocna = (Circle) obj;
			if(this.center.equals(pomocna.center) && this.radius == pomocna.radius) {
				return true;
			}
			return false;
		}else
			return false;
	}
	
	public boolean contains(int x, int y) {
		return center.distance(x, y) <= radius;
	}

	public boolean contains(Point p) {
		return center.distance(p.getX(), p.getY()) <= radius;
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(innerColor);
		g.fillOval(this.center.getX() - this.radius + 1, this.center.getY()- this.radius + 1,
				this.radius*2-2, this.radius*2-2 );			
		
	}

	@Override
	public void draw(Graphics g) {	
		fill(g);
		g.setColor(color);
		g.drawOval(center.getX()-radius, center.getY()-radius, radius*2, radius*2);
		if (selected) {
			g.setColor(Color.BLUE);
			g.drawRect(center.getX() - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() - radius - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() + radius - 2, center.getY() - 2, 4, 4);
			g.drawRect(center.getX() - 2, center.getY() - radius - 2, 4, 4);
			g.drawRect(center.getX() - 2, center.getY() + radius - 2, 4, 4);
		}
		
	}
	
	
	@Override
	public void moveTo(int x, int y) {
		center.moveTo(x, y);
		
	}

	@Override
	public void moveBy(int byX, int byY) {
		center.moveBy(byX, byY);
		
	}
	
	@Override
	public int compareTo(Object o) {

		if(o instanceof Circle) {
			return (int)(this.area()-((Circle)o).area());
		}
		return 0;
	}

	public Point getCenter() {
		return center;
	}
	
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius)  throws Exception{
		if(radius<0) {
			throw new Exception("vrednost poluprecnika mora biti veca od 0");
		}
		this.radius = radius;
	}
		
	public String toString(){
		return "Center: "+center+" radius= "+ radius + " borderColor= " + color.getRGB() + " innerColor= " + innerColor.getRGB() ;
	}

}

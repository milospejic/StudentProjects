package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Shape2D{

	private Point upperLeftPoint;
	private int width;
	private int height;
	
	public Rectangle() {
		
	}
	public Rectangle(Point upperLeftPoint, int width,int height) {
		this.upperLeftPoint=upperLeftPoint;
		this.width=width;
		this.height=height;
	}
	public Rectangle(Point upperLeftPoint, int width,int height,Color color) {
		this(upperLeftPoint, width, height);
		this.setColor(color);
	}
	public Rectangle(Point upperLeftPoint, int width,int height,Color color, Color innerColor) {
		this(upperLeftPoint, width, height,color);
		setInnerColor(innerColor);
	}
	public Rectangle(Point upperLeftPoint, int width,int height, boolean selected) {
		this(upperLeftPoint,width,height);
		setSelected(selected);
	}
	
	public Rectangle(Point upperLeftPoint, int width,int height,boolean selected,Color color) {
		this(upperLeftPoint, width, height, selected);
		this.setColor(color);
	}
	
	public Rectangle(Point upperLeftPoint, int width,int height,boolean selected,Color color, Color innerColor) {
		this(upperLeftPoint, width, height, selected, color);
		setInnerColor(innerColor);
	}
	
	public int area() {
		return this.height * this.width;
	}

	public boolean equals(Object obj) {
		if(obj instanceof Rectangle) {
			
			Rectangle pomocna = (Rectangle) obj;
			if(this.upperLeftPoint.equals(pomocna.upperLeftPoint) && this.width==pomocna.width && this.height==pomocna.height) {
				return true;
			}
			return false;
		}else
			return false;
	}
	
	public boolean contains(int x, int y) {
		if(x>=upperLeftPoint.getX() && x<=upperLeftPoint.getX()+width
				&& y>=upperLeftPoint.getY() && y<=upperLeftPoint.getY()+height)
			return true;
		return false;
	}

	public boolean contains(Point p) {
		if(p.getX()>=upperLeftPoint.getX() && p.getX()<=upperLeftPoint.getX()+width
				&& p.getY()>=upperLeftPoint.getY() && p.getY()<=upperLeftPoint.getY()+height)
			return true;
		return false;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(upperLeftPoint.getX(), upperLeftPoint.getY(), width, height);	
		fill(g);
		if (selected) {
			g.setColor(Color.blue);
			g.drawRect(upperLeftPoint.getX() - 2, upperLeftPoint.getY() - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() + width - 2, upperLeftPoint.getY() - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() - 2, upperLeftPoint.getY() + height - 2, 4, 4);
			g.drawRect(upperLeftPoint.getX() + width  - 2, upperLeftPoint.getY() + height - 2, 4, 4);
		}
	}
	
	
	@Override
	public void fill(Graphics g) {
		g.setColor(innerColor);
		g.fillRect(upperLeftPoint.getX()+1, upperLeftPoint.getY()+1, width - 1, height - 1);
		
	}
	@Override
	public void moveTo(int x, int y) {
		upperLeftPoint.moveTo(x, y);
		
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		upperLeftPoint.moveBy(byX, byY);
		
	}
	
	@Override
	public int compareTo(Object o) {

		if(o instanceof Rectangle) {
			return this.area()-((Rectangle)o).area();
		}
		return 0;
	}
	public Point getUpperLeftPoint() {
		return upperLeftPoint;
	}

	public void setUpperLeftPoint(Point upperLeftPoint) {
		this.upperLeftPoint = upperLeftPoint;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public String toString() {
		return "UpperLeftPoint: "+upperLeftPoint+" width= "+width+" height= "+ height  + " borderColor= " + color.getRGB() + " innerColor= " + innerColor.getRGB() ; 
	}
	
	
}

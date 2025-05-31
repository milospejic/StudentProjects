package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Point;
import geometry.Shape;
import geometry.Shape2D;
import hexagon.Hexagon;

public class HexagonAdapter extends Shape2D{

	
	private Hexagon hexagon;
	
	
	public HexagonAdapter() {

	}

	public HexagonAdapter(Point p) {
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());
	}

	public HexagonAdapter(Point p, int r) {
		hexagon.setX(p.getX());
		hexagon.setY(p.getY());
		hexagon.setR(r);

	}

	public HexagonAdapter(Point p, int r, boolean sel, Color borderColor, Color innerColor) {
		this.hexagon = new Hexagon(p.getX(), p.getY(), r);
		this.hexagon.setBorderColor(borderColor);
		this.hexagon.setAreaColor(innerColor);
		this.hexagon.setSelected(sel);
	}

	public HexagonAdapter(Point p, int r, Color borderColor, Color innerColor) {

		this.hexagon = new Hexagon(p.getX(), p.getY(), r);
		this.hexagon.setBorderColor(borderColor);
		this.hexagon.setAreaColor(innerColor);

	}

	@Override
	public void moveTo(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Hexagon) {
			return (this.hexagon.getR() - ((Hexagon) o).getR());
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
		//fill(g);
		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.hexagon.getX() - 2, this.hexagon.getY() - 2, 4, 4);
			g.drawRect(this.hexagon.getX() - this.hexagon.getR() - 2, this.hexagon.getY() - 2, 4, 4);
			g.drawRect(this.hexagon.getX() + this.hexagon.getR() - 2, this.hexagon.getY() - 2, 4, 4);
			g.drawRect(this.hexagon.getX() - 2, this.hexagon.getY() - this.hexagon.getR() - 2, 4, 4);
			g.drawRect(this.hexagon.getX() - 2, this.hexagon.getY() + this.hexagon.getR() - 2, 4, 4);
		}
		
	}

	public Hexagon getHexagon() {
		return hexagon;
	}

	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(this.hexagon.getAreaColor());
	}
	
	public String toString(){
		return "Center: "+(new Point(hexagon.getX(),hexagon.getY()))+" radius= "+hexagon.getR()  + " borderColor= " + hexagon.getBorderColor().getRGB() + " innerColor= " +hexagon.getAreaColor().getRGB() ;
	}
	
}

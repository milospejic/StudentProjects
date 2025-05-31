package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.DeselectShapeCmd;
import command.DeselectAllShapesCmd;
import command.UpdateShapeCmd;
import dialogs.CircleDialog;
import dialogs.DonutDialog;
import dialogs.HexagonDialog;
import dialogs.LineDialog;
import dialogs.PointDialog;
import dialogs.RectangleDialog;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.SelectedShapes;
import observer.SelectedShapesObserver;
import strategy.SaveDrawing;
import strategy.SaveLog;
import strategy.SavingManager;

public class DrawingController {

	private DrawingModel model;
	private DrawingFrame frame;
	private PointDialog pointDialog;
	private LineDialog lineDialog;
	private RectangleDialog rectangleDialog;
	private CircleDialog circleDialog;
	private DonutDialog donutDialog;
	private HexagonDialog hexagonDialog;
	
	private boolean operationDraw;
	private boolean operationSelect;
	private Point startPoint;
	private int dotCounter;
	private int logHelper;

	private AddShapeCmd addShapeCmd;
	private RemoveShapeCmd removeShapeCmd;
	private UpdateShapeCmd updateShapeCmd;
	private SelectShapeCmd selectShapeCmd;
	private DeselectShapeCmd  deselectShapeCmd;	
	private DeselectAllShapesCmd  deselectAllShapesCmd;
	private ToFrontCmd toFrontCmd;
	private ToBackCmd toBackCmd;
	private BringToFrontCmd bringToFrontCmd;
	private BringToBackCmd bringToBackCmd;

	private String multiple;
	
	private SavingManager savingManager;
	private SaveDrawing saveDrawing;
	private SaveLog saveLog;
	
	private ArrayList <String> arrayList;
	private ArrayList<String> loadedLog;
	private Shape selectedShape;
	
	private ArrayList<Command> commandList;
	private ArrayList<Command> redoCommandList;
	private Color borderColor;
	private Color innerColor;
	
	private SelectedShapesObserver selectedShapesObserver;
	private  SelectedShapes selectedShapes;
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		selectedShapes= new SelectedShapes();
		selectedShapesObserver= new SelectedShapesObserver(frame,model);
		selectedShapes.addObserver(selectedShapesObserver);
		commandList = new ArrayList<Command>();
		redoCommandList = new ArrayList<Command>();
		arrayList=new ArrayList<String>();
	}

	public void mouseClicked(MouseEvent e) {
		stopLoadedLog();
		Point mouseClick = new Point(e.getX(), e.getY(), Color.BLACK);
		if(operationDraw != false) {	
			if(frame.getTglbtnPoint().isSelected()) {
				pointDialog = new PointDialog();
				pointDialog.setController(this);
				pointDialog.setPoint(mouseClick);
				if(borderColor != null)
				pointDialog.setColor(borderColor);
				pointDialog.setVisible(true);
				
				if(pointDialog.isOk) {
					addShapeCmd= new AddShapeCmd(model,pointDialog.getPoint());
					addShapeCmd.execute();
					commandList.add(addShapeCmd);
					
					
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					redoCommandList.clear();
					log(pointDialog.getPoint(), "Draw");

					
				}
				
			}else if(frame.getTglbtnLine().isSelected()){
				
				if(dotCounter==0) {
					lineDialog = new LineDialog();
					lineDialog.setController(this);
					startPoint = mouseClick;
					dotCounter=1;
				}else {
					lineDialog.setLine(new Line(startPoint,mouseClick));
					lineDialog.setColor(borderColor);
					lineDialog.setVisible(true);
					dotCounter=0;
					if(lineDialog.isOk) {
						addShapeCmd= new AddShapeCmd(model,lineDialog.getLine());
						addShapeCmd.execute();
						commandList.add(addShapeCmd);
						
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
						redoCommandList.clear();
						log(lineDialog.getLine(), "Draw");
					}
				}
									
			}else if(frame.getTglbtnCircle().isSelected()){
				circleDialog = new CircleDialog();
				circleDialog.setController(this);
				circleDialog.setPoint(mouseClick);
				circleDialog.setBorderColor(borderColor);
				circleDialog.setInnerColor(innerColor);
				circleDialog.setVisible(true);
				
				if(circleDialog.isOk) {
					addShapeCmd= new AddShapeCmd(model,circleDialog.getCircle());
					addShapeCmd.execute();
					commandList.add(addShapeCmd);
					
					
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					redoCommandList.clear();
					log(circleDialog.getCircle(), "Draw");
				}	
			}else if(frame.getTglbtnRectangle().isSelected()){
				rectangleDialog = new RectangleDialog();
				rectangleDialog.setController(this);
				rectangleDialog.setPoint(mouseClick);
				rectangleDialog.setBorderColor(borderColor);
				rectangleDialog.setInnerColor(innerColor);
				rectangleDialog.setVisible(true);
				
				if(rectangleDialog.isOk) {
					addShapeCmd= new AddShapeCmd(model,rectangleDialog.getRectangle());
					addShapeCmd.execute();
					commandList.add(addShapeCmd);
					
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					redoCommandList.clear();
					log(rectangleDialog.getRectangle(), "Draw");
					
				}
			}else if(frame.getTglbtnDonut().isSelected()){
				donutDialog = new DonutDialog();
				donutDialog.setController(this);
				donutDialog.setPoint(mouseClick);
				donutDialog.setBorderColor(borderColor);
				donutDialog.setInnerColor(innerColor);
				donutDialog.setVisible(true);
				if(donutDialog.isOk) {
					addShapeCmd= new AddShapeCmd(model,donutDialog.getDonut());
					addShapeCmd.execute();
					
					commandList.add(addShapeCmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					redoCommandList.clear();
					log(donutDialog.getDonut(), "Draw");
				}
				
			}else if(frame.getTglbtnHexagon().isSelected()){
				hexagonDialog = new HexagonDialog();
				hexagonDialog.setController(this);
				hexagonDialog.setPoint(mouseClick);
				hexagonDialog.setBorderColor(borderColor);
				hexagonDialog.setInnerColor(innerColor);
				hexagonDialog.setVisible(true);
				if(hexagonDialog.isOk) {
					addShapeCmd= new AddShapeCmd(model,hexagonDialog.getHexagonAdapter());
					addShapeCmd.execute();
					commandList.add(addShapeCmd);

					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
					redoCommandList.clear();
					log(hexagonDialog.getHexagonAdapter(), "Draw");
				}
				
			}
			if(dotCounter==0) {
				operationDraw=false;
				clickOnDraw();
			}
			
			if(model.size() > 0) {
				frame.getBtnSelect().setEnabled(true);
			}
			
			
		}
		
		
		if(operationSelect !=false){
			for (int i = model.size()-1; i >= 0; i--) {
				if (model.getShape(i).contains(mouseClick.getX(), mouseClick.getY()) && model.getShape(i).isSelected() == true  ) {
					deselectShapeCmd = new DeselectShapeCmd(model.getShape(i));
					deselectShapeCmd.execute();
					commandList.add(deselectShapeCmd);
					log(model.getShape(i),"Deselect");										
					break;
				}
				if (model.getShape(i).contains(mouseClick.getX(), mouseClick.getY()) && model.getShape(i).isSelected() == false  ) {
					selectShapeCmd = new SelectShapeCmd(model.getShape(i));
					selectShapeCmd.execute();
					commandList.add(selectShapeCmd);
					log(model.getShape(i),"Select");
					break;
				}
			}
			if(frame.getView().isOnView(mouseClick) != true && getNumberOfSelected()>0){
		            ArrayList<Shape> selectedShapesList= new ArrayList<Shape>();
		            model.getShapes().forEach(shape -> {
		            	if(shape.isSelected())
		            		selectedShapesList.add(shape);});
		            deselectAllShapesCmd = new DeselectAllShapesCmd(selectedShapesList);
		            deselectAllShapesCmd.execute();
		            commandList.add(deselectAllShapesCmd);
		            
		            arrayList.add("Deselect: ALL");
		            frame.getList().setModel(sort());
					
			}
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
		}
		selectedShapes.setNumberOfSelected(getNumberOfSelected());
		frame.repaint();
		
	}
	
	// CLICK ON OPTIONS METHODS
	public void clickOnDraw() {
		
		frame.getLblMode().setText("Drawing Mode");
		operationDraw=true;
		operationSelect= false;
	    frame.getTglbtnPoint().setEnabled(true);
	    frame.getTglbtnLine().setEnabled(true);
	    frame.getTglbtnCircle().setEnabled(true);
	    frame.getTglbtnRectangle().setEnabled(true);
	    frame.getTglbtnDonut().setEnabled(true);
	    frame.getTglbtnHexagon().setEnabled(true);
	}
	
	public void clickOnSelect() {
		
		
		frame.getLblMode().setText("Select Mode");
		operationDraw=false;
		operationSelect= true;
	    frame.getTglbtnPoint().setEnabled(false);
	    frame.getTglbtnLine().setEnabled(false);
	    frame.getTglbtnCircle().setEnabled(false);
	    frame.getTglbtnRectangle().setEnabled(false);
	    frame.getTglbtnDonut().setEnabled(false);
	    frame.getTglbtnHexagon().setEnabled(false);
	}
	
	public void clickOnUndo() {
		stopLoadedLog();
		undo();
	}
	
	public void clickOnRedo() {
		stopLoadedLog();
		redo();
		
	}
	
	public void clickOnToFront() {
		stopLoadedLog();
		toFront();
		redoCommandList.clear();
	}
	
	
	public void clickOnToBack() {
		stopLoadedLog();
		toBack();
		redoCommandList.clear();
	}
	
	public void clickOnBringToFront() {
		stopLoadedLog();
		bringToFront();
		redoCommandList.clear();
	}
	
	
	public void clickOnBringToBack() {
		stopLoadedLog();
		bringToBack();
		redoCommandList.clear();
	}
	
	
	public void modify() {
		stopLoadedLog();
		
		
		Shape shape =  model.getShape(getSelected());
		
		if (shape instanceof Point) {
			pointDialog = new PointDialog();
			pointDialog.setController(this);
			pointDialog.setPoint((Point)shape);
			pointDialog.setVisible(true);
			
			if(pointDialog.isOk) {
				log(pointDialog.getPoint(),"Modify");
				updateShapeCmd = new UpdateShapeCmd(shape,pointDialog.getPoint());
				updateShapeCmd.execute();
				commandList.add(updateShapeCmd);
				redoCommandList.clear();
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		} else if (shape instanceof Line) {
			lineDialog = new LineDialog();
			lineDialog.setController(this);
			lineDialog.setLine(((Line)shape));
			lineDialog.setVisible(true);
			
			if(lineDialog.isOk) {
				log(lineDialog.getLine(),"Modify");
				updateShapeCmd = new UpdateShapeCmd(shape,lineDialog.getLine());
				updateShapeCmd.execute();
				commandList.add(updateShapeCmd);
				
				redoCommandList.clear();
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		} else if (shape instanceof Rectangle) {
			rectangleDialog= new RectangleDialog();
			rectangleDialog.setController(this);
			rectangleDialog.setRectangle((Rectangle)shape);
			rectangleDialog.setVisible(true);
			
			if(rectangleDialog.isOk) {
				log(rectangleDialog.getRectangle(),"Modify");
				updateShapeCmd = new UpdateShapeCmd(shape,rectangleDialog.getRectangle());
				updateShapeCmd.execute();
				commandList.add(updateShapeCmd);
				
				redoCommandList.clear();
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		
		}else if (shape instanceof Donut) {
				donutDialog= new DonutDialog();
				donutDialog.setController(this);
				donutDialog.setDonut((Donut)shape);
				donutDialog.setVisible(true);
				
				if(donutDialog.isOk) {
					log(donutDialog.getDonut(),"Modify");
					updateShapeCmd = new UpdateShapeCmd(shape,donutDialog.getDonut());
					updateShapeCmd.execute();
					commandList.add(updateShapeCmd);
					
					redoCommandList.clear();
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
				}
		} else if (shape instanceof Circle) {
			circleDialog= new CircleDialog();
			circleDialog.setController(this);
			circleDialog.setCircle((Circle)shape);
			circleDialog.setVisible(true);
			
			if(circleDialog.isOk) {

				log(circleDialog.getCircle(),"Modify");
				updateShapeCmd = new UpdateShapeCmd(shape,circleDialog.getCircle());
				updateShapeCmd.execute();
				commandList.add(updateShapeCmd);
				
				redoCommandList.clear();
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		}else if (shape instanceof HexagonAdapter) {
			hexagonDialog = new HexagonDialog();
			hexagonDialog.setController(this);
			hexagonDialog.setHexagonAdapter((HexagonAdapter)shape);
			hexagonDialog.setVisible(true);
			
			if(hexagonDialog.isOk) {
				log(hexagonDialog.getHexagonAdapter(),"Modify");
				updateShapeCmd = new UpdateShapeCmd(shape,hexagonDialog.getHexagonAdapter());
				updateShapeCmd.execute();
				commandList.add(updateShapeCmd);
				
				redoCommandList.clear();
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		}
		frame.repaint();
		
	}
	
	public void delete() {
		stopLoadedLog();
		if (JOptionPane.showConfirmDialog(null, "Do you really want to delete selected shape?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			
			ArrayList<Shape> selectedShapesList= new ArrayList<Shape>();
            model.getShapes().forEach(shape -> {
            	if(shape.isSelected())
            		selectedShapesList.add(shape);});
            selectedShapesList.forEach(shape -> {
					removeShapeCmd = new RemoveShapeCmd(model,shape);
					removeShapeCmd.execute();
					commandList.add(removeShapeCmd);
				});
            logDeleteMultiple(selectedShapesList);
			selectedShapes.setNumberOfSelected(getNumberOfSelected());
			if(model.isEmpty()) {
				clickOnDraw();
				frame.getBtnSelect().setEnabled(false);
			}
			
			
			frame.repaint();
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			redoCommandList.clear();
		}
	}
	
	
	
	
	// DIALOG METHODS
	
	public void pointOk() {
		try {
			int X = Integer.parseInt(pointDialog.getTxtX().getText());
			int Y = Integer.parseInt(pointDialog.getTxtY().getText());
		
			if(X < 0|| Y < 0 ) {
				JOptionPane.showMessageDialog(null, "You entered wrong value!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}

			pointDialog.setPoint(new Point(X,Y,false,pointDialog.getColor()));
			pointDialog.isOk = true;
			
			
	}
	catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "You entered wrong data type!", "Error!", JOptionPane.ERROR_MESSAGE);

		}
		
		pointDialog.setVisible(false);
	}
	
	
	public void lineOk() {
		if(lineDialog != null) {
		try {
			
			int X1 = Integer.parseInt(lineDialog.getTxtX1().getText());			
			int Y1 = Integer.parseInt(lineDialog.getTxtY1().getText());
			int X2 = Integer.parseInt(lineDialog.getTxtX2().getText());
			int Y2 = Integer.parseInt(lineDialog.getTxtY2().getText());
			if(X1 < 0|| Y1 < 0 || X2 < 0 || Y2 < 0) {
				JOptionPane.showMessageDialog(null, "You entered wrong value!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (lineDialog.getColor() == null) 
				lineDialog.setColor(Color.BLACK);
			
			lineDialog.setLine( new Line(new Point(X1, Y1), new Point(X2,Y2),lineDialog.getColor()));
			lineDialog.isOk = true;
		}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "You entered wrong data type!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		lineDialog.setVisible(false);

	
	}
	
	
	public void rectangleOk() {
		try {
			int X = Integer.parseInt(rectangleDialog.getTxtX().getText());
			int Y = Integer.parseInt(rectangleDialog.getTxtY().getText());
			int height = Integer.parseInt(rectangleDialog.getTxtHeight().getText());
			int width= Integer.parseInt(rectangleDialog.getTxtWidth().getText());
		
			if(X < 0|| Y < 0 || height < 1 || width < 1) {
				JOptionPane.showMessageDialog(null, "You entered wrong value!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (rectangleDialog.getBorderColor() == null) 
				rectangleDialog.setBorderColor(Color.BLACK);	
			if (rectangleDialog.getInnerColor() == null) 
				rectangleDialog.setInnerColor(Color.WHITE);
			rectangleDialog.setWidthDefault(rectangleDialog.getTxtWidth().getText());
			rectangleDialog.setHeightDefault( rectangleDialog.getTxtHeight().getText());
			rectangleDialog.setRectangle(new Rectangle(new Point(X, Y), width, height, rectangleDialog.getBorderColor(), rectangleDialog.getInnerColor()));
			rectangleDialog.isOk = true;
			
		}catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "You entered wrong data type!", "Error!", JOptionPane.ERROR_MESSAGE);
	
			}
		
		rectangleDialog.setVisible(false);
	}
	
	public void circleOk() {
		
		try {
			
			int X = Integer.parseInt(circleDialog.getTxtX().getText());
			
			int Y = Integer.parseInt(circleDialog.getTxtY().getText());
			
			int radius = Integer.parseInt(circleDialog.getTxtRadius().getText());
			
			if(X < 0|| Y < 0  || radius < 1) {
				JOptionPane.showMessageDialog(null, "You entered wrong value!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (circleDialog.getBorderColor() == null) 
				circleDialog.setBorderColor(Color.BLACK);	
			if (circleDialog.getInnerColor() == null) 
				circleDialog.setInnerColor(Color.WHITE);
			
			circleDialog.setRadiusDefault(circleDialog.getTxtRadius().getText());
			

			circleDialog.setCircle(new Circle(new Point(X, Y),radius, circleDialog.getBorderColor(), circleDialog.getInnerColor()));
			

			circleDialog.isOk = true;
			


	}	
	catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "You entered wrong data type!", "Error!", JOptionPane.ERROR_MESSAGE);

		}
		
		circleDialog.setVisible(false);
	}
	public void donutOk() {
		try {
			int X = Integer.parseInt(donutDialog.getTxtX().getText());
			int Y = Integer.parseInt(donutDialog.getTxtY().getText());
			int innerRadius = Integer.parseInt(donutDialog.getTxtInnerRadius().getText());
			int radius = Integer.parseInt(donutDialog.getTxtRadius().getText());
			
			if(X < 0|| Y < 0 || innerRadius < 1 || radius < 1) {
				JOptionPane.showMessageDialog(null, "You entered wrong value!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(innerRadius > radius ) {
				JOptionPane.showMessageDialog(null, "Inner radius can not have a higher value than radius", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (donutDialog.getBorderColor() == null) 
				donutDialog.setBorderColor(Color.BLACK);	
			if (donutDialog.getInnerColor() == null) 
				donutDialog.setInnerColor(Color.WHITE);
			donutDialog.setRadiusDefault(donutDialog.getTxtRadius().getText());
			donutDialog.setInnerRadiusDefault(donutDialog.getTxtInnerRadius().getText());
			donutDialog.setDonut(new Donut(new Point(X, Y),radius,innerRadius, donutDialog.getBorderColor(), donutDialog.getInnerColor()));
			donutDialog.isOk = true;
			
	}
	catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "You entered wrong data type!", "Error!", JOptionPane.ERROR_MESSAGE);

		}
		
		donutDialog.setVisible(false);
	}
	
	public void hexagonOk() {
		try {
			
			int X = Integer.parseInt(hexagonDialog.getTxtX().getText());
			
			int Y = Integer.parseInt(hexagonDialog.getTxtY().getText());
			int radius = Integer.parseInt(hexagonDialog.getTxtRadius().getText());
			
			if(X < 0|| Y < 0  || radius < 1) {
				JOptionPane.showMessageDialog(null, "You entered wrong value!", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (hexagonDialog.getBorderColor() == null) 
				hexagonDialog.setBorderColor(Color.BLACK);	
			if (hexagonDialog.getInnerColor() == null) 
				hexagonDialog.setInnerColor(Color.WHITE);
			hexagonDialog.setRadiusDefault(hexagonDialog.getTxtRadius().getText());
			hexagonDialog.setHexagonAdapter(new HexagonAdapter(new Point(X,Y),radius,hexagonDialog.getBorderColor(),hexagonDialog.getInnerColor()));
			hexagonDialog.isOk = true;
			
	}
	catch(Exception ex) {
			JOptionPane.showMessageDialog(null, "You entered wrong data type!", "Error!", JOptionPane.ERROR_MESSAGE);

		}
		
		hexagonDialog.setVisible(false);
	}
	
	public void undo(){
		
		if(!(commandList.get(commandList.size()-1) instanceof RemoveShapeCmd)){
		
			commandList.get(commandList.size()-1).unexecute();
			redoCommandList.add(commandList.get(commandList.size()-1));
			commandList.remove(commandList.size()-1);
		}else {
			
			for(int i=0;i<commandList.size();i++) {	
				
				if(commandList.get(commandList.size()-1) instanceof RemoveShapeCmd) {
					commandList.get(commandList.size()-1).unexecute();
					redoCommandList.add(commandList.get(commandList.size()-1));
					commandList.remove(commandList.size()-1); 
					
				}else {
					break;
				}
				
			}
			if(!model.isEmpty())
			clickOnSelect();
			else
			clickOnDraw();
			
			
		}
		selectedShapes.setNumberOfSelected(getNumberOfSelected());
		if(!model.isEmpty()) {
			frame.getBtnSelect().setEnabled(true);
		}else {
			frame.getBtnSelect().setEnabled(false);
		}
		if(commandList.isEmpty()) {
			frame.getBtnUndo().setEnabled(false);
		}
			frame.repaint();
			arrayList.add("Undo");
			frame.getList().setModel(sort());
			frame.getBtnRedo().setEnabled(true);
			
	}
		
	public void redo(){
		if(!(redoCommandList.get(redoCommandList.size()-1) instanceof RemoveShapeCmd)){
			if(redoCommandList.get(redoCommandList.size()-1) instanceof SelectShapeCmd) {
				clickOnSelect();
			}
			
			redoCommandList.get(redoCommandList.size()-1).execute();
			commandList.add(redoCommandList.get(redoCommandList.size()-1));
			redoCommandList.remove(redoCommandList.size()-1);
		}else {
			for(int i=redoCommandList.size()-1;i>=0;i--) {	
				if(redoCommandList.get(i) instanceof RemoveShapeCmd) {
					redoCommandList.get(redoCommandList.size()-1).execute();
					commandList.add(redoCommandList.get(redoCommandList.size()-1));
					redoCommandList.remove(redoCommandList.size()-1);
				}else {
					break;
				}
				
			}
			if(!model.isEmpty()) 
				clickOnSelect();
				else
				clickOnDraw();
		}
		selectedShapes.setNumberOfSelected(getNumberOfSelected());	
		if(!model.isEmpty()) {
			frame.getBtnSelect().setEnabled(true);
		}else {
			frame.getBtnSelect().setEnabled(false);
			
		}
			frame.repaint();
			arrayList.add("Redo");
			
			frame.getBtnUndo().setEnabled(true);
			frame.getList().setModel(sort());
			if(redoCommandList.isEmpty())
			frame.getBtnRedo().setEnabled(false);
			if(model.isEmpty()) {
			frame.getBtnSelect().setEnabled(false);
		}
			
		
	}
	
	private void log(Shape shape, String lastOperation) {		
	
			if(shape instanceof Point) {
				arrayList.add(lastOperation + ": Point= " +((Point)shape).toString());			
			}else if (shape instanceof Line) {
				arrayList.add(lastOperation + ": Line= " +((Line)shape).toString());			
	
			}else if(shape instanceof Donut) {
				arrayList.add(lastOperation + ": Donut= " +((Donut)shape).toString());			
	
			}else if(shape instanceof Circle) {
				arrayList.add(lastOperation + ": Circle= " +((Circle)shape).toString());			
	
			}else if(shape instanceof Rectangle) {
				arrayList.add(lastOperation + ": Rectangle= " +((Rectangle)shape).toString());			
	
			}else if(shape instanceof HexagonAdapter) {
				arrayList.add(lastOperation + ": Hexagon= " +((HexagonAdapter)shape).toString());			
			}
		
		frame.getList().setModel(sort());
	}
	
	private void logDeleteMultiple(ArrayList<Shape> selectedShapesList) {
		 multiple ="Delete";
		selectedShapesList.forEach(shape -> {
			if(shape instanceof Point) {
				multiple= multiple + ": Point= " +((Point)shape).toString();			
			}else if (shape instanceof Line) {
				multiple= multiple + ": Line= " +((Line)shape).toString();			
	
			}else if(shape instanceof Donut) {
				multiple= multiple +  ": Donut= " +((Donut)shape).toString();			
	
			}else if(shape instanceof Circle) {
				multiple= multiple + ": Circle= " +((Circle)shape).toString();			
	
			}else if(shape instanceof Rectangle) {
				multiple= multiple + ": Rectangle= " +((Rectangle)shape).toString();			
	
			}else if(shape instanceof HexagonAdapter) {
				multiple= multiple + ": Hexagon= " +((HexagonAdapter)shape).toString();			
			}
		});
		arrayList.add(multiple);
		frame.getList().setModel(sort());
	}
	
	private DefaultListModel<String> sort()
	{
		Iterator<String> iterator = arrayList.iterator();
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		while(iterator.hasNext()) {
			dlm.addElement(iterator.next());
		}	
		
		return dlm;
	}

	public void saveDrawing() {
		stopLoadedLog();
		saveDrawing = new SaveDrawing(model);
		savingManager = new SavingManager(saveDrawing);
		
		JFileChooser jFileChooser= new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int dlg = jFileChooser.showOpenDialog(frame);
		
		if (dlg == JFileChooser.APPROVE_OPTION){
            String address= jFileChooser.getSelectedFile().getPath();
            savingManager.save(address);
		}	else {
           JOptionPane.showMessageDialog(null, "Saving drawing has been canceled!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
	}
		
	public void loadDrawing() {
		stopLoadedLog();
		JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int dlg = jFileChooser.showOpenDialog(frame);
		File file = jFileChooser.getSelectedFile();
		
		if (dlg == JFileChooser.APPROVE_OPTION) {
			
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				model.clear();
				commandList.clear();
				redoCommandList.clear();
				frame.getList().removeAll();
				clickOnDraw();
				ArrayList<Shape> tempList = (ArrayList<Shape>) objectInputStream.readObject();
				tempList.forEach(shape-> shape.setSelected(false));
				tempList.forEach(shape->{
					addShapeCmd= new AddShapeCmd(model, shape);
					addShapeCmd.execute();
					commandList.add(addShapeCmd);
					log(shape,"Draw");
				});
				objectInputStream.close();
				fileInputStream.close();
				if(model.size()>0) {
					frame.getBtnSelect().setEnabled(true);
					frame.getBtnUndo().setEnabled(true);
				}
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "File not found!", "Message", JOptionPane.INFORMATION_MESSAGE);

			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(null, "Something went wrong!", "Message",
						JOptionPane.INFORMATION_MESSAGE);
			}
			
			selectedShapes.setNumberOfSelected(getNumberOfSelected());
			frame.getView().repaint();
			
		} else {
			JOptionPane.showMessageDialog(null, "Loading drawing cancelled!", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void saveLog() {
		stopLoadedLog();
		saveLog = new SaveLog(frame);
		savingManager = new SavingManager(saveLog);		
		JFileChooser jFileChooser= new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int dlg = jFileChooser.showOpenDialog(frame);	
		if (dlg == JFileChooser.APPROVE_OPTION){
           String address= jFileChooser.getSelectedFile().getPath();
            savingManager.save(address);
		}	else {
           JOptionPane.showMessageDialog(null, "Saving log has been canceled!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
	}

	public void loadLog() {
		stopLoadedLog();
		JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int dlg = jFileChooser.showOpenDialog(frame);
		File file = jFileChooser.getSelectedFile();
		if(getNumberOfSelected()>0) {
			deselectAllShapesCmd= new DeselectAllShapesCmd((ArrayList<Shape>)model.getShapes());
			deselectAllShapesCmd.execute();
			arrayList.add("Deselect: ALL");
			frame.getList().setModel(sort());
		}
		if (dlg == JFileChooser.APPROVE_OPTION) {

		
			try {
				loadedLog= new ArrayList<String>();
				loadedLog.addAll(Files.readAllLines(Paths.get(file.getPath())));
				logHelper=0;
				frame.getList().removeAll();;
				frame.getBtnUndo().setEnabled(false);
				frame.getBtnNextStep().setEnabled(true);
				

			}  catch (IOException ioe) {
				JOptionPane.showMessageDialog(null, "Something went wrong!", "Message",
						JOptionPane.INFORMATION_MESSAGE);

			}

			
			frame.getView().repaint();
			
		} else {
			JOptionPane.showMessageDialog(null, "Loading log cancelled!", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
	}

	public void nextStep() {	
		
		String logActivity = loadedLog.get(logHelper);
		String[] array = logActivity.split(" ");		
		String operation = array[0];
		String loggedShape ="";
		if(array.length>1) {
		loggedShape = array[1];
		}
		if(operation.equals("Draw:")) {
			clickOnDraw();
			Shape shape;
			if(loggedShape.equals("Point=")) {
				shape = new Point(Integer.parseInt(array[2]),Integer.parseInt(array[3]),Color.decode(array[5]));
				addShapeCmd = new AddShapeCmd(model, shape);
				
			}else if(loggedShape.equals("Line=")) {
				shape = new Line(new Point(Integer.parseInt(array[2]),Integer.parseInt(array[3])),new Point(Integer.parseInt(array[7]),Integer.parseInt(array[8])), new Color(Integer.parseInt(array[12])));
				addShapeCmd = new AddShapeCmd(model, shape);
			}else if(loggedShape.equals("Rectangle=")) {
				shape = new Rectangle(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]), Integer.parseInt(array[10]),new Color(Integer.parseInt(array[12])),new Color(Integer.parseInt(array[14])));
				addShapeCmd = new AddShapeCmd(model, shape);
			}else if(loggedShape.equals("Donut=")) {
				shape = new Donut(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),Integer.parseInt(array[14]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
				addShapeCmd = new AddShapeCmd(model, shape);
			}else if(loggedShape.equals("Circle=")) {
				Circle circle = new Circle(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
				addShapeCmd = new AddShapeCmd(model, circle);
			}else if(loggedShape.equals("Hexagon=")) {
				shape = new HexagonAdapter(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
				addShapeCmd = new AddShapeCmd(model, shape);
			}
			addShapeCmd.execute();
			commandList.add(addShapeCmd);
			
			frame.getBtnUndo().setEnabled(true);	
		}else if (operation.equals("Delete:")) {
			ArrayList<Shape> selectedShapesList= new ArrayList<Shape>();
            model.getShapes().forEach(shape -> {
            	if(shape.isSelected())
            		selectedShapesList.add(shape);});
            selectedShapesList.forEach(shape -> {
					removeShapeCmd = new RemoveShapeCmd(model,shape);
					removeShapeCmd.execute();
					commandList.add(removeShapeCmd);
					
					
				});
            logDeleteMultiple(selectedShapesList);
			
			if(model.isEmpty()) {
				clickOnDraw();
				frame.getBtnSelect().setEnabled(false);
			}
		
		}else if (operation.equals("Modify:")) {
			if(selectedShape != null) {
				Shape shape;
				if(loggedShape.equals("Point=")) {
					shape = new Point(Integer.parseInt(array[2]),Integer.parseInt(array[3]),Color.decode(array[5]));
					updateShapeCmd = new UpdateShapeCmd(model.getShape(getSelected()),shape);
				}else if(loggedShape.equals("Line=")) {
					shape = new Line(new Point(Integer.parseInt(array[2]),Integer.parseInt(array[3])),new Point(Integer.parseInt(array[7]),Integer.parseInt(array[8])), new Color(Integer.parseInt(array[12])));
					updateShapeCmd = new UpdateShapeCmd(model.getShape(getSelected()),shape);
				}else if(loggedShape.equals("Rectangle=")) {
					shape = new Rectangle(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]), Integer.parseInt(array[10]),new Color(Integer.parseInt(array[12])),new Color(Integer.parseInt(array[14])));
					updateShapeCmd = new UpdateShapeCmd(model.getShape(getSelected()),shape);
				}else if(loggedShape.equals("Donut=")) {
					shape = new Donut(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),Integer.parseInt(array[14]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
					updateShapeCmd = new UpdateShapeCmd(model.getShape(getSelected()),shape);
				}else if(loggedShape.equals("Circle=")) {
					Circle circle = new Circle(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
					updateShapeCmd = new UpdateShapeCmd(model.getShape(getSelected()),circle);
				}else if(loggedShape.equals("Hexagon=")) {
					shape = new HexagonAdapter(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
					updateShapeCmd = new UpdateShapeCmd(model.getShape(getSelected()),shape);
				}
				
				updateShapeCmd.execute();
				commandList.add(updateShapeCmd);
				frame.getBtnUndo().setEnabled(true);	
			}
					
		}else if (operation.equals("Undo")) {
			undo();
			
		}else if (operation.equals("Redo")) {
			redo();
		}else if (operation.equals("ToFront:")) {
			toFront();
			
		}else if (operation.equals("ToBack:")) {
			toBack();
		}else if (operation.equals("BringToFront:")) {
			bringToFront();
			
		}else if (operation.equals("BringToBack:")) {
			bringToBack();
		}else if (operation.equals("Select:")) {
			clickOnSelect();
			if(loggedShape.equals("Point=")) {
				selectedShape = new Point(Integer.parseInt(array[2]),Integer.parseInt(array[3]),Color.decode(array[5]));				
			}else if(loggedShape.equals("Line=")) {
				selectedShape = new Line(new Point(Integer.parseInt(array[2]),Integer.parseInt(array[3])),new Point(Integer.parseInt(array[7]),Integer.parseInt(array[8])), new Color(Integer.parseInt(array[12])));
			}else if(loggedShape.equals("Rectangle=")) {
				selectedShape = new Rectangle(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]), Integer.parseInt(array[10]),new Color(Integer.parseInt(array[12])),new Color(Integer.parseInt(array[14])));
			}else if(loggedShape.equals("Donut=")) {
				selectedShape = new Donut(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),Integer.parseInt(array[14]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
			}else if(loggedShape.equals("Circle=")) {
				
				selectedShape = new Circle(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
			}else if(loggedShape.equals("Hexagon=")) {
				selectedShape = new HexagonAdapter(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
			}
			
			model.getShapes().forEach(shape -> {
				if(shape.toString().equals(selectedShape.toString())) {
					selectShapeCmd = new SelectShapeCmd(shape);
					selectShapeCmd.execute();
					commandList.add(selectShapeCmd);
					selectedShape = shape;
				}
			});;

			frame.getBtnUndo().setEnabled(true);
		
			
		} else if (operation.equals("Deselect:") && selectedShape != null) {
			if(loggedShape.equals("ALL")) {
				 ArrayList<Shape> selectedShapes= new ArrayList<Shape>();
		            model.getShapes().forEach(shape -> {
		            	if(shape.isSelected())
		            		selectedShapes.add(shape);});
		            deselectAllShapesCmd = new DeselectAllShapesCmd(selectedShapes);
		            deselectAllShapesCmd.execute();
		            commandList.add(deselectAllShapesCmd);
		           
			}else {
				if(loggedShape.equals("Point=")) {
					selectedShape = new Point(Integer.parseInt(array[2]),Integer.parseInt(array[3]),Color.decode(array[5]));					
				}else if(loggedShape.equals("Line=")) {
					selectedShape = new Line(new Point(Integer.parseInt(array[2]),Integer.parseInt(array[3])),new Point(Integer.parseInt(array[7]),Integer.parseInt(array[8])), new Color(Integer.parseInt(array[12])));
				}else if(loggedShape.equals("Rectangle=")) {
					selectedShape = new Rectangle(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]), Integer.parseInt(array[10]),new Color(Integer.parseInt(array[12])),new Color(Integer.parseInt(array[14])));
				}else if(loggedShape.equals("Donut=")) {
					selectedShape = new Donut(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),Integer.parseInt(array[14]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
				}else if(loggedShape.equals("Circle=")) {
					selectedShape = new Circle(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
				}else if(loggedShape.equals("Hexagon=")) {
					selectedShape = new HexagonAdapter(new Point (Integer.parseInt(array[3]),Integer.parseInt(array[4])), Integer.parseInt(array[8]),new Color(Integer.parseInt(array[10])),new Color(Integer.parseInt(array[12])));
				}
				
				model.getShapes().forEach(shape -> {
					if(shape.toString().equals(selectedShape.toString())) {
						deselectShapeCmd = new DeselectShapeCmd(shape);
						deselectShapeCmd.execute();
						commandList.add(deselectShapeCmd);
						
					}
				});;
			}
			
		} 
		
		if(model.size() > 0) {
			frame.getBtnSelect().setEnabled(true);
		}
		selectedShapes.setNumberOfSelected(getNumberOfSelected());
		if((operation.equals("Draw:")) || (operation.equals("Modify:")) || (operation.equals("Select:")) || (operation.equals("Deselect:")) )
		arrayList.add(logActivity);
		frame.getList().setModel(sort());
		
		frame.repaint();

		logHelper++;
			if(logHelper == loadedLog.size()) {
				
				frame.getBtnNextStep().setEnabled(false);
			}
			}

	public void chooseBorderColor() {
		borderColor = JColorChooser.showDialog(null, "Choose a color", borderColor);
		frame.getBtnBorderColor().setBackground(borderColor);
		
	}

	public void chooseInnerColor() {
		innerColor = JColorChooser.showDialog(null, "Choose a color", innerColor);
		frame.getBtnInnerColor().setBackground(innerColor);
		
	}

	public void toFront() {
			toFrontCmd = new ToFrontCmd(model, (getSelected()));
			toFrontCmd.execute();
			commandList.add(toFrontCmd);
			log(model.getShape(getSelected()),"ToFront");
			selectedShapes.setNumberOfSelected(getNumberOfSelected());
			frame.repaint();
		
	}

	public void toBack() {
			toBackCmd = new ToBackCmd(model, getSelected());
			toBackCmd.execute();
			commandList.add(toBackCmd);
			log(model.getShape(getSelected()),"ToBack");
			selectedShapes.setNumberOfSelected(getNumberOfSelected());
			frame.repaint();
		
		
	}

	public void bringToFront() {
			bringToFrontCmd = new BringToFrontCmd(model, (getSelected()));
			bringToFrontCmd.execute();
			commandList.add(bringToFrontCmd);
			log(model.getShape(getSelected()),"BringToFront");
			selectedShapes.setNumberOfSelected(getNumberOfSelected());
			frame.repaint();
		
		
	}

	public void bringToBack() {
			bringToBackCmd = new BringToBackCmd(model, (getSelected()));
			bringToBackCmd.execute();
			commandList.add(bringToBackCmd);
			log(model.getShape(getSelected()),"BringToBack");
			selectedShapes.setNumberOfSelected(getNumberOfSelected());
			frame.repaint();
		
	}
	
	public void stopLoadedLog(){
		if(loadedLog != null && frame.getBtnNextStep().isEnabled())
		if(loadedLog.size()>0) {
			if (JOptionPane.showConfirmDialog(null, "You have not finished the steps of the loaded log, by clicking yes won't be able to continue the steps from the log you loaded", "Stop loaded log", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			loadedLog.clear();
			logHelper=0;
			frame.getBtnNextStep().setEnabled(false);		
			}
		}
	}
	
	
	public int getNumberOfSelected() {
		int numberOfSelected= 0;
		for (int i = model.getShapes().size()-1; i >= 0; i--) {
			if (model.getShape(i).isSelected()) {
				numberOfSelected++;
			}
		}	
		
		
		return numberOfSelected;
	}
	public int getSelected() {
		for (int i = model.getShapes().size()-1; i >= 0; i--) {
			if (model.getShapes().get(i).isSelected()) {
				return i;
			}
		}
		return -1;
	}
}
